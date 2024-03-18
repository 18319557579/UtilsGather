package com.example.utilsgather.cutomerview;

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

public class PictureView3 extends View {
    private Paint mPaint;
    private int mWidth = 400;
    private int mHeight = 400;


    public PictureView3(Context context) {
        super(context);
        init();
    }

    public PictureView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PictureView3(Context context, AttributeSet attrs, int defStyleAttr) {
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
//        int layId = canvas.saveLayer(0, 0, mWidth, mHeight, mPaint);
        int layId = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(getDest(mWidth, mHeight), 0, 0, mPaint);

        //设置混合模式为SRC_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));

        canvas.drawBitmap(getSrc(mWidth, mHeight), mWidth / 2, mWidth / 2, mPaint);

        canvas.restoreToCount(layId);


    }

    public Bitmap getDest(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //目标图像橘色
        paint.setColor(Color.parseColor("#FF8C00"));
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        return bitmap;

    }

    public Bitmap getSrc(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        //源图像绿色
        paint.setColor(Color.parseColor("#006400"));
        canvas.drawRect(0, 0, 400, 400, paint);
        return bitmap;

    }
}

