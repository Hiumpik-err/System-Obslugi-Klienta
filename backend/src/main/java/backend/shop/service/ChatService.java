package backend.shop.service;

import backend.shop.model.ChatMessage;
import backend.shop.repo.ChatRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepo repo;

    public ChatService(ChatRepo repo) {
        this.repo = repo;
    }

    public boolean saveMessage(ChatMessage message) {
        try{
            this.repo.save(message);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public Optional<List<ChatMessage>> getMessagesWhere(int senderId, int reciverId) {
        try{
            var data = this.repo.findLastMessages(senderId, reciverId, PageRequest.of(0,20));
            return Optional.of(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
