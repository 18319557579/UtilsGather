package com.example.uioperate.base_adapter.item_touch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final IItemTouchHelperAdapter mAdapter;

    // 拖拽默认是上下
    private final int mDragFlags;
    // 侧滑默认是左右
    private final int mSwipeFlags;

    public MyItemTouchHelperCallback(IItemTouchHelperAdapter mAdapter, int dragFlags, int swipeFlags) {
        this.mAdapter = mAdapter;
        mDragFlags = dragFlags;
        mSwipeFlags = swipeFlags;
    }

    // 这里可以限制整个的拖拽、侧滑行为（也会影响到）
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(mDragFlags, mSwipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            mAdapter.onItemSelected(viewHolder);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        mAdapter.onItemClear(viewHolder);
    }

    // 下面这两个方法只是说是否能够通过触控行为来触发侧滑、拖拽，但是不影响item主动触发的
    @Override
    public boolean isItemViewSwipeEnabled() {
        return mSwipeFlags != 0;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mDragFlags != 0;
    }
}
