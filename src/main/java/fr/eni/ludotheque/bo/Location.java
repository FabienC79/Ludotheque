package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "exemplaire_id")
    private Exemplaire exemplaire;

    @NonNull
    private LocalDate dateLocation;

    private LocalDate dateRetour;

    private Float montantFacture;

    private Boolean retourné = false;
}

