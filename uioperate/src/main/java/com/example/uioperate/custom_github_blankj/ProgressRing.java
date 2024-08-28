package com.example.uioperate.custom_github_blankj;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.uioperate.R;

public class ProgressRing extends View {
    // 进度条起始颜色
    private int progressStartColor;
    // 进度条结束颜色
    private int progressEndColor;
    // 背景起始颜色
    private int bgStartColor;
    // 背景中间颜色
    private int bgMidColor;
    // 背景结束颜色
    private int bgEndColor;
    // 进度值
    private int progress;
    // 进度条宽度值
    private float progressWidth;
    // 起始角度
    private int startAngle;
    // 扫过的角度
    private int sweepAngle;
    // 是否开启动画
    private boolean showAnim;

    private Paint progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
    private Paint bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

    public ProgressRing(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ProgressRing);
        try {
            progressStartColor = ta.getColor(R.styleable.ProgressRing_pr_progress_start_color, Color.YELLOW);
            progressEndColor = ta.getColor(R.styleable.ProgressRing_pr_progress_end_color, progressStartColor);
            bgStartColor = ta.getColor(R.styleable.ProgressRing_pr_bg_start_color, Color.LTGRAY);
            bgMidColor = ta.getColor(R.styleable.ProgressRing_pr_bg_mid_color, bgStartColor);
            bgEndColor = ta.getColor(R.styleable.ProgressRing_pr_bg_end_color, bgStartColor);
            progress = ta.getInt(R.styleable.ProgressRing_pr_progress, 0);
            progressWidth = ta.getDimension(R.styleable.ProgressRing_pr_progress_width, 8f);
            startAngle = ta.getInt(R.styleable.ProgressRing_pr_start_angle, 150);
            sweepAngle = ta.getInt(R.styleable.ProgressRing_pr_sweep_angle, 240);
            showAnim = ta.getBoolean(R.styleable.ProgressRing_pr_show_anim, true);
        } finally {
            ta.recycle();
        }
    }

}
