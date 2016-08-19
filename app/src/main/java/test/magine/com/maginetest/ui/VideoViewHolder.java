package test.magine.com.maginetest.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.magine.com.maginetest.R;
import test.magine.com.maginetest.api.responce.Video;

/**
 * VideoViewHolder describes an item view and metadata about its place within the RecyclerView.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {

    public static final String IMAGE_BASE_URL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/";

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.studio)
    TextView studio;
    @Bind(R.id.image)
    ImageView image;

    public VideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
    /**
     * Fills item view with {@link Video} data.
     */
    public void setItem(Video item) {
        title.setText(item.title);
        studio.setText(item.studio);
        Glide.with(image.getContext())
                .load(IMAGE_BASE_URL + item.imageSmall)
                .dontAnimate()
                .fitCenter()
                .into(image);
    }
}
