package com.example.uioperate.base_adapter.grid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;
import com.example.uioperate.base_adapter.BaseAdapter;

public class GridAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new GridHolder(getResId(viewGroup, R.layout.item_grid));
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GridHolder gridHolder = (GridHolder) holder;
        switch (position % 6) {
            case 0:
                gridHolder.image.setImageResource(R.drawable.wangze1);
                break;
            case 1:
                gridHolder.image.setImageResource(R.drawable.wangze2);
                break;
            case 2:
                gridHolder.image.setImageResource(R.drawable.wangze3);
                break;
            case 3:
                gridHolder.image.setImageResource(R.drawable.wangze4);
                break;
            case 4:
                gridHolder.image.setImageResource(R.drawable.wangze5);
                break;
            case 5:
                gridHolder.image.setImageResource(R.drawable.wangze6);
                break;
        }
    }

    static class GridHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public GridHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
