package com.example.utilsuser.qihang.one.RectPointView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;
import com.example.utilsuser.qihang.one.six.CustomView;


public class UseRectPointViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_rect_point_view);

//        addView();
//        handleProgress();
        handleRotateProgress();

        matrixMulti();
    }

    private void addView() {
        LinearLayout rootView = findViewById(R.id.root);
        CustomView customView = new CustomView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2f
        );
        layoutParams.weight = 1;
        layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;

        rootView.addView(customView, layoutParams);

        rootView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
    }

    /*private void handleProgress() {
        ImageView imageView = findViewById(R.id.img_avator);
        SeekBar seekBar = findViewById(R.id.seek_bar);
        Bitmap mOriginBmp = BitmapFactory.decodeResource(getResources(), R.drawable.avator);

        Bitmap mTempBitmap = Bitmap.createBitmap(mOriginBmp.getWidth(), mOriginBmp.getHeight(), Bitmap.Config.ARGB_8888);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtil.d("进度变为了: " + progress);

                Bitmap bitmap = handleColorMatrixBmp(progress, mTempBitmap, mOriginBmp);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Bitmap handleColorMatrixBmp(int progress, Bitmap tempBmp, Bitmap originBmp) {
        Canvas canvas = new Canvas(tempBmp);
        Paint paint = new Paint();
        ColorMatrix mSaturationMatrix = new ColorMatrix();
        mSaturationMatrix.setSaturation(progress);
        paint.setColorFilter(new ColorMatrixColorFilter(mSaturationMatrix));
        canvas.drawBitmap(originBmp, 0f, 0f, paint);
        return tempBmp;
    }*/

    private void handleRotateProgress(){
        ImageView imageView = findViewById(R.id.img_avator);
        SeekBar seekBar = findViewById(R.id.seek_bar);
        seekBar.setMax(360);
        seekBar.setProgress(180);

        Bitmap mOriginBmp = BitmapFactory.decodeResource(getResources(), R.drawable.avator);

        Bitmap mTempBitmap = Bitmap.createBitmap(mOriginBmp.getWidth(), mOriginBmp.getHeight(), Bitmap.Config.ARGB_8888);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                LogUtil.d("旋转角度为: " + progress);

                Bitmap bitmap = handleColorRotate(progress - 180, mTempBitmap, mOriginBmp);
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private Bitmap handleColorRotate(int degree, Bitmap tempBmp, Bitmap originBmp) {
        Canvas canvas = new Canvas(tempBmp);
        Paint paint = new Paint();
        ColorMatrix mSaturationMatrix = new ColorMatrix();
        mSaturationMatrix.setRotate(0, degree);
        paint.setColorFilter(new ColorMatrixColorFilter(mSaturationMatrix));
        canvas.drawBitmap(originBmp, 0f, 0f, paint);
        return tempBmp;
    }

    private void matrixMulti() {
        ColorMatrix colorMatrix1 = new ColorMatrix(new float[] {
                0.1f, 0.2f, 0.3f, 0.4f, 0.5f,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0
        });
        ColorMatrix colorMatrix2 = new ColorMatrix(new float[] {
                0.11f, 0, 0, 0, 0,
                0, 0.22f, 0, 0, 0,
                0, 0, 0.33f, 0, 0,
                0, 0, 0, 0.44f, 0,
        });
        ColorMatrix resultMatrix = new ColorMatrix(new float[] {
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
        });

        resultMatrix.setConcat(colorMatrix1, colorMatrix2);

        LogUtil.d(printArray(colorMatrix1.getArray()));
        LogUtil.d(printArray(colorMatrix2.getArray()));
        LogUtil.d(printArray(resultMatrix.getArray()));
    }

    private String printArray(float[] array) {
        StringBuilder builder = new StringBuilder("array dump:\n");
        for (int i = 0; i < array.length; i++) {
            if (i % 5 == 0) {
                builder.append("\n");
            }
            builder.append(array[i] + " ");
        }
        return builder.toString();
    }
}
