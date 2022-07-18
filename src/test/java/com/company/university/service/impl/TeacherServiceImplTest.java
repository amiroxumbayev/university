package com.company.university.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.university.dao.GroupDao;
import com.company.university.dao.TeacherDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Teacher;
import com.company.university.domain.Schedule.Week;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest extends AbstractPersonServiceImplTest {
    
    @Mock
    private TeacherDao teacherDao;
    @Mock
    private GroupDao groupDao;
    @InjectMocks
    private TeacherServiceImpl teacherService;
    
    @Test
    void findScheduleByDayOfWeek_shouldFindEntries_whenStatementCorrect() {
        mockFindScheduleMethod();
        List<Schedule> mondaySchedule = new ArrayList<>();
        mondaySchedule.add(getMondaySchedule());
        List<Schedule> actual = teacherService.findScheduleByDayOfWeek(1L, Week.MONDAY);
        List<Schedule> expected = mondaySchedule;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findWeekSchedule_shouldFindEntries_whenStatementCorrect() {
        mockFindScheduleMethod();
        List<Schedule> mondaySchedule = new ArrayList<>();
        mondaySchedule.add(getMondaySchedule());
        List<Schedule> tuesdaySchedule = new ArrayList<>();
        tuesdaySchedule.add(getTuesdaySchedule());
        Map<Week, List<Schedule>> actual = teacherService.findWeekSchedule(1L);
        Map<Week, List<Schedule>> expected = new HashMap<>();
        expected.put(Week.MONDAY, mondaySchedule);
        expected.put(Week.TUESDAY, tuesdaySchedule);
        expected.put(Week.WEDNESDAY, new ArrayList<>());
        expected.put(Week.THURSDAY, new ArrayList<>());
        expected.put(Week.FRIDAY, new ArrayList<>());
        expected.put(Week.SATURDAY, new ArrayList<>());
        expected.put(Week.SUNDAY, new ArrayList<>());
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void save_shouldSaveEntity_whenStatementCorrect() {
        when(teacherDao.save(Teacher.builder().build())).thenReturn(1L);
        Long actual = teacherService.save(Teacher.builder().build());
        Long expected = 1L;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntry_whenStatementCorrect() {
        Teacher teacher = Teacher.builder().build();
        when(teacherDao.findById(1L)).thenReturn(Optional.of(teacher));
        Teacher actual = teacherService.findById(1L);
        Teacher expected = teacher;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Teacher> items = new ArrayList<>();
        items.add(Teacher.builder().build());
        when(teacherDao.findAll()).thenReturn(items);
        List<Teacher> actual = teacherService.findAll();
        List<Teacher> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntriesByPage_whenStatementCorrect() {
        List<Teacher> items = new ArrayList<>();
        items.add(Teacher.builder().build());
        when(teacherDao.findAll(new Page(1,1))).thenReturn(items);
        List<Teacher> actual = teacherService.findAll(new Page(1,1));
        List<Teacher> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Teacher teacher = Teacher.builder().build();
        teacherService.update(teacher);
        verify(teacherDao).update(teacher);
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        teacherService.deleteById(1L);
        verify(teacherDao).deleteById(1L);
    }
    
    private void mockFindScheduleMethod() {
        List<Schedule> schedule = new ArrayList<>();
        schedule.add(getMondaySchedule());
        schedule.add(getTuesdaySchedule());
        Map<Long, List<Schedule>> mapSchedule = new HashMap<>();
        mapSchedule.put(1L, schedule);
        Group group = Group.builder()
                .withId(1L)
                .withName("test")
                .withSchedule(mapSchedule)
                .build();
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        when(groupDao.findTeacherGroupsById(1L)).thenReturn(groups);
    }
}
