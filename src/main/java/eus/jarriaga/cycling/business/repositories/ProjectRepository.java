package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.Client;
import eus.jarriaga.cycling.business.entities.Project;
import org.springframework.data.repository.CrudRepository;

public interface    ProjectRepository extends CrudRepository<Project,Long> {
    Iterable<Project> findAllByOrderByName();

    Iterable<Project> findByArchivedOrderByName(Boolean archived);

    Iterable<Project> findAllByClientOrderByName(Client client);

    Project findProjectById(Long id);
}
