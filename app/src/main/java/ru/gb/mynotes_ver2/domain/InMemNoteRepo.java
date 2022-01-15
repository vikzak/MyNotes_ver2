package ru.gb.mynotes_ver2.domain;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InMemNoteRepo implements NoteRepo {

    public static final NoteRepo INSTANCE = new InMemNoteRepo();

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final ArrayList<Note> result = new ArrayList<>();

    private final Handler handler = new Handler(Looper.getMainLooper());

    private InMemNoteRepo() {
        Calendar calendar = Calendar.getInstance();
        Random random = new Random();

        //private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMMM.yyyy", Locale.getDefault());
//        result.add(new Note(UUID.randomUUID().toString(), "Title #1", "Message one", new Date()));
        for (int i = 1; i < 51; i++){
            int temp = random.nextInt(3);
            String messageTitle = "Заметка #" + i;
            String messageText = "Это текст тестовой заметки №" + i;
            calendar.add(Calendar.DAY_OF_YEAR, - temp);
            result.add(new Note(UUID.randomUUID().toString(), messageTitle, messageText, calendar.getTime()));
            //calendar.add(Calendar.MONTH, - temp);
            //result.add(new Note(UUID.randomUUID().toString(), messageTitle, messageText, calendar.getTime()));
        }
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
                        if (new Random().nextBoolean()) {
                            if (new Random().nextBoolean()) {
                                callback.onSuccess(result);
                            } else {
                                callback.onSuccess(new ArrayList<>());
                            }
                        } else {
                            callback.onError(new IOException());
                        }

                    }
                });
            }
        });

    }
}
