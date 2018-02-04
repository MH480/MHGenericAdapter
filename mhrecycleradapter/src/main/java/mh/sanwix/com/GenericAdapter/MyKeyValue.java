package mh.sanwix.com.GenericAdapter;

import android.view.View;

class MyKeyValue
{
    int Key;
    View Value;
    Class clazz;
    private View.OnClickListener Listener;
    private boolean isPosistion;

    MyKeyValue(int key, View value, Class _cllaz, boolean _isPosistion)
    {
        Key = key;
        Value = value;
        clazz = _cllaz;
        isPosistion = _isPosistion;
    }

    MyKeyValue(int key, View value, Class _cllaz)
    {
        Key = key;
        Value = value;
        clazz = _cllaz;
        isPosistion = false;
    }

    public MyKeyValue(int key, View.OnClickListener listener)
    {
        Key = key;
        Listener = listener;
    }
}
