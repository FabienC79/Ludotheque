package fr.eni.ludotheque.bll;

import fr.eni.ludotheque.bo.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {
    public void ajouterClient(Client client);
    public void supprimerClient(Integer id);
    public void modifierClient(Client client);
    public void modifierAdresseClient(Integer id, Client client);
    public Optional<Client> obtenirClientParId(Integer id);
    public List<Client> rechercherClientParNom(String nom);
}
