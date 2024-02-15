package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;


import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.LogbookLogService;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.LogbookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/logbooks")
public class LogbookController {

    private final LogbookService logbookService;

    public LogbookController(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @GetMapping("/user")
    public ResponseEntity<LogbookDto> getLogbookForUser(Principal principal) {
        LogbookDto logbookDto = logbookService.getLogbookForUser(principal.getName());
        return ResponseEntity.ok(logbookDto);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<LogbookDto> getLogbookForUser(@PathVariable String username) {
        LogbookDto logbookDto = logbookService.getLogbookForUser(username);
        return ResponseEntity.ok(logbookDto);
    }

    @GetMapping("/{logbookId}")
    public ResponseEntity<LogbookDto> getLogbook(@PathVariable Long logbookId) {
        LogbookDto logbookDto = logbookService.getLogbookDtoById(logbookId);
        return ResponseEntity.ok(logbookDto);
    }

    @PostMapping("/{logbookId}/logs")
    public ResponseEntity<LogbookLogDto> addLogToLogbook(@PathVariable Long logbookId, @RequestBody LogbookLogDto logDto) {
        LogbookLogDto newLog = logbookService.addLogToLogbook(logbookId, logDto);
        return ResponseEntity.ok(newLog);
    }

    @DeleteMapping("/{logbookId}/logs/{logId}")
    public ResponseEntity<LogbookLogDto> deleteLogFromLogbook(@PathVariable Long logbookId, @PathVariable Long logId) {
        logbookService.deleteLogFromLogbook(logbookId, logId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{logbookId}/logs/{logId}/images")
    public ResponseEntity<Object> addImageToLog(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long logId) throws IOException {
        logbookService.addImageToLog(logId, multipartFile);
        return ResponseEntity.ok().body("The picture was added to the log");
    }

}
