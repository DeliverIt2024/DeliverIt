package edu.famu.deliverit.model.Rest;

import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Abstracts.AVendors;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor

public class RestVendors extends AVendors {
    private ArrayList<DocumentReference> menu;

    public RestVendors(String vendorId, String name, String phone, String email, Map<String, String> address, double averageRating, String imageUrl, ArrayList<DocumentReference> menu) {
        super(vendorId, name, phone, email, address, averageRating, imageUrl);
        this.menu = menu;
    }
}
