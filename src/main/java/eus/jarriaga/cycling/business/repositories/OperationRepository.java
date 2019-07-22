package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.Operation;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<Operation,Long> {

        Iterable<Operation> findAllByOrderByName();
}
