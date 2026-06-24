package backend.shop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class PaymentDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentDetailsId;
    private LocalDate paymentAccomplishedDate;
    private String paymentType;
    private boolean isPaid;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Orders orderId;
}
