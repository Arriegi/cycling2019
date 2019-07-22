package eus.jarriaga.cycling.business.exceptions;

public class RepeatedEmailException extends Exception {
    public RepeatedEmailException() {
        super();
    }

    public RepeatedEmailException(String email) {
        super("Repeated email " + email);
    }
}
