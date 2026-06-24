package backend.shop.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
public class Warnings{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer warningId;
    private String issueTopic;
    private String issueStatus;
    private LocalDate recivedDate;
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
