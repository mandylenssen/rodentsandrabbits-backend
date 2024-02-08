package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "pets")

public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private Date birthday;
    private String species;
    private String gender;
    private String details;
    private String medication;
    private String diet;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_data_id", referencedColumnName = "id")
    private ImageData imageData;

    @ManyToMany(mappedBy = "pets")
    private Set<Booking> bookings = new HashSet<>();

    @OneToMany(mappedBy = "pet")
    @JsonIgnore
    List<DiaryLog> diaryLogs;

    public Pet() {
    }

    public Pet(Long id, String name, Date birthday, String species, String gender, String details, String medication, String diet, User owner) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.species = species;
        this.gender = gender;
        this.details = details;
        this.medication = medication;
        this.diet = diet;
        this.owner = owner;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<DiaryLog> getDiaryLogs() {
        return diaryLogs;
    }


    public void setDiaryLogs(List<DiaryLog> diaryLogs) {
        this.diaryLogs = diaryLogs;
    }

    public ImageData getImageData() {
        return this.imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }
}
