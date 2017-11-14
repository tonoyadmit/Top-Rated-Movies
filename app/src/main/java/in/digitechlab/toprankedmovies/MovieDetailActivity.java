package in.digitechlab.toprankedmovies;

/**
 * Created by DELL on 9/8/2017.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import in.digitechlab.popularmovies1.BuildConfig;
import in.digitechlab.popularmovies1.R;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static java.lang.Integer.parseInt;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private static final String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w500";
    private static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";
    private Movie mMovie;
    ImageView backdrop;
    ImageView poster;
    TextView title;
    TextView description;
    TextView user_rating;
    TextView release_date;
    ImageButton favourite_button;
    private RecyclerView rRecyclerView , tRecyclerView;
    private ReviewsAdapter rAdapter;
    private TrailerAdapter tAdapter;
    boolean isMarkFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        if (getIntent().hasExtra(EXTRA_MOVIE)) {
            mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        } else {
            throw new IllegalArgumentException("Detail activity must receive a movie parcelable");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mMovie.getTitle());



        backdrop = (ImageView) findViewById(R.id.backdrop);
        title = (TextView) findViewById(R.id.movie_title);
        description = (TextView) findViewById(R.id.movie_description);
        poster = (ImageView) findViewById(R.id.movie_poster);
        user_rating = (TextView) findViewById(R.id.user_rating);
        release_date = (TextView) findViewById(R.id.release_date);
        favourite_button = (ImageButton) findViewById(R.id.button_favorite);

        if (savedInstanceState == null) {
            isMarkFavourite = MovieIsFavourite(parseInt(mMovie.getId()));
            updateButtonImage();
        }
        else {
            isMarkFavourite = savedInstanceState.getBoolean("mark favorite");
            updateButtonImage();
        }

        favourite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMarkFavourite = MovieIsFavourite(parseInt(mMovie.getId()));
                favouriteButton();
            }
        });

        title.setText(mMovie.getTitle());
        description.setText(mMovie.getDescription());
        user_rating.setText("Movie Rating: "+mMovie.getUserRating()+"/10");
        release_date.setText(String.format("%.4s", mMovie.getReleaseDate()));

        String urlBuilder1 = new StringBuilder()
                .append(BASE_POSTER_URL)
                .append(mMovie.getPoster()).toString();

        String urlBuilder2 = new StringBuilder()
                .append(BASE_BACKDROP_URL)
                .append(mMovie.getBackdrop()).toString();

        Picasso.with(this)
                .load(urlBuilder1)
                .into(poster);
        Picasso.with(this)
                .load(urlBuilder2)
                .into(backdrop);


        tRecyclerView = (RecyclerView) findViewById(R.id.recycler_trailers);
        tRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        tAdapter = new TrailerAdapter(this);
        tRecyclerView.setAdapter(tAdapter);

        retrofitTrailers();

        rRecyclerView = (RecyclerView) findViewById(R.id.recycler_reviews);
        rRecyclerView.setLayoutManager(new CustomLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        rAdapter = new ReviewsAdapter(this);
        rRecyclerView.setAdapter(rAdapter);

        retrofitReviews();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("mark favorite", isMarkFavourite);
    }



    private void retrofitTrailers() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3/movie/"+mMovie.getId())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.THE_MOVIEDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MovieTrailersApi service = restAdapter.create(MovieTrailersApi.class);
        service.getAllTrailers(new Callback<Trailer.TrailerResult>() {
            @Override
            public void success(Trailer.TrailerResult TrailerResult, Response response) {
                tAdapter.setTrailerList(TrailerResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }


    private void retrofitReviews() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.themoviedb.org/3/movie/"+mMovie.getId())
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", BuildConfig.THE_MOVIEDB_API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        MovieReviewsApi service = restAdapter.create(MovieReviewsApi.class);
        service.getAllReviews(new Callback<Review.ReviewResult>() {
            @Override
            public void success(Review.ReviewResult ReviewResult, Response response) {
                rAdapter.setReviewList(ReviewResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });

    }


    private boolean MovieIsFavourite(int movieId) {
        Cursor cursor = this.getContentResolver()
                .query(FavouriteMovies.FavoriteEntry.CONTENT_URI,
                        new String[]{FavouriteMovies.FavoriteEntry.MOVIE_ID}, null, null, null);
        assert cursor != null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int id = cursor.getInt(cursor.getColumnIndex(
                        FavouriteMovies.FavoriteEntry.MOVIE_ID));
                if (id == movieId) {
                    cursor.close();
                    return true;
                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        return false;
    }

    private void updateButtonImage() {
        if (isMarkFavourite) {
            favourite_button.setImageDrawable(getResources()
                    .getDrawable(R.drawable.fav_selected));
        } else {
            favourite_button.setImageDrawable(getResources()
                    .getDrawable(R.drawable.favourite_button));
        }
    }

    private void favouriteButton() {
        if (isMarkFavourite) {
            this.getContentResolver()
                    .delete(FavouriteMovies.FavoriteEntry.CONTENT_URI,
                            FavouriteMovies.FavoriteEntry.MOVIE_ID + " = ? ",
                            new String[]{String.valueOf(mMovie.getId())});

            isMarkFavourite = false;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(FavouriteMovies.FavoriteEntry.MOVIE_ID, mMovie.getId());
            contentValues.put(FavouriteMovies.FavoriteEntry.TITLE, mMovie.getTitle());
            contentValues.put(FavouriteMovies.FavoriteEntry.POSTER_PATH, mMovie.getPoster());
            contentValues.put(FavouriteMovies.FavoriteEntry.OVERVIEW, mMovie.getDescription());
            contentValues.put(FavouriteMovies.FavoriteEntry.BACKDROP, mMovie.getBackdrop());
            contentValues.put(FavouriteMovies.FavoriteEntry.VOTE_AVERAGE, mMovie.getUserRating());
            contentValues.put(FavouriteMovies.FavoriteEntry.RELEASE_DATE, mMovie.getReleaseDate());

            this.getContentResolver()
                    .insert(FavouriteMovies.FavoriteEntry.CONTENT_URI, contentValues);
            isMarkFavourite = true;
        }
        updateButtonImage();
    }

}
