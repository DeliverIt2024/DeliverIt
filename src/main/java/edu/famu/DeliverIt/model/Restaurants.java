package edu.famu.DeliverIt.model;

import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurants {
    @DocumentId
    private @Nullable String restaurantId;
    private String name,email,phoneNumber;
    private GeoPoint address;
    private ArrayList<MenuItem> menu;
    private double rating;
}
