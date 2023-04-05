package com.example.todo.repository;

import com.example.todo.entity.ToDo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SQL_INSERT = "INSERT INTO todo (id, description, created, updated, completed) VALUES (:id, :description, :created, :updated, :completed)";
    private final String SQL_FIND_ALL = "SELECT * FROM todo";
    private final String SQL_FIND_BY_ID = SQL_FIND_ALL + " WHERE id = :id";
    private final String SQL_UPDATE = "UPDATE todo SET description = :description, created = :created, updated = :updated, completed = :completed WHERE id = :id";
    private final String SQL_DELETE = "DELETE FROM todo WHERE id = :id";

    private RowMapper<ToDo> toDoRowMapper = new RowMapper<ToDo>() {
        @Override
        public ToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ToDo toDo = new ToDo();

            toDo.setId(rs.getString("id"));
            toDo.setDescription(rs.getString("description"));
            toDo.setCreated(rs.getTimestamp("created").toLocalDateTime());
            toDo.setUpdated(rs.getTimestamp("updated").toLocalDateTime());
            toDo.setCompleted(rs.getBoolean("completed"));
            return toDo;
        }
    };

    public ToDoRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo save(ToDo entity) {
        ToDo toDoFromDb = findById(entity.getId());
        if (toDoFromDb != null) {
            toDoFromDb.setDescription(entity.getDescription());
            toDoFromDb.setCompleted(entity.isCompleted());
            toDoFromDb.setUpdated(LocalDateTime.now());
            return upsert(toDoFromDb, SQL_UPDATE);
        }
        return upsert(entity, SQL_INSERT);
    }

    private ToDo upsert(ToDo toDo, String sql) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", toDo.getId());
        parameters.put("description", toDo.getDescription());
        parameters.put("created", toDo.getCreated());
        parameters.put("updated", toDo.getUpdated());
        parameters.put("completed", toDo.isCompleted());
        jdbcTemplate.update(sql, parameters);
        return findById(toDo.getId());
    }

    @Override
    public Collection<ToDo> saveAll(Collection<ToDo> entities) {
        entities.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo entity) {
        upsert(entity, SQL_DELETE);
    }

    @Override
    public ToDo findById(String id) {
        try {
            Map<String, Object> parameters = Collections.singletonMap("id", id);
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, parameters, toDoRowMapper);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Collection<ToDo> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, toDoRowMapper);
    }

}
