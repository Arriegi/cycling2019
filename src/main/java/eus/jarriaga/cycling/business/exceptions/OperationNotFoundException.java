package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.Operation;

public class OperationNotFoundException extends  ItemNotFoundException {
    public OperationNotFoundException() {}

    public OperationNotFoundException(Long id) {
        super(Operation.class.getSimpleName(),id);
    }

}
