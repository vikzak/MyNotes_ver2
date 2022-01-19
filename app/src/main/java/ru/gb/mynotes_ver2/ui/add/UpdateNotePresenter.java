package ru.gb.mynotes_ver2.ui.add;

import android.os.Bundle;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.Callback;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.NoteRepo;

public class UpdateNotePresenter implements NotePresenter{
    public static final String KEY = "AddNoteDialogFragment_UPDATENOTE";
    public static final String ARG_NOTE = "ARG_NOTE";

    private AddNoteView view;
    private NoteRepo repository;
    private Note note;

    public UpdateNotePresenter(AddNoteView view, NoteRepo repository, Note note) {
        this.view = view;
        this.repository = repository;
        this.note = note;

        view.setActionButtonText(R.string.button_update);
        view.setTitle(note.getTitle());
        view.setMessage(note.getMessage());
    }

    @Override
    public void onActionPressed(String title, String message) {
        view.showProgress();
        repository.update(note.getId(), title, message, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                //view.noteUpdate(result);
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
