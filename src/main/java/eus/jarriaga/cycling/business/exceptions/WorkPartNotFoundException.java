package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.WorkPart;

public class WorkPartNotFoundException extends ItemNotFoundException {
    public WorkPartNotFoundException(){}

    public WorkPartNotFoundException(Long id) {
        super(WorkPart.class.getSimpleName(),id);
    }

}
