package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
