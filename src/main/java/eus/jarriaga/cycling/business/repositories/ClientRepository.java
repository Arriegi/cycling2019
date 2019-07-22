package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository  extends CrudRepository<Client,Long> {
    Iterable<Client> findAllByOrderByName();
}
