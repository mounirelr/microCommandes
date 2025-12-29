package ma.bank.commandesservice.client;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import ma.bank.commandesservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name = "PRODUCTS-SERVICE")
public interface ProductRestClient {

    @GetMapping("/product/{id}")
    @CircuitBreaker(name = "PRODUCTS-SERVICE", fallbackMethod = "getDefaultProduct")
    Product getProductById(@PathVariable int id);

    @GetMapping("/product") // match controller
    @CircuitBreaker(name = "PRODUCTS-SERVICE", fallbackMethod = "getDefaultProducts")
    List<Product> getProducts();

    default Product getDefaultProduct(int id, Exception e) {
        return Product.builder()
                .id(id)
                .name("NULL")
                .price(0.0)
                .build();
    }

    default List<Product> getDefaultProducts(Exception e) {
        return List.of();
    }
}