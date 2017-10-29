package mh.sanwix.com.GenericAdapter;

import android.view.View;

/**
 * Created by m.hoseini on 10/21/2017.
 */

public interface MHIstickyHeader
{
    int getHeaderLayout(int headerPosition);
    void bindHeaderData(View header, int headerPosition);
    boolean isHeader(int itemPosition);
}
