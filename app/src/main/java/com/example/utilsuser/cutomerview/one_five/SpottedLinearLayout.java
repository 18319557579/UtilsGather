package com.example.utilsuser.cutomerview.one_five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpottedLinearLayout extends LinearLayout {
    public SpottedLinearLayout(Context context) {
        super(context);
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SpottedLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
//        paint.setColor(0xFFca517e);
//
//        canvas.drawCircle(100, 300, 50, paint);
//        canvas.drawCircle(getWidth() - 100, getHeight() - 100, 150, paint);
        canvas.drawColor(0xFFFFFF00);
    }
    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {

        super.dispatchDraw(canvas);

    }
}
