package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.exceptions.ProjectNotDeletedException;
import eus.jarriaga.cycling.business.exceptions.ProjectNotFoundException;
import eus.jarriaga.cycling.business.repositories.ProjectRepository;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WorkPartRepository workPartRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/projects")
    public List<Project> getProjects(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<Project>) projectRepository.findAll();
    }

    @PostMapping("/projects")
    public Project newProject(@RequestBody Project newProject) {
        return projectRepository.save(newProject);
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable Long id) throws ProjectNotFoundException {
        return projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
    }

    @PutMapping("/projects/{id}")
    public Project replaceProject(@RequestBody Project newProject, @PathVariable Long id)
            throws ProjectNotFoundException {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));
        Optional.ofNullable(newProject.getName()).ifPresent((name) -> project.setName(name));
        Optional.ofNullable(newProject.getAcceptedBudget()).ifPresent((acceptedBudget) -> project.setAcceptedBudget(acceptedBudget));
        Optional.ofNullable(newProject.getArchived()).ifPresent((archived) -> project.setArchived(archived));
        Optional.ofNullable(newProject.getDischargeDate()).ifPresent((date) -> project.setDischargeDate(date));
        Optional.ofNullable(newProject.getNote()).ifPresent((note) -> project.setNote(note));
        return projectRepository.save(project);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) throws ProjectNotDeletedException {
        try {
            projectRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException dive) {
            throw new ProjectNotDeletedException();
        }
    }
}
