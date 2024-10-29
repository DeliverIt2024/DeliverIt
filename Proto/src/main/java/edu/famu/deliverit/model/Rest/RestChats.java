package edu.famu.deliverit.model.Rest;

import com.google.cloud.firestore.DocumentReference;
import edu.famu.deliverit.model.Abstracts.AChats;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor

public class RestChats extends AChats {
    private DocumentReference userIdOne, userIdTwo;
    private List<DocumentReference> messages;

    public RestChats(String chatId, DocumentReference userIdOne, DocumentReference userIdTwo) {
        super(chatId);
        this.userIdOne = userIdOne;
        this.userIdTwo = userIdTwo;
        this.messages = new ArrayList<>();
    }
}
