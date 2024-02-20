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

//    @Lob
    private byte[] imageData;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logbook_log_id", referencedColumnName = "id")
    private LogbookLog logbookLog;

    public ImageData() {
    }


    public ImageData(MultipartFile multipartFile) throws IOException {
        this.setName(multipartFile.getName());
        this.setType(multipartFile.getContentType());
        this.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
    }

    public void updateImageData(MultipartFile multipartFile) throws IOException {
        this.setName(multipartFile.getName());
        this.setType(multipartFile.getContentType());
        this.setImageData(ImageUtil.compressImage(multipartFile.getBytes()));
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
