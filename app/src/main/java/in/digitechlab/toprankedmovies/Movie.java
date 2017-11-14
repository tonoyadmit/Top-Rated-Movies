package in.digitechlab.toprankedmovies;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by DELL on 9/8/2017.
 */

public class Movie implements Parcelable {
    @SerializedName("id")
    private String movieId;
    private String title;
    @SerializedName("poster_path")
    private String poster;
    @SerializedName("overview")
    private String description;
    @SerializedName("backdrop_path")
    private String backdrop;
    @SerializedName("vote_average")
    private String userRating;
    @SerializedName("release_date")
    private String releaseDate;

    public Movie() {}

    public Movie(String movieId, String title, String poster, String description, String backdrop, String userRating, String releaseDate) {
        this.movieId = movieId;
        this.title = title;
        this.poster = poster;
        this.description = description;
        this.backdrop = backdrop;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        movieId = in.readString();
        title = in.readString();
        poster = in.readString();
        description = in.readString();
        backdrop = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() { return backdrop; }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getUserRating() { return userRating; }

    public void setUserRating(String userRating) { this.userRating = userRating; }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getId() { return movieId; }

    public void setId(String id) { this.movieId = id; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieId);
        parcel.writeString(title);
        parcel.writeString(poster);
        parcel.writeString(description);
        parcel.writeString(backdrop);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}
