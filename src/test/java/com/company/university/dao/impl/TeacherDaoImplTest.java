package com.company.university.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.TeacherDao;
import com.company.university.domain.Teacher;
import com.company.university.domain.Group;
import com.company.university.domain.Page;

class TeacherDaoImplTest extends AbstractDaoImplTest {

    private TeacherDao teacherDao;

    @BeforeEach
    void setDao() {
        teacherDao = new TeacherDaoImpl(dataSource);
    }

    @Test
    void save_shouldInsertEntryToDB_whenStatementCorrect() {
        List<Group> groups = new ArrayList<Group>();
        groups.add(Group.builder()
                .withId(1L)
                .build());
        Teacher teacher = Teacher.builder()
                .withId(null)
                .withName("name")
                .withEmail("email")
                .withPassword("password")
                .withGroups(groups)
                .build();
        Long id = teacherDao.save(teacher);
        Teacher actual = teacherDao.findById(id).get();
        Teacher expected = Teacher.builder()
                .withId(3L)
                .withName("name")
                .withEmail("email")
                .withPassword("password")
                .build();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void findById_shouldFindEntryById_whenStatementCorrect() {

        Teacher actual = teacherDao.findById(1L).get();
        Teacher expected = Teacher.builder()
                .withId(1L)
                .withName("test")
                .withEmail("test")
                .withPassword("test")
                .withGroups(null)
                .build();
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldThrowNoSuchElementException_whenStatementIncorrect() {
        
        Throwable actual = assertThrows(NoSuchElementException.class, () -> {
            teacherDao.findById(3L).get();
        });
        String expected = "No value present";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Teacher> actual = teacherDao.findAll();
        List<Teacher> expected = new ArrayList<>();
        expected.add(Teacher.builder()
                .withId(1L)
                .withName("test")
                .withEmail("test")
                .withPassword("test")
                .withGroups(null)
                .build());
        expected.add(Teacher.builder()
                .withId(2L)
                .withName("test1")
                .withEmail("test1")
                .withPassword("test1")
                .withGroups(null)
                .build());
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findAllByPage_shouldFindCertainEntries_whenStatementCorrect() {
        Page page = new Page(2,1);
        List<Teacher> actual = teacherDao.findAll(page);
        List<Teacher> expected = new ArrayList<>();
        expected.add(Teacher.builder()
                .withId(2L)
                .withName("test1")
                .withEmail("test1")
                .withPassword("test1")
                .withGroups(null)
                .build());
        assertThat(actual, equalTo(expected));
    }

    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Teacher updatedFactory = Teacher.builder()
                .withId(1L)
                .withName("updatedtest")
                .withEmail("test")
                .withPassword("test")
                .build();
        teacherDao.update(updatedFactory);
        Teacher actual = teacherDao.findById(1L).get();
        Teacher expected = updatedFactory;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldThrowDataBaseRuntimeException_whenStatementIncorrect() {
        Throwable actual = assertThrows(DataBaseRuntimeException.class, () -> {
            teacherDao.update(null);
        });
        String expected = "Failed to update entry in database";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        teacherDao.deleteById(2L);
        List<Teacher> actual = teacherDao.findAll();
        int expected = 1;
        assertThat(actual.size(), equalTo(expected));
    }
    
}
