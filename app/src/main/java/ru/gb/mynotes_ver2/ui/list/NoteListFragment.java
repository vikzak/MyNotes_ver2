package ru.gb.mynotes_ver2.ui.list;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.InMemNoteRepo;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.NoteAdapterItem;
import ru.gb.mynotes_ver2.ui.add.AddNoteDialogFragment;
import ru.gb.mynotes_ver2.ui.add.AddNotePresenter;
import ru.gb.mynotes_ver2.ui.add.UpdateNotePresenter;

public class NoteListFragment extends Fragment implements NoteListView{

    private SwipeRefreshLayout swipeRefreshLayout;
    private View empty;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView noteList;
    private NoteAdapter adapter;
    private NotePresenter presenter;
    private Note selectedNote;


    public NoteListFragment(){
        super(R.layout.fragment_note_list);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NotePresenter(requireContext(),this, InMemNoteRepo.INSTANCE);
        adapter = new NoteAdapter(this);

        adapter.setOnClick(new NoteAdapter.OnClick() {
            @Override
            public void onClick(Note note) {
                Toast.makeText(requireContext(), note.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(Note note) {
                selectedNote = note;
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

        view.findViewById(R.id.float_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNoteDialogFragment.addInstance()
                        .show(getParentFragmentManager(),AddNoteDialogFragment.TAG);

            }
        });

        // работает только с LinearLayout
        // украшения
//        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
//        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.bg_divider));
//        noteList.addItemDecoration(itemDecoration);
        //
//        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.action_add:
//                        Toast.makeText(requireContext(), "add", Toast.LENGTH_SHORT).show();
//                        return true;
//                    case R.id.action_del:
//                        Toast.makeText(requireContext(), "del", Toast.LENGTH_SHORT).show();
//                        return true;
//                }
//                return false;
//            }
//        });

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
        getParentFragmentManager()
                .setFragmentResultListener(AddNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(AddNotePresenter.ARG_NOTE);
                        presenter.onNoteAdd(note);

                    }
                });

        getParentFragmentManager()
                .setFragmentResultListener(UpdateNotePresenter.KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(UpdateNotePresenter.ARG_NOTE);
                        presenter.onNoteUpdate(note);
                    }
                });

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

    @Override
    public void onNoteAdd(NoteAdapterItem noteAdapterItem) {
        int index = adapter.addItem(noteAdapterItem);
        adapter.notifyItemInserted(index - 1);
        noteList.smoothScrollToPosition(index - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {
        int index = adapter.removeItem(selectedNote);
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void onNoteUpdated(NoteAdapterItem noteAdapterItem) {
        int index = adapter.updateItem(noteAdapterItem);
        adapter.notifyItemChanged(index);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.notes_content_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete_note){
            presenter.removeNote(selectedNote);
            //Toast.makeText(requireContext(), "note deleted: " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == R.id.action_update_note){
            AddNoteDialogFragment.updateInstance(selectedNote)
                    .show(getParentFragmentManager(),AddNoteDialogFragment.TAG);
            //Toast.makeText(requireContext(), "note edit / update: " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
