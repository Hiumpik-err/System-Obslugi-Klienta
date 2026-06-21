package backend.shop.repo;

import backend.shop.model.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryDetailsRepo extends JpaRepository<DeliveryDetails, Integer> {
}
