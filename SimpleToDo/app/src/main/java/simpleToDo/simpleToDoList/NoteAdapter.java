package simpleToDo.simpleToDoList;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.sdsmdg.tastytoast.TastyToast;
import com.simpleToDoList.R;

import java.util.ArrayList;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    List<Note> notes = new ArrayList<>();
    private OnItemEditClickListener onItemEditClickListener;
    Context mContext;


    public NoteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public void setItemEditClickListener(OnItemEditClickListener onItemEditClickListener) {
        this.onItemEditClickListener = onItemEditClickListener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteViewHolder holder, final int position) {
        Note note = notes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemEditClickListener != null) {
                    onItemEditClickListener.onItemEditClicked(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showPopupMenu(holder.itemView);
                return true;
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_delete:
                        deleteElement(0);
                        TastyToast.makeText(mContext, "Удалено :)", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
                        return true;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    public Note getItem(int pos) {
        return notes.get(pos);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder  {

        private TextView noteTitle;

        NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.title);

        }
    }

    public void addNote(Note note) {
        notes.add(note);
        notifyDataSetChanged();
    }

    public void replaceNote(Note note, int pos) {
        notes.remove(pos);
        notes.add(pos, note);
        notifyDataSetChanged();
    }

    public void clearList() {
        notes.clear();
        notifyDataSetChanged();
    }
    /*public void addNewElement() {
        Note note = new Note();
        notes.add(note);
        notifyDataSetChanged();
    }*/

/*    public void deleteElement(Note note){
        notes.remove(note);
        notifyDataSetChanged();
    }*/

    public void deleteElement(int pos) {
        if (notes.size() > 0) {
            notes.remove(pos);
            notifyDataSetChanged();
        }
    }

    public interface OnItemEditClickListener {
        void onItemEditClicked(int pos);
    }

}
