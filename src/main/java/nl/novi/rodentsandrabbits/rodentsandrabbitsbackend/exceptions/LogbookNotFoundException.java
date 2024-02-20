package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LogbookNotFoundException extends RuntimeException {
    public LogbookNotFoundException(String message) {
        super(message);
    }
}
