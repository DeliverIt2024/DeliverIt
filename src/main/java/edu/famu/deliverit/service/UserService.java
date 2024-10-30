package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Users;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Data

public class UserService {
    private Firestore firestore;

    private static final String USERS_COLLECTION = "Users";

    private static final String TASKS_COLLECTION = "Tasks";

    public UserService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Users documentToUser(DocumentSnapshot document) throws ParseException {
        Users user = null;

        if(document.exists())
        {
            user = new Users();
            user.setUserId(document.getId());
            user.setUsername(document.getString("username"));
            user.setPhone(document.getString("phone"));
            user.setEmail(document.getString("email"));
            user.setPassword(document.getString("password"));
            user.setProfilePhotoUrl(document.getString("profilePhoto"));
            user.setCreatedAt(document.getTimestamp("createdAt"));
            user.setFriends(null);
            user.setOrderHistory(null);
            user.setChats(null);
            user.setFavorites(null);
            /*
            ArrayList<String> friends = document.get("friends", ArrayList.class);
            user.setFriends(friends);

            // Retrieve order history as a list of order IDs (assuming stored as strings)
            List<String> orderHistory = document.get("orderHistory", List.class);
            user.setOrderHistory(orderHistory);

            // Retrieve chat IDs (assuming stored as strings)
            List<String> chats = document.get("chats", List.class);
            user.setChats(chats);

            // Retrieve friends as a list of user IDs
            List<String> friends = document.get("friends", List.class);
            user.setFriends(friends);

            // Retrieve favorite vendors as a list of vendor IDs
            List<String> favorites = document.get("favorites", List.class);
            user.setFavorites(favorites);
            */

        }
        return user;
    }

    public List<Users> getAllUsers() throws InterruptedException, ExecutionException {
        CollectionReference usersCollection = firestore.collection(USERS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Users> users = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Users user = null;
            try {
                user = documentToUser(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            users.add(user);
        });

        return users;
    }

    public Users getUserDetails(String userId) throws InterruptedException, ExecutionException, ParseException {
        DocumentReference usersRef = firestore.collection(USERS_COLLECTION).document(userId);
        DocumentSnapshot userSnap = usersRef.get().get();
        return documentToUser(userSnap);
    }
}
