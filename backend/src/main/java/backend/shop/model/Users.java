package backend.shop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> role;

    @Column(updatable = false, nullable = false)
    private Instant accountCreationDate;

    private boolean isActive;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private DeliveryDetails deliveryDetails;

    @PrePersist
    protected void onCreate(){
        accountCreationDate = Instant.now();
        isActive = false;
        if(role.isEmpty() || role == null){
            role = Set.of("USER");
        }
    }

}
