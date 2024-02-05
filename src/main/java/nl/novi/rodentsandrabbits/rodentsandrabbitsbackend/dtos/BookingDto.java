package nl.novi.rodentsandrabbits.rodentsandrabbitsbackend.dtos;

import java.util.Date;
import java.util.List;

public class BookingDto {

    private Long id;
    private Date startDate;
    private Date endDate;
    private String additionalInfo;

    private List<Long> petIds;

    public BookingDto(Long id, Date startDate, Date endDate, String additionalInfo, List<Long> petIds) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.additionalInfo = additionalInfo;
        this.petIds = petIds;
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

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }
}

