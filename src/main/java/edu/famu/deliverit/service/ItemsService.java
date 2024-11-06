package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Items;
import edu.famu.deliverit.model.Rest.RestItems;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Data
public class ItemsService {
    private Firestore firestore;

    private static final String ITEM_COLLECTION = "Items";

    public ItemsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Items documentToItem(DocumentSnapshot document) throws ParseException {
        Items item = null;

        if(document.exists())
        {
            item = new Items();
            item.setItemId(document.getId());
            item.setDescription(document.getString("description"));
            item.setName(document.getString("name"));
            item.setRating(document.getDouble("rating"));
            item.setPrice(document.getDouble("price"));
            item.setVendorId(document.get("vendorId").toString());
        }
        return item;
    }
    public String addItem(Items item) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentReference> writeResult =  firestore.collection(ITEM_COLLECTION).add(item);
        DocumentReference rs =  writeResult.get();
        return rs.getId();
    }
    public List<Items> getAllItems() throws InterruptedException, ExecutionException {
        CollectionReference itemCollection = firestore.collection(ITEM_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = itemCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Items> items = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Items item = null;
            try {
                item = documentToItem(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            items.add(item);
        });

        return items;
    }

    public boolean deleteItem(String itemId) {
        try {
            DocumentReference productRef = firestore.collection(ITEM_COLLECTION).document(itemId);
            productRef.delete().get(); // Delete document and wait for completion
            return true; // Deletion successful
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }

    public String updateItemInfo(String itemId, RestItems updatedItem) throws InterruptedException, ExecutionException {
        DocumentReference itemRef = firestore.collection(ITEM_COLLECTION).document(itemId);

        ApiFuture<WriteResult> writeResult = itemRef.update(
                "name", updatedItem.getName(),
                "price", updatedItem.getPrice(),
                "description", updatedItem.getDescription(),
                "rating", updatedItem.getRating(),

                "updatedAt", Timestamp.now()
        );

        return writeResult.get().getUpdateTime().toString();
    }
}
