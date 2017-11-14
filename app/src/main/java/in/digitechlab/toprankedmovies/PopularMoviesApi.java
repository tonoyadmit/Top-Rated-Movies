package in.digitechlab.toprankedmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by DELL on 9/8/2017.
 */

public interface PopularMoviesApi {
    @GET("/movie/popular")
    void getAllMovies(Callback<Movie.MovieResult> cb);
}
