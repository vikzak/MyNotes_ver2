package ru.gb.mynotes_ver2.domain;

import android.app.DatePickerDialog;

import java.util.Date;

public class Note {
    private String id;
    private String title;
    private String message;
    private Date createdDate;

    public Note(String id, String title, String message, Date createdDate) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
