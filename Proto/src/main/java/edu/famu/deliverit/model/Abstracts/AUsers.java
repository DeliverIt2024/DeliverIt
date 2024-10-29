package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import edu.famu.deliverit.model.Default.Chats;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AUsers {
    //@DocumentId
    private String userId;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String profilePhotoUrl;
    private Timestamp createdAt;




}
