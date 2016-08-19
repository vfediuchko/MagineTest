package test.magine.com.maginetest;

import android.app.Application;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import test.magine.com.maginetest.api.ApiService;

/**
 * MagineTestApp provides own implementation of {@link Application} class.
 **/
public class MagineTestApp extends Application {
    private static ApiService mApiService;

    /**
     * Creates instance of {@link ApiService}  class.
     **/
    @Override
    public void onCreate() {
        super.onCreate();
        mApiService = new RestAdapter.Builder()
                .setEndpoint(ApiService.BASE_URI)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(new Gson()))
                .build()
                .create(ApiService.class);

    }

    /**
     * Provides instance of  {@link ApiService} .
     **/
    public static ApiService getApiService() {
        return MagineTestApp.mApiService;
    }
}
