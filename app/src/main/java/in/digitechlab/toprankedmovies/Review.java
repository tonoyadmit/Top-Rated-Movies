package in.digitechlab.toprankedmovies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 9/24/2017.
 */

public class Review implements Parcelable {

    @SerializedName("id")
    private String reviewId;
    private String author;
    private String content;

    public Review() {}

    protected Review(Parcel in) {
        reviewId = in.readString();
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) { return new Review(in); }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };


    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() { return 0;  }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewId);
        parcel.writeString(author);
        parcel.writeString(content);
    }

    public static class ReviewResult {
        private List<Review> results;

        public List<Review> getResults() {
            return results;
        }
    }
}
