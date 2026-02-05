package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import fr.eni.ludotheque.bo.Jeu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class JeuRepositoryTest {

    @Autowired
    private JeuRepository jeuRepository;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testJeuRepository() {

        Genre genre = new Genre();
        Genre genre2 = new Genre();
        genre.setId(1);
        genre.setLibelle("famille");
        genre2.setId(2);
        genre2.setLibelle("Bataille");
        genreRepository.save(genre);
        genreRepository.save(genre2);

        Jeu jeu = new Jeu("reférence", 12F, "une description", 24F, 15F, "Skyjo");
        jeu.addGenre(genre);
        jeu.addGenre(genre2);

        Jeu jeux = jeuRepository.save(jeu);

        Jeu jeuDB = jeuRepository.findById(jeux.getId()).orElse(null);
        Assertions.assertThat(jeuDB).isNotNull();
        Assertions.assertThat(jeuDB.getGenres()).hasSize(2);
        Assertions.assertThat(jeuDB.getGenres()).contains(genre);
        Assertions.assertThat(jeuDB.getGenres()).contains(genre2);

    }

}
