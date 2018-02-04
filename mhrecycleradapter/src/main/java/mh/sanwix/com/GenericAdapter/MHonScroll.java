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

public class MHonScroll extends RecyclerView.OnScrollListener
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
        MHScrollState state = MHScrollState.NotScrolling;
        int LastChildInAdapter = recyclerView.getAdapter().getItemCount() - 1;

        int pos = getFirstItemVisible(recyclerView);
        int lastPos = getLastItemVisible(recyclerView);

        if (pos == 0 && dy < 0)
            state = MHScrollState.Top;
        else if (lastPos == LastChildInAdapter && dy > 0)
        {
            pos = lastPos;
            state = MHScrollState.Bottom;
        }
        else if (dx > 0 || dy > 0)
            state = MHScrollState.MoveToBottom;
        else if (dx < 0 || dy < 0)
            state = MHScrollState.MoveToTop;

        /*else if (dx < dxOld || dy < dyOld)
            state = MHScrollState.MoveToBottom;
        else
            state = MHScrollState.MoveToTop;*/
        dxOld = dx;
        dyOld = dy;
        if (state != MHScrollState.NotScrolling)
        {
            if (state == MHScrollState.Bottom)
                recyclerView.stopScroll();
            onScrolling.onScrolling(null, pos, dx, dy, state);
        }

    }


    private int getFirstItemVisible(RecyclerView rc)
    {
        int FirstItemVisible = 0;
        RecyclerView.LayoutManager lm = rc.getLayoutManager();


        if (lm instanceof GridLayoutManager)
        {
            FirstItemVisible = ((GridLayoutManager) lm).findFirstCompletelyVisibleItemPosition();
        }
        else if (lm instanceof LinearLayoutManager)
        {
            FirstItemVisible = ((LinearLayoutManager) lm).findFirstCompletelyVisibleItemPosition();
        }
        else
        {
            StaggeredGridLayoutManager stag = ((StaggeredGridLayoutManager) lm);
            int cellSize = stag.getSpanCount();
            if (cellSize > 0)
            {
                int[] columns = new int[cellSize];
                int[] cell = stag.findFirstCompletelyVisibleItemPositions(columns);
                if (cell != null && cell.length > 0)
                    FirstItemVisible = cell[0];
            }
        }
        return FirstItemVisible;
    }


    private int getLastItemVisible(RecyclerView rc)
    {
        int LastItemVisible = 0;
        RecyclerView.LayoutManager lm = rc.getLayoutManager();
        LastItemVisible = lm.getItemCount() -1;
        if (LastItemVisible < 0)
            LastItemVisible = 0;

        if (lm instanceof GridLayoutManager)
        {
            LastItemVisible = ((GridLayoutManager) lm).findLastCompletelyVisibleItemPosition();
        }
        else if (lm instanceof LinearLayoutManager)
        {
            LastItemVisible = ((LinearLayoutManager) lm).findLastCompletelyVisibleItemPosition();
        }
        else
        {
            StaggeredGridLayoutManager stag = ((StaggeredGridLayoutManager) lm);
            int cellSize = stag.getSpanCount();
            if (cellSize > 0)
            {
                int[] columns = new int[cellSize];
                int[] cell = stag.findLastCompletelyVisibleItemPositions(columns);
                if (cell != null && cell.length > 0)
                    LastItemVisible = cell[cell.length - 1];
            }
        }
        return LastItemVisible;
    }


}
