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
import com.company.university.dao.SubjectDao;
import com.company.university.domain.Subject;

@Repository
public class SubjectDaoImpl extends AbstractCrudDaoImpl<Subject> implements SubjectDao {

    private static final String SAVE_QUERY = "INSERT INTO public.Subject (name, credit) values (?,?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM public.Subject WHERE id = ?";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM public.Subject WHERE name = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM public.Subject";
    private static final String FIND_ALL_BY_PAGE_QUERY = "SELECT * FROM public.Subject ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
    private static final String UPDATE_QUERY = "UPDATE public.Subject SET name = ? , credit = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM public.Subject WHERE id = ?";
    
    @Autowired
    public SubjectDaoImpl(DataSource dataSource) {
        super(dataSource, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_BY_PAGE_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    protected RowMapper<Subject> mapRowToEntity() throws SQLException {
        return this::mapRowToSubject;
    }

    private Subject mapRowToSubject(ResultSet row, int rownum) throws SQLException {
        return new Subject(row.getLong("id"), row.getString("name"), row.getInt("credit"));
    }

    @Override
    protected PreparedStatement setSaveEntry(PreparedStatement ps, Subject entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setInt(2, entity.getCredit());
        return ps;
    }

    @Override
    protected PreparedStatementSetter setUpdateEntry(Subject entity) throws SQLException {
        return new PreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, entity.getName());
                ps.setInt(2, entity.getCredit());
                ps.setLong(3, entity.getId());
            }
        };
    }

    @Override
    protected void saveDependentEntry(Subject entity, Long id) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void deleteDependentEntry(Long id) throws SQLException {
        // TODO Auto-generated method stub

    }

    @Override
    public Optional<Subject> findByName(String name) {
        List<Subject> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(FIND_BY_NAME_QUERY, mapRowToEntity(), name);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

}
