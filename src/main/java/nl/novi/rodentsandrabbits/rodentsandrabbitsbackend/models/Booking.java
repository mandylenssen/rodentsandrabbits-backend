package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private Long id;

    private Date startDate;
    private Date endDate;
    private String additionalInfo;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Pet> pets = new ArrayList<>();

    public Booking(Long id, Date startDate, Date endDate, String additionalInfo, List<Pet> pets) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalInfo = additionalInfo;
        this.getPets().addAll(pets);
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
