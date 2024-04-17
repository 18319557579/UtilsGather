package com.example.utilsuser.cutomerview.one_five;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;

public class CustomEditText extends EditText {
    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.drawColor(Color.parseColor("#66BB6A")); // 涂上绿色
        super.draw(canvas);
    }
}
