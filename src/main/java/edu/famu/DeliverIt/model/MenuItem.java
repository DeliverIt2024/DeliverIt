package edu.famu.DeliverIt.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @DocumentId
    private @Nullable String itemId;
    private Double price;
    private String name;
    private String description;
    private String Category;
}
