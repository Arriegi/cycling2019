package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Client;
import eus.jarriaga.cycling.business.exceptions.ClientNotDeletedException;
import eus.jarriaga.cycling.business.exceptions.ClientNotFoundException;
import eus.jarriaga.cycling.business.repositories.ClientRepository;
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
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    private final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/clients")
    public List<Client> getClients(Principal principal) {
        if (principal != null) {
            logger.info("PRINCIPAL: " + principal.getName());
        }
        return (List<Client>) clientRepository.findAll();
    }

    @PostMapping("/clients")
    public Client newClient(@RequestBody Client newClient) throws ClientNotFoundException {
        return clientRepository.save(newClient);
    }

    @GetMapping("/clients/{id}")
    public Client getClient(@PathVariable Long id) throws ClientNotFoundException {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }

    @PutMapping("/clients/{id}")
    public Client replaceOperation(@RequestBody Client newClient, @PathVariable Long id)
            throws ClientNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
        Optional.ofNullable(newClient.getName()).ifPresent((name) -> client.setName(name));
        Optional.ofNullable(newClient.getDischargeDate()).ifPresent((dischargeDate) -> client.setDischargeDate(dischargeDate));
        return clientRepository.save(client);
    }

    @DeleteMapping("/clients/{id}")
    public void deleteClient(@PathVariable Long id) throws ClientNotDeletedException {
        try {
            clientRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException dive) {
            throw new ClientNotDeletedException();
        }
    }

}
