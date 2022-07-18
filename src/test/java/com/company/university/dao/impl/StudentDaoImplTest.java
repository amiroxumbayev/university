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
import com.company.university.dao.StudentDao;
import com.company.university.domain.Grade;
import com.company.university.domain.Page;
import com.company.university.domain.Student;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.domain.Task.Type;

class StudentDaoImplTest extends AbstractDaoImplTest {

    private StudentDao studentDao;

    @BeforeEach
    void setDao() {
        studentDao = new StudentDaoImpl(dataSource);
    }

    @Test
    void save_shouldInsertEntryToDB_whenStatementCorrect() {
        List<Grade> grades = new ArrayList<Grade>();
        grades.add(new Grade(1L, Task.builder()
                .withId(1L)
                .withTitle("test")
                .withSubject(new Subject(1L, "test", 1))
                .withType(Type.LABORATORY)
                .withDescription("test")
                .build(), 100.));
        Student student = Student.builder()
                .withId(null)
                .withName("name")
                .withEmail("email")
                .withPassword("password")
                .withYear(2020)
                .withAvgGpa(4.)
                .withGrades(grades)
                .build();
        Long id = studentDao.save(student);
        Student actual = studentDao.findById(id).get();
        Student expected = Student.builder()
                .withId(3L)
                .withName("name")
                .withEmail("email")
                .withPassword("password")
                .withYear(2020)
                .withAvgGpa(4.)
                .withGrades(grades)
                .build();
        assertThat(actual, equalTo(expected));
    }

    @Test
    void findById_shouldFindEntryById_whenStatementCorrect() {
        Student actual = studentDao.findById(1L).get();
        Student expected = Student.builder()
                .withId(1L)
                .withName("test")
                .withEmail("test")
                .withPassword("test")
                .withYear(2022)
                .withAvgGpa(4.)
                .withGrades(new ArrayList<Grade>())
                .build();
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void findById_shouldThrowNoSuchElementException_whenStatementIncorrect() {
        
        Throwable actual = assertThrows(NoSuchElementException.class, () -> {
            studentDao.findById(3L).get();
        });
        String expected = "No value present";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void findAll_shouldFindEntries_whenStatementCorrect() {
        List<Student> actual = studentDao.findAll();
        List<Student> expected = new ArrayList<>();
        expected.add(Student.builder()
                .withId(1L)
                .withName("test")
                .withEmail("test")
                .withPassword("test")
                .withYear(2022)
                .withAvgGpa(4.)
                .withGrades(new ArrayList<Grade>())
                .build());
        expected.add(Student.builder()
                .withId(2L)
                .withName("test1")
                .withEmail("test1")
                .withPassword("test1")
                .withYear(2022)
                .withAvgGpa(4.)
                .withGrades(new ArrayList<Grade>())
                .build());
        assertThat(actual, equalTo(expected));
    }

    @Test
    void findAllByPage_shouldFindCertainEntries_whenStatementCorrect() {
        Page page = new Page(2,1);
        List<Student> actual = studentDao.findAll(page);
        List<Student> expected = new ArrayList<>();
        expected.add(Student.builder()
                .withId(2L)
                .withName("test1")
                .withEmail("test1")
                .withPassword("test1")
                .withYear(2022)
                .withAvgGpa(4.)
                .withGrades(new ArrayList<Grade>())
                .build());
        assertThat(actual, equalTo(expected));
    }

    @Test
    void update_shouldUpdateEntry_whenStatementCorrect() {
        Student updatedFactory = Student.builder()
                .withId(1L)
                .withName("updatedtest")
                .withEmail("test")
                .withYear(2022)
                .withAvgGpa(4.)
                .withGrades(new ArrayList<Grade>())
                .withPassword("test")
                .build();
        studentDao.update(updatedFactory);
        Student actual = studentDao.findById(1L).get();
        Student expected = updatedFactory;
        assertThat(actual, equalTo(expected));
    }
    
    @Test
    void update_shouldThrowDataBaseRuntimeException_whenStatementIncorrect() {
        Throwable actual = assertThrows(DataBaseRuntimeException.class, () -> {
            studentDao.update(null);
        });
        String expected = "Failed to update entry in database";
        assertThat(actual.getMessage(), equalTo(expected));
    }

    @Test
    void deleteById_shouldDeleteEntry_whenStatementCorrect() {
        studentDao.deleteById(2L);
        List<Student> actual = studentDao.findAll();
        int expected = 1;
        assertThat(actual.size(), equalTo(expected));
    }

}
