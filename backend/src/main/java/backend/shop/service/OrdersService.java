package backend.shop.service;

import backend.shop.model.Orders;
import backend.shop.repo.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrdersService {
    private final OrdersRepo repo;
    public OrdersService(OrdersRepo repo){
        this.repo = repo;
    }

   /* public Optional<Integer> addNewOrder(Orders order){
        try{
            this.repo.save(order);
            Optional<Orders> orderEntity= this.repo.findFirstByOrderByOrderIdDesc();
            int id = orderEntity.isPresent() ? orderEntity.get().getOrderId() : 0;

            if(id == 0) throw new Exception("Error, id nie moze byc 0");
            return Optional.of(id);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return Optional.empty();
        }
    }*/
}
