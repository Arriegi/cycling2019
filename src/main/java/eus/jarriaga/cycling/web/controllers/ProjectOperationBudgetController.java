package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.exceptions.ProjectOperationBudgetNotDeletedException;
import eus.jarriaga.cycling.business.repositories.ProjectOperationBudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class ProjectOperationBudgetController {

    @Autowired
    private ProjectOperationBudgetRepository projectOperationBudgetRepository;

    @DeleteMapping("/projectoperationbudget/{id}")
    public void deleteProject(@PathVariable Long id) throws ProjectOperationBudgetNotDeletedException {
        try {
            projectOperationBudgetRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException dive) {
            throw new ProjectOperationBudgetNotDeletedException();
        }
    }

}
