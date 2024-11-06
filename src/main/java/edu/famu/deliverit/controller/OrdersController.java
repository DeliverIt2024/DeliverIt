package edu.famu.deliverit.controller;

//http:localhost:8080/api/order
//http://localhost:8080/api/order FOR POSTMAN

import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Items;
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
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addOrder( @RequestBody Orders order) {
        try{
            Orders orders = new Orders();
            orders.setOrderId(service.addOrder(order));
            orders.setTotalPrice(orders.getTotalPrice());
            orders.setOrderDate(orders.getOrderDate());
            orders.setUserId(orders.getUserId());
            orders.setVendorId(orders.getUserId());
            orders.setItems(orders.getItems());

            String id = service.addOrder(order);
            return ResponseEntity.status(201).body(new ApiResponse<>(true,"User created",id,null));
        } catch (ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"Internal server error", null,e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false,"Unable to reach firebase", null,e));
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

    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponse<String>> updateOrderInfo(@PathVariable String orderId, @RequestBody Orders updatedOrder) {
        try {
            String updateTime = service.updateOrderInfo(orderId, updatedOrder);
            return ResponseEntity.ok(new ApiResponse<>(true, "Admin info updated", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
}
