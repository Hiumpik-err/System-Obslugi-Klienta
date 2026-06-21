package backend.shop.repo;

import backend.shop.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepo extends JpaRepository<ChatMessage, Integer> {
    @Query("SELECT c FROM ChatMessage c WHERE " +
            "(c.sender = :senderId AND c.recipient = :recipientId) OR " +
            "(c.sender = :recipientId AND c.recipient = :senderId) " +
            "ORDER BY c.sendDate DESC")
    List<ChatMessage> findLastMessages(@Param("senderId") Integer senderId,
                                       @Param("recipientId") Integer recipientId,
                                       Pageable pageable);
}
