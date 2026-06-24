package backend.shop.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DeliveryDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryDetailsId;
    private String region; //Woj
    private String city;//miasto
    private String street;//ulica
    private String townCode;//kod pocztowy
    private String homeNumber;//nr domu
    private LocalDate deliveredDate;
    private double deliveryCost;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="userId")
    private Users userId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderId")
    private Orders orderId;


}

