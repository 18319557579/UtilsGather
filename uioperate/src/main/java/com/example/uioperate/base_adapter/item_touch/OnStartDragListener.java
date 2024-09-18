package com.example.uioperate.base_adapter.item_touch;


import androidx.recyclerview.widget.RecyclerView;

public interface OnStartDragListener {
    /**
     * 当View需要拖拽时回调
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
