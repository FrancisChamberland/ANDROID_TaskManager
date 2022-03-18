package com.chamberland.kickmyb.transfer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    public Long id;
    public String name;
    public int percentageDone;
    public int percentageTimeSpent;
    public Date deadline;

    public String getFormattedDeadLine(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deadline);
    }
}
