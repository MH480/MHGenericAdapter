package mh.sanwix.com.GenericAdapter;

import android.view.View;

class MyKeyValue
{
    public int Key;
    public View Value;
    public Class clazz;
    public View.OnClickListener Listener;
    private boolean isPosistion;
    public MyKeyValue(int key,View value, Class _cllaz,boolean _isPosistion)
    {
        Key = key;
        Value = value;
        clazz = _cllaz;
        isPosistion = _isPosistion;
    }

    public MyKeyValue(int key,View value, Class _cllaz)
    {
        Key = key;
        Value = value;
        clazz = _cllaz;
        isPosistion = false;
    }

    public MyKeyValue(int key,  View.OnClickListener listener )
    {
        Key = key;
        Listener = listener;
    }
}
