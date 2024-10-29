package edu.famu.deliverit.model.Abstracts;

import com.google.cloud.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AOrders {
    private String orderId;
    private Timestamp orderDate;
    private double totalPrice;
}
