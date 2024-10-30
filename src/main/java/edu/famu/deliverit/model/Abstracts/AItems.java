package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AItems {
    @DocumentId
    private String itemId;
    private String name;
    private double price;
    private String description;
    private double rating;
}
