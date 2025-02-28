package com.note.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private int id;
    private String title;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
