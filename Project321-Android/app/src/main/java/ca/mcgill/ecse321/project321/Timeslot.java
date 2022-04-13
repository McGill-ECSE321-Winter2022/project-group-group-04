package ca.mcgill.ecse321.project321;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Timeslot {

    private Time startTime;
    private Time endTime;
    private Date date;

    public Timeslot(Time startTime, Time endTime, Date date){
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }
    public String getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str_date = formatter.format(this.date);
        return str_date;
    }
    public void setDate(Date newdate){
        this.date = newdate;
    }
    public String getStartTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String str_time = formatter.format(this.startTime);
        return str_time;
    }
    public void setStartTime(Time newStartTime){
        this.startTime = newStartTime;
    }
    public String getEndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String str_time = formatter.format(this.endTime);
        return str_time;
    }
    public void setEndTime(Time newEndTime){
        this.startTime = newEndTime;
    }
}
