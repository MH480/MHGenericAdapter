package alosina.mh.sanwix.com.recyclertestadapter;

import mh.sanwix.com.GenericAdapter.MHBindView;
import mh.sanwix.com.GenericAdapter.MHBindViewChild;

public class myModel
{
    @MHBindView(value = R.id.chk)
    public boolean data;

    @MHBindView(value = R.id.btn)
    public String salam;



    public myModel(String data)
    {
        this.data = false;
        salam = "clickable";

    }

    @MHBindViewChild(R.id.btn)
    public String get()
    {

        return "";
    }

}
