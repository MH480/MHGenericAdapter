package alosina.mh.sanwix.com.recyclertestadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by m.hoseini on 10/29/2017.
 */

public class MySemiFram extends FrameLayout
{
    public MySemiFram(@NonNull Context context)
    {
        super(context);
    }

    public MySemiFram(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MySemiFram(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MySemiFram(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        int h = getLayoutParams().height;
        int w = getLayoutParams().height;
        @SuppressLint("DrawAllocation")
        RectF oval = new RectF(30f, h - 30, 200f, h + 30f);
        @SuppressLint("DrawAllocation")
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawArc(oval, 90f, -70f, false, paint);

        super.onDraw(canvas);
    }


}
