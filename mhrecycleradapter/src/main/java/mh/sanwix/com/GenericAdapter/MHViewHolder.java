package mh.sanwix.com.GenericAdapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.support.v4.media.RatingCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
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
    private boolean hasPosition;

    public MHViewHolder(View itemView, Class<T> model)
    {
        super(itemView);
        ItemsHolder = new ArrayList<>();
        hasPosition = false;
        if (model != null)
            setViewModel(model, itemView);

    }

    public boolean hasPosition()
    {
        return hasPosition;
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
                        ItemsHolder.add(new MyKeyValue(col.value(), tv, TextView.class, hasPosition = col.isPosition()));
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

    public View setValue(MHBindView col, Object value)
    {
        int propertyID = col.value();
        boolean isAppend = col.isTextAppend(), isHTML = col.isHtml(), ifNullinVisible = col.hiddenIfNull();
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID) && kv.Value != null)
            {
                if (kv.clazz == TextView.class)
                {
                    TextView tv = ((TextView) kv.Value);
                    if (value == null && ifNullinVisible)
                        tv.setVisibility(View.GONE);
                    else
                    {
                        tv.setVisibility(View.VISIBLE);
                        if (isAppend)
                            tv.append(String.valueOf(value));
                        else
                            tv.setText(isHTML ? Html.fromHtml(String.valueOf(value)).toString() : String.valueOf(value));

                    }
                    return kv.Value;
                }
                else if (kv.clazz == EditText.class)
                {
                    EditText txt = ((EditText) kv.Value);
                    if (value == null && ifNullinVisible)
                        txt.setVisibility(View.GONE);
                    else
                    {
                        if (isAppend)
                            txt.append(String.valueOf(value));
                        else
                            txt.setText(isHTML ? Html.fromHtml(String.valueOf(value)).toString() : String.valueOf(value));
                    }
                    return kv.Value;
                }
                else if (kv.clazz == Button.class)
                {
                    Button btn = ((Button) kv.Value);
                    if (value == null && ifNullinVisible)
                        btn.setVisibility(View.GONE);
                    else
                    {
                        if (isAppend)
                            btn.append(String.valueOf(value));
                        else
                            btn.setText(isHTML ? Html.fromHtml(String.valueOf(value)).toString() : String.valueOf(value));
                    }
                    return kv.Value;
                }
                else if (kv.clazz == CheckBox.class)
                {
                    CheckBox chk = ((CheckBox) kv.Value);
                    if (value == null && ifNullinVisible)
                        chk.setVisibility(View.GONE);
                    else
                    {
                        chk.setChecked((Boolean) value);
                    }
                    return kv.Value;
                }
                else if (kv.clazz == RadioButton.class)
                {
                    RadioButton rbtn = ((RadioButton) kv.Value);
                    if (value == null && ifNullinVisible)
                        rbtn.setVisibility(View.GONE);
                    else
                        rbtn.setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == Switch.class)
                {
                    Switch swt = ((Switch) kv.Value);
                    if (value == null && ifNullinVisible)
                        swt.setVisibility(View.GONE);
                    else
                        swt.setChecked((Boolean) value);
                    return kv.Value;
                }
                else if (kv.clazz == ImageButton.class)
                {
                    ImageButton imgbtn = ((ImageButton) kv.Value);
                    if (value == null && ifNullinVisible)
                        imgbtn.setVisibility(View.GONE);
                    else
                    {
                        if (value instanceof Integer)
                            imgbtn.setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                        else if (value instanceof Drawable)
                            imgbtn.setImageDrawable(((Drawable) value));
                        else if (value instanceof Bitmap)
                            imgbtn.setImageBitmap(((Bitmap) value));
                        else if (value instanceof String)
                        {
                            String strColor = (String) value;
                            if (!strColor.startsWith("#"))
                                strColor = "#" + strColor;
                            imgbtn.setBackgroundColor(Color.parseColor(strColor));
                        }
                    }
                    return kv.Value;
                }
                else if (kv.clazz == ImageView.class)
                {
                    ImageView img = ((ImageView) kv.Value);
                    if (value == null && ifNullinVisible)
                        img.setVisibility(View.GONE);
                    else
                    {

                        if (value instanceof Integer)
                            img.setImageDrawable(this.itemView.getContext().getResources().getDrawable(Integer.parseInt(value + "")));
                        else if (value instanceof Drawable)
                            img.setImageDrawable(((Drawable) value));
                        else if (value instanceof Bitmap)
                            img.setImageBitmap(((Bitmap) value));
                        else if (value instanceof String)
                        {
                            String strColor = (String) value;
                            if (!strColor.startsWith("#"))
                                strColor = "#" + strColor;
                            img.setBackgroundColor(Color.parseColor(strColor));
                        }
                    }
                    return kv.Value;
                }
                else if (kv.clazz == RatingBar.class)
                {
                    RatingBar rtb = ((RatingBar) kv.Value);
                    if (value == null && ifNullinVisible)
                        rtb.setVisibility(View.GONE);
                    else
                        rtb.setRating((Float.parseFloat(value + "")));
                    return kv.Value;
                }
                else
                {
                    return kv.Value;
                }


            }
        return null;
    }


    public View setValue(MHBindViewAction col, Object value)
    {
        int propertyID = col.value();
        for (MyKeyValue kv : ItemsHolder)
            if (kv.Key == (propertyID) && kv.Value != null)
            {
                if (kv.clazz == TextView.class || kv.clazz == AppCompatTextView.class || kv.clazz.getSuperclass() == AppCompatTextView.class)
                {
                    TextView tv = ((TextView) kv.Value);
                    tv.setText(String.valueOf(value));
                    if (col.hiddenIfNull())
                        tv.setVisibility(View.GONE);


                    return kv.Value;
                }
                else if (kv.clazz == EditText.class || kv.clazz == AppCompatEditText.class || kv.clazz.getSuperclass() == AppCompatEditText.class)
                {
                    EditText txt = ((EditText) kv.Value);
                    txt.setText(String.valueOf(value));
                    if (col.hiddenIfNull())
                        txt.setVisibility(View.GONE);
                    return kv.Value;
                }
                else if (kv.clazz == Button.class || kv.clazz == AppCompatButton.class || kv.clazz.getSuperclass() == AppCompatButton.class)
                {
                    Button btn = ((Button) kv.Value);
                    btn.setText(String.valueOf(value));
                    if (col.hiddenIfNull())
                        btn.setVisibility(View.GONE);
                    return kv.Value;
                }
                else if (kv.clazz == CheckBox.class || kv.clazz == AppCompatCheckBox.class || kv.clazz.getSuperclass() == AppCompatCheckBox.class)
                {
                    CheckBox chk = ((CheckBox) kv.Value);
                    chk.setChecked((Boolean) value);
                    if (col.hiddenIfNull())
                        chk.setVisibility(View.GONE);
                    return kv.Value;
                }
                else if (kv.clazz == RadioButton.class || kv.clazz == AppCompatRadioButton.class || kv.clazz.getSuperclass() == AppCompatRadioButton.class)
                {
                    RadioButton btn = ((RadioButton) kv.Value);
                    btn.setChecked((Boolean) value);
                    if (col.hiddenIfNull())
                        btn.setVisibility(View.GONE);
                    return kv.Value;
                }
                else if (kv.clazz == Switch.class || kv.clazz == SwitchCompat.class || kv.clazz.getSuperclass() == SwitchCompat.class)
                {
                    Switch swt = (Switch) kv.Value;
                    swt.setChecked((Boolean) value);
                    if (col.hiddenIfNull())
                        swt.setVisibility(View.GONE);
                    return kv.Value;
                }
                else if (kv.clazz == ImageButton.class || kv.clazz == AppCompatImageButton.class || kv.clazz.getSuperclass() == AppCompatImageButton.class)
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
                        ImageButton img = ((ImageButton) kv.Value);
                        img.setBackgroundColor(Color.parseColor(strColor));
                        if (col.hiddenIfNull())
                            img.setVisibility(View.GONE);
                    }
                    return kv.Value;
                }
                else if (kv.clazz == ImageView.class || kv.clazz == AppCompatImageView.class || kv.clazz.getSuperclass() == AppCompatImageView.class)
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
                        ImageView img = ((ImageView) kv.Value);
                        img.setBackgroundColor(Color.parseColor(strColor));
                        if (col.hiddenIfNull())
                            img.setVisibility(View.GONE);
                    }
                    return kv.Value;
                }
                else if (kv.clazz == RatingBar.class || kv.clazz == RatingCompat.class || kv.clazz.getSuperclass() == RatingCompat.class)
                {
                    RatingBar rat = ((RatingBar) kv.Value);
                    rat.setRating((Float.parseFloat(value + "")));
                    if (col.hiddenIfNull())
                        rat.setVisibility(View.GONE);
                    return kv.Value;
                }
                else
                {
                    return kv.Value;
                }


            }
        return null;
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
                        ((ImageView) kv.Value).setBackgroundColor(Color.parseColor(strColor));
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
