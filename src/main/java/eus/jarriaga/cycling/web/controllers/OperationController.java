package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Operation;
import eus.jarriaga.cycling.business.exceptions.OperationNotFoundException;
import eus.jarriaga.cycling.business.repositories.OperationRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class OperationController {

    @Autowired
    private OperationRepository operationRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/operations")
    public List<Operation> getOperations(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<Operation>) operationRepository.findAll();
    }

    @PostMapping("/operations")
    public Operation newOperation(@RequestBody Operation newOperation) throws OperationNotFoundException {
        return operationRepository.save(newOperation);
    }

    @GetMapping("/operations/{id}")
    public Operation getOperation(@PathVariable Long id) throws OperationNotFoundException {
        return operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
    }

    @PutMapping("/operations/{id}")
    public Operation replaceOperation(@RequestBody Operation newOperation, @PathVariable Long id)
            throws OperationNotFoundException {
        Operation operation = operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        Optional.ofNullable(newOperation.getName()).ifPresent((name) -> operation.setName(name));
        Optional.ofNullable(newOperation.getDescription()).ifPresent((description) -> operation.setDescription(description));
        return operationRepository.save(operation);
    }

    @DeleteMapping("/operations/{id}")
    public void deleteOperation(@PathVariable Long id) {
        operationRepository.deleteById(id);
    }
}
