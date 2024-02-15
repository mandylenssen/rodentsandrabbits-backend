package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;

import java.util.Date;
import java.util.List;

public class LogbookLogDto {

    private Long id;
    private Date date;
    private String entry;
    private List<PetDto> pets;

    public LogbookLogDto(Long id, Date date, String entry, List<PetDto> pets) {
        this.id = id;
        this.date = date;
        this.entry = entry;
        this.pets = pets;
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

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }
}
