package edu.famu.deliverit.model.Rest;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Abstracts.AMessages;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RestMessages extends AMessages {
    private DocumentReference userId;

    public RestMessages(String text, Timestamp timestamp, DocumentReference userId) {
        super(text, timestamp);
        this.userId = userId;
    }
}
