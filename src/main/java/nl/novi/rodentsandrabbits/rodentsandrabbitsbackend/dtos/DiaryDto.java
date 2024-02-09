package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

public class DiaryDto {

    public Long id;

    public DiaryDto() {
    }

    public DiaryDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
