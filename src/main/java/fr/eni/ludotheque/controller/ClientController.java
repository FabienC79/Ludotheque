package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bll.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // S3019 - Ajouter un client
    @PostMapping
    public ResponseEntity<String> ajouterClient(@RequestBody Client client) {
        try {
            clientService.ajouterClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body("Client ajouté avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'ajout du client");
        }
    }

    // S3020 - Supprimer un client
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerClient(@PathVariable Integer id) {
        try {
            Optional<Client> client = clientService.obtenirClientParId(id);
            if (client.isPresent()) {
                clientService.supprimerClient(id);
                return ResponseEntity.ok("Client supprimé avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression");
        }
    }

    // S3021 - Modifier un client complètement (infos du client et adresse)
    @PutMapping("/{id}")
    public ResponseEntity<String> modifierClient(@PathVariable Integer id, @RequestBody Client client) {
        try {
            Optional<Client> existingClient = clientService.obtenirClientParId(id);
            if (existingClient.isPresent()) {
                client.setId(id);
                clientService.modifierClient(client);
                return ResponseEntity.ok("Client modifié avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification");
        }
    }

    // S3022 - Modifier uniquement l'adresse du client
    @PatchMapping("/{id}/adresse")
    public ResponseEntity<String> modifierAdresseClient(@PathVariable Integer id, @RequestBody Client client) {
        try {
            Optional<Client> existingClient = clientService.obtenirClientParId(id);
            if (existingClient.isPresent()) {
                clientService.modifierAdresseClient(id, client);
                return ResponseEntity.ok("Adresse du client modifiée avec succès");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la modification de l'adresse");
        }
    }

    // S3023 - Trouver les clients dont le nom commence par la chaine fournie
    @GetMapping("/search")
    public ResponseEntity<List<Client>> rechercherClientParNom(@RequestParam String nom) {
        try {
            List<Client> clients = clientService.rechercherClientParNom(nom);
            if (clients.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // S3024 - Trouver un client par id
    @GetMapping("/{id}")
    public ResponseEntity<Client> obtenirClientParId(@PathVariable Integer id) {
        try {
            Optional<Client> client = clientService.obtenirClientParId(id);
            if (client.isPresent()) {
                return ResponseEntity.ok(client.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

