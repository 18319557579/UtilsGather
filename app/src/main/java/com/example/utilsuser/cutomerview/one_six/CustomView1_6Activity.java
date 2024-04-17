package com.example.utilsuser.cutomerview.one_six;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsuser.R;
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
                /*viewPropertyAnimator.scaleX(0.5f)
                        .scaleY(0.5f)
                        .alpha(0.5f);*/

                /*PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX", 0.2f);
                PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY", 0.2f);
                PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha", 0.2f);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivRwx, holder1, holder2, holder3);
                animator.setInterpolator(new FastOutLinearInInterpolator());
                animator.start();*/

                /*ObjectAnimator animator1 = ObjectAnimator.ofFloat(ivRwx, "scaleX", 0.2f);
                animator1.setInterpolator(new LinearInterpolator());
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(ivRwx, "alpha", 0.2f);
                animator2.setInterpolator(new DecelerateInterpolator());
                AnimatorSet animatorSet = new AnimatorSet();
                // 两个动画依次执行
//                animatorSet.playSequentially(animator1, animator2);
//                animatorSet.playTogether(animator1, animator2);
//                animatorSet.play(animator1).with(animator2);
//                animatorSet.play(animator1).before(animator2);
                animatorSet.play(animator1).after(animator2);
                animatorSet.start();*/

                // 在 0% 处开始
                Keyframe keyframe1 = Keyframe.ofFloat(0, 0);
                // 时间经过 50% 的时候，动画完成度 100%
                Keyframe keyframe2 = Keyframe.ofFloat(0.5f, 300);
                // 时间见过 100% 的时候，动画完成度倒退到 80%，即反弹 20%
                Keyframe keyframe3 = Keyframe.ofFloat(1, 100);
                PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("translationX", keyframe1, keyframe2, keyframe3);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(ivRwx, holder);
                animator.start();
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