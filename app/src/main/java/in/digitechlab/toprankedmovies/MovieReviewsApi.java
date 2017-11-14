package in.digitechlab.toprankedmovies;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by DELL on 9/25/2017.
 */

public interface MovieReviewsApi {

    @GET("/reviews")
    void getAllReviews(Callback<Review.ReviewResult> cb);

}
