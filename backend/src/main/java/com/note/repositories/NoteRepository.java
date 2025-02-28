package com.note.repositories;

import com.note.models.Note;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    public List<Note> getAll(String title);

    public Optional<Note> getOne(String id);
    public Note create(Note note);

    public Note update(String id, Note note);

    public void delete(String id);
}
