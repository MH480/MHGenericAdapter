package alosina.mh.sanwix.com.recyclertestadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.hoseini on 1/15/2018.
 */

public class adapter extends RecyclerView.Adapter<adapter.ViewModel>
{
    List<String> strings = new ArrayList<>();

    @Override
    public int getItemCount()
    {
        return strings.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public ViewModel onCreateViewHolder(ViewGroup parent, int viewType)
    {


        switch (viewType)
        {
            default:
        }
        return new ViewModel(parent);
    }

    @Override
    public void onBindViewHolder(ViewModel holder, int position)
    {


        String s = strings.get(position);
        holder.tv.setText(s);
    }



    class ViewModel extends RecyclerView.ViewHolder
    {
        public TextView tv;
        public ViewModel(View itemView)
        {
            super(itemView);
            tv = itemView.findViewById(0);
        }
    }


}
