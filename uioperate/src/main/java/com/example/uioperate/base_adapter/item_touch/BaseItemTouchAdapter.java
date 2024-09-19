package com.example.uioperate.base_adapter.item_touch;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.base_adapter.BaseAdapter;

import java.util.Collections;

public abstract class BaseItemTouchAdapter<T> extends BaseAdapter<T> implements IItemTouchHelperAdapter{
    // 使得BaseItemTouchAdapter有能力可以从item触发拖到到RecyclerView
    protected OnStartDragListener mOnStartDragListener;
    public void setOnStartDragListener(OnStartDragListener mOnStartDragListener) {
        this.mOnStartDragListener = mOnStartDragListener;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(getDataList(), fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        getDataList().remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemSelected(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setTranslationZ(10);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {
        viewHolder.itemView.setTranslationZ(0);
    }
}
