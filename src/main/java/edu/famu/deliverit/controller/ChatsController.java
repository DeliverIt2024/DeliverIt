package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Chats;
import edu.famu.deliverit.service.ChatsService;
import edu.famu.deliverit.util.ApiResponse;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/chat")
public class ChatsController {
    private final ChatsService service;

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
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addChat( @RequestBody Chats chat) {
        try{
            Chats chats = new Chats();
            chats.setChatId(service.addChat(chat));
            chats.setUserIdOne(chats.getUserIdOne());
            chats.setUserIdTwo(chats.getUserIdTwo());
            chats.setMessages(chats.getMessages());

            String id = service.addChat(chat);
            return ResponseEntity.status(201).body(new ApiResponse<>(true,"User created",id,null));
        } catch (ExecutionException e){
            return ResponseEntity.status(500).body(new ApiResponse<>(false,"Internal server error", null,e));
        } catch (InterruptedException e){
            return ResponseEntity.status(503).body(new ApiResponse<>(false,"Unable to reach firebase", null,e));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteChat(@RequestParam String chatId) {
        boolean deleted = service.deleteChat(chatId);

        if (deleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null, null));
        } else {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Failed to delete user", null, null));
        }
    }
}
