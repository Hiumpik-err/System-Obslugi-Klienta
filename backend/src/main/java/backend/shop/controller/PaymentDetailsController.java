package backend.shop.controller;

import backend.shop.model.PaymentDetails;
import backend.shop.service.PaymentDetailsService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {
    private final PaymentDetailsService service;

    public PaymentDetailsController(PaymentDetailsService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<?> addNewDeliveryDetail(@RequestBody PaymentDetails pd){
        if(this.service.addNewPaymentDetail(pd)){
            return new ResponseEntity<>("Added Successfully", HttpStatusCode.valueOf(204));
        }
        return new ResponseEntity<>("Operation Failed", HttpStatusCode.valueOf(400));
    }
}
