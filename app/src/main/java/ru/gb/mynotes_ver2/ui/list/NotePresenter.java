package ru.gb.mynotes_ver2.ui.list;

import java.util.List;

import ru.gb.mynotes_ver2.domain.Callback;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.NoteRepo;

public class NotePresenter    {
    private NoteListView view;
    private NoteRepo noteRepo;

    public NotePresenter(NoteListView view, NoteRepo noteRepo) {
        this.view = view;
        this.noteRepo = noteRepo;
    }

    public void requestNote(){
        view.showProgress();
        noteRepo.getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                view.showNote(result);
                view.hideProgress();
            }
            @Override
            public void onError(Throwable throwable) {
                view.hideProgress();
            }
        });

    }
}
