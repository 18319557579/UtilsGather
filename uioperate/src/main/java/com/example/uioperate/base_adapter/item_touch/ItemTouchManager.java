package com.example.uioperate.base_adapter.item_touch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchManager implements OnStartDragListener{
    private final ItemTouchHelper mItemTouchHelper;

    public ItemTouchManager(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        IItemTouchHelperAdapter iItemTouchHelperAdapter = (IItemTouchHelperAdapter) adapter;
        // 往ItemTouchHelper.Callback中传入IItemTouchHelperAdapter接口的实例，使得触控行为发生后可以通知给Adapter
        MyItemTouchHelperCallback myItemTouchHelperCallback = new MyItemTouchHelperCallback(iItemTouchHelperAdapter);
        // 这里真实传入我们实现了的ItemTouchHelper.Callback
        mItemTouchHelper = new ItemTouchHelper(myItemTouchHelperCallback);
        // 绑定到RecyclerView上
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        /* 这里我们是又封装了一个基类BaseItemTouchAdapter，用于简化用户接入触控的能力，并且BaseItemTouchAdapter中有变量
        可以让item主动触发拖动行为
         */
        // 如果用户不是继承自BaseItemTouchAdapter的话，那么如果用户还需要item主动触发拖动行为，就需要改ItemTouchManager的代码了
        if (adapter instanceof BaseItemTouchAdapter) {
            BaseItemTouchAdapter baseItemTouchAdapter = (BaseItemTouchAdapter) adapter;
            // 将OnStartDragListener接口实例设置到BaseItemTouchAdapter
            baseItemTouchAdapter.setOnStartDragListener(this);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
