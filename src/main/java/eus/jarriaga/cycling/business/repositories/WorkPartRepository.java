package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.*;
import eus.jarriaga.imputador.business.entities.*;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkPartRepository extends CrudRepository<WorkPart,Long> {

    List<WorkPart> findAllByUser(User user);

    List<WorkPart> findAllByProject(Project project);

    List<WorkPart> findAllByOperation(Operation operation);

    List<WorkPart> findAllByProjectClient(Client client);

    List<WorkPart> findAllByDateBetween(LocalDate from, LocalDate to);

    List<WorkPart> findAllByOperationAndDateBetween(Operation operation, LocalDate from, LocalDate to);

    List<WorkPart> findAllByUserAndDateBetween(User user, LocalDate from, LocalDate to);

    List<WorkPart> findAllByProjectAndDateBetween(Project project, LocalDate from, LocalDate to);

    List<WorkPart> findAllByProjectClientAndDateBetween(Client client, LocalDate from, LocalDate to);

}
