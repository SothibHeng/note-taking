package com.note.api.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.note.models.Note;
import lombok.Data;

import java.util.Date;

@Data
public class NoteView {
    private String id;
    private String title;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ssz")
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ssz")
    private Date updatedAt;

    public NoteView(Note note) {
        this.id = String.valueOf(note.getId());
        this.title = note.getTitle();
        this.content = note.getContent();
        this.createdAt = note.getCreatedAt();
        this.updatedAt = note.getUpdatedAt();
    }
}
