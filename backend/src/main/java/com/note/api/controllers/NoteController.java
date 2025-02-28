package com.note.api.controllers;

import com.note.api.forms.NoteCreateForm;
import com.note.api.forms.NoteUpdateForm;
import com.note.api.views.NoteView;
import com.note.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    @GetMapping()
    public ResponseEntity<List<NoteView>> getAll(
            @RequestParam(name = "title") Optional<String> title
    ) {
        String titleParam = null;
        if (title.isPresent()) {
            titleParam = title.get();
        }
        List<NoteView> views = noteService.getAll(titleParam);
        return ResponseEntity.ok(views);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteView> getOne(
            @PathVariable(name = "id") String id
    ) {
        NoteView noteView = this.noteService.getOne(id);
        return new ResponseEntity<>(noteView, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<NoteView> create(
            @RequestBody NoteCreateForm payload
    ) {
        NoteView noteView = this.noteService.create(payload);
        return new ResponseEntity<>(noteView, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<NoteView> update(
            @PathVariable(name = "id") String id,
            @RequestBody NoteUpdateForm payload
            ) {
        NoteView noteView = this.noteService.update(id, payload);
        return new ResponseEntity<>(noteView, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(name = "id") String id
    ) {
        this.noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
