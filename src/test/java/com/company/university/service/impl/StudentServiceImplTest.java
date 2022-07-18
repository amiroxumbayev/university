package com.company.university.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
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
import com.company.university.dao.StudentDao;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Student;
import com.company.university.domain.Schedule.Week;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest extends AbstractPersonServiceImplTest{
    
    @Mock
    private StudentDao studentDao;
    @Mock
    private GroupDao groupDao;
    @InjectMocks
    private StudentServiceImpl studentService;
    
    @Test
    void findScheduleByDayOfWeek_shouldFindEntries_whenStatementCorrect() {
        mockFindScheduleMethod();
        List<Schedule> mondaySchedule = new ArrayList<>();
        mondaySchedule.add(getMondaySchedule());
        List<Schedule> actual = studentService.findScheduleByDayOfWeek(1L, Week.MONDAY);
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
        Map<Week, List<Schedule>> actual = studentService.findWeekSchedule(1L);
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
        when(studentDao.save(Student.builder().build())).thenReturn(1L);
        Long actual = studentService.save(Student.builder().build());
        Long expected = 1L;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldFindEntry_whenStatementCorrect() {
        Student student = Student.builder().build();
        when(studentDao.findById(1L)).thenReturn(Optional.of(student));
        Student actual = studentService.findById(1L);
        Student expected = student;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Student> items = new ArrayList<>();
        items.add(Student.builder().build());
        when(studentDao.findAll()).thenReturn(items);
        List<Student> actual = studentService.findAll();
        List<Student> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAll_shouldFindEntriesByPage_whenStatementCorrect() {
        List<Student> items = new ArrayList<>();
        items.add(Student.builder().build());
        when(studentDao.findAll(new Page(1,1))).thenReturn(items);
        List<Student> actual = studentService.findAll(new Page(1,1));
        List<Student> expected = items;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Student student = Student.builder().build();
        studentService.update(student);
        verify(studentDao).update(student);
    }
    
    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        studentService.deleteById(1L);
        verify(studentDao).deleteById(1L);
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
        Optional<Group> optional = Optional.of(group);
        when(groupDao.findStudentGroupById(1L)).thenReturn(optional);
    }
}
