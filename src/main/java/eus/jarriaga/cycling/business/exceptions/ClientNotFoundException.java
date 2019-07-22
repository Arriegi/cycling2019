package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.Client;

public class ClientNotFoundException extends ItemNotFoundException {
    public ClientNotFoundException(){}

    public ClientNotFoundException(Long id) {
        super(Client.class.getSimpleName(),id);
    }

    public ClientNotFoundException(String email) {
        super(Client.class.getSimpleName(),email);
    }
}
