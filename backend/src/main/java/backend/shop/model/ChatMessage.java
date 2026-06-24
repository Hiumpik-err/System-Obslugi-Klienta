package backend.shop.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;
    private String message;
    private LocalDateTime sendDate;
    private Integer sender;
    private Integer recipient;
}
