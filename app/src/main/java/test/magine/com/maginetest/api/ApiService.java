package test.magine.com.maginetest.api;

import retrofit.http.GET;
import rx.Observable;
import test.magine.com.maginetest.api.responce.Movies;

/**
 * Back-end API interface
 */
public interface ApiService {

    String BASE_URI = "http://commondatastorage.googleapis.com";


    /**
     * Get data request.
     *
     * @return {@link rx.Observable} for {@link Movies} that contains list of {Movie}
     */

    @GET("/gtv-videos-bucket/sample/videos-enhanced-c.json")
    Observable<Movies> getData();

}
