package fr.eni.ludotheque.dal;

import fr.eni.ludotheque.bo.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}
