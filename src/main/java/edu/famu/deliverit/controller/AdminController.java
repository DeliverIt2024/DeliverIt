package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Admin;
import edu.famu.deliverit.model.LoginRequest;
import edu.famu.deliverit.service.AdminService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService service;

    @Autowired
    public AdminController(AdminService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest) {
        try {
            boolean isAuthenticated = service.login(loginRequest);
            if (isAuthenticated) {
                ApiResponse<String> response = new ApiResponse<>(true, "Login successful", null, null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = new ApiResponse<>(false, "Invalid email or password", null, null);
                return ResponseEntity.status(401).body(response);
            }
        } catch (ExecutionException | ParseException e) {
            ApiResponse<String> response = new ApiResponse<>(false, "Internal Server Error", null, e);
            return ResponseEntity.status(500).body(response);
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Admin>>> getAllAdmin() {
        try{
            List<Admin> admin = service.getAllAdmin();
            if(admin != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Admin List", admin, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No users", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
}
