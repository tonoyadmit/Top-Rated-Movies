package in.digitechlab.toprankedmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

import in.digitechlab.popularmovies1.BuildConfig;
import in.digitechlab.popularmovies1.R;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private MoviesAdapter.MovieViewHolder mViewHolder;
    public String api_settings = "not selected";
    private ArrayList<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(this, "MOVIES ARE LODING ...", Toast.LENGTH_LONG).show();

        if (savedInstanceState != null) {
            api_settings = savedInstanceState.getString("mydata");
        } else {

        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        movieList = new ArrayList<Movie>();

        retrofitMovies();
    }

    @Override
    public void onResume(){
        super.onResume();
        retrofitMovies();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("mydata", api_settings);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sort_most_popular:
                this.api_settings = "most popular";
                retrofitMovies();
                break;
            case R.id.sort_top_rated:
                this.api_settings = "top rated";
                retrofitMovies();
                break;
            case R.id.my_favourite:
                this.api_settings = "favorites";
                retrofitMovies();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrofitMovies() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.THE_MOVIEDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        if(api_settings == "most popular" || api_settings == "not selected") {
            PopularMoviesApi service = restAdapter.create(PopularMoviesApi.class);
            service.getAllMovies(new Callback<Movie.MovieResult>() {
                @Override
                public void success(Movie.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else if (api_settings == "top rated") {
            TopRatedMoviesApi service = restAdapter.create(TopRatedMoviesApi.class);
            service.getAllMovies(new Callback<Movie.MovieResult>() {
                @Override
                public void success(Movie.MovieResult movieResult, Response response) {
                    mAdapter.setMovieList(movieResult.getResults());
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        } else if (api_settings == "favorites") {
            getFavouriteMovies();
        }
    }

    private void getFavouriteMovies() {
        Cursor cursor = this.getContentResolver()
                .query(FavouriteMovies.FavoriteEntry.CONTENT_URI, null, null, null, null);

        mAdapter.clear(movieList);
        assert cursor != null;

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Movie movie = new Movie(
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.TITLE)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.BACKDROP)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.VOTE_AVERAGE)),
                        cursor.getString(cursor.getColumnIndex(
                                FavouriteMovies.FavoriteEntry.RELEASE_DATE)));
                movieList.add(movie);

            } while (cursor.moveToNext());
        }else{
            Toast.makeText(this, "No List Found" , Toast.LENGTH_LONG).show();
        }
        mAdapter.setMovieList(movieList);
        cursor.close();
    }

}
