package com.example.uioperate.base_adapter.item_touch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchManager implements OnStartDragListener {
    private final ItemTouchHelper mItemTouchHelper;

    private ItemTouchManager(RecyclerView recyclerView, int dragFlags, int swipeFlags) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        IItemTouchHelperAdapter iItemTouchHelperAdapter = (IItemTouchHelperAdapter) adapter;
        // 往ItemTouchHelper.Callback中传入IItemTouchHelperAdapter接口的实例，使得触控行为发生后可以通知给Adapter
        MyItemTouchHelperCallback myItemTouchHelperCallback = new MyItemTouchHelperCallback(iItemTouchHelperAdapter, dragFlags, swipeFlags);
        // 这里真实传入我们实现了的ItemTouchHelper.Callback
        mItemTouchHelper = new ItemTouchHelper(myItemTouchHelperCallback);
        // 绑定到RecyclerView上
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        /* 这里我们是又封装了一个基类BaseItemTouchAdapter，用于简化用户接入触控的能力，并且BaseItemTouchAdapter中有变量
        可以让item主动触发拖动行为
         */
        if (adapter instanceof IAssembleDrag) {
            IAssembleDrag iAssembleDrag = (IAssembleDrag) adapter;
            // 将OnStartDragListener接口实例设置到BaseItemTouchAdapter
            iAssembleDrag.assembleDrag(this);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    // 虽然这个方法是静态的，但是每一次对它的调用都会创建一个ItemTouchManager的实例，所以不用担心实例是重复的
    public static ItemTouchManager attachTo(RecyclerView recyclerView) {
        return new ItemTouchManager(recyclerView,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,  // 拖拽默认上下
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT  // 侧滑默认左右
        );
    }

    public static ItemTouchManager attachTo(RecyclerView recyclerView, int dragFlags, int swipeFlags) {
        return new ItemTouchManager(recyclerView, dragFlags, swipeFlags);
    }
}
