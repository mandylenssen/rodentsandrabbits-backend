package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogbookLogDto {

    private Long id;
    private Date date;
    private String entry;
    private List<Long> petsIds = new ArrayList<>();

    public LogbookLogDto(Long id, Date date, String entry, List<Long> petsIds) {
        this.id = id;
        this.date = date;
        this.entry = entry;
        this.petsIds = petsIds;
    }

    public LogbookLogDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public List<Long> getPetsIds() {
        return petsIds;
    }

    public void setPetsIds(List<Long> pets) {
        this.petsIds = pets;
    }
}
