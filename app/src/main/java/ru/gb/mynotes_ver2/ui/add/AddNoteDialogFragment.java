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

public class AddNoteDialogFragment extends BottomSheetDialogFragment implements AddNoteView {

    public static final String TAG = "AddNoteDialogFragment";
    private static final String ARG_NOTE = "ARG_NOTE";
    private Button buttonSave;
    private ProgressBar progressBar;
    private EditText editTitle;
    private EditText editMessage;
    //private AddNotePresenter presenter;
    private NotePresenter presenter;

    public static AddNoteDialogFragment addInstance() {
        //AddNoteDialogFragment fragment = new AddNoteDialogFragment();
        return new AddNoteDialogFragment();
    }

    public static AddNoteDialogFragment updateInstance(Note note) {
        AddNoteDialogFragment fragment = new AddNoteDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
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

        editTitle = view.findViewById(R.id.title_note_input);
        editMessage = view.findViewById(R.id.enter_note_input);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.onActionPressed(editTitle.getText().toString(), editMessage.getText().toString());
            }
        });
        if (getArguments() == null){
            presenter = new AddNotePresenter(this, InMemNoteRepo.INSTANCE);
        } else {
            Note note = getArguments().getParcelable(ARG_NOTE);
            presenter = new UpdateNotePresenter(this, InMemNoteRepo.INSTANCE, note);
        }

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
    public void showError(String errorAddNote) {

    }

    @Override
    public void setActionButtonText(int title) {
        buttonSave.setText(title);
    }

    @Override
    public void setTitle(String title) {
        editTitle.setText(title);

    }

    @Override
    public void setMessage(String message) {
        editMessage.setText(message);

    }

    @Override
    public void actionCompleted(String key, Bundle bundle) {
        getParentFragmentManager()
                .setFragmentResult(key, bundle);
        dismiss();

    }
}
