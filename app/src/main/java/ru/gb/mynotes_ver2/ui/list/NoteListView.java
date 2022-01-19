package ru.gb.mynotes_ver2.ui.list;

import java.util.List;

//import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.NoteAdapterItem;

public interface NoteListView {
    void showNote(List<AdapterItem> notes);
    void showProgress();
    void hideProgress();

    void showEmpty();
    void hideEmpty();

    void showError(String error);

    void onNoteAdd(NoteAdapterItem noteAdapterItem);

    void onNoteRemoved(Note selectedNote);

    void onNoteUpdated(NoteAdapterItem noteAdapterItem);
}
