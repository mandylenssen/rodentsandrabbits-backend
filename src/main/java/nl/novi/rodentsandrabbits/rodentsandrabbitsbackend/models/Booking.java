package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private Date startDate;
    private Date endDate;
    private String additionalInfo;

    @ManyToMany(mappedBy = "bookings")
    private Set<Pet> pets;

    public Booking(Long id, Date startDate, Date endDate, String additionalInfo) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalInfo = additionalInfo;
    }

    public Booking() {

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

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}