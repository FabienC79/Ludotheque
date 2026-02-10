package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import fr.eni.ludotheque.dal.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired  //injection du bena ClientRepository
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void ajouterClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void supprimerClient(Integer id) {
        clientRepository.deleteById(id);
    }

    @Override
    public void modifierClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void modifierAdresseClient(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client existingClient = optionalClient.get();
            existingClient.setAdresse(client.getAdresse());
            clientRepository.save(existingClient);
        }
    }

    @Override
    public Optional<Client> obtenirClientParId(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> rechercherClientParNom(String nom) {
        return clientRepository.findByNomStartingWith(nom);
    }
}
