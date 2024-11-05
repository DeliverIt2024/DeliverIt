package edu.famu.deliverit.controller;

import edu.famu.deliverit.service.AdminService;
import edu.famu.deliverit.service.ItemsService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemsService service;

    @Autowired
    public ItemController(ItemsService service) {
        this.service = service;
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
}
