package com.example.utilsgather.cutomerview.one_six;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.R;
import com.example.utilsgather.logcat.LogUtil;

public class CustomView1_6Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_1_6);

        ImageView ivRwx = findViewById(R.id.iv_rwx);
        Button btnClick = findViewById(R.id.btn_click);
        Button btnClickCancel = findViewById(R.id.btn_click_cancel);

        Button btnClick1 = findViewById(R.id.btn_click1);
        SportsView sportsView = findViewById(R.id.sports_view);
        Button btnClickCancel1 = findViewById(R.id.btn_click_cancel1);
        Button btnClickResume1 = findViewById(R.id.btn_click_resume1);
        Button btnClickPause1 = findViewById(R.id.btn_click_pause1);

        Runnable endRunnable = new Runnable() {
            @Override
            public void run() {
                LogUtil.d("viewPropertyAnimator回调withEndAction()");
            }
        };

        Path interpolatorPath = new Path();
        interpolatorPath.lineTo(1, 1);

        ViewPropertyAnimator viewPropertyAnimator = ivRwx.animate();
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                LogUtil.d("viewPropertyAnimator回调onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                LogUtil.d("viewPropertyAnimator回调onAnimationEnd()");
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                LogUtil.d("viewPropertyAnimator回调onAnimationCancel()");
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                LogUtil.d("viewPropertyAnimator回调onAnimationRepeat()");
            }
        });
        viewPropertyAnimator.setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                LogUtil.d("viewPropertyAnimator回调onAnimationUpdate()");
            }
        });
        viewPropertyAnimator.withStartAction(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("viewPropertyAnimator回调withStartAction()");
            }
        });
        viewPropertyAnimator.withEndAction(endRunnable);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPropertyAnimator.translationXBy(200)
                        .setInterpolator(new PathInterpolator(interpolatorPath))
                        .setDuration(5000);
            }
        });
        btnClickCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPropertyAnimator.cancel();
            }
        });

        ObjectAnimator animator = ObjectAnimator.ofFloat(sportsView, "progress", 0, 65);
        animator.setDuration(5000)
                .setInterpolator(new DecelerateInterpolator());
        animator.setRepeatCount(3);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationEnd()");
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationCancel()");
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationRepeat()");
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationUpdate()");
            }
        });
        animator.addPauseListener(new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationPause()");
            }

            @Override
            public void onAnimationResume(@NonNull Animator animation) {
                LogUtil.d("ObjectAnimator回调onAnimationResume()");
            }
        });
        btnClick1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animator.start();
            }
        });
        btnClickCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.cancel();
            }
        });
        btnClickResume1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.resume();
            }
        });
        btnClickPause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animator.pause();
            }
        });
    }

}