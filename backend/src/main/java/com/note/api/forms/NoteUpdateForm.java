package com.note.api.forms;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
public class NoteUpdateForm {
    private Optional<String> title;
    private Optional<String> content;
}
