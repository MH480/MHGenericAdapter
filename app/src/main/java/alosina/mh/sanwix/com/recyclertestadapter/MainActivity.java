package alosina.mh.sanwix.com.recyclertestadapter;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mh.sanwix.com.GenericAdapter.MHIonBindView;
import mh.sanwix.com.GenericAdapter.MHIonScrolling;
import mh.sanwix.com.GenericAdapter.MHIstickyHeader;
import mh.sanwix.com.GenericAdapter.MHOnItemClickListener;
import mh.sanwix.com.GenericAdapter.MHRecyclerAdapter;
import mh.sanwix.com.GenericAdapter.MHScrollState;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, MHOnItemClickListener, MHIonBindView, MHIstickyHeader, MHIonScrolling
{
    List<myModel> models = new ArrayList<>();
    RecyclerView mRecycler;
    MHRecyclerAdapter<myModel, MyVHModel> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        models.add(new myModel("header"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));



        mAdapter = new MHRecyclerAdapter<myModel, MyVHModel>(MyVHModel.class);
        mRecycler = findViewById(R.id.mrecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setItems(models);
        models.add(new myModel("asb"));
        models.add(new myModel("asb"));
        mAdapter.setEmptyView(MyEmptyVH.class);
        mAdapter.setBindViewCallBack(this);
        mRecycler.addOnItemTouchListener(mAdapter.buildTouchItemListener(mRecycler, this));
        mRecycler.setOnScrollListener(mAdapter.buildScrollListener(this));
        mRecycler.addItemDecoration(mAdapter.buildStickyHeader(mRecycler,this));
        mAdapter.setLazyView(R.layout.layout_item_waiting);

        /*mAdapter.beginLazyLoading();
        new CountDownTimer(5000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Log.i(" MH TAG", "onTick: "  + millisUntilFinished);
            }

            @Override
            public void onFinish()
            {
                mAdapter.endLazyLoading();
                mAdapter.setItems(models);
            }
        }.start();*/



    }


    @Override
    public void onClick(View view)
    {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFocusChange(View view, boolean b)
    {
        int pos = Integer.parseInt(view.getTag().toString());
        final myModel m = mAdapter.getItem(pos);
        m.salam = ((EditText) view).getText().toString();
        if (!b)
        mAdapter.notifyItemChanged(pos);
    }

    @Override
    public void onItemClick(int recycler_id, View itemView, int position, @Nullable View clickedView)
    {
        if (clickedView != null && clickedView.getId() == R.id.btn)
            Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(int recycler_id, View itemView, int position, @Nullable View clickedView)
    {

    }


    @Override
    public void BindViewHolder(ViewGroup rootView, Object HolderClass, int posision, View... childViews)
    {
        Log.i("asd", "BindViewHolder: ");
    }


    @Override
    public int getHeaderLayout(int headerPosition)
    {
        return R.layout.layout_item_header;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition)
    {

    }

    @Override
    public boolean isHeader(int itemPosition)
    {
        return itemPosition == 3;
    }

    @Override
    public void onScrolling(View child, int position, int dx, int dy, MHScrollState state)
    {
        switch (state)
        {
            case Top:
                Log.i(" MH ", "onScrolling: TOP : " + String.valueOf(position));
                break;
            case Bottom:
                Log.i(" MH ", "onScrolling: Bottom : " + String.valueOf(position));
                break;
            case MoveToBottom:
                //Log.i(" MH ", "onScrolling: Moving to Bottom");
                break;
            case MoveToTop:
                //Log.i(" MH ", "onScrolling: Moving to TOP");
                break;
        }
    }
}


