package edu.famu.deliverit.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Chats;
import edu.famu.deliverit.model.Default.Messages;
import edu.famu.deliverit.model.Rest.RestChats;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Data

public class ChatsService {
    private Firestore firestore;

    private static final String CHATS_COLLECTION = "Chats";

    public ChatsService() {
        this.firestore = FirestoreClient.getFirestore();
    }

    private Chats documentToChats(DocumentSnapshot document) throws ParseException, ExecutionException, InterruptedException {
        Chats chat = null;

        if (document.exists()) {
            chat = new Chats();
            chat.setChatId(document.getId());

            chat.setUserIdOne(document.getString("userOne"));
            chat.setUserIdTwo(document.getString("userTwo"));

            List<Map<String, Object>> messagesData = (List<Map<String, Object>>) document.get("messages");
            if (messagesData != null) {
                List<Messages> messagesList = new ArrayList<>();
                for (Map<String, Object> messageData : messagesData) {
                    String textBody = (String) messageData.get("textBody");
                    Timestamp timeSent = (Timestamp) messageData.get("timeSent");

                    String userId = (String) messageData.get("userId");

                    Messages message = new Messages(textBody, timeSent, userId);
                    messagesList.add(message);
                }
                chat.setMessages(messagesList);
            } else {
                chat.setMessages(new ArrayList<>()); // Ensure messages is not null
            }
        }
        return chat;
    }


    public List<Chats> getAllChats() throws InterruptedException, ExecutionException {
        CollectionReference chatsCollection = firestore.collection(CHATS_COLLECTION);
        ApiFuture<QuerySnapshot> querySnapshot = chatsCollection.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Chats> chats = documents.size() == 0 ? null : new ArrayList<>();

        documents.forEach(document -> {
            Chats chat = null;
            try {
                chat = documentToChats(document);
            } catch (ParseException | ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            chats.add(chat);
        });

        return chats;
    }
    public String addChat(Chats chat) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentReference> writeResult =  firestore.collection(CHATS_COLLECTION).add(chat);
        DocumentReference rs =  writeResult.get();
        return rs.getId();
    }
    public boolean deleteChat(String chatId){
        try {
            DocumentReference productRef = firestore.collection(CHATS_COLLECTION).document(chatId);
            productRef.delete().get(); // Delete document and wait for completion
            return true; // Deletion successful
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }

    public String updateChat(String chatId, RestChats updatedChat) throws InterruptedException, ExecutionException {
        DocumentReference adminRef = firestore.collection(CHATS_COLLECTION).document(chatId);

        ApiFuture<WriteResult> writeResult = adminRef.update(
                "userIdOne", updatedChat.getUserIdOne(),
                "userIdTwo", updatedChat.getUserIdTwo(),
                "messages", updatedChat.getMessages(),

                "updatedAt", Timestamp.now()
        );

        return writeResult.get().getUpdateTime().toString();
    }
    public List<Chats> userChats(String userId) throws InterruptedException, ExecutionException, ParseException {
        CollectionReference chatsCollection = firestore.collection(CHATS_COLLECTION);

        // Query to filter documents where userOne or userTwo matches the userId
        ApiFuture<QuerySnapshot> querySnapshot = chatsCollection
                .whereEqualTo("userOne", userId)
                .get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        // Check for matches in "userTwo" as well
        ApiFuture<QuerySnapshot> querySnapshotTwo = chatsCollection
                .whereEqualTo("userTwo", userId)
                .get();

        List<QueryDocumentSnapshot> documentsTwo = querySnapshotTwo.get().getDocuments();

        List<Chats> chats = new ArrayList<>();

        // Process documents from both queries
        for (QueryDocumentSnapshot document : documents) {
            chats.add(documentToChats(document));
        }

        for (QueryDocumentSnapshot document : documentsTwo) {
            // Avoid duplicates if a chat matches both conditions
            if (documents.stream().noneMatch(d -> d.getId().equals(document.getId()))) {
                chats.add(documentToChats(document));
            }
        }

        return chats;
    }
    public Chats getChatById(String chatId) throws ExecutionException, InterruptedException, ParseException {
        DocumentReference chatRef = firestore.collection(CHATS_COLLECTION).document(chatId);
        ApiFuture<DocumentSnapshot> future = chatRef.get();
        DocumentSnapshot document = future.get();
    
        if (document.exists()) {
            return documentToChats(document);
        } else {
            return null; // Chat not found
        }
    }
    
}
