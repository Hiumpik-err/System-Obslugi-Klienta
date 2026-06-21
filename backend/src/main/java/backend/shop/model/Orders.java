package backend.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.locks.Condition;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Orders{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer orderId;
    private String products;
    private BigDecimal wholeCost;
    private LocalDate orderDate;

    @OneToOne(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PaymentDetails paymentDetails;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToOne(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DeliveryDetails deliveryDetails;

}
