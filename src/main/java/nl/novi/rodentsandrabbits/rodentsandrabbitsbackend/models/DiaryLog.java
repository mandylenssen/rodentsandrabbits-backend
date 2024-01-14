package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "diary_logs")
public class DiaryLog {

    @Id
    @GeneratedValue

    private Long id;
    private Date date;
    private String name;

    public DiaryLog(Long id, Date date, String name, Pet pet) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.pet = pet;
    }

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;





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
