package alosina.mh.sanwix.com.recyclertestadapter;

import mh.sanwix.com.GenericAdapter.MHBindView;
import mh.sanwix.com.GenericAdapter.MHBindViewAction;

public class myModel
{
    @MHBindView(value = R.id.chk,isHtml = true)
    public boolean data;


    @MHBindView(value = R.id.btn,isHtml = true,hiddenIfNull = true)
    public String salam;


    int i = 0;
    public myModel(String data)
    {
        this.data = false;
        salam ="clickable";
        i++;
    }


    //@MHBindViewAction(value = R.id.btn,hiddenIfNull = true)
    public String get()
    {
        return null;//String.valueOf(i);
    }

}
