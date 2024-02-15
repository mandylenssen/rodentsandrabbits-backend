package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.controllers;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services.LogbookLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logbooks/{logbookId}/logs")
public class LogbookLogController {
    private final LogbookLogService logbookLogService;

    public LogbookLogController(LogbookLogService logbookLogService) {
        this.logbookLogService = logbookLogService;
    }

    @GetMapping
    public ResponseEntity<List<LogbookLogDto>> getAllLogsForLogbook(@PathVariable Long logbookId) {
        List<LogbookLogDto> logs = logbookLogService.getAllLogsForLogbook(logbookId);
        return ResponseEntity.ok(logs);
    }

    @PostMapping
    public ResponseEntity<LogbookLogDto> addLogToLogbook(@PathVariable Long logbookId, @RequestBody LogbookLogDto logDto) {
        LogbookLogDto newLog = logbookLogService.addLogToLogbook(logbookId, logDto);
        return ResponseEntity.ok(newLog);
    }


}
