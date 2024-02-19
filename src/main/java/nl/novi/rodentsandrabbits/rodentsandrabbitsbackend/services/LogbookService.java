package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.exceptions.LogbookNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookLogRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogbookService {

    private final LogbookRepository logbookRepository;
    private final LogbookLogRepository logbookLogRepository;
    private final PetRepository petRepository;

    public LogbookService(LogbookRepository logbookRepository,
                          LogbookLogRepository logbookLogRepository,
                          PetRepository petRepository) {
        this.logbookRepository = logbookRepository;
        this.logbookLogRepository = logbookLogRepository;
        this.petRepository = petRepository;
    }

    public LogbookDto getLogbookForUser(String username) {
        Logbook logbook = logbookRepository.findByUserName(username)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for username: " + username));
        return transferToDto(logbook);
    }

    public LogbookDto getLogbookDtoById(Long id) {
        Logbook logbook = logbookRepository.findById(id)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for id: " + id));
        return transferToDto(logbook);
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

    public LogbookLogDto addLogToLogbook(Long logbookId, LogbookLogDto logDto) {
        // Get logbook by id
        Logbook logbook = getLogbookById(logbookId);

        // Create a new LogbookLog entity from the DTO
        LogbookLog newLog = new LogbookLog();
        newLog.setEntry(logDto.getEntry());
        newLog.setDate(logDto.getDate()); // Ensure date is correctly parsed and set

        // Resolve pets by their IDs and set them
        List<Pet> pets = petRepository.findAllById(logDto.getPetsIds());
        newLog.setPets(pets);

        // Associate the log with its logbook
        newLog.setLogbook(logbook);

        // Save the new log entity to the database
        LogbookLog savedLog = logbookLogRepository.save(newLog);

        // Convert the saved entity back to DTO and return it
        return transferToLogbookLogDto(savedLog);
    }


//    public LogbookLogDto addLogToLogbook(Long logbookId, LogbookLogDto logDto) {
//        // get logbook by id
//        Logbook logbook = getLogbookById(logbookId);
//
//        // create logbook log from logbook log dto
//        LogbookLogDto log = addLogToLogbook(logbook, logDto);
//
//        // save logbook log??
//
//        // return logbookLogDto
//        return log;
//    }

    public void deleteLogFromLogbook(Long logbookId, Long logId) {
        deleteLog(logId);
    }

    public LogbookLogDto addLogToLogbook(Logbook logbook, LogbookLogDto logDto) {
        LogbookLog log = transferToLogbookLog(logDto, logbook);
        log.setLogbook(logbook);
        logbook.getLogs().add(log);
        logbookLogRepository.save(log);
        return transferToLogbookLogDto(log);
    }

    public void deleteLog(Long logId) {
        logbookLogRepository.deleteById(logId);
    }

    public void addImageToLog(Long logId, MultipartFile multipartFile) throws IOException {
        LogbookLog log = logbookLogRepository.findById(logId)
                .orElseThrow(() -> new EntityNotFoundException("Log not found for id: " + logId));

        List<ImageData> imageDataList = log.getLogbookImageData();

        ImageData imageData = new ImageData(multipartFile);
        imageData.setLogbookLog(log);
        imageDataList.add(imageData);
        log.setLogbookImageData(imageDataList);

        logbookLogRepository.save(log);
    }

    public Long getLogbookIdForUser(String username) {
        Logbook logbook = logbookRepository.findByUserName(username)
                .orElseThrow(() -> new LogbookNotFoundException("Logbook not found for username: " + username));
        return logbook.getId();
    }

//    private LogbookLogDto transferToLogbookLogDto(LogbookLog log) {
//        LogbookLogDto logDto = new LogbookLogDto();
//        logDto.setId(log.getId());
//        logDto.setDate(log.getDate());
//        logDto.setEntry(log.getEntry());
//
//        logDto.setPetsIds(log.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
//
//        return logDto;
//    }
private LogbookLogDto transferToLogbookLogDto(LogbookLog log) {
    LogbookLogDto dto = new LogbookLogDto();
    dto.setId(log.getId());
    dto.setDate(log.getDate());
    dto.setEntry(log.getEntry());
    dto.setPetsIds(log.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
    return dto;
}


    private LogbookLog transferToLogbookLog(LogbookLogDto logDto, Logbook logbook) {
        LogbookLog log = new LogbookLog();
        log.setDate(logDto.getDate());
        log.setEntry(logDto.getEntry());
        log.setLogbook(logbook);
        if (logDto.getPetsIds() != null) {
        List<Pet> pets = logDto.getPetsIds().stream()
                .map(id -> petRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Pet not found for id: " + id)))
                .collect(Collectors.toList());
        log.setPets(pets);
    } else {
        log.setPets(new ArrayList<>());
    }
        return log;
    }

    private LogbookDto transferToDto(Logbook logbook) {
        List<LogbookLogDto> logDtos = logbook.getLogs().stream()
                .map(this::transferLogToDto)
                .collect(Collectors.toList());

        return new LogbookDto(logbook.getId(), logbook.getUserName(), logDtos);
    }

    private LogbookLogDto transferLogToDto(LogbookLog logbookLog) {
        LogbookLogDto dto = new LogbookLogDto();
        dto.setId(logbookLog.getId());
        dto.setEntry(logbookLog.getEntry());
        return dto;
    }
}
