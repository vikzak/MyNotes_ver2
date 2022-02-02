package ru.gb.mynotes_ver2.domain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SharePrefNoteRep implements NoteRepo{

    @SuppressLint("StaticFieldLeak")
    private static SharePrefNoteRep INSTANCE;
    private static final String KEY_NOTES = "KEY_NOTES";

    public static SharePrefNoteRep getInstance(Context context) {
        if (INSTANCE == null){
            INSTANCE = new SharePrefNoteRep(context);
        }
        return INSTANCE;
    }

    private Context context;
    private SharedPreferences sharedPreferences;
    private Gson gson =  new Gson();

    public SharePrefNoteRep(Context context) {
        this.context = context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences("notes", Context.MODE_PRIVATE);
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {
        String data = sharedPreferences.getString(KEY_NOTES,"[]");

        Type type = new TypeToken<List<Note>>(){
        }.getType();

        List<Note> result = gson.fromJson(data,type);
        callback.onSuccess(result);

    }

    @Override
    public void save(String titleNote, String messageNote, Callback<Note> callback) {
        String data = sharedPreferences.getString(KEY_NOTES,"[]");

        Type type = new TypeToken<ArrayList<Note>>(){
        }.getType();

        ArrayList<Note> result = gson.fromJson(data,type);
        Note note = new Note(UUID.randomUUID().toString(), titleNote, messageNote, new Date());
        result.add(note);
        String toSave = gson.toJson(result, type);
        sharedPreferences.edit().putString(KEY_NOTES, toSave).apply();
        callback.onSuccess(note);

    }

    @Override
    public void update(String noteId, String titleNote, String messageNote, Callback<Note> callback) {
        String data = sharedPreferences.getString(KEY_NOTES,"[]");

        Type type = new TypeToken<ArrayList<Note>>(){
        }.getType();

        ArrayList<Note> result = gson.fromJson(data,type);

        Note note = new Note(noteId, titleNote, messageNote, new Date());
        //result.set((Integer.parseInt(noteId)), note);
        result.add(note);
        String toSave = gson.toJson(result, type);
        //String toSave = new GsonBuilder().create().toJson(note);
        //String toSave = gson.newBuilder().create().toJson(note);
        sharedPreferences.edit().putString(KEY_NOTES, toSave).apply();
        callback.onSuccess(note);

    }

    @Override
    public void delete(Note note, Callback<Void> callback) {
        //
        String data = sharedPreferences.getString(KEY_NOTES,"[]");

        Type type = new TypeToken<ArrayList<Note>>(){
        }.getType();

        ArrayList<Note> result = gson.fromJson(data,type);
        sharedPreferences.edit().remove(KEY_NOTES).commit();
        //callback.onSuccess();

    }
}
