package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExemplaireRepository extends JpaRepository<Exemplaire, Integer> {
    Optional<Exemplaire> findByCodeBarre(Float codeBarre);
}
