package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Items;
import edu.famu.deliverit.model.Default.Orders;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Data

public class OrdersService {
    private Firestore firestore;

    private static final String ORDER_COLLECTION = "Orders";

    public OrdersService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Orders documentToOrder(DocumentSnapshot document) throws ParseException {
        Orders order = null;

        if(document.exists())
        {
            order = new Orders();
            order.setOrderId(document.getId());
            order.setOrderDate(document.getTimestamp("orderDate"));
            order.setTotalPrice(document.getDouble("totalPrice"));


            Object vendorIdField = document.get("vendorId");
            if (vendorIdField instanceof DocumentReference) {
                order.setVendorId(((DocumentReference) vendorIdField).getId());
            } else if (vendorIdField instanceof String) {
                order.setVendorId((String) vendorIdField);
            }

            Object userIdField = document.get("userId");
            if (userIdField instanceof DocumentReference) {
                order.setUserId(((DocumentReference) userIdField).getId());
            } else if (userIdField instanceof String) {
                order.setUserId((String) userIdField);
            }

            List<String> itemIds = (List<String>) document.get("items");
            if (itemIds != null) {
                order.setItems(itemIds);  // Set the list of item IDs to the order
            } else {
                order.setItems(new ArrayList<>());  // Ensure items list is not null
            }
            /*
            List<Map<String, Object>> items = (List<Map<String, Object>>) document.get("items");
            if (items != null) {
                ArrayList<Items> list = new ArrayList<>();
                for (Map<String, Object> itemData : items) {
                    Items item = new Items();
                    item.setItemId((String) itemData.get("itemId"));
                    item.setName((String) itemData.get("name"));
                    item.setPrice((Double) itemData.get("price"));
                    item.setDescription((String) itemData.get("description"));
                    item.setRating((Double) itemData.get("rating"));

                    Object itemVendorId = itemData.get("vendorId");
                    if (itemVendorId instanceof DocumentReference) {
                        item.setVendorId(((DocumentReference) itemVendorId).getId());
                    } else if (itemVendorId instanceof String) {
                        item.setVendorId((String) itemVendorId);
                    }
                    list.add(item);
                }
                order.setItems(list);
            } else {
                order.setItems(new ArrayList<>()); // Ensure menu is not null
            }
             */
        }
        return order;
    }

    public List<Orders> getOrdersByUserId(String userId) throws InterruptedException, ExecutionException {
        CollectionReference ordersCollection = firestore.collection(ORDER_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = ordersCollection.whereEqualTo("userId", userId).get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
    
        List<Orders> orders = documents.size() == 0 ? null : new ArrayList<>();
    
        documents.forEach(document -> {
            Orders order = null;
            try {
                order = documentToOrder(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            orders.add(order);
        });
    
        return orders;
    }

    
    public List<Orders> getAllOrders() throws InterruptedException, ExecutionException {
        CollectionReference ordersCollection = firestore.collection(ORDER_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = ordersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Orders> orders = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Orders order = null;
            try {
                order = documentToOrder(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            orders.add(order);
        });

        return orders;
    }
    public String addOrder(Orders order) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentReference> writeResult =  firestore.collection(ORDER_COLLECTION).add(order);
        DocumentReference rs =  writeResult.get();
        return rs.getId();
    }
    public boolean deleteChat(String orderId){
        try {
            DocumentReference productRef = firestore.collection(ORDER_COLLECTION).document(orderId);
            productRef.delete().get(); // Delete document and wait for completion
            return true; // Deletion successful
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }

    public String updateOrderInfo(String orderId, Orders updatedOrder) throws InterruptedException, ExecutionException {
        DocumentReference orderRef = firestore.collection(ORDER_COLLECTION).document(orderId);

        ApiFuture<WriteResult> writeResult = orderRef.update(
                "orderId", updatedOrder.getOrderId(),
                "vendorId", updatedOrder.getVendorId(),
                "userId", updatedOrder.getUserId(),
                "orderDate", updatedOrder.getOrderDate(),
                "totalPrice", updatedOrder.getTotalPrice(),
                "items", updatedOrder.getItems(),
                "updatedAt", Timestamp.now()
        );

        return writeResult.get().getUpdateTime().toString();
    }
}
