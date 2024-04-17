package com.example.utilsuser.cutomerview.one_seven;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsuser.R;

public class CustomView1_7Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_1_7);

        ColorAnimation colorAnimation = findViewById(R.id.color_animation);
        Button startAnimation = findViewById(R.id.start_animation);

        PointAnimation pointAnimation = findViewById(R.id.point_animation);
        Button startPoint = findViewById(R.id.start_point);

        MyCustomView myCustomView = findViewById(R.id.my_custom_view);
        Button startBg = findViewById(R.id.start_bg);


        ObjectAnimator animator = ObjectAnimator.ofInt(colorAnimation, "color", 0xffff0000, 0xff00ff00);
//        ObjectAnimator animator = ObjectAnimator.ofArgb(colorAnimation, "color", 0xffff0000, 0xff00ff00);
        animator.setEvaluator(new HsvEvaluator());
        animator.setDuration(3000);

        startAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.start();
            }
        });

        ObjectAnimator pointAnimator = ObjectAnimator.ofObject(pointAnimation, "position",
                new PointFEvaluator(), new PointF(0, 0), new PointF(800, 800));
        pointAnimator.setDuration(2000);
        pointAnimator.setInterpolator(new BounceInterpolator());
        startPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointAnimator.start();
            }
        });
    }


    private class PointFEvaluator implements TypeEvaluator<PointF> {
        PointF newPoint = new PointF();

        //自定义求值器的关键在于，得到动画完成度fraction、初始值
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            float x = startValue.x + (fraction * (endValue.x - startValue.x));
            float y = startValue.y + (fraction * (endValue.y - startValue.y));

            newPoint.set(x, y);

            return newPoint;
        }
    }

    private class HsvEvaluator implements TypeEvaluator<Integer> {
        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            // 把 ARGB 转换成 HSV
            Color.colorToHSV(startValue, startHsv);
            Color.colorToHSV(endValue, endHsv);

            // 计算当前动画完成度（fraction）所对应的颜色值
            if (endHsv[0] - startHsv[0] > 180) {
                endHsv[0] -= 360;
            } else if (endHsv[0] - startHsv[0] < -180) {
                endHsv[0] += 360;
            }
            outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
            if (outHsv[0] > 360) {
                outHsv[0] -= 360;
            } else if (outHsv[0] < 0) {
                outHsv[0] += 360;
            }
            outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
            outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

            // 计算当前动画完成度（fraction）所对应的透明度
            int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

            // 把 HSV 转换回 ARGB 返回
            return Color.HSVToColor(alpha, outHsv);
        }
    }



}