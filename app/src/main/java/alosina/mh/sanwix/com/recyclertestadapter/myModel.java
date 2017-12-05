package alosina.mh.sanwix.com.recyclertestadapter;

import mh.sanwix.com.GenericAdapter.MHBindView;
import mh.sanwix.com.GenericAdapter.MHBindViewAction;

public class myModel
{
    @MHBindView(value = R.id.chk)
    public boolean data;


    @MHBindView(value = R.id.btn)
    public String salam;


    int i = 0;
    public myModel(String data)
    {
        this.data = false;
        salam = "clickable";
        i++;
    }


    @MHBindViewAction(R.id.btn)
    public String get()
    {

        return String.valueOf(i);
    }

}
