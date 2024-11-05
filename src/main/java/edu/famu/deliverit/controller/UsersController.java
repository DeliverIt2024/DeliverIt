package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Default.Users;
import edu.famu.deliverit.service.UserService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

//http:localhost:8080/api/user
//http://localhost:8080/api/vendor FOR POSTMAN

@RestController
@RequestMapping("/api/user")
public class UsersController {
    private final UserService service;

    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Users>>> getAllUsers() {
        try{
            List<Users> users = service.getAllUsers();
            if(users != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Users List", users, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No users", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Users>> getUserById(@PathVariable(name="userId") String userId){
        try {
            Users user = service.getUserDetails(userId);

            if(user != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "User", user, null));
            else
                return ResponseEntity.status(20).body(new ApiResponse<>(true, "User not found", null, null));

        } catch (ParseException | ExecutionException | java.text.ParseException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@RequestParam String userId) {
        boolean deleted = service.deleteUser(userId);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Failed to delete user", null, null));
        }
    }
}
