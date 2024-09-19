package com.example.uioperate.base_adapter.item_touch;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItemTouchManager implements OnStartDragListener {
    private final ItemTouchHelper mItemTouchHelper;

    // todo 这里还是不够人性化，如果要单独修改拖拖拽或侧滑比较麻烦
    public ItemTouchManager(RecyclerView recyclerView) {
        this(recyclerView,
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,  // 拖拽默认上下
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT  // 侧滑默认左右
        );
    }

    public ItemTouchManager(RecyclerView recyclerView, int dragFlags, int swipeFlags) {
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
}
