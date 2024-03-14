package com.example.utilsgather.cutomerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.R;
import com.example.utilsgather.source_file.BitmapGainUtil;
import com.example.utilsgather.source_file.DrawableGainUtil;

public class CustomView extends View {
    Paint paint = new Paint();
    Paint paint2 = new Paint();
    Path path = new Path(); // 初始化 Path 对象
    {
        paint.setColor(Color.RED); // 设置为红色

        paint.setStyle(Paint.Style.FILL); // Style 修改为画线模式
        paint.setStrokeWidth(5); // 线条宽度为 20 像素

        paint.setAntiAlias(true);

        // 使用 path 对图形进行描述（这段描述代码不必看懂）
//        path.addArc(200, 200, 400, 400, -225, 225);
//        path.arcTo(400, 200, 600, 400, -180, 225, false);
//        path.lineTo(400, 542);
    }

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(300, 300, 200, paint);

//        setLayerType(LAYER_TYPE_SOFTWARE, null);

        /*canvas.drawColor(Color.BLACK);  // 纯黑
        canvas.drawColor(Color.parseColor("#88880000")); // 半透明红色
        canvas.drawRGB(100, 200, 100);
        canvas.drawARGB(50, 100, 200, 100);

        path.addArc(100, 100, 500, 300, -100, 90);
        canvas.drawPath(path, paint);
        path.arcTo();
        path.addRoundRect();
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawBitmap();*/



        /*canvas.drawCircle(300, 300, 200, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(100, 100, 500, 500, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(700, 100, 1100, 500, paint);*/

//        paint.setStrokeCap(Paint.Cap.SQUARE);
//        canvas.drawPoint(50, 50, paint);

//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
//// 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
//        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
//                10 /* 一共绘制 8 个数（4 个点）*/, paint);

        /*paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(50, 50, 350, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(400, 50, 700, 200, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(200, 200, 800, 500, paint);

        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(points, paint);*/

//        canvas.drawRoundRect(100, 100, 500, 300, 80, 20, paint);

//        paint.setStyle(Paint.Style.FILL); // 填充模式
//        canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形
//        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
//        paint.setStyle(Paint.Style.STROKE); // 画线模式
//        canvas.drawArc(200, 100, 800, 500, 180, -10, false, paint); // 绘制不封口的弧形

//        path.addCircle(300, 300, 200, Path.Direction.CW);


//        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        path.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线


//        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100); // 画斜线
//        path.moveTo(200, 100); // 我移~~
//        path.lineTo(200, 0); // 画竖线


//        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100);
//        path.arcTo(100, 100, 300, 300, -90, 90, false); // 强制移动到弧形起点（无痕迹）


//        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100);
//        path.addArc(100, 100, 300, 300, -90, 90);



        /*path.setFillType(Path.FillType.WINDING);

        path.addCircle(300, 300, 200, Path.Direction.CW);
        path.addCircle(500, 300, 200, Path.Direction.CCW);

        path.addCircle(300, 700, 50, Path.Direction.CW);
        path.addCircle(300, 700, 200, Path.Direction.CCW);

        path.moveTo(300, 900);
        path.lineTo(500, 1400);
        path.lineTo(20, 1100);
        path.lineTo(580, 1100);
        path.lineTo(100, 1400);

        path.moveTo(300, 900);
        path.lineTo(100, 1400);

        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成*/

        Bitmap bitmap = BitmapGainUtil.getBitmapFromDrawable(this.getContext(), R.drawable.rwx_img);
//        canvas.drawBitmap(bitmap, 200, 100, paint);

        // 创建一个 Matrix 对象
        Matrix matrix = new Matrix();
// 设置矩阵的变换。例如，对图片进行90度旋转。
        matrix.postRotate(180);

// 你也可以设置矩阵的其他变换，比如缩放和位移
        float scaleFactor = 0.5f; // 定义缩放因子，0.5 表示缩小一半
// 对 Bitmap 缩放
        matrix.postScale(scaleFactor, scaleFactor);

// 设置矩阵的位移，比如向右移动 100 像素，向下移动 200 像素
        matrix.postTranslate(200, 200);

        canvas.drawBitmap(bitmap, matrix, paint);


        /*int sideLength = Math.min(bitmap.getWidth(), bitmap.getHeight()) * 2/ 3;
        Rect src = new Rect(0, 0, sideLength, sideLength);

        RectF dst = new RectF(300f, 300f, 500f, 500f);

        canvas.drawBitmap(bitmap, src, dst, paint);*/

        int sideLength = Math.min(bitmap.getWidth(), bitmap.getHeight()) * 2/ 3;
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getWidth());

//        RectF dst = new RectF(300f, 300f, 800f, 500f);
        Rect dst = new Rect(300, 300, 800, 500);

        canvas.drawBitmap(bitmap, src, dst, paint);

        /*String text = "Hello World!";
        paint.setTextSize(18);
        canvas.drawText(text, 100, 25, paint);
        paint.setTextSize(36);
        canvas.drawText(text, 100, 70, paint);
        paint.setTextSize(60);
        canvas.drawText(text, 100, 145, paint);
        paint.setTextSize(84);
        canvas.drawText(text, 100, 240, paint);*/

        /*canvas.drawCircle();
        canvas.drawRect();
        canvas.drawPoints();
        canvas.drawOval();

        canvas.drawLine();
        canvas.drawRoundRect();
        canvas.drawArc();

        Path path1 = new Path();

        path.lineTo();
        path.quadTo();
        path.cubicTo();
        path.moveTo();

        path.arcTo();
        path.close();

        path.addCircle();

        path.addRoundRect();

        path.addPath();

        path.addArc();*/
    }
}
