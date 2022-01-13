package ru.gb.mynotes_ver2.domain;

import java.util.List;

public interface NoteRepo {
    void getAll(Callback<List<Note>> callback);
}
