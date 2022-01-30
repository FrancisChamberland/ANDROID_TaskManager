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
    private LocalDateTime endDateTime;

    public Task(String name, LocalDateTime endDateTime){
        this.name = name;
        this.endDateTime = endDateTime;
        startDateTime = LocalDateTime.now();
    }

    public String getName(){
        return name;
    }

    public String getEndDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return endDateTime.format(formatter);
    }

    public int getProgressPercentage(){
        long secondsLeft = ChronoUnit.SECONDS.between(LocalDateTime.now(), endDateTime);
        long totalSeconds = ChronoUnit.SECONDS.between(startDateTime, endDateTime);
        long secondsElapsed = totalSeconds - secondsLeft;
        return (int) (secondsElapsed / totalSeconds * 100);
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
