package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ClientServiceImplTest {

    @Autowired
    private ClientService clientService;


    @Autowired
    private ClientRepository clientRepository;


    @Test
    @DisplayName("Test de création d'un client")
    public void testCreationClientCasPositif() {
        //Arrange
        Adresse adresse = new Adresse("Edouard Branly", "79000", "Niort");
        Client client = new Client("Candelon", "Fabien", "Fabien2@gmail.com", "064646465");
        client.setAdresse(adresse);

        //Act
        clientService.ajouterClient(client);

        //Assert
        Optional<Client> optionalClient = clientRepository.findById(client.getId());
        Assertions.assertTrue(optionalClient.isPresent());
        Assertions.assertEquals(client, optionalClient.get());
        

    }

}
