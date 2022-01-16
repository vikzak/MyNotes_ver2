package ru.gb.mynotes_ver2.domain;

import java.util.List;

public interface NoteRepo {
    void getAll(Callback<List<Note>> callback);
    void save(String titleNote, String messageNote,Callback<Note> callback);
    void update(String noteId, String titleNote, String messageNote,Callback<Note> callback);
    void delete(Note note,Callback<Void> callback);
}
