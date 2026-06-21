package backend.shop.service;

import backend.shop.repo.ProductsRepo;
import org.springframework.stereotype.Service;


@Service
public class ProductsService {
    private final ProductsRepo productsRepo;

    public ProductsService(ProductsRepo productsRepo){
        this.productsRepo = productsRepo;
    }

}
