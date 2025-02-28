package com.note.services.internal;

import com.note.api.forms.NoteCreateForm;
import com.note.api.forms.NoteUpdateForm;
import com.note.api.views.NoteView;
import com.note.infrastructure.exceptions.BadRequestException;
import com.note.infrastructure.exceptions.ResourceNotFoundException;
import com.note.models.ApiError;
import com.note.models.ErrorCode;
import com.note.models.Note;
import com.note.repositories.NoteRepository;
import com.note.services.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {
    private final NoteRepository noteRepository;
    @Override
    public List<NoteView> getAll(String title) {
        List<Note> notes = noteRepository.getAll(title);
        return notes.stream()
                .map(NoteView::new)
                .toList();
    }

    @Override
    public NoteView getOne(String id) {
        Note note = noteRepository.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        return new NoteView(note);
    }


    @Override
    public NoteView create(NoteCreateForm form) {
        List<ApiError> errors = new ArrayList<>();

        if (form.getTitle() == null || form.getTitle().isEmpty()) {
            errors.add(new ApiError(ErrorCode.INVALID_PARAMETER, "title is required", "/title"));
        } else if (form.getTitle().length() > 64) {
            errors.add(new ApiError(ErrorCode.EXCEED_LIMIT, "title is exceeded 64 characters", "/title"));
        }

        if (form.getContent() == null || form.getContent().isEmpty()) {
            errors.add(new ApiError(ErrorCode.INVALID_PARAMETER, "content is required", "/content"));
        } else if (form.getContent().length() > 1024) {
            errors.add(new ApiError(ErrorCode.EXCEED_LIMIT, "content is exceeded 1024 characters", "/content"));
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }

        Note note = new Note();
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        Note newNote = this.noteRepository.create(note);
        return new NoteView(newNote);
    }

    @Override
    public NoteView update(String id, NoteUpdateForm form) {
        this.noteRepository.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        Note note = new Note();
        List<ApiError> errors = new ArrayList<>();

        if (form.getTitle() != null && form.getTitle().isPresent()) {
            if (form.getTitle().get().isEmpty()) {
                errors.add(new ApiError(ErrorCode.INVALID_PARAMETER, "title is required", "/title"));
            } else if (form.getTitle().get().length() > 64) {
                errors.add(new ApiError(ErrorCode.EXCEED_LIMIT, "title is exceeded 64 characters", "/title"));
            }
            note.setTitle(form.getTitle().get());
        }

        if (form.getContent() != null && form.getContent().isPresent()) {
            if (form.getContent().get().isEmpty()) {
                errors.add(new ApiError(ErrorCode.INVALID_PARAMETER, "content is required", "/content"));
            } else if (form.getContent().get().length() > 1024) {
                errors.add(new ApiError(ErrorCode.EXCEED_LIMIT, "content is exceeded 1024 characters", "/content"));
            }
            note.setContent(form.getContent().get());
        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }

        Note updatedNote = this.noteRepository.update(id,note);

        return new NoteView(updatedNote);
    }

    @Override
    public void delete(String id) {
        this.noteRepository.getOne(id)
                .orElseThrow(() -> new ResourceNotFoundException());
        this.noteRepository.delete(id);
    }
}
