package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ExemplaireRepositoryTest {

    @Autowired
    private ExemplaireRepository exemplaireRepository;
    @Autowired
    private JeuRepository jeuRepository;

    @Test
    void ajouterExemplaire() {
        Jeu jeu = new Jeu("reférence", 12F, "une description", 24F, 15F, "Skyjo");
        Exemplaire exemplaire = new Exemplaire();
        exemplaire.setJeu(jeu);
        exemplaire.setLouable(true);
        exemplaire.setCodeBarre(123456789F);

        exemplaireRepository.save(exemplaire);

        Exemplaire exemplaire2 = exemplaireRepository.findById(exemplaire.getId()).orElse(null);
        Assertions.assertEquals(exemplaire2, exemplaire);
        Assertions.assertEquals(exemplaire2.getLouable(), exemplaire.getLouable());

    }

}
