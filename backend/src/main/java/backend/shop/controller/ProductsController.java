package backend.shop.controller;

import backend.shop.model.Products;
import backend.shop.service.ProductsService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService){
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>("Działa", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id){
        return new ResponseEntity<>("Nwm", HttpStatusCode.valueOf(200));
    }
    @PostMapping("/products")
    public ResponseEntity<?> addNewProduct(@RequestBody Products product){
        return new ResponseEntity<>("Nwm", HttpStatusCode.valueOf(200));
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Products product){
        return new ResponseEntity<>("Nwm", HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        return new ResponseEntity<>("Nwm", HttpStatusCode.valueOf(200));
    }
}
