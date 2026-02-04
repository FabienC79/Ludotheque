package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testCreationClient() {
        //Arrange
        Client client = new Client("Candelon", "Fabien", "Fabien@gmail.com", "064646464");

        //Act
        clientRepository.save(client);

        //Assert
        Client client1 = clientRepository.findById(client.getId()).orElse(null);
        Assertions.assertNotNull(client1);
        Assertions.assertEquals(client.getNom(), client1.getNom());

        clientRepository.delete(client);


    }
}
