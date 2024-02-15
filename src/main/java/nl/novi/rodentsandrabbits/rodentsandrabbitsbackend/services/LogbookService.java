package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions.LogbookNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogbookService {
    private final LogbookRepository logbookRepository;
    private final LogbookLogService logbookLogService;


    public LogbookService(LogbookRepository logbookRepository, LogbookLogService logbookLogService) {
        this.logbookRepository = logbookRepository;
        this.logbookLogService = logbookLogService;
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

    public LogbookLogDto addLogToLogbook(Long logbookId, LogbookLogDto logDto) {
        // get logbook by id
        Logbook logbook = getLogbookById(logbookId);

        // create logbook log from logbook log dto
        LogbookLogDto log = logbookLogService.addLogToLogbook(logbook, logDto);

        // save logbook log??

        // return logbookLogDto
        return log;
    }

    public void deleteLogFromLogbook(Long logbookId, Long logId) {
        logbookLogService.deleteLog(logId);
    }

    public void addImageToLog(Long logId, MultipartFile multipartFile) throws IOException {
        logbookLogService.addImageToLog(logId, multipartFile);
    }

}
