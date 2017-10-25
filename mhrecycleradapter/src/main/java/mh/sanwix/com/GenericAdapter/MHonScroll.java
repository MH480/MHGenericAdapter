package mh.sanwix.com.GenericAdapter;

import android.provider.FontsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by m.hoseini on 10/21/2017.
 */

class MHonScroll extends RecyclerView.OnScrollListener
{
    MHIonScrolling onScrolling;
    int dxOld, dyOld;

    public MHonScroll(MHIonScrolling _scScrolling)
    {
        onScrolling = _scScrolling;
        dxOld = 0;
        dyOld = 0;

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState)
    {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        super.onScrolled(recyclerView, dx, dy);
        MHScrollState state;
        View child = recyclerView.findChildViewUnder(dx, dy);
        int LastChildInAdapter = recyclerView.getAdapter().getItemCount() - 1;;

        if (child != null)
        {
            int pos = recyclerView.getChildAdapterPosition(child);

            if (pos == 0)
                state = MHScrollState.Top;
            else if (pos == LastChildInAdapter)
                state = MHScrollState.Bottom;
            else if (dx < dxOld || dy < dyOld)
                state = MHScrollState.MoveToBottom;
            else
                state = MHScrollState.MoveToTop;
            dxOld = dx;
            dyOld = dy;
            onScrolling.onScrolling(child, pos, dx, dy, state);
        }
    }


    private int getFirstItemVisible(RecyclerView rc)
    {
        int FirstItemVisible = 0;
        RecyclerView.LayoutManager lm = rc.getLayoutManager();


        if (lm instanceof GridLayoutManager)
        {
            FirstItemVisible = ((GridLayoutManager) lm).findFirstVisibleItemPosition();
        }
        else if (lm instanceof LinearLayoutManager)
        {
            FirstItemVisible = ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
        }
        else
        {
            StaggeredGridLayoutManager stag = ((StaggeredGridLayoutManager) lm);
            int cellSize = stag.getSpanCount();
            if (cellSize > 0)
            {
                int[] columns = new int[cellSize];
                int[] cell = stag.findFirstVisibleItemPositions(columns);
                if (cell != null && cell.length > 0)
                    FirstItemVisible = cell[0];
            }
        }
        return FirstItemVisible;
    }


}
