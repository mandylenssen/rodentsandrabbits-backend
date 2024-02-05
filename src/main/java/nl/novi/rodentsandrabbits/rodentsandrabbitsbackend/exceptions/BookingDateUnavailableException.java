package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions;

public class BookingDateUnavailableException extends RuntimeException {
    public BookingDateUnavailableException(String message) {
        super(message);
    }
}