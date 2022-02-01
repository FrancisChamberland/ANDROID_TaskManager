package com.chamberland.kickmyb.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Task {
    private String name;
    private ZonedDateTime startDateTime;
    private ZonedDateTime dueDateTime;

    public Task(String name, LocalDateTime dueDateTime){
        this.name = name;
        this.dueDateTime = getDateTimeFromLocal(dueDateTime);
        startDateTime = getDateTimeNow();
    }

    public String getName(){
        return name;
    }

    public String getEndDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dueDateTime.format(formatter);
    }

    public int getProgressPercentage(){
        ZonedDateTime currentDateTime = getDateTimeNow();
        long secondsLeft = ChronoUnit.SECONDS.between(currentDateTime, dueDateTime);
        long totalSeconds = ChronoUnit.SECONDS.between(startDateTime, dueDateTime);
        long secondsElapsed = totalSeconds - secondsLeft;
        return (int) ((double)secondsElapsed / totalSeconds * 100);
    }

    public String getFormattedTimeElapsed(){
        long daysElapsed = getDaysElapsed();
        long hoursElapsed = getHoursElapsed();
        long minutesElapsed = getMinutesElapsed();
        if (daysElapsed > 0){
            return getTimeFormatted(daysElapsed, "journée");
        } else if (hoursElapsed > 0){
            return getTimeFormatted(hoursElapsed, "heure");
        } else if (minutesElapsed > 0) {
            return getTimeFormatted(minutesElapsed, "minute");
        } else {
            return "moins d'une minute";
        }
    }

    public ZonedDateTime getDateTimeFromLocal(LocalDateTime dateTime){
        ZoneId zone = ZoneId.of( "America/Montreal" );
        return ZonedDateTime.of(dateTime, zone);
    }

    public ZonedDateTime getDateTimeNow() {
        ZoneId zone = ZoneId.of( "America/Montreal" );
        return ZonedDateTime.now(zone);
    }

    private String getTimeFormatted(long time, String timeLevel){
        String timeString = String.valueOf(time);
        String timeLevelFormatted = time > 1 ? timeLevel + 's' : timeLevel;
        return String.format("%s %s", timeString, timeLevelFormatted);
    }

    private long getDaysElapsed(){
        ZonedDateTime currentDateTime = getDateTimeNow();
        return ChronoUnit.DAYS.between(startDateTime, currentDateTime);
    }

    private long getHoursElapsed(){
        ZonedDateTime currentDateTime = getDateTimeNow();
        return ChronoUnit.HOURS.between(startDateTime, currentDateTime);
    }

    private long getMinutesElapsed(){
        ZonedDateTime currentDateTime = getDateTimeNow();
        return ChronoUnit.MINUTES.between(startDateTime, currentDateTime);
    }
}
