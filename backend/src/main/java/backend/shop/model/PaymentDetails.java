package backend.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;

import java.time.LocalDate;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
