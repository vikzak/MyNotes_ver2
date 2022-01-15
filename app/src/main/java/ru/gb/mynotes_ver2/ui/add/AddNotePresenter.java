package ru.gb.mynotes_ver2.ui.add;

import ru.gb.mynotes_ver2.domain.Callback;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.NoteRepo;

public class AddNotePresenter {

    private AddNoteView view;
    private NoteRepo repo;

    public AddNotePresenter(AddNoteView view, NoteRepo repo) {
        this.view = view;
        this.repo = repo;
    }

    public void saveNote (String titleNote, String messageNote){
        view.showProgress();
        repo.save(titleNote, messageNote, new Callback<Note>() {
            @Override
            public void onSuccess(Note result) {
                view.hideProgress();
                view.noteSave(result);
            }

            @Override
            public void onError(Throwable throwable) {
                view.hideProgress();

            }
        });
    }
}
