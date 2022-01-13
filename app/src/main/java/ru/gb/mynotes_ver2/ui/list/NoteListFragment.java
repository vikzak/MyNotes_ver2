package ru.gb.mynotes_ver2.ui.list;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.InMemNoteRepo;
import ru.gb.mynotes_ver2.domain.Note;

public class NoteListFragment extends Fragment implements NoteListView{

    private ProgressBar progressBar;
    private RecyclerView noteList;
    private NoteAdapter adapter;
    private NotePresenter presenter;


    public NoteListFragment(){
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotePresenter(this, InMemNoteRepo.INSTANCE);
        adapter = new NoteAdapter();

        adapter.setOnClick(new NoteAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressbar);

        noteList = view.findViewById(R.id.note_list);

        noteList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        //noteList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        noteList.setAdapter(adapter);

        // работает только с LinearLayout
        //DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        //itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.bg_divider));
        //noteList.addItemDecoration(itemDecoration);

        presenter.requestNote();
    }

    @Override
    public void showNote(List<Note> notes) {
        adapter.setData(notes);
        adapter.notifyDataSetChanged();

//        for (Note note: notes){
//            View itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_note, noteList, false);
//
//            itemView.findViewById(R.id.note_card).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view ) {
//                    Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//            TextView title = itemView.findViewById(R.id.title_note);
//            title.setText(note.getTitle());
//
//            TextView date = itemView.findViewById(R.id.date_note);
//            date.setText(simpleDateFormat.format(note.getCreatedDate()));
//
//            TextView message = itemView.findViewById(R.id.content_note);
//            message.setText(note.getMessage());
//
//            noteList.addView(itemView);
//        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }
}
