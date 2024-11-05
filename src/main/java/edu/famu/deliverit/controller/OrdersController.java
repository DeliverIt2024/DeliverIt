package edu.famu.deliverit.controller;

//http:localhost:8080/api/order
//http://localhost:8080/api/order FOR POSTMAN

import edu.famu.deliverit.model.Default.Orders;
import edu.famu.deliverit.service.OrdersService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/order")

public class OrdersController {
    private final OrdersService service;

    public OrdersController(OrdersService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Orders>>> getAllOrders() {
        try{
            List<Orders> orders = service.getAllOrders();
            if(orders != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Order List", orders, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No orders", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@RequestParam String orderId) {
        boolean deleted = service.deleteChat(orderId);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Failed to delete user", null, null));
        }
    }
}
