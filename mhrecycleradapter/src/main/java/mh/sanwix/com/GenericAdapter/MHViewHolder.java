package mh.sanwix.com.GenericAdapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.hoseini on 8/26/2017.
 */

class MHViewHolder<T> extends RecyclerView.ViewHolder
{
    private T MyModel;
    private List<MyKeyValue> ItemsHolder;

    public MHViewHolder(View itemView, Class<T> model)
    {
        super(itemView);
        ItemsHolder = new ArrayList<>();
        if (model != null)
            setViewModel(model, itemView);

    }

    private void setViewModel(Class<T> model, View itemView)
    {
        MyModel = null;
        try
        {
            MyModel = model.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        if (MyModel == null)
        {
            Log.i("MH_Model ", " setViewModel: Null");
            return;
        }

        Class<?> clazz = MyModel.getClass();
        for (Field f : clazz.getDeclaredFields())
        {

            int modifier = f.getModifiers();
            MHBindView col = f.getAnnotation(MHBindView.class);
            if (Modifier.isPublic(modifier) && !Modifier.isStatic(modifier) && !Modifier.isFinal(modifier) && col != null)
                try
                {
                    f.setAccessible(true);
                    if (f.getType() == TextView.class)
                    {
                        TextView tv = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), tv, TextView.class));
                        f.set(MyModel, tv);
                    }
                    else if (f.getType() == Button.class)
                    {
                        Button btn = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), btn, Button.class));
                        f.set(MyModel, btn);

                    }
                    else if (f.getType() == EditText.class)
                    {
                        EditText txt = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), txt, EditText.class));
                        f.set(MyModel, txt);
                    }
                    else if (f.getType() == CheckBox.class)
                    {
                        CheckBox chk = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), chk, CheckBox.class));
                        f.set(MyModel, chk);
                    }
                    else if (f.getType() == RadioButton.class)
                    {
                        RadioButton rdbtn = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), rdbtn, RadioButton.class));
                        f.set(MyModel, rdbtn);
                    }
                    else if (f.getType() == Switch.class)
                    {
                        Switch swc = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), swc, Switch.class));
                        f.set(MyModel, swc);
                    }
                    else if (f.getType() == ImageButton.class)
                    {
                        ImageButton imgbtn = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), imgbtn, ImageButton.class));
                        f.set(MyModel, imgbtn);
                    }
                    else if (f.getType() == ImageView.class)
                    {
                        ImageView img = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), img, ImageView.class));
                        f.set(MyModel, img);
                    }
                    else if (f.getType() == RatingBar.class)
                    {
                        RatingBar ratBar = itemView.findViewById(col.value());
                        ItemsHolder.add(new MyKeyValue(col.value(), ratBar, RatingBar.class));
                        f.set(MyModel, ratBar);
                    }
                    else
                    {
                        View v = itemView.findViewById(col.value());
                        if (v != null)
                        {
                            ItemsHolder.add(new MyKeyValue(col.value(), v, v.getClass()));
                            f.set(MyModel, v);
                        }
                    }


                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
        }


    }

    public View setValue(int propertyID, Object value, boolean isAppend, boolean isHTML)
    {
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID) && kv.Value != null)
            {
                if (kv.clazz == TextView.class)
                {
                    TextView tv = ((TextView) kv.Value);
                    if (isAppend)
                        tv.append(String.valueOf(value));
                    else
                        tv.setText(isHTML ? Html.fromHtml(String.valueOf(value)) : String.valueOf(value));

                    return kv.Value;
                }
                else if (kv.clazz == EditText.class)
                {
                    EditText txt = ((EditText) kv.Value);
                    if (isAppend)
                        txt.append(String.valueOf(value));
                    else
                        txt.setText(isHTML ? Html.fromHtml(String.valueOf(value)) : String.valueOf(value));
                    return kv.Value;
                }
                else if (kv.clazz == Button.class)
                {
                    Button btn = ((Button) kv.Value);
                    if (isAppend)
                        btn.append(String.valueOf(value));
                    else
                        btn.setText(isHTML ? Html.fromHtml(String.valueOf(value)) : String.valueOf(value));
                    return kv.Value;
                }
                else if (kv.clazz == CheckBox.class)
                {
                    ((CheckBox) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == RadioButton.class)
                {
                    ((RadioButton) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == Switch.class)
                {
                    ((Switch) kv.Value).setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == ImageButton.class)
                {
                    if (value instanceof Integer)
                        ((ImageButton) kv.Value).setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                    else if (value instanceof Drawable)
                        ((ImageButton) kv.Value).setImageDrawable(((Drawable) value));
                    else if (value instanceof Bitmap)
                        ((ImageButton) kv.Value).setImageBitmap(((Bitmap) value));
                    else if (value instanceof String)
                    {
                        String strColor = (String) value;
                        if (!strColor.startsWith("#"))
                            strColor = "#" + strColor;
                        ((ImageButton) kv.Value).setBackgroundColor(Color.parseColor(strColor));
                    }
                    return kv.Value;
                }
                else if (kv.clazz == ImageView.class)
                {
                    if (value instanceof Integer)
                        ((ImageView) kv.Value).setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                    else if (value instanceof Drawable)
                        ((ImageView) kv.Value).setImageDrawable(((Drawable) value));
                    else if (value instanceof Bitmap)
                        ((ImageView) kv.Value).setImageBitmap(((Bitmap) value));
                    else if (value instanceof String)
                    {
                        String strColor = (String) value;
                        if (!strColor.startsWith("#"))
                            strColor = "#" + strColor;
                        ((ImageButton) kv.Value).setBackgroundColor(Color.parseColor(strColor));
                    }
                    return kv.Value;
                }
                else if (kv.clazz == RatingBar.class)
                {
                    ((RatingBar) kv.Value).setRating((Float.parseFloat(value + "")));
                    return kv.Value;
                }
                else
                {
                    return kv.Value;
                }


            }
        return null;
    }


    public void setListener(int propertyID, Object value)
    {
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID))
            {
                kv.Value.setOnClickListener(((View.OnClickListener) value));
                return;
            }

    }

    public int getViewID(Class<T> model)
    {
        MHBindView col = model.getAnnotation(MHBindView.class);
        return (col != null) ? col.value() : 0;
    }

    private Field getFieldByName(T model, String name)
    {
        Class<?> clazz = model.getClass();
        if (clazz != null)
        {
            Field[] fs = clazz.getDeclaredFields();
            if (fs != null)
                for (Field f : fs)
                    if (f != null && f.getName() != null)
                        if (f.getName().equalsIgnoreCase(name))
                            return f;

        }
        return null;
    }

    public Object[] getItemsHolder()
    {
        Object[] vs = new Object[ItemsHolder.size()];
        for (int i = 0; i < ItemsHolder.size(); i++)
            vs[i] = ItemsHolder.get(i).Value;
        return vs;
    }

    public Object getMyModel()
    {
        return MyModel;
    }
}
