package mh.sanwix.com.GenericAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by m.hoseini on 8/7/2017.
 */

class MHTouchItemClick<ViewHolderModel> implements RecyclerView.OnItemTouchListener
{
    private GestureDetector mGestureDetector;
    private RecyclerView rv;
    private Class<ViewHolderModel> viewHolderModel;
    private List<Integer> ClickableIdsOnClick, clickable;
    private boolean isVHoK;
    private MHOnItemClickListener mListener;

    MHTouchItemClick(RecyclerView _rv, MHOnItemClickListener listener, Class<ViewHolderModel> myVHolder)
    {
        ClickableIdsOnClick = new ArrayList<>();
        clickable = new ArrayList<>();
        mListener = listener;
        rv = _rv;
        //setViewHolderModel(myVHolder);
        //findonClickAnot(myVHolder);
        this.viewHolderModel = myVHolder;
        isVHoK = false;
        findIDS(myVHolder);
        mGestureDetector = new GestureDetector(_rv.getContext(), new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                super.onLongPress(e);
                View raw = rv.findChildViewUnder(e.getX(), e.getY());
                if (raw == null)
                    return;
                View clickedview = null;
                if (isVHoK)
                {
                    for (int i = 0; i < ClickableIdsOnClick.size(); i++)
                    {
                        int id = ClickableIdsOnClick.get(i);
                        View tmp = raw.findViewById(id);
                        clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                        if (clickedview != null)
                            break;
                    }
                }
                else
                {
                    if (raw instanceof ViewGroup)
                    {
                        for (int i = 0; i < ClickableIdsOnClick.size(); i++)
                        {
                            int id = ClickableIdsOnClick.get(i);
                            View tmp = raw.findViewById(id);
                            clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                            if (clickedview != null)
                                break;
                        }
                    }

                }

                if (mListener != null)
                    mListener.onItemLongClick(rv.getId(), raw, rv.getChildLayoutPosition(raw), clickedview);
            }
        });
    }


/*

    public void setIds(List<Integer> ids)
    {
        if (this.ClickableIds == null || this.ClickableIds.isEmpty())
            this.ClickableIds = new ArrayList<>();
        ClickableIds.addAll(ids);
        //distinct
        Set<Integer> hs = new HashSet<>();
        hs.addAll(ClickableIds);
        ClickableIds.clear();
        ClickableIds.addAll(hs);
        //distinct

        isVHoK = true;
    }
*/


    private void findIDS(Class<ViewHolderModel> model)
    {
        ViewHolderModel MyModel = null;
        //try
        try
        {
            MyModel = model.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (MyModel == null)
        {
            Log.i("MH_VHModel ", " setViewModel: Null");
            return;
        }

        for (Field f : model.getDeclaredFields())
        {
            Class<?> clazz = MyModel.getClass();
            int modifier = f.getModifiers();
            MHBindView col = f.getAnnotation(MHBindView.class);
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) &&
                    !Modifier.isFinal(modifier) && col != null && col.isClickable())
                clickable.add(col.value());
        }
    }

    private void findonClickAnot(Class<ViewHolderModel> model)
    {
        ViewHolderModel MyModel = null;
        //try
        try
        {
            MyModel = model.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (MyModel == null)
        {
            Log.i("MH_VHModel ", " setViewModel: Null");
            return;
        }

        for (Field f : model.getDeclaredFields())
        {
            Class<?> clazz = MyModel.getClass();
            int modifier = f.getModifiers();
            MHonClick col = f.getAnnotation(MHonClick.class);
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) &&
                    !Modifier.isFinal(modifier) && col != null)
                ClickableIdsOnClick.add(col.value());
        }
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
    {
        View raw = rv.findChildViewUnder(e.getX(), e.getY());
        if (raw == null)
            return false;
        View clickedview = null;
        if (isVHoK)
        {
            for (int i = 0; i < clickable.size(); i++)
            {
                int id = clickable.get(i);
                View tmp = raw.findViewById(id);
                clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                if (clickedview != null)
                    break;
            }
        }
        else
        {
            if (raw instanceof ViewGroup)
            {
                for (int i = 0; i < clickable.size(); i++)
                {
                    int id = clickable.get(i);
                    View tmp = raw.findViewById(id);
                    clickedview = isPointInsideView(e.getRawX(), e.getRawY(), tmp);
                    if (clickedview != null)
                        break;
                }
            }

        }

        if (mListener != null && mGestureDetector.onTouchEvent(e))
            mListener.onItemClick(rv.getId(), raw, rv.getChildLayoutPosition(raw), clickedview);
        return false;
    }

    private View isPointInsideView(float x, float y, View child)
    {
        if (child == null)
            return null;

        int location[] = new int[2];
        child.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + child.getWidth())) &&
                (y > viewY && y < (viewY + child.getHeight())))
        {
            return child;
        }
        else
        {
            return null;
        }

    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e)
    {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept)
    {

    }


}
