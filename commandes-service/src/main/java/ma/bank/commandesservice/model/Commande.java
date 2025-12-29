package ma.bank.commandesservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Data
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private int quantite;
    private LocalDate date;
    private Double montant;
    private int productId;
    @Transient
    private Product product;

}
