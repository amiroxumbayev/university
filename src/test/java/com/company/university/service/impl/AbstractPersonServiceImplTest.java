package com.company.university.service.impl;

import java.time.LocalDateTime;
import com.company.university.domain.Schedule;
import com.company.university.domain.Subject;
import com.company.university.domain.Schedule.Week;

public class AbstractPersonServiceImplTest {
    
    protected Schedule getMondaySchedule() {
        LocalDateTime startTime = LocalDateTime.of(2022, 6, 2, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2022, 6, 2, 2, 1);
        return Schedule.builder()
                .withId(1L)
                .withSubject(new Subject(1L, "test", 1))
                .withWeek(Week.MONDAY)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build();
    }
    
    protected Schedule getTuesdaySchedule() {
        LocalDateTime startTime = LocalDateTime.of(2022, 6, 2, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2022, 6, 2, 2, 1);
        return Schedule.builder()
                .withId(2L)
                .withSubject(new Subject(1L, "test", 1))
                .withWeek(Week.TUESDAY)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build();
    }
}
