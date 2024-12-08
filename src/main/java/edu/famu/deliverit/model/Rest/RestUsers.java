package edu.famu.deliverit.model.Rest;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Abstracts.AUsers;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class RestUsers extends AUsers {
    private List<DocumentReference> orderHistory;
    private List<DocumentReference> chats;
    private List<DocumentReference> friends;
    private List<DocumentReference> favorites;

    public RestUsers(String userId, String username, String phone, String email, String password, String profilePhotoUrl, String firstName, String lastName, Timestamp createdAt, List<DocumentReference> orderHistory, List<DocumentReference> chats, List<DocumentReference> friends, List<DocumentReference> favorites) {
        super(userId, username, phone, email, password, profilePhotoUrl, firstName, lastName, createdAt);
        this.orderHistory = orderHistory;
        this.chats = chats;
        this.friends = friends;
        this.favorites = favorites;
    }
}
