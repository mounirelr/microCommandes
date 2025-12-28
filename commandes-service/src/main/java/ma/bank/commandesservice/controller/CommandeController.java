package ma.bank.commandesservice.controller;

import ma.bank.commandesservice.model.Commande;
import ma.bank.commandesservice.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RefreshScope
@RequestMapping("/commandes")
public class CommandeController {

    @Value("${mes-config-ms.commandes-last}")
    private int lastDays;
    private final  CommandeRepository repository;

    public CommandeController(CommandeRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<Commande> getCommandes() {
        return repository.findAll();
    }

    @GetMapping("/lastDays")
    public List<Commande> getLastDays() {
        return  repository.findByDateAfter(LocalDate.now().minusDays(lastDays));
    }

    @GetMapping("/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping
    public Commande addCommande(@RequestBody Commande commande) {
        commande.setDate(LocalDate.now());
        return repository.save(commande);
    }


}
