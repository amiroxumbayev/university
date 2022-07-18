package com.company.university.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.GroupDao;
import com.company.university.domain.Faculty;
import com.company.university.domain.Group;
import com.company.university.domain.Page;
import com.company.university.domain.Schedule;
import com.company.university.domain.Student;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.domain.Task.Type;
import com.company.university.domain.Schedule.Week;

class GroupDaoImplTest extends AbstractDaoImplTest {

    private GroupDao groupDao;

    @BeforeEach
    void setDao() {
        groupDao = new GroupDaoImpl(dataSource);
    }

    @Test
    void save_shouldInsertEntryToDB_whenStatementCorrect() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Group group = fillGroup(localDateTime);
        Long id = groupDao.save(group);
        Group actual = groupDao.findById(id).get();
        Group expected = getExpectedGroup(localDateTime);
        assertThat(actual, equalTo(expected));
    }

    private Group fillGroup(LocalDateTime localDateTime) {
        List<Student> students = new ArrayList<>();
        students.add(Student.builder()
                .withId(1L)
                .build());
        List<Schedule> schedule = new ArrayList<>();
        schedule.add(Schedule.builder()
                .withSubject(null)
                .withWeek(Week.MONDAY)
                .withStartTime(localDateTime)
                .withEndTime(localDateTime)
                .build());
        Map<Long, List<Schedule>> mapSchedule = new HashMap<>();
        mapSchedule.put(1L, schedule);
        List<Task> tasks = new ArrayList<>();
        tasks.add(Task.builder()
                .withTitle("title")
                .withSubject(new Subject(1L, null, 0))
                .withType(Type.LABORATORY)
                .withDescription("description")
                .build());
        Map<Long, List<Task>> mapTasks = new HashMap<>();
        mapTasks.put(1L, tasks);
        return Group.builder()
                .withName("name")
                .withFaculty(new Faculty(1L, "test", null))
                .withStudents(students)
                .withSchedule(mapSchedule)
                .withWorkPrograms(mapTasks)
                .build();
    }

    private Group getExpectedGroup(LocalDateTime localDateTime) {
        List<Schedule> schedule = new ArrayList<>();
        Subject subject = new Subject(1L, "test", 1);
        schedule.add(Schedule.builder()
                .withId(3L)
                .withSubject(subject)
                .withWeek(Week.MONDAY)
                .withStartTime(localDateTime)
                .withEndTime(localDateTime)
                .build());
        Map<Long, List<Schedule>> mapSchedule = new HashMap<>();
        mapSchedule.put(1L, schedule);
        List<Task> tasks = new ArrayList<>();
        tasks.add(Task.builder()
                .withId(2L)
                .withTitle("title")
                .withSubject(subject)
                .withType(Type.LABORATORY)
                .withDescription("description")
                .build());
        Map<Long, List<Task>> mapTasks = new HashMap<>();
        mapTasks.put(1L, tasks);
        return Group.builder()
                .withId(3L)
                .withName("name")
                .withSchedule(mapSchedule)
                .withWorkPrograms(mapTasks)
                .build();
    }

    @Test
    void findById_shouldFindEntryById_whenStatementCorrect() {
        Group actual = groupDao.findById(1L).get();
        Group expected = Group.builder()
                .withId(1L)
                .withName("test")
                .withSchedule(getMapSchedule())
                .withWorkPrograms(getMapTasks())
                .build();
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldThrowNoSuchElementException_whenStatementIncorrect() {
        
        Throwable actual = assertThrows(NoSuchElementException.class, () -> {
            groupDao.findById(3L).get();
        });
        String expected = "No value present";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Group> actual = groupDao.findAll();
        List<Group> expected = new ArrayList<>();
        expected.add(Group.builder()
                .withId(1L)
                .withName("test")
                .withSchedule(getMapSchedule())
                .withWorkPrograms(getMapTasks())
                .build());
        expected.add(Group.builder()
                .withId(2L)
                .withName("test1")
                .withSchedule(new HashMap<Long, List<Schedule>>())
                .withWorkPrograms(new HashMap<Long, List<Task>>())
                .build());
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAllByPage_shouldFindCertainEntries_whenStatementCorrect() {
        Page page = new Page(2,1);
        List<Group> actual = groupDao.findAll(page);
        List<Group> expected = new ArrayList<>();
        expected.add(Group.builder()
                .withId(2L)
                .withName("test1")
                .withSchedule(new HashMap<Long, List<Schedule>>())
                .withWorkPrograms(new HashMap<Long, List<Task>>())
                .build());
        assertThat(actual, equalTo(expected));
    }

    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Group updatedFactory = Group.builder()
                .withId(1L)
                .withName("updatedtest")
                .withFaculty(new Faculty(1L, "test", null))
                .withSchedule(new HashMap<Long, List<Schedule>>())
                .withWorkPrograms(new HashMap<Long, List<Task>>())
                .build();
        groupDao.update(updatedFactory);
        Group actual = groupDao.findById(1L).get();
        Group expected = Group.builder()
                .withId(1L)
                .withName("updatedtest")
                .withSchedule(getMapSchedule())
                .withWorkPrograms(getMapTasks())
                .build();
        assertThat(actual, equalTo(expected));
    }
    
    private Map<Long, List<Schedule>> getMapSchedule() {
        LocalDateTime startTime = LocalDateTime.of(2022, 6, 2, 1, 1);
        LocalDateTime endTime = LocalDateTime.of(2022, 6, 2, 2, 1);
        List<Schedule> schedule = new ArrayList<>();
        schedule.add(Schedule.builder()
                .withId(1L)
                .withSubject(new Subject(1L, "test", 1))
                .withWeek(Week.MONDAY)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build());
        schedule.add(Schedule.builder()
                .withId(2L)
                .withSubject(new Subject(1L, "test", 1))
                .withWeek(Week.TUESDAY)
                .withStartTime(startTime)
                .withEndTime(endTime)
                .build());
        Map<Long, List<Schedule>> mapTasks = new HashMap<>();
        mapTasks.put(1L, schedule);
        return mapTasks;
    }
    
    private Map<Long, List<Task>> getMapTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(Task.builder()
                .withId(1L)
                .withTitle("test")
                .withSubject(new Subject(1L, "test", 1))
                .withType(Type.LABORATORY)
                .withDescription("test")
                .build());
        Map<Long, List<Task>> mapTasks = new HashMap<>();
        mapTasks.put(1L, tasks);
        return mapTasks;
    }
    
    @Test
    void update_shouldThrowDataBaseRuntimeException_whenStatementIncorrect() {
        Throwable actual = assertThrows(DataBaseRuntimeException.class, () -> {
            groupDao.update(null);
        });
        String expected = "Failed to update entry in database";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        groupDao.deleteById(2L);
        List<Group> actual = groupDao.findAll();
        int expected = 1;
        assertThat(actual.size(), equalTo(expected));
    }
    
    @Test
    void findStudentGroupById_shouldFindEntry_whenStatementCorrect() {
        Group actual = groupDao.findStudentGroupById(1L).orElseThrow(DataBaseRuntimeException::new);
        Group expected = Group.builder()
                .withId(1L)
                .withName("test")
                .withSchedule(getMapSchedule())
                .withWorkPrograms(getMapTasks())
                .build();
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findGroupsById_shouldFindEntries_whenStatementCorrect() {
        List<Group> actual = groupDao.findTeacherGroupsById(1L);
        List<Group> expected = new ArrayList<>();
        expected.add(Group.builder()
                .withId(1L)
                .withName("test")
                .withSchedule(getMapSchedule())
                .withWorkPrograms(getMapTasks())
                .build());
        assertThat(actual, equalTo(expected));
    }

}
