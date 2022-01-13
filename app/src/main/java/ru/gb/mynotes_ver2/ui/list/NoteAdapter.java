package ru.gb.mynotes_ver2.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final ArrayList<Note> data = new ArrayList<>();

    private OnClick onClick;



    public void setData(Collection<Note> notes) {
        data.clear();
        data.addAll(notes);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        Note note = data.get(position);
        holder.getTitle().setText(note.getTitle());
        holder.getMessage().setText(note.getMessage());
        holder.getDate().setText(simpleDateFormat.format(note.getCreatedDate()));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public OnClick getOnClick() {
        return onClick;
    }

    interface OnClick{
        void onClick(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        private final TextView date;

        private final TextView message;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_note);

            date = itemView.findViewById(R.id.date_note);

            message = itemView.findViewById(R.id.content_note);

            itemView.findViewById(R.id.note_card).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Note note = data.get(getAdapterPosition());
                    if (getOnClick() != null){
                        getOnClick().onClick(note);
                    }
                }
            });

        }

        public TextView getTitle() {
            return title;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMessage() {
            return message;
        }
    }

}
