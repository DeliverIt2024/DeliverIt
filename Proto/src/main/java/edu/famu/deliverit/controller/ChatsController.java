package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Default.Chats;
import edu.famu.deliverit.model.Default.Users;
import edu.famu.deliverit.service.ChatsService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chat")
public class ChatsController {
    private ChatsService service;

    public ChatsController(ChatsService service) {
        this.service = service;
    }
    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<Chats>>> getAllChats() {
        try{
            List<Chats> chats = service.getAllChats();
            if(chats != null)
                return ResponseEntity.ok(new ApiResponse<>(true, "Chat List", chats, null));
            else
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No chats", null, null));
        }catch (ParseException | ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
}
