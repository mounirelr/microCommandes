package ma.bank.commandesservice;

import ma.bank.commandesservice.client.ProductRestClient;
import ma.bank.commandesservice.model.Commande;
import ma.bank.commandesservice.model.Product;
import ma.bank.commandesservice.repository.CommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CommandesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandesServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner start(
			CommandeRepository commandeRepository, ProductRestClient productRestClient) {
		return (args) -> {

			List<Product> allProducts = productRestClient.getProducts();
			System.out.println("Products fetched: " + allProducts.size());

			allProducts.forEach(product -> {
				Commande commande = Commande.builder()
						.description("Commande pour le produit : " + product.getName())
						.quantite((int) (Math.random() * 5 + 1))
						.date(LocalDate.now())
						.montant(product.getPrice())
						.productId(product.getId())
						.build();

				commandeRepository.save(commande);
			});
		};
	}



}
