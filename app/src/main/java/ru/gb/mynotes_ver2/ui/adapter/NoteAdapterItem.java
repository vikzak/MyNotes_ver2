package ru.gb.mynotes_ver2.ui.adapter;

import ru.gb.mynotes_ver2.domain.Note;

public class NoteAdapterItem implements AdapterItem{

    private Note note;

    private String title;
    private String message;
    private String time;


    public NoteAdapterItem(Note note, String title, String message, String time) {
        this.note = note;
        this.title = title;
        this.message = message;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public Note getNote() {
        return note;
    }
}
