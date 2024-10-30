package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//HIIIII
public class AChats {
    @DocumentId
    private String chatId;
}
