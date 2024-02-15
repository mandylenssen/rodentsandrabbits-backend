package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions.LogbookNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogbookService {
    private final LogbookRepository logbookRepository;


    public LogbookService(LogbookRepository logbookRepository) {
        this.logbookRepository = logbookRepository;
    }

    public LogbookDto getLogbookForUser(String username) {
        Logbook logbook = logbookRepository.findByUserName(username)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for username: " + username));
        return convertToDto(logbook);
    }

    public LogbookDto getLogbookDtoById(Long id) {
        Logbook logbook = logbookRepository.findById(id)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for id: " + id));
        return convertToDto(logbook);
    }

    public Logbook getLogbookById(Long id) {
        Logbook logbook = logbookRepository.findById(id)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for id: " + id));
        return logbook;
    }

    public void createLogbookForUser(String username) {
        Logbook newLogbook = new Logbook();
        newLogbook.setUserName(username);
        logbookRepository.save(newLogbook);
    }

    private LogbookDto convertToDto(Logbook logbook) {
        List<LogbookLogDto> logDtos = logbook.getLogs().stream()
                .map(this::convertLogToDto)
                .collect(Collectors.toList());

        return new LogbookDto(logbook.getId(), logbook.getUserName(), logDtos);
    }

    private LogbookLogDto convertLogToDto(LogbookLog logbookLog) {
        LogbookLogDto dto = new LogbookLogDto();
        dto.setId(logbookLog.getId());
        dto.setEntry(logbookLog.getEntry());
        return dto;
    }

}
