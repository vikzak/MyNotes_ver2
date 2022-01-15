package ru.gb.mynotes_ver2.ui.add;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.InMemNoteRepo;
import ru.gb.mynotes_ver2.domain.Note;

public class AddNoteDialogFragment extends BottomSheetDialogFragment implements AddNoteView{

    public static final String TAG = "AddNoteDialogFragment";
    public static final String KEY = "AddNoteDialogFragment";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static AddNoteDialogFragment newInstance(){
        AddNoteDialogFragment fragment = new AddNoteDialogFragment();
        return fragment;
    }

    private Button buttonSave;
    private ProgressBar progressBar;

    private AddNotePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddNotePresenter(this, InMemNoteRepo.INSTANCE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_not_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progress_add_note);
        buttonSave = view.findViewById(R.id.save_button);

        EditText editTextTitle = view.findViewById(R.id.title_note_input);
        EditText editTextMessage = view.findViewById(R.id.enter_note_input);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.saveNote(editTextTitle.getText().toString(), editTextMessage.getText().toString());
            }
        });
    }

    @Override
    public void showProgress() {
        buttonSave.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        buttonSave.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void noteSave(Note note) {

        Bundle bundleResult = new Bundle();
        bundleResult.putParcelable(ARG_NOTE, note);
        getParentFragmentManager()
                .setFragmentResult(KEY, bundleResult);
        dismiss();


    }

    @Override
    public void showError(String errorAddNote) {

    }
}
