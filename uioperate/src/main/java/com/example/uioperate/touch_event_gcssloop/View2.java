package com.example.uioperate.touch_event_gcssloop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.utilsgather.logcat.LogUtil;

public class View2 extends View {
    public View2(Context context) {
        super(context);
        init();
    }

    public View2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public View2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("触发了单击");
            }
        });
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtil.d("触发了长按");

                // 如果是 return false，那么代表事件没有被消费掉，后续还是可以回调onClick的
                return false;

                // 如果是 return true，代表事件被消费掉了，后续无法回调onClick了
//                return true;
            }
        });
    }
}
