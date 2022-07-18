package com.company.university.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.company.university.domain.Schedule;
import com.company.university.domain.Schedule.Week;
import com.company.university.service.PersonService;

public abstract class AbstractPersonServiceImpl implements PersonService {

    @Override
    public List<Schedule> findScheduleByDayOfWeek(Long id, Week dayOfWeek) {
        List<Schedule> schedule = findSchedule(id);
        return schedule.stream()
                .filter(e -> e.getWeek().equals(dayOfWeek))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Week, List<Schedule>> findWeekSchedule(Long id) {
        Map<Week, List<Schedule>> scheduleBydayOfWeek = new HashMap<>();
        List<Schedule> schedule = findSchedule(id);
        scheduleBydayOfWeek.put(Week.MONDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.TUESDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.WEDNESDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.THURSDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.FRIDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.SATURDAY, new ArrayList<>());
        scheduleBydayOfWeek.put(Week.SUNDAY, new ArrayList<>());
        schedule.forEach(e -> {
            List<Schedule> daySchedule = scheduleBydayOfWeek.get(e.getWeek());
            daySchedule.add(e);
            scheduleBydayOfWeek.put(e.getWeek(), daySchedule);
            });
        return scheduleBydayOfWeek;
    }
    
    protected abstract List<Schedule> findSchedule(Long id);

}
