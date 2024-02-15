package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;


import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.LogbookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/logbooks")
public class LogbookController {

    private final LogbookService logbookService;

    public LogbookController(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<LogbookDto> getLogbookForUser(@PathVariable String username) {
        LogbookDto logbookDto = logbookService.getLogbookForUser(username);
        return ResponseEntity.ok(logbookDto);
    }


}
