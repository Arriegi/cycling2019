package eus.jarriaga.cycling.business.propertyeditors;

import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.repositories.ProjectRepository;

import java.beans.PropertyEditorSupport;

public class ProjectEditor extends PropertyEditorSupport {
    private ProjectRepository projectRepository;

    public ProjectEditor(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public String getAsText() {
        Project exoticType = (Project) getValue();
        return exoticType == null ? "" : exoticType.getName();
    }

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        setValue(projectRepository.findById(Long.valueOf(id)));
    }

}
