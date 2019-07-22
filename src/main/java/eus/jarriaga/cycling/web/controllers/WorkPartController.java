package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.WorkPart;
import eus.jarriaga.cycling.business.exceptions.WorkPartNotFoundException;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class WorkPartController {
    @Autowired
    private WorkPartRepository workPartRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/workparts")
    public List<WorkPart> getWorkParts(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<WorkPart>) workPartRepository.findAll();
    }

    @PostMapping("/workparts")
    public WorkPart newWorkPart(@RequestBody WorkPart newWorkPart) throws WorkPartNotFoundException {
        return workPartRepository.save(newWorkPart);
    }

    @GetMapping("/workparts/{id}")
    public WorkPart getWorkPart(@PathVariable Long id) throws WorkPartNotFoundException {
        return workPartRepository.findById(id).orElseThrow(() -> new WorkPartNotFoundException(id));
    }

    @GetMapping("/workparts/from/{from}/to/{to}")
    public List<WorkPart> getWorkpartsBetweenDates(@PathVariable("from") String from, @PathVariable("to") String to) {
        return workPartRepository.findAllByDateBetween(LocalDate.parse(from),LocalDate.parse(to));
    }

    @PutMapping("/workparts/{id}")
    public WorkPart replaceOperation(@RequestBody WorkPart newWorkPart, @PathVariable Long id)
            throws WorkPartNotFoundException {
        WorkPart workPart = workPartRepository.findById(id).orElseThrow(() -> new WorkPartNotFoundException(id));
        Optional.ofNullable(newWorkPart.getDuration()).ifPresent((duration) -> workPart.setDuration(duration));
        return workPartRepository.save(workPart);
    }

    @DeleteMapping("/workparts/{id}")
    public void deleteWorkPart(@PathVariable Long id) {
        workPartRepository.deleteById(id);
    }
}
