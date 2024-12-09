package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Chats;
import edu.famu.deliverit.model.Default.Items;
import edu.famu.deliverit.model.Rest.RestItems;
import edu.famu.deliverit.service.AdminService;
import edu.famu.deliverit.service.ItemsService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemsService service;

    @Autowired
    public ItemController(ItemsService service) {
        this.service = service;
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addItems( @RequestBody Items item) {
        try{
            Items items = new Items();
            items.setItemId(service.addItem(item));
            items.setPrice(items.getPrice());
            items.setName((String)items.getName());
            items.setRating(items.getRating());
            items.setDescription((String)items.getDescription());
            items.setVendorId((String)items.getVendorId());

            String id = service.addItem(item);
            return ResponseEntity.status(201).body(new ApiResponse<>(true,"User created",id,null));
        } catch (ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"Internal server error", null,e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false,"Unable to reach firebase", null,e));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Items>>> getAllItems() {
        try{
            List<Items> item = service.getAllItems();
            if(item != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Admin List", item, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No users", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ApiResponse<Items>> getItemById(@PathVariable String itemId) throws java.text.ParseException {
        try {
            Items item = service.getItemById(itemId);  
            if (item != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Item found", item, null));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Item not found", null, null));
            }
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        } catch (ParseException e) {
            return ResponseEntity.status(400).body(new ApiResponse<>(false, "Failed to parse item", null, e));
        }
    }


    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteItem(@RequestParam String itemId) {
        boolean deleted = service.deleteItem(itemId);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Failed to delete user", null, null));
        }
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ApiResponse<String>> updateItemInfo(@PathVariable String itemId, @RequestBody RestItems updatedItem) {
        try {
            String updateTime = service.updateItemInfo(itemId, updatedItem);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item info updated", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
}
