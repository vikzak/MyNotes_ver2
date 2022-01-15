package ru.gb.mynotes_ver2.ui.add;

import ru.gb.mynotes_ver2.domain.Note;

public interface AddNoteView {
    void showProgress();
    void hideProgress();
    void noteSave(Note note);
    void showError(String errorAddNote);
}
