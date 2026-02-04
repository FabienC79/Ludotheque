package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseRepository extends JpaRepository<Adresse, Integer> {
}
