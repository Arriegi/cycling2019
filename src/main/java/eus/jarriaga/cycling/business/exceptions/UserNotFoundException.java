package eus.jarriaga.cycling.business.exceptions;

import eus.jarriaga.cycling.business.entities.User;

public class UserNotFoundException extends ItemNotFoundException {
    public UserNotFoundException(){}

    public UserNotFoundException(Long id) {
        super(User.class.getSimpleName(),id);
    }

    public UserNotFoundException(String email) {
        super(User.class.getSimpleName(),email);
    }
}
