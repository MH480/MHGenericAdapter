package alosina.mh.sanwix.com.recyclertestadapter;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import mh.sanwix.com.GenericAdapter.MHBindView;

@MHBindView(R.layout.layout_item_recycler)
public class MyVHModel
{

    @MHBindView(value = R.id.btn,isClickable = true)
    public Button tvSubTitle;
    @MHBindView(R.id.chk)
    public CheckBox tvTitle;


}
