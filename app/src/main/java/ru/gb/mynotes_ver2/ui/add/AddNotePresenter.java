package ru.gb.mynotes_ver2.ui.add;

import android.os.Bundle;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.Callback;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.NoteRepo;

public class AddNotePresenter implements NotePresenter{

    public static final String KEY = "AddNoteDialogFragment_ADDNOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NoteRepo repo;


    public AddNotePresenter(AddNoteView view, NoteRepo repo) {
        this.view = view;
        this.repo = repo;

        view.setActionButtonText(R.string.button_save);
    }


    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();
        repo.save(title, message, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ARG_NOTE, result);
                view.actionCompleted(KEY, bundle);
            }

            @Override
            public void onError(Throwable throwable) {
                view.hideProgress();

            }
        });

    }
}
