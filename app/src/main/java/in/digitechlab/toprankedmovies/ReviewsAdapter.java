package in.digitechlab.toprankedmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.digitechlab.popularmovies1.R;

/**
 * Created by DELL on 9/24/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>{

    private List<Review> mReviewList;
    private LayoutInflater mInflater;
    private Context mContext;

    public ReviewsAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mReviewList = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_review, parent, false);
        final ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        String author = mReviewList.get(position).getAuthor();
        holder.textViewAuthor.setText(String.format("Author: %s", author));
        holder.textViewContent.setText(mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() { return (mReviewList == null) ? 0 : mReviewList.size(); }

    public void setReviewList(List<Review> ReviewList)
    {
        this.mReviewList.clear();
        this.mReviewList.addAll(ReviewList);
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder
    {
        final TextView textViewAuthor;
        final TextView textViewContent;

        public ReviewViewHolder(View itemView)
        {
            super(itemView);
            textViewAuthor = (TextView)itemView.findViewById(R.id.review_author);
            textViewContent = (TextView)itemView.findViewById(R.id.review_content);
        }
    }
}
