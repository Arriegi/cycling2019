package eus.jarriaga.cycling.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.entities.WorkPart;
import eus.jarriaga.cycling.business.repositories.ProjectRepository;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class ProjectDatesCalculator {
    private final Log logger = LogFactory.getLog(getClass());

    private Long projectId;

    private WorkPartRepository partRepository;
    private ProjectRepository projectRepository;

    public ProjectDatesCalculator(WorkPartRepository partRepository, ProjectRepository projectRepository, Long projectId) {
        this.partRepository = partRepository;
        this.projectRepository = projectRepository;
        this.projectId = projectId;
    }

    public String getJSONParts() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            Project project = projectRepository.findById(this.projectId).get();
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(partRepository.findAllByProject(project));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<WorkPart> getParts() {
        if (this.projectId == null) {
            return null;
        }
        Project project = projectRepository.findById(this.projectId).get();
        return partRepository.findAllByProject(project);
    }

}
