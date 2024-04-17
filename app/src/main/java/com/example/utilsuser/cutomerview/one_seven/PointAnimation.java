package com.example.utilsuser.cutomerview.one_seven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PointAnimation extends View {
    private final Paint paint;
    private PointF position;

    public PointF getPosition() {
        return position;
    }

    public void setPosition(PointF position) {
        this.position = position;
        invalidate();
    }

    public PointAnimation(Context context) {
        super(context);
    }

    public PointAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PointAnimation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(30);

        position = new PointF(0, 0);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(position.x, position.y, paint);
    }
}


