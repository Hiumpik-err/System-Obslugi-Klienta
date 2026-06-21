package backend.shop.service;

import backend.shop.model.PaymentDetails;
import backend.shop.repo.PaymentDetailsRepo;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailsService {
    private final PaymentDetailsRepo repo;
    public PaymentDetailsService(PaymentDetailsRepo repo){
        this.repo = repo;
    }

    public boolean addNewPaymentDetail(PaymentDetails pd){
        try{
            this.repo.save(pd);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
