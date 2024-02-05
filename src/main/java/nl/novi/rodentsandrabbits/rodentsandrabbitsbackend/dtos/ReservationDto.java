package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

import java.util.Date;

public class ReservationDto {

    private Long id;
    private Date startDate;
    private Date endDate;


    public ReservationDto(Long id, Date startDate, Date endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ReservationDto() {

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
