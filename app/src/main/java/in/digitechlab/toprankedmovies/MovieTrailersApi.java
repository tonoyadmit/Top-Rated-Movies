package in.digitechlab.toprankedmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by DELL on 9/25/2017.
 */

public interface MovieTrailersApi {

    @GET("/videos")
    void getAllTrailers(Callback<Trailer.TrailerResult> cb);

}