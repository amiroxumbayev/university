package com.company.university.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.company.university.dao.CrudDao;
import com.company.university.dao.DataBaseRuntimeException;
import com.company.university.domain.Page;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E> {

    protected JdbcTemplate jdbcTemplate;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String findAllByPageQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDaoImpl(DataSource dataSource, String saveQuery, String findByIdQuery, String findAllQuery,
            String findAllByPageQuery, String updateQuery, String deleteByIdQuery) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.findAllByPageQuery = findAllByPageQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public Long save(E entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Long key = null;
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);
                setSaveEntry(ps, entity);
                return ps;
            }, keyHolder);
            
        Map<String, Object> keys = keyHolder.getKeys();
        List<Object> values = keys.values().stream().collect(Collectors.toList());
        key = (long) (int) values.get(0);
        saveDependentEntry(entity, key);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to save entry in database", e);
        }
        return key;
    }

    @Override
    public Optional<E> findById(Long id) {
        List<E> items = new ArrayList<>();
        try {
            items = jdbcTemplate.query(findByIdQuery, mapRowToEntity(), id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find entry in database", e);
        }
        return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
    }

    @Override
    public List<E> findAll() {
        try {
            return jdbcTemplate.query(findAllQuery, mapRowToEntity());
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to find all entries in database", e);
        }
    }

    @Override
    public List<E> findAll(Page page) {
        Integer offset = (page.getPageNumber() * page.getRows()) - page.getRows();
        try {
            return jdbcTemplate.query(findAllByPageQuery, mapRowToEntity(), offset, page.getRows());
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Failed to find all entries of page in database", e);
        }
    }

    @Override
    public void update(E entity) {
        try {
            jdbcTemplate.update(updateQuery, setUpdateEntry(entity));
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to update entry in database", e);
        }

    }

    @Override
    public void deleteById(Long id) {
        try {
            deleteDependentEntry(id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Failed to delete entry in database", e);
        }
        jdbcTemplate.update(deleteByIdQuery, id);
    }

    protected abstract RowMapper<E> mapRowToEntity() throws SQLException;

    protected abstract PreparedStatement setSaveEntry(PreparedStatement preparedStatement, E entity)
            throws SQLException;

    protected abstract PreparedStatementSetter setUpdateEntry(E entity) throws SQLException;

    protected abstract void saveDependentEntry(E entity, Long id) throws SQLException;

    protected abstract void deleteDependentEntry(Long id) throws SQLException;
}
