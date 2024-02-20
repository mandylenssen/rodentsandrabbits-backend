package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

import java.util.ArrayList;
import java.util.List;

public class LogbookDto {

    private Long id;
    private String userName;
    private List<LogbookLogDto> logs;

    public LogbookDto(){}

    public LogbookDto(Long id, String userName, List<LogbookLogDto> logs) {
        this.id = id;
        this.userName = userName;
        this.logs = logs;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LogbookLogDto> getLogs() {
        return logs;
    }

    public void setLogs(List<LogbookLogDto> logs) {
        this.logs = logs;
    }
}
