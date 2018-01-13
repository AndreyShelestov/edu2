package simpleToDo.simpleToDoList;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;



public class ItemDecorationAlbumColumns extends RecyclerView.ItemDecoration {

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(16, 16, 16, 16);

    }
}
