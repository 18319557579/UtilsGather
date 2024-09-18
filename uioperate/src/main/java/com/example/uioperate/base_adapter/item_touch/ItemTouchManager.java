package com.example.uioperate.base_adapter.item_touch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchManager implements OnStartDragListener{
    private final ItemTouchHelper mItemTouchHelper;

    public ItemTouchManager(RecyclerView recyclerView) {
        IItemTouchHelperAdapter iItemTouchHelperAdapter = (IItemTouchHelperAdapter) recyclerView.getAdapter();
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(iItemTouchHelperAdapter));
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
