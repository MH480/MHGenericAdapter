package mh.sanwix.com.GenericAdapter;

import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.hoseini on 2/4/2018.
 */
class MHBindDataAsync<Model> extends AsyncTask<MHKeyValuePair<Integer, MHViewHolder<Model>>, Void, Void>
{
    private MHRecyclerAdapter mhRecyclerAdapter;
    int position;
    MHViewHolder<Model> holder;
    private MHKeyValuePair<Integer, MHViewHolder<Model>> data;

    public MHBindDataAsync(MHRecyclerAdapter mhRecyclerAdapter)
    {
        this.mhRecyclerAdapter = mhRecyclerAdapter;
    }

    @Override
    protected Void doInBackground(MHKeyValuePair<Integer, MHViewHolder<Model>>[] mhViewHolders)
    {
        data = mhViewHolders[0];
        holder = data.getValue();
        position = data.getKey();
        mhRecyclerAdapter.getDataRefactoring(position);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        List<View> childs = new ArrayList<>();
        for (int i = 0; i < mhRecyclerAdapter.mKeyValueData.size(); i++) // propertiesNames is string because i need property value with column i can`t get property value
        {
            Pair<MHBindView, Object> data1 = mhRecyclerAdapter.mKeyValueData.get(i);
            View j = holder.setValue(data1.first, data1.first.isPosition() ? position : data1.second);
            childs.add(j);
        }

        for (int i = 0; i < mhRecyclerAdapter.mKeyValueAction.size(); i++)
        {
            Pair<MHBindViewAction, Object> data2 = mhRecyclerAdapter.mKeyValueAction.get(i);
            holder.setValue(data2.first, data2.second);
        }

        mhRecyclerAdapter.mKeyValueData = new MH_Map<>();
        mhRecyclerAdapter.mKeyValueAction = new MH_Map<>();
        if (mhRecyclerAdapter.bindView != null && holder.itemView instanceof ViewGroup)
        {
            View[] array = new View[0];

            if (childs.size() > 0)
            {
                array = new View[childs.size()];
                childs.toArray(array);
            }
            mhRecyclerAdapter.bindView.BindViewHolder((ViewGroup) holder.itemView, holder.getMyModel(), position, array);

        }
    }

    public void setData(MHKeyValuePair<Integer, MHViewHolder<Model>> data)
    {
        this.data = data;
    }
}
