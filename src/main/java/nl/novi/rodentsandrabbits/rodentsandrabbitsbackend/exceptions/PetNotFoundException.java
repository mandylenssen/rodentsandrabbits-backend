package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message) {
        super(message);
    }
}