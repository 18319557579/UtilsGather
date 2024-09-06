package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uioperate.R;

import java.util.List;

public class MyNineGridAdapter extends AbstractNineGridAdapter{
    private List<String> datas;

    public MyNineGridAdapter(List<String> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if ((position + 1) % 2 == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public View onCreateItemView(Context context, ViewGroup parent, int itemType) {
        View view;
        if (itemType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_img_icon, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_img, parent, false);
        }
        return view;
    }

    @Override
    public void onBindItemView(View itemView, int itemType, int position) {
        if ((position + 1) % 3 == 0) {
            itemView.findViewById(R.id.iv_img).setBackgroundColor(Color.YELLOW);
        } else {
            itemView.findViewById(R.id.iv_img).setBackgroundColor(Color.GREEN);
        }
    }
}
