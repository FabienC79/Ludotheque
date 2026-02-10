package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bll.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ClientService clientService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    // S3019 - Test positif : Ajouter un client
    @Test
    @DisplayName("Test S3019 : Ajouter un client avec succès")
    public void testAjouterClient_CasPositif() throws Exception {
        String json = "{\"nom\":\"Dupont\",\"prenom\":\"Jean\",\"adresseMail\":\"jean.dupont@email.com\",\"telephone\":\"0123456789\",\"adresse\":{\"rue\":\"10 Rue de la Paix\",\"codePostal\":\"75000\",\"ville\":\"Paris\"}}";
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test S3020 : Supprimer un client avec succès")
    public void testSupprimerClient_CasPositif() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Martin", "Marie", "marie.martin@email.com", "0987654321");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        mockMvc.perform(delete("/api/clients/" + clientId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test S3020 : Supprimer un client inexistant")
    public void testSupprimerClient_CasNegatif() throws Exception {
        mockMvc.perform(delete("/api/clients/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test S3021 : Modifier un client complètement")
    public void testModifierClient_CasPositif() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Bernard", "Pierre", "pierre.bernard@email.com", "0111111111");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        String json = "{\"nom\":\"Bernard\",\"prenom\":\"Pierre\",\"adresseMail\":\"pierre.bernard.new@email.com\",\"telephone\":\"0222222222\",\"adresse\":{\"rue\":\"20 Avenue de la Liberté\",\"codePostal\":\"75002\",\"ville\":\"Paris\"}}";
        mockMvc.perform(put("/api/clients/" + clientId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test S3022 : Modifier uniquement l'adresse du client")
    public void testModifierAdresseClient_CasPositif() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Rousseau", "Luc", "luc.rousseau@email.com", "0333333333");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        String json = "{\"adresse\":{\"rue\":\"50 Boulevard Saint-Germain\",\"codePostal\":\"75005\",\"ville\":\"Paris\"}}";
        mockMvc.perform(patch("/api/clients/" + clientId + "/adresse")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test S3023 : Rechercher clients dont le nom commence par 'Du'")
    public void testRechercherClientParNom_CasPositif() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Dupuis", "Sophie", "sophie.dupuis@email.com", "0444444444");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);

        mockMvc.perform(get("/api/clients/search")
                .param("nom", "Du")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test S3023 : Rechercher clients avec un critère qui ne retourne rien")
    public void testRechercherClientParNom_CasNegatif() throws Exception {
        mockMvc.perform(get("/api/clients/search")
                .param("nom", "ZZZZZ")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test S3024 : Trouver un client par id")
    public void testObtenirClientParId_CasPositif() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Lefevre", "Anne", "anne.lefevre@email.com", "0555555555");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        mockMvc.perform(get("/api/clients/" + clientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test S3024 : Trouver un client inexistant")
    public void testObtenirClientParId_CasNegatif() throws Exception {
        mockMvc.perform(get("/api/clients/99999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}





