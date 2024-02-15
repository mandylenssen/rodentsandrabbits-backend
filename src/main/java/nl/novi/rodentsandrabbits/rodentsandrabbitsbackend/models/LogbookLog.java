package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "logbook_logs")
public class LogbookLog {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    @Column(nullable = false, length = 10000)
    private String entry;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "logbook_log_pets",
            joinColumns = @JoinColumn(name = "logbook_log_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private List<Pet> pets;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logbook_id", nullable = false)
    private Logbook logbook;

    @OneToMany(mappedBy = "logbookLog", cascade = CascadeType.ALL)
    private List<ImageData> logbookImageData;

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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Logbook getLogbook() {
        return logbook;
    }

    public void setLogbook(Logbook logbook) {
        this.logbook = logbook;
    }

    public List<ImageData> getLogbookImageData() {
        return logbookImageData;
    }

    public void setLogbookImageData(List<ImageData> logbookImageData) {
        this.logbookImageData = logbookImageData;
    }
}
