package com.example.utilsgather.cutomerview.one_five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class AppImageView extends AppCompatImageView {
    public AppImageView(@NonNull Context context) {
        super(context);
    }

    public AppImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        boolean DEBUG = true;
        if (DEBUG) {
            int width = getWidth();
            int height = getHeight();
            String show = String.format("尺寸: %s x %s", width, height);

            Paint paint = new Paint();
            paint.setColor(0xFFFFFF00);
            paint.setTextSize(50);

            canvas.drawText(show, 0, paint.getFontSpacing(), paint);
        }

    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    @Override
    public void onDrawForeground(@NonNull Canvas canvas) {




        super.onDrawForeground(canvas);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(0xFFFF0000);
//        canvas.drawRect(0, 200, 200, 300, paint);
        canvas.drawColor(0xFFFF0000);
        super.draw(canvas);

    }
}
