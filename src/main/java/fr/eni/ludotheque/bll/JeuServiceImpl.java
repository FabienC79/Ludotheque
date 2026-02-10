package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.dal.JeuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JeuServiceImpl implements JeuService {

    private JeuRepository jeuRepository;

    @Autowired
    public JeuServiceImpl(JeuRepository jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    @Override
    public List<Jeu> obtenirTousLesJeux() {
        return jeuRepository.findAll();
    }

    @Override
    public Optional<Jeu> obtenirJeuParId(Integer id) {
        return jeuRepository.findById(id);
    }
}

