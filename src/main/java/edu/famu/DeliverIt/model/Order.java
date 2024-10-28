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
public class Order {
    @DocumentId
    private @Nullable String orderId;
    @DocumentId
    private @Nullable String userId;
    private ArrayList<MenuItem> items;
    private GeoPoint address;
    private String phoneNumber;
}
