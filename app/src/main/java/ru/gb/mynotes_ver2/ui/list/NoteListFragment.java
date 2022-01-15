package ru.gb.mynotes_ver2.ui.list;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.InMemNoteRepo;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;

public class NoteListFragment extends Fragment implements NoteListView{

    private SwipeRefreshLayout swipeRefreshLayout;
    private View empty;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView noteList;
    private NoteAdapter adapter;
    private NotePresenter presenter;


    public NoteListFragment(){
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotePresenter(requireContext(),this, InMemNoteRepo.INSTANCE);
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

        coordinatorLayout = view.findViewById(R.id.coordinator);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestNote();
            }
        });

        noteList = view.findViewById(R.id.note_list);
        empty = view.findViewById(R.id.is_empty);
        noteList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        //noteList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        noteList.setAdapter(adapter);

        // работает только с LinearLayout
        // украшения
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.bg_divider));
//        noteList.addItemDecoration(itemDecoration);
        //
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add:
                        Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_del:
                        Toast.makeText(requireContext(), "del", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_search:
                        Toast.makeText(requireContext(), "search", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        presenter.requestNote();
    }

    @Override
    public void showNote(List<AdapterItem>notes) {
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
        //swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        //swipeRefreshLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showEmpty() {
        empty.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideEmpty() {
        empty.setVisibility(View.GONE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(coordinatorLayout,error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.requestNote();
                    }
                }).show();
    }
}
