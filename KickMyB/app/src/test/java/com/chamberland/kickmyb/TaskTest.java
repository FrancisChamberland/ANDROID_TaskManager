package com.chamberland.kickmyb;

import org.junit.Test;

import static org.junit.Assert.*;

import com.chamberland.kickmyb.models.Task;

import java.time.LocalDateTime;

public class TaskTest {
    @Test
    public void getProgressPercentageIsCorrect() {
        LocalDateTime endDateTime = LocalDateTime.of(2022, 2, 10, 10, 10);
        Task task = new Task("Do something", endDateTime);
        int expectedResult = 0;
        int result = task.getProgressPercentage();
        assertEquals(expectedResult, result);
    }
}