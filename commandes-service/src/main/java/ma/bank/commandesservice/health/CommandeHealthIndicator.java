package ma.bank.commandesservice.health;

import ma.bank.commandesservice.repository.CommandeRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CommandeHealthIndicator  implements HealthIndicator {


    private final CommandeRepository commandeRepository;

    public CommandeHealthIndicator(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }


    @Override
    public Health health() {
        long count = commandeRepository.count();

        if (count > 0) {
            return Health.up()
                    .withDetail("nombreCommandes", count)
                    .build();
        } else {
            return Health.down()
                    .withDetail("message", "Aucune commande dans la table COMMANDE")
                    .build();
        }
    }

}
