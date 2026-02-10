package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.JeuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/jeux")
public class JeuController {

    private JeuService jeuService;
    private ExemplaireService exemplaireService;

    @Autowired
    public JeuController(JeuService jeuService, ExemplaireService exemplaireService) {
        this.jeuService = jeuService;
        this.exemplaireService = exemplaireService;
    }

    // S3025 - API REST pour trouver les jeux et pour chaque jeu le nombre d'exemplaires disponible
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> obtenirJeuxAvecExemplairesDisponibles() {
        try {
            List<Jeu> jeux = jeuService.obtenirTousLesJeux();
            List<Map<String, Object>> result = new ArrayList<>();

            for (Jeu jeu : jeux) {
                Map<String, Object> jeuInfo = new LinkedHashMap<>();
                jeuInfo.put("id", jeu.getId());
                jeuInfo.put("titre", jeu.getTitre());
                jeuInfo.put("reference", jeu.getReference());
                jeuInfo.put("description", jeu.getDescription());
                jeuInfo.put("ageMini", jeu.getAgeMini());
                jeuInfo.put("duree", jeu.getDuree());
                jeuInfo.put("tarifJour", jeu.getTarifJour());

                // Compter les exemplaires louables (disponibles = louable ET non loué)
                // Pour simplifier, on compte les exemplaires louables
                long exemplairesDisponibles = jeu.getExemplaires() != null ?
                        jeu.getExemplaires().stream().filter(Exemplaire::getLouable).count() : 0;

                jeuInfo.put("exemplairesDisponibles", exemplairesDisponibles);
                result.add(jeuInfo);
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // S3026 - Recherche des informations d'un exemplaire connaissant le codebarre
    @GetMapping("/exemplaires/{codeBarre}")
    public ResponseEntity<Exemplaire> obtenirExemplaireParCodeBarre(@PathVariable Float codeBarre) {
        try {
            Optional<Exemplaire> exemplaire = exemplaireService.obtenirExemplaireParCodeBarre(codeBarre);
            if (exemplaire.isPresent()) {
                return ResponseEntity.ok(exemplaire.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

