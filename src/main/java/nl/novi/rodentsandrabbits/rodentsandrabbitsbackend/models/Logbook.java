package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;
import jakarta.persistence.*;


import java.util.List;

@Entity
@Table(name = "logbooks")
public class Logbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "logbook", cascade = CascadeType.ALL)
    private List<LogbookLog> logs;

    public Logbook() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<LogbookLog> getLogs() {
        return logs;
    }

    public void setLogs(List<LogbookLog> logs) {
        this.logs = logs;
    }
}
