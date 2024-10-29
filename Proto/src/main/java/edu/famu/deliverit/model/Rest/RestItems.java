package edu.famu.deliverit.model.Rest;

import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Abstracts.AItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RestItems extends AItems {
    private DocumentReference vendorId;

    public RestItems(String itemId, String name, double price, String description, double rating, DocumentReference vendorId) {
        super(itemId, name, price, description, rating);
        this.vendorId = vendorId;
    }
}
