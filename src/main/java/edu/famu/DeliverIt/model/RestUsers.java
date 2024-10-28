package edu.famu.DeliverIt.model;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class RestUsers extends AUsers{
    private @Nullable ArrayList<DocumentReference> friends;
    private @Nullable ArrayList<DocumentReference> orders;

    public RestUsers(String userId, String email, String name, String address, String phoneNumber, String role, GeoPoint location, ArrayList<DocumentReference> friends, ArrayList<DocumentReference> orders) {
        super(userId, email, name, address, phoneNumber, role, location);
        this.friends = friends;
        this.orders = orders;
    }
}
