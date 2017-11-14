package in.digitechlab.toprankedmovies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by DELL on 9/26/2017.
 */

public class CustomLinearLayoutManager extends LinearLayoutManager {

    public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}