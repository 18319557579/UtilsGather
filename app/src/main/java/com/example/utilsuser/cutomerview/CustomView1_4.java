package com.example.utilsuser.cutomerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsuser.R;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.source_file.BitmapGainUtil;

public class CustomView1_4 extends View {
    Paint paint;
    Bitmap bitmap;
    Camera camera;
    Matrix matrix = new Matrix();
    {   paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);

        bitmap = BitmapGainUtil.getBitmapFromDrawable(this.getContext(), R.drawable.rwx_img);

        camera = new Camera();
    }

    public CustomView1_4(Context context) {
        super(context);
    }

    public CustomView1_4(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView1_4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        float[] fff = new float[9];
        matrix.reset();
//        camera.restore();
//        matrix.postTranslate(100, 0);
//        matrix.setScale(0.5f, 0.5f);


//        matrix.preScale(2,1);
//        matrix.postTranslate(200, 200);
        show(matrix);

        /*matrix.preTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        show(matrix);

        matrix.preRotate(45);
        show(matrix);

//        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
        show(matrix);*/


//        matrix.preRotate(45, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
//        show(matrix);

//        matrix.preScale(2, 1, bitmap.getWidth() / 2, bitmap.getHeight() / 2);

//        matrix.preTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);



//        float left = 0, top = 0, right = bitmap.getWidth(), bottom = bitmap.getHeight();
//        float[] pointsSrc = {left, top, right, top, left, bottom, right, bottom};
//        float[] pointsDst = {left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};
//        matrix.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
//        matrix.postTranslate(100, 0);
        show(matrix);

//        matrix.preScale(2, 1);
//        show(matrix);
//
//        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
//        show(matrix);

//        matrix.preTranslate(100, 0);
//        matrix.preRotate(45);

//        float x = 100f;
//        float y = 100;
        float x = 0f;
        float y = 0;

//        canvas.drawBitmap(bitmap, x, y, paint);



        canvas.save();

        camera.save();
        camera.rotateX(30); // 旋转 Camera 的三维空间
        canvas.translate(bitmap.getWidth() / 2, bitmap.getHeight() / 2); // 旋转之后把投影移动回来
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2); // 旋转之前把绘制内容移动到轴心（原点）
        camera.restore();

//        canvas.concat(matrix);

//        canvas.clipRect(x + 100, y + 100, x + bitmap.getWidth() - 100, y + bitmap.getHeight() - 100);

        /*Path path = new Path();
        path.addCircle(450, 450, 200, Path.Direction.CW);
        canvas.clipPath(path);*/

//        canvas.rotate(45);
//        canvas.translate(200, 0);

//        canvas.rotate(45, 200, 200);
//        canvas.scale(1.3f, 0.8f, 200, 200);
//        canvas.skew(0.5f, 0);

//        canvas.rotate(45, bitmap.getWidth(), bitmap.getHeight());

        canvas.drawBitmap(bitmap, x, y, paint);
//        canvas.drawRect(x, y, 300, 300, paint);


        paint.setColor(0xFFFF00FF);
        canvas.drawLine(0, 0, 2000, 0, paint);
        paint.setColor(0xFFEE7942);
        canvas.drawLine(0, 0, 0, 2000, paint);
        paint.setColor(0xFFFF0000);
        paint.setStrokeWidth(50);
        canvas.drawPoint(0, 0, paint);

        canvas.restore();

        //------------------------------------------------------------------------------------------

    }

    private void show(Matrix matrix) {
        float[] fff = new float[9];
        matrix.getValues(fff);

        String formatString =
                "|%f, %f, %f|\n" +
                "|%f, %f, %f|\n" +
                "|%f, %f, %f|";


        String matrixStr = String.format(formatString, fff[0], fff[1], fff[2], fff[3], fff[4], fff[5], fff[6], fff[7], fff[8]);
        LogUtil.d(matrixStr);
    }
}
