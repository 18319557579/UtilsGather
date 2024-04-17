package com.example.utilsuser.cutomerview.one_seven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ColorAnimation extends View {
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    private Paint paint;

    public ColorAnimation(Context context) {
        super(context);
    }

    public ColorAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(color);
        canvas.drawCircle(400, 400, 400, paint);
    }
}
