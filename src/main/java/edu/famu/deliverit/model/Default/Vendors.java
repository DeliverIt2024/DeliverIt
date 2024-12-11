package edu.famu.deliverit.model.Default;

import edu.famu.deliverit.model.Abstracts.AVendors;
import edu.famu.deliverit.model.Rest.RestItems;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Map;

@Data
@NoArgsConstructor

public class Vendors extends AVendors {
    private ArrayList<RestItems> menu;

    public Vendors(String vendorId, String name, String phone, String email, Map<String, String> address, double averageRating, String imageUrl, ArrayList<RestItems> menu) {
        super(vendorId, name, phone, email, address, averageRating, imageUrl);
        this.menu = menu;
    }
}
