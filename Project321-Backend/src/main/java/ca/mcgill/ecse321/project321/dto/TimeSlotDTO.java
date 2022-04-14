package ca.mcgill.ecse321.project321.dto;

import java.sql.Date;
import java.sql.Time;

public class TimeSlotDTO {
    private Date    date;
    private Time    startTime;
    private Time    endTime;
    private Integer maxOrderPerSlot;

    public TimeSlotDTO() {}

    public TimeSlotDTO(Date date, Time startTime, Time endTime, Integer maxOrderPerSlot) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxOrderPerSlot = maxOrderPerSlot;
    }

    public Date getDate() {
        return this.date;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    public Integer getMaxOrderPerSlot() {
        return this.maxOrderPerSlot;
    }
}
