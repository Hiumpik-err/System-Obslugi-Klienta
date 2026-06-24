package backend.shop.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "email_index", columnList = "email", unique = true),
        }
)
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> role;
    private LocalDate accountCreationDate;
    private boolean isActive = false;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private DeliveryDetails deliveryDetails;

}
