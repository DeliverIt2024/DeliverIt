package edu.famu.deliverit.model.Default;

import com.google.cloud.Timestamp;
import edu.famu.deliverit.model.Abstracts.AOrders;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class Orders extends AOrders {
    private String vendorId;
    private String userId;
    private List<String> items;

    public Orders(String orderId, Timestamp orderDate, double totalPrice, String vendorId, String userId, List<String> items) {
        super(orderId, orderDate, totalPrice);
        this.vendorId = vendorId;
        this.userId = userId;
        this.items = items;
    }
}
