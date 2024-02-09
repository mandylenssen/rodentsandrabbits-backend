package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models.Pet;

import java.util.Date;

public class DiaryLogDto {

    private Long id;
    private Date date;
    private String name;
    private Pet pet;

    public DiaryLogDto(Long id, Date date, String name, Pet pet) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.pet = pet;
    }

    public DiaryLogDto() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
