package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.Timestamp;
import com.google.protobuf.util.Timestamps;
import edu.famu.deliverit.util.TimestampDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AMessages {
    private String text;
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp timestamp;
    public Timestamp getTimestamp(){return timestamp;}

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
