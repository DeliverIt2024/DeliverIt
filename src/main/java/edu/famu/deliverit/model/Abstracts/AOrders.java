package edu.famu.deliverit.model.Abstracts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.cloud.Timestamp;
import com.google.protobuf.util.Timestamps;
import edu.famu.deliverit.util.TimestampDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AOrders {
    private String orderId;
    @JsonDeserialize(using = TimestampDeserializer.class)
    private Timestamp orderDate;
    private double totalPrice;
    public Timestamp getOrderDate(){return orderDate;}

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
}
