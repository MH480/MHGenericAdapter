package alosina.mh.sanwix.com.recyclertestadapter;

import mh.sanwix.com.GenericAdapter.MHBindView;

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



}
