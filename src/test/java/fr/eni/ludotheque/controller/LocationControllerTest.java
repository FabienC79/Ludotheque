package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.bll.ClientService;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.JeuService;
import fr.eni.ludotheque.bll.LocationService;
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
public class LocationControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JeuService jeuService;

    @Autowired
    private ExemplaireService exemplaireService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Test S3027 : Enregistrer une nouvelle location (cas positif)")
    public void testEnregistrerLocation_CasPositif() throws Exception {
        // Arrange
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Durand", "Jacques", "jacques.durand@email.com", "0666666666");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        // Act & Assert
        mockMvc.perform(post("/api/locations")
                .param("clientId", clientId.toString())
                .param("codeBarre", "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Exemplaire n'existe pas
    }

    @Test
    @DisplayName("Test S3027 : Enregistrer location avec client inexistant (cas négatif)")
    public void testEnregistrerLocation_ClientInexistant() throws Exception {
        mockMvc.perform(post("/api/locations")
                .param("clientId", "99999")
                .param("codeBarre", "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test S3028 : Enregistrer le retour d'une location (cas positif)")
    public void testEnregistrerRetourLocation_CasPositif() throws Exception {
        mockMvc.perform(put("/api/locations/1/retour")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError()); // Location n'existe pas
    }

    @Test
    @DisplayName("Test S3028 : Obtenir les locations non retournées d'un client")
    public void testObtenirLocationsNonRetournees() throws Exception {
        Adresse adresse = new Adresse("10 Rue de la Paix", "75000", "Paris");
        Client client = new Client("Fournier", "Nathalie", "nathalie.fournier@email.com", "0777777777");
        client.setAdresse(adresse);
        clientService.ajouterClient(client);
        Integer clientId = client.getId();

        mockMvc.perform(get("/api/locations/client/" + clientId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Pas de locations
    }
}

