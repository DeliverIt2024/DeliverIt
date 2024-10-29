package edu.famu.deliverit.model.Default;

import edu.famu.deliverit.model.Abstracts.AChats;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class Chats extends AChats {
    private String userIdOne, userIdTwo;
    private List<Messages> messages;

    public Chats(String chatId, String userIdOne, String userIdTwo, List<Messages> messages) {
        super(chatId);
        this.userIdOne = userIdOne;
        this.userIdTwo = userIdTwo;
        this.messages = messages;
    }
}
