package ru.gb.mynotes_ver2.ui.list;

import java.util.List;

import ru.gb.mynotes_ver2.domain.Note;

public interface NoteListView {
    void showNote(List<Note> notes);
    void showProgress();
    void hideProgress();
}
