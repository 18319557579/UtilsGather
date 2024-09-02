package com.example.uioperate.custom_juejin_newki.thermometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SineWaveView extends View {
    public SineWaveView(Context context) {
        super(context);
        init();
    }

    public SineWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SineWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint paint;

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF00000F);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int originX = width / 2;
        int originY = height / 2;

        float pixelX = (float) (2 * Math.PI / width);

        float previousY = originY;
        for (int x = -width; x < width; x++) {
            float newX = pixelX * x;  //计算新的X值
            float newY = (float)(Math.sin(newX) * 100);  //计算新的Y值，振幅设为100像素
            canvas.drawLine(x - 1, previousY, x, originY - newY, paint);
            previousY = originY - newY; // 更新前一个点的Y坐标，用于下一次绘制
        }
    }

    /**
     * 根据当前的 x 值（像素做单位），求出对应的 sinx 值
     */
    private float sinCalculate(int x, float frequency, float phaseShift, float A, float C) {
        return 0f;
    }
}
