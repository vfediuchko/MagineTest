package test.magine.com.maginetest.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import test.magine.com.maginetest.MagineTestApp;
import test.magine.com.maginetest.R;
import test.magine.com.maginetest.api.responce.Movie;
import test.magine.com.maginetest.api.responce.Movies;
import test.magine.com.maginetest.api.responce.Video;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FIRST_VISIBLE_ITEM_POSITION = "extra_first_visible_item_position";
    public static final String EXTRA_MOVIES = "extra_movies";

    @Bind(R.id.list)
    RecyclerView recyclerView;
    @Bind(R.id.progressbar)
    ProgressBar progressBar;
    private ArrayList<Video> videoList = new ArrayList<>();
    private VideosAdapter adapter;
    private Gson gson;
    private Movies movies;
    private LinearLayoutManager linearLayoutManager;


    /**
     * Binds annotated fields {@code @Bind} in the specified {@link View}
     * Init adapter {@link VideosAdapter}
     * Sets LayoutManager and Adapter for {@link RecyclerView }
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gson = new Gson();
        adapter = new VideosAdapter(this, videoList);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        getData(savedInstanceState);
    }
    /**
     * Saves data to restore state after screen rotation (after destruction of current activity)
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String moviesJson = gson.toJson(movies);
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        outState.putInt(EXTRA_FIRST_VISIBLE_ITEM_POSITION, firstVisibleItemPosition);
        outState.putString(EXTRA_MOVIES, moviesJson);
        super.onSaveInstanceState(outState);
    }

    /**
     * Provides {@link Movies} data from {@link Bundle} savedInstanceState,
     * saved during screen rotation (before activity was destroyed) and
     * Starts loading data if savedInstanceState == null.
     */
    private void getData(Bundle savedInstanceState) {
        if (null == savedInstanceState) {
            loadData();
            return;
        }
        int firstVisibleItemPosition = savedInstanceState.getInt(EXTRA_FIRST_VISIBLE_ITEM_POSITION);
        String moviesJson = savedInstanceState.getString(EXTRA_MOVIES);
        if (!TextUtils.isEmpty(moviesJson)) {
            movies = gson.fromJson(moviesJson, Movies.class);
            if (null != movies) {
                updateData();
                linearLayoutManager.scrollToPosition(firstVisibleItemPosition);
            } else {
                loadData();
            }
        } else {
            loadData();
        }
    }

    /**
     * Loads data from server using {@link test.magine.com.maginetest.api.ApiService}
     * Makes progressbar visible for user while data is loading;
     */
    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        MagineTestApp.getApiService().getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Movies>() {
                            @Override
                            public void call(Movies movies) {
                                MainActivity.this.movies = movies;
                                updateData();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                showDataLoadingError();
                            }
                        }
                );
    }

    /**
     * Provides list of {@link Video} data from {@link Movies};
     * Updates videoList with new data and notify adapter that data set changed;
     * Sets progressBar invisible. (Because loading data process was successfully finished )
     */
    private void updateData() {
        for (Movie movie : movies.categories) {
            for (Video video : movie.videos) {
                videoList.add(video);
            }
        }
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Informs user  that loading data error happened.
     * Allows the user to retry loading data.
     * Sets progressBar invisible. (Because loading data process was finished).
     */
    private void showDataLoadingError() {
        progressBar.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.loading_error))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loadData();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
