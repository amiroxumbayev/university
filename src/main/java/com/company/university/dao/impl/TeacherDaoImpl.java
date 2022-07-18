package com.company.university.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.dao.TeacherDao;
import com.company.university.domain.Group;
import com.company.university.domain.Subject;
import com.company.university.domain.Teacher;

@Repository
public class TeacherDaoImpl extends AbstractCrudDaoImpl<Teacher> implements TeacherDao {

    private static final String SAVE_QUERY = "INSERT INTO public.Teacher (name, email, password) values (?,?,?)";
    private static final String SAVE_GROUPS_QUERY = "INSERT INTO public.Teacher_Group (teacher_id, group_id) values (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM public.Teacher WHERE id = ?";
    private static final String FIND_BY_EMAIL_QUERY = "SELECT * FROM public.Teacher WHERE email = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM public.Teacher";
    private static final String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM public.Teacher ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    private static final String UPDATE_QUERY = "UPDATE public.Teacher SET name = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM public.Teacher  WHERE id = ?";
    private static final String DELETE_GROUPS_BY_ID_QUERY = "DELETE FROM public.Teacher_Group WHERE teacher_id = ?";
    
    @Autowired
    public TeacherDaoImpl(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_BY_PAGE_QUERY, UPDATE_QUERY,
                DELETE_BY_ID_QUERY);
    }

    @Override
    protected RowMapper<Teacher> mapRowToEntity() throws SQLException {
        return this::mapRowToTeacher;
    }

    private Teacher mapRowToTeacher(ResultSet row, int rownum) throws SQLException {
        return Teacher.builder()
                .withId(row.getLong("id"))
                .withName(row.getString("name"))
                .withEmail(row.getString("email"))
                .withPassword(row.getString("password"))
                .build();
    }

    @Override
    protected PreparedStatement setSaveEntry(PreparedStatement ps, Teacher entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getEmail());
        ps.setString(3, entity.getPassword());
        return ps;
    }

    @Override
    protected PreparedStatementSetter setUpdateEntry(Teacher entity) throws SQLException {
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
    protected void saveDependentEntry(Teacher entity, Long id) throws SQLException {
        entity.getGroups().forEach(s -> jdbcTemplate.update(SAVE_GROUPS_QUERY, id, s.getId()));
    }

    @Override
    protected void deleteDependentEntry(Long id) throws SQLException {
        jdbcTemplate.update(DELETE_GROUPS_BY_ID_QUERY, id);
    }
    
    @Override
    public Optional<Teacher> findByEmail(String email) {
        List<Teacher> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_BY_EMAIL_QUERY, mapRowToEntity(), email);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

}
