package edu.famu.deliverit.controller;

import edu.famu.deliverit.model.Default.Admin;
import edu.famu.deliverit.model.Default.Chats;
import edu.famu.deliverit.model.Rest.RestChats;
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
    @PutMapping("/{chatId}")
    public ResponseEntity<ApiResponse<String>> updateChatInfo(@PathVariable String chatId, @RequestBody RestChats updatedChat) {
        try {
            String updateTime = service.updateChat(chatId, updatedChat);
            return ResponseEntity.ok(new ApiResponse<>(true, "Chat updated successfully", updateTime, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<Chats>>> getUserChats(@PathVariable String userId) {
        try {
            List<Chats> chats = service.userChats(userId);

            if (chats.isEmpty()) {
                return ResponseEntity.status(204).body(new ApiResponse<>(true, "No chats found for user", null, null));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, "User's Chats List", chats, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        } catch (ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Data parsing error", null, e));
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<ApiResponse<Chats>> getChatById(@PathVariable String chatId) throws java.text.ParseException {
        try {
            Chats chat = service.getChatById(chatId);

            if (chat == null) {
                return ResponseEntity.status(404).body(new ApiResponse<>(false, "Chat not found", null, null));
            }

            return ResponseEntity.ok(new ApiResponse<>(true, "Chat retrieved successfully", chat, null));
        } catch (ExecutionException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Internal Server Error", null, e));
        } catch (InterruptedException e) {
            return ResponseEntity.status(503).body(new ApiResponse<>(false, "Unable to reach firebase", null, e));
        } catch (ParseException e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, "Data parsing error", null, e));
        }
    }

}
