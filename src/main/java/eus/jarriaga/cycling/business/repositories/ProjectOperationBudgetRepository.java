package eus.jarriaga.cycling.business.repositories;

import eus.jarriaga.cycling.business.entities.ProjectOperationBudget;
import org.springframework.data.repository.CrudRepository;

public interface ProjectOperationBudgetRepository  extends CrudRepository<ProjectOperationBudget,Long> {
    Iterable<ProjectOperationBudget> findByProjectId(Long projectId);

    ProjectOperationBudget findByProjectIdAndOperationId(Long projectId, Long operationId);
}
