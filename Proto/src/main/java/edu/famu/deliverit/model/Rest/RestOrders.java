package edu.famu.deliverit.model.Rest;

import com.google.cloud.firestore.DocumentReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class RestOrders {
    private DocumentReference vendorId;
    private DocumentReference userId;
    private List<DocumentReference> items;

    public RestOrders(DocumentReference vendorId, DocumentReference userId, List<DocumentReference> items) {
        this.vendorId = vendorId;
        this.userId = userId;
        this.items = items;
    }
}
