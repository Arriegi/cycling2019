package eus.jarriaga.cycling.business.propertyeditors;

import eus.jarriaga.cycling.business.entities.Client;
import eus.jarriaga.cycling.business.repositories.ClientRepository;

import java.beans.PropertyEditorSupport;

public class ClientEditor extends PropertyEditorSupport {

    private ClientRepository clientRepository;

    public ClientEditor(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public String getAsText() {
        Client exoticType = (Client) getValue();
        return exoticType == null ? "" : exoticType.getName();
    }

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        setValue(clientRepository.findById(Long.valueOf(id)));
    }
}