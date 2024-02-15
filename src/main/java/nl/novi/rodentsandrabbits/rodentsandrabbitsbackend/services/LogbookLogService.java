package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.services;

import jakarta.persistence.EntityNotFoundException;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.LogbookLogDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos.PetDto;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.ImageData;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Logbook;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.LogbookLog;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.LogbookLogRepository;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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



    private LogbookLogDto transferToLogbookLogDto(LogbookLog log) {
        LogbookLogDto logDto = new LogbookLogDto();
        logDto.setId(log.getId());
        logDto.setDate(log.getDate());
        logDto.setEntry(log.getEntry());

        logDto.setPetsIds(log.getPets().stream().map(Pet::getId).collect(Collectors.toList()));

        return logDto;
    }

    private LogbookLog transferToLogbookLog(LogbookLogDto logDto, Logbook logbook) {
        LogbookLog log = new LogbookLog();
        log.setDate(logDto.getDate());
        log.setEntry(logDto.getEntry());

        log.setLogbook(logbook);
        List<Pet> pets = logDto.getPetsIds().stream()
                .map(id -> petRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Pet not found for id: " + id)))
                .collect(Collectors.toList());
        log.setPets(pets);

        return log;
    }

    public LogbookLogDto addLogToLogbook(Logbook logbook, LogbookLogDto logDto) {
        LogbookLog log = transferToLogbookLog(logDto, logbook);
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
}
