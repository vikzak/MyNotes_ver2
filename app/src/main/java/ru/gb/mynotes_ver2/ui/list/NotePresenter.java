package ru.gb.mynotes_ver2.ui.list;

import android.content.Context;
import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.Callback;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.NoteRepo;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.HeaderAdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.NoteAdapterItem;

public class NotePresenter    {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private NoteListView view;
    private NoteRepo noteRepo;

    private Context context;

    public NotePresenter(Context context, NoteListView view, NoteRepo noteRepo) {
        this.view = view;
        this.noteRepo = noteRepo;
        this.context = context;
    }

    public void requestNote(){
        view.showProgress();
        noteRepo.getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                ArrayList<AdapterItem> adapterItems = new ArrayList<>();
                //adapterItems.add(new HeaderAdapterItem("This is header"));
                Date lastDate = null;
                for (Note note: result ){
                    Date createdDate = note.getCreatedDate();
                    if (!createdDate.equals(lastDate)){
                        lastDate = note.getCreatedDate();
                        // убираем группировку
                        // adapterItems.add(new HeaderAdapterItem(dateFormat.format(lastDate)));
                    }
                    adapterItems.add(new NoteAdapterItem(note,
                            note.getTitle(),
                            note.getMessage(),
                            timeFormat.format(note.getCreatedDate())));
                }
                view.showNote(adapterItems);
                if (adapterItems.isEmpty()){
                    view.showEmpty();
                } else {
                    view.hideEmpty();
                }
                view.hideProgress();
            }
            @Override
            public void onError(Throwable throwable) {
                view.showError(context.getString(R.string.try_again));
                view.hideProgress();
            }
        });

    }


    public void onNoteAdd(Note note) {
        NoteAdapterItem noteAdapterItem = new NoteAdapterItem(note,
                note.getTitle(),
                note.getMessage(),
                timeFormat.format(note.getCreatedDate()));
        view.onNoteAdd(noteAdapterItem);
        view.hideEmpty();

    }

    public void removeNote(Note selectedNote) {
        view.showProgress();
        noteRepo.delete(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void result) {
                view.hideProgress();
                view.onNoteRemoved(selectedNote);
            }

            @Override
            public void onError(Throwable throwable) {
                view.hideProgress();

            }
        });
    }
}
