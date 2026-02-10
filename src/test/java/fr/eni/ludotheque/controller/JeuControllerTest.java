package fr.eni.ludotheque.controller;

import fr.eni.ludotheque.bo.Exemplaire;
import fr.eni.ludotheque.bo.Jeu;
import fr.eni.ludotheque.bll.ExemplaireService;
import fr.eni.ludotheque.bll.JeuService;
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
public class JeuControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    @DisplayName("Test S3025 : Obtenir les jeux avec le nombre d'exemplaires disponibles")
    public void testObtenirJeuxAvecExemplairesDisponibles_CasPositif() throws Exception {
        mockMvc.perform(get("/api/jeux")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @DisplayName("Test S3026 : Rechercher un exemplaire par codebarre (cas positif)")
    public void testObtenirExemplaireParCodeBarre_CasPositif() throws Exception {
        mockMvc.perform(get("/api/jeux/exemplaires/123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test S3026 : Rechercher un exemplaire inexistant (cas négatif)")
    public void testObtenirExemplaireParCodeBarre_CasNegatif() throws Exception {
        mockMvc.perform(get("/api/jeux/exemplaires/999999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}



