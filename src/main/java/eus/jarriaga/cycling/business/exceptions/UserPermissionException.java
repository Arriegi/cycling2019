package eus.jarriaga.cycling.business.exceptions;

public class UserPermissionException extends Exception {

    public UserPermissionException() {
    }

    public UserPermissionException(String message) {
        super(message);
    }
}
