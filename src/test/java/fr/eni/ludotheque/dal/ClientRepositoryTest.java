package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Adresse;
import fr.eni.ludotheque.bo.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdresseRepository adresseRepository;

    @Test
    void testCreationClient() {
        //Arrange
        Adresse adresse = new Adresse("Edouard Branly", "79000", "Niort");
        Client client = new Client("Candelon", "Fabien", "Fabien@gmail.com", "064646464");
        client.setAdresse(adresse);

        //Act
        clientRepository.save(client);
//        adresseRepository.save(adresse);

        //Assert
        Client client1 = clientRepository.findById(client.getId()).orElse(null);
        Assertions.assertNotNull(client1);
        Assertions.assertEquals(client.getNom(), client1.getNom());
        Assertions.assertEquals(client.getAdresse(), client1.getAdresse());

        Adresse adresse1 = adresseRepository.findById(adresse.getId()).orElse(null);
        Assertions.assertNotNull(adresse1);
        Assertions.assertEquals(adresse.getRue(), adresse1.getRue());

//        clientRepository.delete(client);


    }
}
