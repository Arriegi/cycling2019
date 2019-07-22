package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.Role;

public class RoleNotFoundException extends ItemNotFoundException {
    public RoleNotFoundException(){}

    public RoleNotFoundException(Long id) {
        super(Role.class.getSimpleName(),id);
    }
}
