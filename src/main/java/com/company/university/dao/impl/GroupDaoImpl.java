package com.company.university.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.GroupDao;
import com.company.university.domain.Group;
import com.company.university.domain.Schedule;
import com.company.university.domain.Schedule.Week;
import com.company.university.domain.Subject;
import com.company.university.domain.Task;
import com.company.university.domain.Task.Type;

@Repository
public class GroupDaoImpl extends AbstractCrudDaoImpl<Group> implements GroupDao {

    private static final String SAVE_QUERY = "INSERT INTO public.Group_ (name, faculty_id) values (?,?)";
    private static final String SAVE_STUDENTS_QUERY = "INSERT INTO public.Group_Student (group_id, student_id) values (?, ?)";
    private static final String SAVE_SCHEDULE_QUERY = "INSERT INTO public.Schedule (group_id, subject_id, day_week, start_time, end_time) values"
            + "(?, ?, ?, ?, ?)";
    private static final String SAVE_WORK_PROGRAMS_QUERY = "INSERT INTO public.Task (group_id, subject_id, title, type, description) values"
            + "(?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM Group_ WHERE id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM Group_ WHERE name = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM Group_";
    private static final String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM public.Group_ ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    private static final String FIND_SCHEDULE_BY_ID_QUERY = "SELECT * FROM public.Schedule WHERE group_id = ?";
    private static final String FIND_WORK_PROGRAMS_BY_ID_QUERY = "SELECT * FROM public.Task WHERE group_id = ?";
    private static final String FIND_ALL_SUBJECTS_QUERY = "SELECT * FROM public.Subject";
    private static final String FIND_SUBJECT_BY_ID_QUERY = "SELECT * FROM public.Subject WHERE id = ?";
    private static final String FIND_TEACHER_GROUPS_BY_ID_QUERY = "SELECT g.id, g.name, g.faculty_id FROM public.Teacher_Group tg INNER JOIN public.Group_  g ON tg.group_id = g.id"
            + " WHERE tg.teacher_id = ?";
    private static final String FIND_STUDENT_GROUP_BY_ID_QUERY = "SELECT g.id, g.name, g.faculty_id FROM public.Group_Student gs INNER JOIN public.Group_  g ON gs.group_id = g.id"
            + " WHERE gs.student_id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.Group_ SET name = ?, faculty_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM public.Group_ WHERE id = ?";
    private static final String DELETE_STUDENTS_BY_ID_QUERY = "DELETE FROM public.Group_Student WHERE group_id = ?";
    private static final String DELETE_SCHEDULE_BY_ID_QUERY = "DELETE FROM public.Schedule WHERE group_id = ?";
    private static final String DELETE_WORK_PROGRAMS_BY_ID_QUERY = "DELETE FROM public.Task WHERE group_id = ?";
    
