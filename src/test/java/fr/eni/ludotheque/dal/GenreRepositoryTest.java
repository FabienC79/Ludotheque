package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    void testAjouterGenre(Genre genre) {

        Genre genre3 = new Genre();
        genre3.setId(3);
        genre3.setLibelle("Aventure");

        genreRepository.save(genre3);

        Assertions.assertNotNull(genreRepository.findById(genre3.getId()));


    }
}
