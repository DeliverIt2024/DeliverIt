package edu.famu.deliverit.model.Default;

import com.google.cloud.firestore.annotation.DocumentId;

import edu.famu.deliverit.model.Abstracts.AItems;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor


public class Items extends AItems {
    private String vendorId;

    public Items(String itemId, String name, double price, String description, double rating, String vendorId) {
        super(itemId, name, price, description, rating);
        this.vendorId = vendorId;
    }
}
