package eus.jarriaga.cycling.business.exceptions;

public class ItemNotFoundException extends Exception {

    public ItemNotFoundException(){}

    public ItemNotFoundException(String className, Long id) {
        super("No se ha encontrado " + className + " " + id);
    }

    public ItemNotFoundException(String className, String name) {
        super("No se ha encontrado " + className + " " + name);
    }
}
