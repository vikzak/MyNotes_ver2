package ru.gb.mynotes_ver2.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

import ru.gb.mynotes_ver2.R;
import ru.gb.mynotes_ver2.domain.Note;
import ru.gb.mynotes_ver2.ui.adapter.AdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.HeaderAdapterItem;
import ru.gb.mynotes_ver2.ui.adapter.NoteAdapterItem;

public class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NOTE = 1;

    //private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final ArrayList<AdapterItem> data = new ArrayList<>();

    private OnClick onClick;
    private Fragment fragment;

    public NoteAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(Collection<AdapterItem> notes) {
        data.clear();
        data.addAll(notes);
    }

    public int addItem(NoteAdapterItem note) {
        data.add(note);
        return data.size(); //количество элементов в списке
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position) instanceof NoteAdapterItem){
            return TYPE_NOTE;
        }
        if (data.get(position) instanceof NoteAdapterItem){
            return TYPE_HEADER;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_NOTE) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
            return new NoteViewHolder(itemView);
        }
        if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new IllegalStateException("Cannon create this type of view holder");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NoteViewHolder) {
            NoteViewHolder noteViewHolder = (NoteViewHolder) holder;
            NoteAdapterItem note = ((NoteAdapterItem) data.get(position));
            noteViewHolder.getTitle().setText(note.getTitle());
            noteViewHolder.getMessage().setText(note.getMessage());
            //noteViewHolder.getDate().setText(simpleDateFormat.format(note.getCreatedDate()));
            noteViewHolder.getDate().setText(note.getTime());
        }

        if (holder instanceof  HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            String header = ((HeaderAdapterItem)data.get(position)).getHeader();
            headerViewHolder.header.setText(header);
        }



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnClick getOnClick() {
        return onClick;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;

    }

    public int removeItem(Note selectedNote) {
        int index = 0;
        for (int i = 0; i < data.size(); i++){
            if (data.get(i) instanceof NoteAdapterItem && ((NoteAdapterItem) data.get(i))
                    .getNote()
                    .getId()
                    .equals(selectedNote.getId())){
                index = i;
                break;
            }
        }
        data.remove(index);
        return index;
    }

    interface OnClick{
        void onClick(Note note);
        void onLongClick(Note note);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView date;
        private final TextView message;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            CardView cardView = itemView.findViewById(R.id.note_card);
            fragment.registerForContextMenu(cardView);

            title = itemView.findViewById(R.id.title_note);

            date = itemView.findViewById(R.id.date_note);

            message = itemView.findViewById(R.id.content_note);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem){
                        if (getOnClick() != null){
                            getOnClick().onClick(((NoteAdapterItem) item).getNote());
                        }
                    }
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    cardView.showContextMenu();
                    AdapterItem item = data.get(getAdapterPosition());
                    if (item instanceof NoteAdapterItem){
                        if (getOnClick() != null){
                            getOnClick().onLongClick(((NoteAdapterItem) item).getNote());
                        }
                    }
                    return true;
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

    class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView header;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);

        }
    }
}