    @Autowired
    public GroupDaoImpl(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_BY_PAGE_QUERY, UPDATE_QUERY,
                DELETE_BY_ID_QUERY);
    }
    
    @Override
    public List<Group> findTeacherGroupsById(Long id) {
        try {
            return jdbcTemplate.query(FIND_TEACHER_GROUPS_BY_ID_QUERY, mapRowToEntity(), id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
    }
    
    @Override
    public Optional<Group> findStudentGroupById(Long id) {
        List<Group> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_STUDENT_GROUP_BY_ID_QUERY, mapRowToEntity(), id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

    @Override
    protected RowMapper<Group> mapRowToEntity() throws SQLException {
        return this::mapRowToGroup;
    }

    private Group mapRowToGroup(ResultSet row, int rownum) throws SQLException {
        List<Schedule> scheduleList = jdbcTemplate.query(FIND_SCHEDULE_BY_ID_QUERY, this::mapRowToSchedule,
                row.getLong("id"));
        List<Task> taskList = jdbcTemplate.query(FIND_WORK_PROGRAMS_BY_ID_QUERY, this::mapRowToTask, row.getLong("id"));
        List<Subject> subjects = jdbcTemplate.query(FIND_ALL_SUBJECTS_QUERY, this::mapRowToSubject);
        Map<Long, List<Schedule>> scheduleMap = new HashMap<>();
        Map<Long, List<Task>> taskMap = new HashMap<>();
        subjects.forEach(s -> {
            List<Schedule> schedules = scheduleList.stream()
                    .filter(schedule -> Objects.equals(schedule.getSubject().getId(), s.getId()))
                    .collect(Collectors.toList());
            if (!schedules.isEmpty()) {
                scheduleMap.put(s.getId(), schedules);
            }
            List<Task> tasks = taskList.stream().filter(task -> Objects.equals(task.getSubject().getId(), s.getId()))
                    .collect(Collectors.toList());
            if (!tasks.isEmpty()) {
                taskMap.put(s.getId(), tasks);
            }
        });
        return Group.builder()
                .withId(row.getLong("id"))
                .withName(row.getString("name"))
                .withSchedule(scheduleMap)
                .withWorkPrograms(taskMap).build();
    }

    private Schedule mapRowToSchedule(ResultSet row, int rownum) throws SQLException {
        List<Subject> items = jdbcTemplate.query(FIND_SUBJECT_BY_ID_QUERY, this::mapRowToSubject,
                row.getLong("subject_id"));
        return Schedule.builder()
                .withId(row.getLong("id"))
                .withSubject(items.get(0))
                .withWeek(Week.valueOf(row.getString("day_week")))
                .withStartTime(row.getTimestamp("start_time").toLocalDateTime())
                .withEndTime(row.getTimestamp("end_time").toLocalDateTime())
                .build();
    }

    private Task mapRowToTask(ResultSet row, int rownum) throws SQLException {
        List<Subject> items = jdbcTemplate.query(FIND_SUBJECT_BY_ID_QUERY, this::mapRowToSubject,
                row.getLong("subject_id"));
        return Task.builder()
                .withId(row.getLong("id"))
                .withTitle(row.getString("title"))
                .withSubject(items.get(0))
                .withType(Type.valueOf(row.getString("type").toUpperCase()))
                .withDescription(row.getString("description"))
                .build();
    }

    private Subject mapRowToSubject(ResultSet row, int rownum) throws SQLException {
        return new Subject(row.getLong("id"), row.getString("name"), row.getInt("credit"));
    }

    @Override
    protected PreparedStatement setSaveEntry(PreparedStatement ps, Group entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setLong(2, entity.getFaculty().getId());
        return ps;
    }

    @Override
    protected PreparedStatementSetter setUpdateEntry(Group entity) throws SQLException {
        return new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, entity.getName());
                ps.setLong(2, entity.getFaculty().getId());
                ps.setLong(3, entity.getId());
            }
        };
    }

    @Override
    protected void saveDependentEntry(Group entity, Long id) throws SQLException {
        entity.getStudents().forEach(s -> jdbcTemplate.update(SAVE_STUDENTS_QUERY, id, s.getId()));
        entity.getSchedule().forEach((k, v) -> v.forEach(s -> jdbcTemplate.update(SAVE_SCHEDULE_QUERY, id, k,
                s.getWeek().toString(), Timestamp.valueOf(s.getStartTime()), Timestamp.valueOf(s.getEndTime()))));
        entity.getWorkPrograms().forEach((k, v) -> v.forEach(s -> jdbcTemplate.update(SAVE_WORK_PROGRAMS_QUERY, id, k,
                s.getTitle(), s.getType().toString(), s.getDescription())));
    }

    @Override
    protected void deleteDependentEntry(Long id) throws SQLException {
        jdbcTemplate.update(DELETE_STUDENTS_BY_ID_QUERY, id);
        jdbcTemplate.update(DELETE_SCHEDULE_BY_ID_QUERY, id);
        jdbcTemplate.update(DELETE_WORK_PROGRAMS_BY_ID_QUERY, id);
    }
    
    @Override
    public Optional<Group> findByName(String name) {
        List<Group> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_BY_NAME_QUERY, mapRowToEntity(), name);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

}
