package ma.bank.commandesservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.bank.commandesservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductRestClient {
   @GetMapping("/product/{id}")
    @CircuitBreaker(name = "PRODUCTS-SERVICE",fallbackMethod = "getDefaultProduct")
    Product getProductById(int id);

    default Product getDefaultProduct(int id ,Exception e) {
        return  new Product().builder()
                .id(new Random().nextInt())
                .name("NULL")
                .price(0.0)
                .build();
    }
    @GetMapping("/product")
    @CircuitBreaker(name = "PRODUCTS-SERVICE",fallbackMethod = "getDefaultProducts")
    default List<Product> getDefaultProducts(Exception e) {
        return List.of();
    }

    List<Product> getProducts();
}
