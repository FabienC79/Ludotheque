package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Jeu;
import java.util.List;
import java.util.Optional;

public interface JeuService {
    public List<Jeu> obtenirTousLesJeux();
    public Optional<Jeu> obtenirJeuParId(Integer id);
}

