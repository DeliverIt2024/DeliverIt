package edu.famu.deliverit.model.Default;

import com.google.cloud.Timestamp;
import edu.famu.deliverit.model.Abstracts.AUsers;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter

public class Users extends AUsers {
    private List<String> orderHistory;
    private List<String> chats;
    private List<String> friends;
    private List<String> favorites;

    public Users(String userId, String username, String phone, String email, String password, String profilePhotoUrl, Timestamp createdAt, List<String> orderHistory, List<String> chats, List<String> friends, List<String> favorites) {
        super(userId, username, phone, email, password, profilePhotoUrl, createdAt);
        this.orderHistory = orderHistory;
        this.chats = chats;
        this.friends = friends;
        this.favorites = favorites;
    }
}
