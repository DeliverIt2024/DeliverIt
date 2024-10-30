package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AMessages {
    private String text;
    private Timestamp timestamp;
}
