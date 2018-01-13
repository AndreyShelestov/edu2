package randomword.random.com.twitterdetective;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class SearchesAdapter extends RecyclerView.Adapter<SearchesAdapter.ViewHolder> {
    private final View.OnClickListener clickListener;
    private final View.OnLongClickListener longClickListener;
    private final List<String> tags;

    public SearchesAdapter(View.OnClickListener clickListener, View.OnLongClickListener longClickListener, List<String> tags) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
        this.tags = tags;
    }

    public SearchesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return (new ViewHolder(view, clickListener, longClickListener));
    }

    @Override
    public void onBindViewHolder(SearchesAdapter.ViewHolder holder, int position) {
        holder.textView.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(View itemView, View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.textViewRec);
            itemView.setOnClickListener(clickListener);
            itemView.setOnLongClickListener(longClickListener);
        }
    }
}
