package ca.mcgill.ecse321.project321;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Timeslot {

    private String startTime;
    private String endTime;
    private String date;

    public Timeslot(String startTime, String endTime, String date){
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
