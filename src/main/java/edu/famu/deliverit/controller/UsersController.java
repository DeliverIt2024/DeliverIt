package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.LoginRequest;
import edu.famu.deliverit.model.Default.Items;
import edu.famu.deliverit.model.Default.Users;
import edu.famu.deliverit.model.Rest.RestUsers;
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
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "User not found", null, null));

        } catch (ParseException | ExecutionException | java.text.ParseException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addUser( @RequestBody Users user) {
        try{
            Users users = new Users();
            users.setUserId(service.addUser(user));
            users.setFavorites(users.getFavorites());
            users.setChats(users.getChats());
            users.setFriends(users.getFriends());
            users.setEmail(users.getEmail());
            users.setPassword((String)users.getPassword());
            users.setOrderHistory(users.getOrderHistory());
            users.setPhone(users.getPhone());
            users.setCreatedAt(users.getCreatedAt());
            users.setUsername(users.getUsername());
            users.setFirstName(users.getFirstName());
            users.setLastName(users.getLastName());
            String id = service.addUser(user);
            return ResponseEntity.status(201).body(new ApiResponse<>(true,"User created",id,null));
        } catch (ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"Internal server error", null,e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false,"Unable to reach firebase", null,e));
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

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> updateUserProfile(@PathVariable String userId, @RequestBody RestUsers updatedUser) {
        try {
            String updateTime = service.updateUserProfile(userId, updatedUser);
            return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Users>> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Users user = service.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            if (user != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", user, null));
            } else {
                return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid email or password", null, null));
            }
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal server error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach Firebase", null, e));
        } catch (ParseException | java.text.ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Error parsing user data", null, e));
        }
}

}
