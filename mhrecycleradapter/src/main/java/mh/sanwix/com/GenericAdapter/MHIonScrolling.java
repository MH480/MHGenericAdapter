package mh.sanwix.com.GenericAdapter;

import android.view.View;

/**
 * Created by m.hoseini on 10/21/2017.
 */

public interface MHIonScrolling
{
    void onScrolling(View child,int position,int dx,int dy,MHScrollState state);
}
