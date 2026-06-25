package backend.shop.controller;

import backend.shop.model.Orders;
import backend.shop.service.OrdersService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService service;
    public OrdersController(OrdersService service){
        this.service = service;
    }

    /*@PostMapping()
    public ResponseEntity<?> addNewOrder(@RequestBody Orders order){
        var id = this.service.addNewOrder(order);
        if(id.isPresent()){
            return new ResponseEntity<>(id.get(), HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Cos poszlo nie tak", HttpStatusCode.valueOf(400));
    }*/
}
