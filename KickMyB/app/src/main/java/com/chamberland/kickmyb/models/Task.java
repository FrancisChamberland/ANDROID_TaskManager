package com.chamberland.kickmyb.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Task {
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime dueDateTime;

    public Task(String name, LocalDateTime endDateTime){
        this.name = name;
        this.dueDateTime = endDateTime;
        startDateTime = LocalDateTime.now();
    }

    public String getName(){
        return name;
    }

    public String getEndDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dueDateTime.format(formatter);
    }

    public int getProgressPercentage(){
        long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now(), dueDateTime);
        long totalSeconds = ChronoUnit.SECONDS.between(startDateTime, dueDateTime);
        long secondsElapsed = totalSeconds - secondsLeft;
        return (int) (secondsElapsed / totalSeconds * 100);
    }

    public String getFormattedTimeElapsed(){
        long daysElapsed = getDaysElapsed();
        long hoursElapsed = getHoursElapsed();
        long minutesElapsed = getMinutesElapsed();
        if (daysElapsed > 0){
            return getTimeFormatted(daysElapsed, "journée");
        } else if (hoursElapsed > 0){
            return getTimeFormatted(daysElapsed, "heure");
        } else if (minutesElapsed > 0) {
            return getTimeFormatted(daysElapsed, "minute");
        } else {
            return "moins d'une minute";
        }
    }

    private String getTimeFormatted(long time, String timeLevel){
        String timeString = String.valueOf(time);
        String timeLevelFormatted = time > 1 ? timeLevel + 's' : timeLevel;
        return String.format("%s %s", timeString, timeLevelFormatted);
    }

    private long getDaysElapsed(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return ChronoUnit.DAYS.between(startDateTime, currentDateTime);
    }

    private long getHoursElapsed(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return ChronoUnit.HOURS.between(startDateTime, currentDateTime);
    }

    private long getMinutesElapsed(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        return ChronoUnit.MINUTES.between(startDateTime, currentDateTime);
    }
}
