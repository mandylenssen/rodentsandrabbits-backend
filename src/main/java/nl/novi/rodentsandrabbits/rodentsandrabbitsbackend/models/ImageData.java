package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.models;

import jakarta.persistence.*;
import nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.utils.ImageUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "image_data")
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;


    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @OneToOne
    @JoinColumn(name = "logbook_log_id", referencedColumnName = "id")
    private LogbookLog logbookLog;

    public ImageData() {
    }

    public ImageData(byte[] bytea, String name, String type) throws IOException {
        this.setName(name);
        this.setType(type);
        this.setImageData(ImageUtil.compressImage(bytea));
    }

    public void updateImageData(byte[] bytea, String name, String type) throws IOException {
        this.setName(name);
        this.setType(type);
        this.setImageData(ImageUtil.compressImage(bytea));
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public LogbookLog getLogbookLog() {
        return logbookLog;
    }

    public void setLogbookLog(LogbookLog logbookLog) {
        this.logbookLog = logbookLog;
    }
}
