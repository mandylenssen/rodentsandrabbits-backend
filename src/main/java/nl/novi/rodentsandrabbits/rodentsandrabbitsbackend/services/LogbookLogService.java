package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookLogRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogbookLogService {
    private final LogbookLogRepository logbookLogRepository;
    private final PetRepository petRepository;
    private final LogbookService logbookService;
    public LogbookLogService(LogbookLogRepository logbookLogRepository, PetRepository petRepository, LogbookService logbookService) {
        this.logbookLogRepository = logbookLogRepository;
        this.petRepository = petRepository;
        this.logbookService = logbookService;
    }

    public List<LogbookLogDto> getAllLogsForLogbook(Long logbookId) {
        List<LogbookLog> logs = logbookLogRepository.findByLogbookId(logbookId);
        return logs.stream().map(this::transferToLogbookLogDto).collect(Collectors.toList());
    }

    public LogbookLogDto addLogToLogbook(Long logbookId, LogbookLogDto logDto) {
        LogbookDto logbook = logbookService.getLogbookDtoById (logbookId);
        LogbookLog log = transferToLogbookLog(logDto, logbook);
        log = logbookLogRepository.save(log);
        return transferToLogbookLogDto(log);
    }

    private LogbookLogDto transferToLogbookLogDto(LogbookLog log) {
        LogbookLogDto logDto = new LogbookLogDto();
        logDto.setId(log.getId());
        logDto.setDate(log.getDate());
        logDto.setEntry(log.getEntry());

        logDto.setPetsIds(log.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

        return logDto;
    }

    private LogbookLog transferToLogbookLog(LogbookLogDto logDto, LogbookDto logbookDto) {
        LogbookLog log = new LogbookLog();
        log.setDate(logDto.getDate());
        log.setEntry(logDto.getEntry());

        Logbook logbook = logbookService.getLogbookById(logbookDto.getId());

        log.setLogbook(logbook);
        List<Pet> pets = logDto.getPetsIds().stream()
                .map(id -> petRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Pet not found for id: " + id)))
                .collect(Collectors.toList());
        log.setPets(pets);

        return log;
    }
}
