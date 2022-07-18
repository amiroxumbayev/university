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
import com.company.university.dao.FacultyDao;
import com.company.university.domain.Faculty;
import com.company.university.domain.Subject;

@Repository
public class FacultyDaoImpl extends AbstractCrudDaoImpl<Faculty> implements FacultyDao {

    private static final String SAVE_QUERY = "INSERT INTO public.Faculty (title) values (?)";
    private static final String SAVE_SUBJECTS_QUERY = "INSERT INTO public.Faculty_Subject (faculty_id, subject_id) values (?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM public.Faculty WHERE id = ?";
    private static final String FIND_BY_TITLE_QUERY = "SELECT * FROM public.Faculty WHERE title = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM public.Faculty";
    private static final String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM public.Faculty ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    private static final String FIND_SUBJECTS_BY_ID_QUERY = "SELECT subject.id, subject.name, subject.credit FROM public.Faculty_Subject INNER JOIN "
            + "public.Subject on Faculty_Subject.subject_id = Subject.id WHERE faculty_id = ?";
    private static final String UPDATE_QUERY = "UPDATE public.Faculty SET title = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM public.Faculty  WHERE id = ?";
    private static final String DELETE_SUBJECTS_BY_ID_QUERY = "DELETE FROM public.Faculty_Subject WHERE faculty_id = ?";
    
    @Autowired
    public FacultyDaoImpl(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_BY_PAGE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected RowMapper<Faculty> mapRowToEntity() throws SQLException {
        return this::mapRowToFaculty;
    }

    private Faculty mapRowToFaculty(ResultSet row, int rownum) throws SQLException {
        List<Subject> subjectList = jdbcTemplate.query(FIND_SUBJECTS_BY_ID_QUERY, this::mapRowToSubject,
                row.getLong("id"));
        return new Faculty(row.getLong("id"), row.getString("title"), subjectList);
    }

    private Subject mapRowToSubject(ResultSet row, int rownum) throws SQLException {
        return new Subject(row.getLong("id"), row.getString("name"), row.getInt("credit"));
    }

    @Override
    protected PreparedStatement setSaveEntry(PreparedStatement ps, Faculty entity) throws SQLException {
        ps.setString(1, entity.getTitle());
        return ps;
    }

    @Override
    protected PreparedStatementSetter setUpdateEntry(Faculty entity) throws SQLException {
        return new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, entity.getTitle());
                ps.setLong(2, entity.getId());
            }
        };
    }

    @Override
    protected void saveDependentEntry(Faculty entity, Long id) throws SQLException {
        entity.getSubjects().forEach(s -> jdbcTemplate.update(SAVE_SUBJECTS_QUERY, id, s.getId()));
    }

    @Override
    protected void deleteDependentEntry(Long id) throws SQLException {
        jdbcTemplate.update(DELETE_SUBJECTS_BY_ID_QUERY, id);
    }
    
    @Override
    public Optional<Faculty> findByTitle(String title) {
        List<Faculty> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_BY_TITLE_QUERY, mapRowToEntity(), title);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

}
