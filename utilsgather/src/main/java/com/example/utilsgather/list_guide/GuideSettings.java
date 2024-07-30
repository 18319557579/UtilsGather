package com.example.utilsgather.list_guide;

import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.list_guide.horizontal.HorizontalAdapter;

public class GuideSettings {
    /**
     * 设置布局与数据
     * @param listView ListView布局
     * @param datas 数据
     */
    public static void set(ListView listView, GuideItemEntity[] datas) {
        listView.setAdapter(new GuideAdapter(datas));
    }

    public static void setHorizontal(RecyclerView rv, GuideItemEntity[] datas) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext(), RecyclerView.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(datas);
        rv.setAdapter(horizontalAdapter);
    }
}
