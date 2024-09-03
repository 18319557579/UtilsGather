package com.example.uioperate.custom_juejin_newki.thermometer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.ui.CustomViewUtil;

public class SineWaveViewPoint extends View {
    public SineWaveViewPoint(Context context) {
        super(context);
        init();
    }

    public SineWaveViewPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SineWaveViewPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    Paint paint;
    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFFff000F);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d(CustomViewUtil.getWidthHeightInfo(widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int originX = width / 2;
        int originY = height / 2;

        float pixelX = (float) (2 * Math.PI / width);

        for (int x = 0; x < width; x++) {
            float newX = pixelX * x;  //计算新的X值。newX 仅仅是用于映射Y的值
            float newY = (float)(Math.sin(newX) * 100);  //计算新的Y值，振幅设为100像素
            canvas.drawPoint(x, originY - newY, paint);
        }
    }
}
