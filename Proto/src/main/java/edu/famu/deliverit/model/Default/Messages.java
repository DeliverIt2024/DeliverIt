package edu.famu.deliverit.model.Default;

import com.google.cloud.Timestamp;
import edu.famu.deliverit.model.Abstracts.AMessages;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Messages extends AMessages {
    private String userId;

    public Messages(String text, Timestamp timestamp, String userId) {
        super(text, timestamp);
        this.userId = userId;
    }
}
