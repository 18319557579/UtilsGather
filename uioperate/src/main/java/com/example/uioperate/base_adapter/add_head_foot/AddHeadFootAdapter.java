package com.example.uioperate.base_adapter.add_head_foot;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;
import com.example.uioperate.base_adapter.BaseAdapter;

public class AddHeadFootAdapter extends BaseAdapter<String> {
    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new AddHeadHolder(getResId(viewGroup, R.layout.item_simple));
    }

    static class AddHeadHolder extends RecyclerView.ViewHolder {
        public TextView txt;
        public AddHeadHolder(View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
        }
    }

}
