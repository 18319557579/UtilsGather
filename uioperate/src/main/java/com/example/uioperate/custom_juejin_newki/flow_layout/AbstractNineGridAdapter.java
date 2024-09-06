package com.example.uioperate.custom_juejin_newki.flow_layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbstractNineGridAdapter {
    public abstract int getItemCount();

    public int getItemViewType(int position) {
        return 0;
    }

    public abstract View onCreateItemView(Context context, ViewGroup parent, int itemType);

    public abstract void onBindItemView(View itemView, int itemType, int position);
}
