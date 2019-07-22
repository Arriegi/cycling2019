package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.Operation;

public class ProjectNotFoundException extends ItemNotFoundException {
    public ProjectNotFoundException(){}

    public ProjectNotFoundException(Long id) {
        super(Operation.class.getSimpleName(),id);
    }

    public ProjectNotFoundException(String name) {
        super(Operation.class.getSimpleName(),name);
    }

}
