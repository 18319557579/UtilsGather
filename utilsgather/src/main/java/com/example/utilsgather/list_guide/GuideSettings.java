package com.example.utilsgather.list_guide;

import android.widget.ListView;

public class GuideSettings {
    /**
     * 设置布局与数据
     * @param listView ListView布局
     * @param datas 数据
     */
    public static void set(ListView listView, GuideItemEntity[] datas) {
        listView.setAdapter(new GuideAdapter(datas));
    }
}
