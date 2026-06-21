package backend.shop.repo;

import backend.shop.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Integer> {
    Optional<Orders> findFirstByOrderByOrderIdDesc();
}
