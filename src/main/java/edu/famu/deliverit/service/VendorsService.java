package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Vendors;
import edu.famu.deliverit.model.Rest.RestItems;
import edu.famu.deliverit.model.Rest.RestVendors;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Data

public class VendorsService {
    private Firestore firestore;

    private static final String VENDORS_COLLECTION = "Vendors";

    public VendorsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Vendors documentToVendor(DocumentSnapshot document) throws ParseException {
        Vendors vendor = null;

        if(document.exists())
        {
            vendor = new Vendors();
            vendor.setVendorId(document.getId());
            vendor.setName(document.getString("name"));
            vendor.setPhone(document.getString("phone"));
            vendor.setEmail(document.getString("email"));

            Map<String, String> address = (Map<String, String>) document.get("address");
            vendor.setAddress(address != null ? address : new HashMap<>());

            vendor.setAverageRating(document.getDouble("averageRating"));
            vendor.setImageUrl(document.getString("imageUrl"));

            /*
            List<Map<String, Object>> menuData = (List<Map<String, Object>>) document.get("menu");
            if (menuData != null) {
                ArrayList<Items> menu = new ArrayList<>();
                for (Map<String, Object> itemData : menuData) {
                    Items item = new Items();
                    item.setItemId((String) itemData.get("itemId"));
                    item.setName((String) itemData.get("name"));
                    item.setPrice((Double) itemData.get("price"));
                    item.setDescription((String) itemData.get("description"));
                    item.setRating((Double) itemData.get("rating"));
                    item.setVendorId((String) itemData.get("vendorId"));
                    menu.add(item);
                }
                vendor.setMenu(menu);
            } else {
                vendor.setMenu(new ArrayList<>()); // Ensure menu is not null
            }
            */
        }
        return vendor;
    }

    public List<Vendors> getAllVendors() throws InterruptedException, ExecutionException {
        CollectionReference usersCollection = firestore.collection(VENDORS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Vendors> vendors = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Vendors vendor = null;
            try {
                vendor = documentToVendor(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            vendors.add(vendor);
        });

        return vendors;
    }

    public Vendors getVendorDetails(String vendorId) throws InterruptedException, ExecutionException, ParseException {
        DocumentReference vendorsRef = firestore.collection(VENDORS_COLLECTION).document(vendorId);
        DocumentSnapshot vendorSnap = vendorsRef.get().get();
        return documentToVendor(vendorSnap);
    }

    public String updateVendorInfo(String userId, RestVendors updatedVendor) throws InterruptedException, ExecutionException {
        DocumentReference userRef = firestore.collection(VENDORS_COLLECTION).document(userId);

        ApiFuture<WriteResult> writeResult = userRef.update(
                "name", updatedVendor.getName(),
                "phone", updatedVendor.getPhone(),
                "email", updatedVendor.getEmail(),
                "address", updatedVendor.getAddress(),
                "imageUrl", updatedVendor.getImageUrl(),
                "updatedAt", Timestamp.now()
        );

        return writeResult.get().getUpdateTime().toString();
    }

    public String addItemToVendorMenu(String vendorId, RestItems newItem) throws InterruptedException, ExecutionException {
        DocumentReference vendorRef = firestore.collection(VENDORS_COLLECTION).document(vendorId);

        DocumentReference itemRef = firestore.collection("Items").document(); // Auto-generate item ID
        newItem.setItemId(itemRef.getId()); // Set the auto-generated ID in the item
        newItem.setVendorId(vendorRef);

        ApiFuture<WriteResult> itemWriteResult = itemRef.set(newItem);
        itemWriteResult.get(); // Wait for the write to complete

        // Now update the vendor's menu
        ApiFuture<WriteResult> vendorWriteResult = vendorRef.update("menu", FieldValue.arrayUnion(newItem));

        return vendorWriteResult.get().getUpdateTime().toString(); // Return the update time
    }
    public boolean deleteVendor(String vendorId){
        try {
            DocumentReference productRef = firestore.collection(VENDORS_COLLECTION).document(vendorId);
            productRef.delete().get(); // Delete document and wait for completion
            return true; // Deletion successful
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }

}
