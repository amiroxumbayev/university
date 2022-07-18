package com.company.university.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.StudentDao;
import com.company.university.domain.Grade;
import com.company.university.domain.Student;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.domain.Task.Type;

@Repository
public class StudentDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentDao {

    private static final String SAVE_QUERY = "INSERT INTO public.Student (name, email, password, year, avg_gpa) values(?, ?, ?, ?, ?)";
    private static final String SAVE_GRADES_QUERY = "INSERT INTO public.Grade (student_id, task_id, score) values (?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM public.Student WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM public.Student WHERE email = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM public.Student";
    private static final String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM public.Student ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    private static final String FIND_GRADES_BY_ID_QUERY = "SELECT * FROM public.Grade WHERE student_id = ?";
    private static final String FIND_TASK_BY_ID_QUERY = "SELECT * FROM public.Task WHERE id = ?";
    private static final String FIND_SUBJECT_BY_ID_QUERY = "SELECT * FROM public.Subject WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.Student SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM public.Student  WHERE id = ?;";
    private static final String DELETE_GRADES_BY_ID_QUERY = "DELETE FROM public.Grade WHERE student_id = ?";
    
    @Autowired
    public StudentDaoImpl(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_BY_PAGE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected RowMapper<Student> mapRowToEntity() throws SQLException {
        return this::mapRowToStudent;
    }

    private Student mapRowToStudent(ResultSet row, int rownum) throws SQLException {
        List<Grade> gradeList = jdbcTemplate.query(FIND_GRADES_BY_ID_QUERY, this::mapRowToGrade, row.getLong("id"));
        return Student.builder()
                .withId(row.getLong("id"))
                .withName(row.getString("name"))
                .withEmail(row.getString("email"))
                .withPassword(row.getString("password"))
                .withYear(row.getInt("year"))
                .withAvgGpa(row.getDouble("avg_gpa"))
                .withGrades(gradeList)
                .build();
    }

    private Grade mapRowToGrade(ResultSet row, int rownum) throws SQLException {
        List<Task> items = jdbcTemplate.query(FIND_TASK_BY_ID_QUERY, this::mapRowToTask, row.getLong("task_id"));
        return new Grade(row.getLong("id"), items.get(0), row.getDouble("score"));
    }

    private Task mapRowToTask(ResultSet row, int rownum) throws SQLException {
        List<Subject> items = jdbcTemplate.query(FIND_SUBJECT_BY_ID_QUERY, this::mapRowToSubject,
                row.getLong("subject_id"));
        return Task.builder()
                .withId(row.getLong("id"))
                .withTitle(row.getString("title"))
                .withSubject(items.get(rownum))
                .withType(Type.valueOf(row.getString("type").toUpperCase()))
                .withDescription(row.getString("description"))
                .build();
    }

    private Subject mapRowToSubject(ResultSet row, int rownum) throws SQLException {
        return new Subject(row.getLong("id"), row.getString("name"), row.getInt("credit"));
    }

    @Override
    protected PreparedStatement setSaveEntry(PreparedStatement ps, Student entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getEmail());
        ps.setString(3, entity.getPassword());
        ps.setInt(4, entity.getYear());
        ps.setDouble(5, entity.getAvgGpa());
        return ps;
    }

    @Override
    protected PreparedStatementSetter setUpdateEntry(Student entity) throws SQLException {
        return new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getEmail());
                ps.setString(3, entity.getPassword());
                ps.setLong(4, entity.getId());
            }
        };
    }

    @Override
    protected void saveDependentEntry(Student entity, Long id) throws SQLException {
        entity.getGrades().forEach(
                s -> jdbcTemplate.update(SAVE_GRADES_QUERY, id, s.getTask().getId(), s.getScore()));
    }

    @Override
    protected void deleteDependentEntry(Long id) throws SQLException {
        jdbcTemplate.update(DELETE_GRADES_BY_ID_QUERY, id);
    }
    
    @Override
    public Optional<Student> findByEmail(String email) {
        List<Student> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, mapRowToEntity(), email);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }
    
}
