package edu.famu.DeliverIt.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRestaurants {
    @DocumentId
    private String userId;
    private ArrayList<Restaurants> userRestaurants;
}
