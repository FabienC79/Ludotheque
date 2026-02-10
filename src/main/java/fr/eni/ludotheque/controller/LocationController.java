package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Location;
import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private LocationService locationService;
    private ClientService clientService;
    private ExemplaireService exemplaireService;

    @Autowired
    public LocationController(LocationService locationService, ClientService clientService, ExemplaireService exemplaireService) {
        this.locationService = locationService;
        this.clientService = clientService;
        this.exemplaireService = exemplaireService;
    }

    // S3027 - API REST pour enregistrer une nouvelle location depuis le scan du codebarre exemplaire
    @PostMapping
    public ResponseEntity<Map<String, Object>> enregistrerLocation(
            @RequestParam Integer clientId,
            @RequestParam Float codeBarre) {
        try {
            // Vérifier que le client existe
            Optional<Client> optionalClient = clientService.obtenirClientParId(clientId);
            if (!optionalClient.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Client non trouvé"));
            }

            // Vérifier que l'exemplaire existe et obtenir ses informations
            Optional<Exemplaire> optionalExemplaire = exemplaireService.obtenirExemplaireParCodeBarre(codeBarre);
            if (!optionalExemplaire.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Exemplaire non trouvé"));
            }

            Exemplaire exemplaire = optionalExemplaire.get();

            // Vérifier que l'exemplaire n'est pas déjà loué
            Optional<Location> locationEnCours = locationService.obtenirLocationParExemplaireNonRetourne(exemplaire.getId());
            if (locationEnCours.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Cet exemplaire est déjà loué"));
            }

            // Enregistrer la location
            Location location = new Location(optionalClient.get(), exemplaire, java.time.LocalDate.now());
            locationService.enregistrerLocation(location);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Location enregistrée avec succès");
            response.put("locationId", location.getId());
            response.put("jeuTitre", exemplaire.getJeu().getTitre());
            response.put("clientNom", optionalClient.get().getNom());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de l'enregistrement de la location"));
        }
    }

    // S3028 - API REST pour enregistrer le retour d'une ou plusieurs locations et générer la facture
    @PutMapping("/{locationId}/retour")
    public ResponseEntity<Map<String, Object>> enregistrerRetourLocation(@PathVariable Integer locationId) {
        try {
            Optional<Location> optionalLocation = locationService.obtenirLocationParExemplaireNonRetourne(locationId);

            // Chercher directement par ID si la méthode ci-dessus ne retourne rien
            // (car findByExemplaireIdAndRetournéFalse cherche par exemplaire, pas par location)
            // On va utiliser une approche alternative

            locationService.enregistrerRetourLocation(locationId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Retour enregistré avec succès");
            response.put("locationId", locationId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur lors de l'enregistrement du retour"));
        }
    }

    // Endpoint pour obtenir les locations non retournées d'un client
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Location>> obtenirLocationsNonRetournees(@PathVariable Integer clientId) {
        try {
            List<Location> locations = locationService.obtenirLocationsNonRetournees(clientId);
            if (locations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(locations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


