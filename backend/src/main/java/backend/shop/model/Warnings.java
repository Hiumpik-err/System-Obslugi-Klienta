package backend.shop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class Warnings{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer warningId;
    private String issueTopic;

    @Builder.Default
    private String issueStatus = "NOWY";

    @CreationTimestamp
    private Instant recivedDate;

    private String affectedProducts;
    private String description;
    private String expectations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private Orders orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private Users userId;

}
