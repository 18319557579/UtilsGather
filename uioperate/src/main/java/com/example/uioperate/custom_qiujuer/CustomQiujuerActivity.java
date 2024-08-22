package com.example.uioperate.custom_qiujuer;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uioperate.R;

public class CustomQiujuerActivity extends AppCompatActivity {
    private static final float MOVE_TOUCH_MAX_Y = 600;
    private float mTouchMoveStartY = 0;
    private TouchFullView mTouchFullView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_qiujuer);

        mTouchFullView = findViewById(R.id.touchFull);

        findViewById(R.id.activity_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchMoveStartY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float y = event.getY();
                        if (y >= mTouchMoveStartY) {
                            float moveSize = y - mTouchMoveStartY;
                            float progress = moveSize >= MOVE_TOUCH_MAX_Y ? 1 : moveSize / MOVE_TOUCH_MAX_Y;
                            mTouchFullView.setProgress(progress);
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        mTouchFullView.release();
                }

                return false;
            }
        });
    }
}