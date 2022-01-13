package ru.gb.mynotes_ver2.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InMemNoteRepo implements NoteRepo {

    public static final NoteRepo INSTANCE = new InMemNoteRepo();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final ArrayList<Note> result = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private InMemNoteRepo() {
        result.add(new Note(UUID.randomUUID().toString(), "Title #1", "Message one", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #2", "Message two", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #3", "Message three", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #4", "Message four", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #5", "Message five", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #6", "Message six", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #7", "Message seven", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #8", "Message eight", new Date()));
        result.add(new Note(UUID.randomUUID().toString(), "Title #9", "Message nine", new Date()));
//        for (int i = 0; i < 10000; i++){
//            result.add(new Note(UUID.randomUUID().toString(), "Title #8", "Message eight", new Date()));
//            result.add(new Note(UUID.randomUUID().toString(), "Title #9", "Message nine", new Date()));
//        }
    }

    @Override
    public void getAll(Callback<List<Note>> callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        });

    }
}
