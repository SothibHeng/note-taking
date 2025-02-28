package com.note.services;

import com.note.api.forms.NoteCreateForm;
import com.note.api.forms.NoteUpdateForm;
import com.note.api.views.NoteView;

import java.util.List;

public interface NoteService {
    public List<NoteView> getAll(String title);

    public NoteView getOne(String id);

    public NoteView create(NoteCreateForm form);

    public NoteView update(String id, NoteUpdateForm form);

    public void delete(String id);

}
