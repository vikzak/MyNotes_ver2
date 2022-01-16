package ru.gb.mynotes_ver2.ui.add;

import android.os.Bundle;

import androidx.annotation.StringRes;

import ru.gb.mynotes_ver2.domain.Note;

public interface AddNoteView {
    void showProgress();

    void hideProgress();

    //void noteSave(Note note);

    void showError(String errorAddNote);

    void setActionButtonText(@StringRes int title);

    void setTitle(String title);

    void setMessage(String message);

    //void noteUpdate(Note result);
    void actionCompleted(String key, Bundle bundle);
}

