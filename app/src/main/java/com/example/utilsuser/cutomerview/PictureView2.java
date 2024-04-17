package com.example.utilsuser.cutomerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class PictureView2 extends View {
    private Paint mPaint;
    private int mWidth = 400;
    private int mHeight = 400;


    public PictureView2(Context context) {
        super(context);
        init();
    }

    public PictureView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //新图层
        int layId = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint);

        canvas.drawBitmap(getDest(mWidth, mHeight), 0, 0, mPaint);

        //设置混合模式为SRC_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

        canvas.drawBitmap(getSrc(mWidth, mHeight), 0, 0, mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(layId);

    }

    public Bitmap getDest(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //目标图像橘色
        paint.setColor(Color.parseColor("#FF8C00"));
        canvas.drawOval(new RectF(0, 0, mWidth * 3 / 4, mWidth * 3 / 4), paint);
        return bitmap;

    }

    public Bitmap getSrc(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //源图像绿色
        paint.setColor(Color.parseColor("#006400"));
        canvas.drawRect(mWidth / 3, mWidth / 3, mWidth * 19 / 20, mWidth * 19 / 20, paint);
        return bitmap;

    }
}

