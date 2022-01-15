package ru.gb.mynotes_ver2.ui.list;

import java.util.List;

//import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;

public interface NoteListView {
    void showNote(List<AdapterItem> notes);
    void showProgress();
    void hideProgress();
}
