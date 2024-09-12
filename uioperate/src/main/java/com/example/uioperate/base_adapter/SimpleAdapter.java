package com.example.uioperate.base_adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;

public class SimpleAdapter extends BaseAdapter<String>{
    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
        simpleViewHolder.textContent.setText("测试" + dataList.get(position));
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new SimpleViewHolder(getResId(viewGroup, R.layout.item_simple));
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView textContent;
        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
            textContent = itemView.findViewById(R.id.txt);
        }
    }
}
