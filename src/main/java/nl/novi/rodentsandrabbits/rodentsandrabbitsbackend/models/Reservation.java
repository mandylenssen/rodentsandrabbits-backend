package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    private Date startDate;
    private Date endDate;

    @ManyToMany()
    private Set<Pet> pets;



    public Reservation(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Reservation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
