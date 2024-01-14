package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "diary_logs")
public class DiaryLog {

    @Id
    @GeneratedValue

    private Long id;
    private Date date;
    private String name;
    private Pet pet;

    public DiaryLog(Long id, Date date, String name, Pet pet) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.pet = pet;
    }

    public DiaryLog() {

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
