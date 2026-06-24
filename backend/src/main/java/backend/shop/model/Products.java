package backend.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Products{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private int amount;
    private BigDecimal price;
    private String description;
    private String category;
    private String imageUrl;

}
