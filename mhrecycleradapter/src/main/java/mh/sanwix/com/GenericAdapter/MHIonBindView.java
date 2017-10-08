package mh.sanwix.com.GenericAdapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by m.hoseini on 8/28/2017.
 */

public interface MHIonBindView
{
    /**
     * it animates the onbindview adapter
     *
     * @param rootView    the root view of the layout as view gruop
     * @param HolderClass the holder class of adapter
     * @param posision    the row position in adapter
     * @param childViews  all Children vieis in adapter witch has MHBindView Anottation
     */
    void BindViewHolder(ViewGroup rootView, Object HolderClass, int posision, View... childViews);
}
