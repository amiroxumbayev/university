package com.company.university.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Schedule.Week;

public interface PersonService {
    
    List<Schedule> findScheduleByDayOfWeek(Long id, Week dayOfWeek);
    Map<Week, List<Schedule>> findWeekSchedule(Long id);
}
