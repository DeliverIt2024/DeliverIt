package edu.famu.deliverit.model.Default;

import edu.famu.deliverit.model.Abstracts.AOrders;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class Orders extends AOrders {
    private String vendorId;
    private String userId;
    private List<Items> items;

    public Orders(String vendorId, String userId, List<Items> items) {
        this.vendorId = vendorId;
        this.userId = userId;
        this.items = items;
    }
}
