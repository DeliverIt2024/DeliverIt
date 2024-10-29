package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AVendors {
    @DocumentId
    private String vendorId;
    private String name;
    private String phone;
    private String email;
    private Map<String,String> address;
    private double averageRating;
    private String imageUrl;
}
