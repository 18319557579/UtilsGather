package com.example.uioperate.custom_juejin_newki.thermometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SineWaveViewLineToSurface extends View {
    public SineWaveViewLineToSurface(Context context) {
        this(context, null);
    }

    public SineWaveViewLineToSurface(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SineWaveViewLineToSurface(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint;
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFff000F);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int originY = height / 2;

        float pixelX = (float) (2 * Math.PI / width);
        for (int x = 0; x < width; x++) {
            float newX = pixelX * x;  //计算新的X值。newX 仅仅是用于映射Y的值
            float newY = (float)(Math.sin(newX) * 100);  //计算新的Y值，振幅设为100像素
            canvas.drawLine(x, originY - newY, x, originY + height, paint);
        }
    }
}
