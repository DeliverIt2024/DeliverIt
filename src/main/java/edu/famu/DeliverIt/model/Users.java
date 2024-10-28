package edu.famu.DeliverIt.model;

import com.google.cloud.firestore.GeoPoint;
import com.google.firebase.database.annotations.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
@NoArgsConstructor
public class Users extends AUsers{
    private @Nullable ArrayList<Users> friends;
    private @Nullable ArrayList<Order> orders;

    public Users(String userId, String email, String name, String address, String phoneNumber, String role, GeoPoint location, ArrayList<Users> friends, ArrayList<Order> orders) {
        super(userId, email, name, address, phoneNumber, role, location);
        this.friends = friends;
        this.orders = orders;
    }
}
