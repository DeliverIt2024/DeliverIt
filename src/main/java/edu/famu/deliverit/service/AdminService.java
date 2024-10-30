package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Admin;
import edu.famu.deliverit.model.LoginRequest;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@Data

public class AdminService {
    private Firestore firestore;

    private static final String ADMIN_COLLECTION = "Admin";

    public AdminService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Admin documentToAdmin(DocumentSnapshot document) throws ParseException {
        Admin admin = null;

        if(document.exists())
        {
            admin = new Admin();
            admin.setAdminId(document.getId());
            admin.setAdminUser(document.getString("adminUser"));
            admin.setEmail(document.getString("email"));
            admin.setPassword(document.getString("password"));
        }
        return admin;
    }

    public boolean login(LoginRequest loginRequest) throws ExecutionException, InterruptedException {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        DocumentSnapshot document = firestore.collection(ADMIN_COLLECTION)
                .whereEqualTo("email", email)
                .get()
                .get()
                .getDocuments()
                .stream()
                .findFirst()
                .orElse(null);

        if (document != null) {
            Admin admin = document.toObject(Admin.class);

            return admin.getPassword().equals(password); // Possible hashing
        }
        return false;
    }

    public List<Admin> getAllAdmin() throws InterruptedException, ExecutionException {
        CollectionReference usersCollection = firestore.collection(ADMIN_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = usersCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Admin> admins = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Admin admin = null;
            try {
                admin = documentToAdmin(document);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            admins.add(admin);
        });

        return admins;
    }

}
