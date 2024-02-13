package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

public class LogbookDto {

    public Long id;

    public LogbookDto() {
    }

    public LogbookDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
