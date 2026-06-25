package backend.shop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Builder
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

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<String> role = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant accountCreationDate;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isActive;

    @OneToOne(mappedBy = "userId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private DeliveryDetails deliveryDetails;


}
