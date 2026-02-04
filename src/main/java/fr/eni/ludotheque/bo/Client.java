package fr.eni.ludotheque.bo;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.NonNull;

@Data
@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false, length = 50)
    @NonNull
    private String nom;
    @Column(nullable = false, length = 50)
    @NonNull
    private String prenom;
    @Column(nullable = false, length = 50)
    @NonNull
    private String adresseMail;
    @Column(nullable = false, length = 10, unique = true)
    @NonNull
    private int telephone;

    public Client() {

    }
}

