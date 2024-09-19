package com.example.uioperate.base_adapter.item_touch;

import androidx.recyclerview.widget.RecyclerView;

// 这些都是触控通知到下面的方法
public interface IItemTouchHelperAdapter {

    // item 发生了交换后，回调该方法
    void onItemMove(int fromPosition, int toPosition);

    // item 被划走了之后，回调该方法
    void onItemDismiss(int position);

    /**
     * item被选中，在侧滑或拖拽过程中更新状态
     */
    void onItemSelected(RecyclerView.ViewHolder viewHolder);

    /**
     * item的拖拽或侧滑结束，恢复默认的状态
     */
    void onItemClear(RecyclerView.ViewHolder viewHolder);
}
