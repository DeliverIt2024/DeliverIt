package edu.famu.deliverit.model.Abstracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import edu.famu.deliverit.util.TimestampDeserializer;
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
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp createdAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
