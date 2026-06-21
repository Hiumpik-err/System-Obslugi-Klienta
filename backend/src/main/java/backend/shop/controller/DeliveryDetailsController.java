package backend.shop.controller;


import backend.shop.model.DeliveryDetails;
import backend.shop.service.DeliveryDetailsService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/delivery-details")
public class DeliveryDetailsController {

    private final DeliveryDetailsService service;
    public DeliveryDetailsController(DeliveryDetailsService service){
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> addNewDeliveryDetail(@RequestBody DeliveryDetails dd){
        if(this.service.addNewDeliveryDetail(dd)){
            return new ResponseEntity<>("Added Successfully", HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>("Operation failed", HttpStatusCode.valueOf(400));
    }
}
