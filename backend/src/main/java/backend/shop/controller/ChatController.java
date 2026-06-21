package backend.shop.controller;

import backend.shop.model.ChatMessage;
import backend.shop.service.ChatService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Repeatable;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message){
        if(this.chatService.saveMessage(message)) {
            return message;
        }
        return null;
    }

    @GetMapping("/api/chat/{senderId}/{reciverId}")
    public ResponseEntity<?> getAllDataFrom(@PathVariable int senderId, @PathVariable int reciverId){
        Optional<List<ChatMessage>> data = this.chatService.getMessagesWhere(senderId, reciverId);
        if(data.isPresent()){
            return new ResponseEntity<>(data.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Nie znalezniono", HttpStatusCode.valueOf(404));
    }
}
