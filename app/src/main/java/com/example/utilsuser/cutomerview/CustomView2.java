package com.example.utilsuser.cutomerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utilsgather.R;
import com.example.utilsgather.source_file.BitmapGainUtil;

public class CustomView2 extends View {
    Paint paint = new Paint();
    {
//        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

//        Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);

//        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"));

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rwx_img);
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);

        /*// 第一个 Shader：头像的 Bitmap
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.batman);
        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// 第二个 Shader：从上到下的线性渐变（由透明到黑色）
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.batman_logo);
        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

// ComposeShader：结合两个 Shader
        Shader shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.OVERLAY);
        paint.setShader(shader);*/


//        ColorFilter lightColorFilter = new LightingColorFilter(0xffffff, 0x003000);

//        ColorFilter porterDuffColorFilter = new PorterDuffColorFilter(0xffff0000, PorterDuff.Mode.LIGHTEN);


// 创建一个ColorMatrixColorFilter并将ColorMatrix传递给它
        /*ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(new float[]{
                1, 0, 0, 0, 50,
                0, 1, 0, 0, 50,
                0, 0, 1, 0, 50,
                0, 0, 0, 1, 0
        });

        paint.setColorFilter(colorFilter);*/
    }

    public CustomView2(Context context) {
        super(context);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(500, 500, 1000, paint);

        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rwx_img);
        canvas.drawBitmap(bitmap, 100, 100, paint);*/

//        canvas.drawRGB(0, 255, 0);

        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DARKEN);

        int saved = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);


        Rect rect = new Rect(50, 300, 350, 600);
        paint.setColor(0x90eb1761);
        canvas.drawCircle(350, 300, 150, paint);  //2.圆。源图形，source
        paint.setXfermode(xfermode); // 设置 Xfermode

        paint.setColor(0xaa1494f7);
        canvas.drawRect(rect, paint);  //1.方。目标图形，destination
        paint.setXfermode(null); // 用完及时清除 Xfermode

        canvas.restoreToCount(saved);
    }
}
