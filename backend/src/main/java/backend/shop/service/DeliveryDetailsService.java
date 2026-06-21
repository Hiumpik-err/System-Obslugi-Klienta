package backend.shop.service;

import backend.shop.model.DeliveryDetails;
import backend.shop.repo.DeliveryDetailsRepo;
import org.springframework.stereotype.Service;

@Service
public class DeliveryDetailsService {
    private final DeliveryDetailsRepo repo;
    public DeliveryDetailsService(DeliveryDetailsRepo repo){
        this.repo = repo;
    }

    public boolean addNewDeliveryDetail(DeliveryDetails dd){
        try{
            this.repo.save(dd);
            return true;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
