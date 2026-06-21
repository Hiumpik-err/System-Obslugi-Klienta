package backend.shop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name ="userId")
    private Users userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "orderId")
    private Orders orderId;


}

