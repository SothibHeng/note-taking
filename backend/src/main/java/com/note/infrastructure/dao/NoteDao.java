package com.note.infrastructure.dao;

import com.note.infrastructure.exceptions.InternalServerErrorException;
import com.note.infrastructure.exceptions.ResourceNotFoundException;
import com.note.models.Note;
import com.note.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NoteDao implements NoteRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Note> getAll(String title) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        StringBuilder query = new StringBuilder("SELECT * FROM notes ORDER BY updated_at;");
        return this.namedParameterJdbcTemplate.query(query.toString(), params, new NoteMapper());
    }

    @Override
    public Optional<Note> getOne(String id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", Integer.parseInt(id));
        String sql = "SELECT * FROM notes WHERE id = :id";
        List<Note> notes = this.namedParameterJdbcTemplate.query(sql, parameters, new NoteMapper());
        if (notes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(notes.get(0));
    }

    @Override
    public Note create(Note note) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("title", note.getTitle());
            parameters.addValue("content", note.getContent());

            String sql = "INSERT INTO notes (title, content) " +
                    "VALUES (:title, :content) RETURNING *;";

            return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new NoteMapper());
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalServerErrorException("Internal server error");
        }
    }

    @Override
    public Note update(String id, Note note) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        String sql = "UPDATE notes SET ";
        parameters.addValue("id", Integer.parseInt(id));

        if (note.getTitle() != null) {
            parameters.addValue("title", note.getTitle());
            sql += "title = :title, ";
        }
        if (note.getContent() != null) {
            parameters.addValue("content", note.getContent());
            sql += "content = :content, ";
        }

        sql += "updated_at = CURRENT_DATE WHERE id = :id RETURNING *;";

        return this.namedParameterJdbcTemplate.queryForObject(sql, parameters, new NoteMapper());
    }

    @Override
    public void delete(String id) {
        try {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("id", Integer.parseInt(id));

            String sql = "DELETE FROM notes WHERE id = :id";
            this.namedParameterJdbcTemplate.update(sql, parameters);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal server error");
        }
    }

    public static class NoteMapper implements RowMapper<Note> {
        public Note mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Note note = new Note();
            note.setId(resultSet.getInt("id"));
            note.setTitle(resultSet.getString("title"));
            note.setContent(resultSet.getString("content"));
            note.setCreatedAt(resultSet.getTimestamp("created_at"));
            note.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            return note;
        }
    }
}
