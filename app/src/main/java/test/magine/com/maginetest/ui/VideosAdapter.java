package test.magine.com.maginetest.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import test.magine.com.maginetest.R;
import test.magine.com.maginetest.api.responce.Video;

/**
 * VideosAdapter provides a binding from an app-specific data set to views that are displayed
 * within a {@link RecyclerView}.
 */
public class VideosAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    public VideosAdapter(Context context, ArrayList<Video> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    private LayoutInflater inflater;
    private ArrayList<Video> data;

    /**
     * Called when RecyclerView needs a new {@link VideoViewHolder} of the given type to represent
     * an item.
     * The new VideoViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(VideoViewHolder, int)} .
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new VideoViewHolder that holds a View of the given view type.
     */
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(inflater.inflate(R.layout.item_video, parent, false));
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * updates the contents of the {@link VideoViewHolder#itemView} to reflect the item at
     * the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.setItem(data.get(position));
    }

    /**
     * @return The number of items from data currently available.
     **/
    @Override
    public int getItemCount() {
        return data.size();
    }
}
