package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
