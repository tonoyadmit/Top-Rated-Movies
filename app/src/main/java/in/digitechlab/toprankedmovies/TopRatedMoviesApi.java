package in.digitechlab.toprankedmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by DELL on 9/8/2017.
 */

public interface TopRatedMoviesApi {
    @GET("/movie/top_rated")
    void getAllMovies(Callback<Movie.MovieResult> cb);
}
