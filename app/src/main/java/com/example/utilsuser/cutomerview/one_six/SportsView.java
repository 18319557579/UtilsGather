package com.example.utilsuser.cutomerview.one_six;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SportsView extends View {
    float progress = 0;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    RectF arcRectF = new RectF();
    Paint paint = new Paint();
    {
        paint.setColor(0xfff61e61);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(50);
        paint.setStyle(Paint.Style.STROKE);
    }

    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        arcRectF.set(25, 25, getHeight() - 25, getHeight() - 25);

        canvas.drawColor(0xffffff00);
        canvas.drawArc(arcRectF, 135, progress * 2.7f, false, paint);
    }
}
