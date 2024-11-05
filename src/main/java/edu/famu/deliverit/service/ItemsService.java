package edu.famu.deliverit.service;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Items;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

@Service
@Data
public class ItemsService {
    private Firestore firestore;

    private static final String ITEM_COLLECTION = "Items";

    public ItemsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Items documentToAdmin(DocumentSnapshot document) throws ParseException {
        Items item = null;

        if(document.exists())
        {
            item = new Items();
            item.setItemId(document.getId());
            item.setDescription(document.getString("description"));
            item.setName(document.getString("name"));
            item.setRating(document.getDouble("rating"));
            item.setPrice(document.getDouble("price"));
            item.setVendorId(document.getReference().toString());
        }
        return item;
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
}
