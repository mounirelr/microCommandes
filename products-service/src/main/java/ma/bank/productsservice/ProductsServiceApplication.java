package ma.bank.productsservice;

import ma.bank.productsservice.model.Product;
import ma.bank.productsservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }


    @Bean
    CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {
            for (int i = 1; i <= 20; i++) {
                Product product = new Product();
                product.setName("Product " + i);
                product.setPrice(50.0 + i * 10);

                productRepository.save(product);
            }


        };
    }

}
