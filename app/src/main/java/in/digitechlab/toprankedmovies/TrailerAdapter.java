package in.digitechlab.toprankedmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{


    private List<Trailer> mTrailerList;
    private LayoutInflater mInflater;
    private Context mContext;

    public TrailerAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mTrailerList = new ArrayList<>();
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_trailer, parent, false);
        final TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                String trailerId = mTrailerList.get(position).getKey();
                playYouTube(trailerId);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.trailerName.setText(mTrailerList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (mTrailerList == null) ? 0 : mTrailerList.size();
    }

    public void setTrailerList(List<Trailer> TrailerList)
    {
        this.mTrailerList.clear();
        this.mTrailerList.addAll(TrailerList);
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
    {
        final TextView trailerName;

        public TrailerViewHolder(View itemView)
        {
            super(itemView);
            trailerName = (TextView) itemView.findViewById(R.id.trailer_name);
        }
    }

    private void playYouTube(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            mContext.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            mContext.startActivity(intent);
        }
    }
}
