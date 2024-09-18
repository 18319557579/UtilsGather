package com.example.uioperate.base_adapter;

import com.example.uioperate.R;

public enum AnimationType {
    TRANSLATE_FROM_RIGHT(R.anim.item_translate_byright),  // 动画从右向左
    TRANSLATE_FROM_LEFT(R.anim.item_translate_byleft),  // 动画从左向右
    TRANSLATE_FROM_BOTTOM(R.anim.item_translate_bybottom),  // 动画从底部向上
    SCALE(R.anim.item_scale_anim),  // 缩放动画
    ALPHA(R.anim.item_alpha_anim);  // 透明度变化动画

    public final int resId;
    AnimationType(int resId) {
        this.resId = resId;
    }
}
