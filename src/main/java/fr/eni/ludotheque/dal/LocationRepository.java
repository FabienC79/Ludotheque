package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findByClientIdAndRetournéFalse(Integer clientId);
    Optional<Location> findByExemplaireIdAndRetournéFalse(Integer exemplaireId);
}

