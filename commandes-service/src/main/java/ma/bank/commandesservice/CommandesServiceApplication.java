package ma.bank.commandesservice;

import ma.bank.commandesservice.model.Commande;
import ma.bank.commandesservice.repository.CommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@EnableDiscoveryClient
public class CommandesServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandesServiceApplication.class, args);
	}


	@Bean
	CommandLineRunner init(CommandeRepository commandeRepository) {
		return args -> {
			for (int i = 1; i <= 20; i++) {
				commandeRepository.save(
						Commande.builder()
								.description("Commande numéro " + i)
								.quantite(i * 2)
								.date(LocalDate.now().minusDays(i))
								.montant(100.0 + (i * 25))
								.build()
				);
			}
		};
	}


}
