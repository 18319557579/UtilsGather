package com.example.uioperate.base_adapter.multi_layout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;
import com.example.uioperate.base_adapter.BaseAdapter;

public class MainAdapter extends BaseAdapter<MainBean> {
    final int TYPE_ONE = 0;
    final int TYPE_TWO = 1;

    @Override
    public int getMyItemViewType(int position) {
        if (position % 2 == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ONE) {
            return new MainHolder(getResId(viewGroup, R.layout.item_main_one));
        } else {
            return new MainHolder(getResId(viewGroup, R.layout.item_main_two));
        }
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MainHolder mainHolder = (MainHolder) holder;
        MainBean itemBean = getDataList().get(position);
        mainHolder.txt_index.setText(position + 1 + "");
        mainHolder.txt_title.setText(itemBean.getTitle());
        mainHolder.txt_des.setText(itemBean.getDes());
    }

    static class MainHolder extends RecyclerView.ViewHolder {
        public TextView txt_index;
        public TextView txt_title;
        public TextView txt_des;

        public MainHolder(View itemView) {
            super(itemView);
            txt_index = itemView.findViewById(R.id.txt_index);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_des = itemView.findViewById(R.id.txt_des);
        }
    }
}
