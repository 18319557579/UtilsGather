package com.example.utilsuser.recyclerview.initial

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsgather.logcat.LogUtil

class MyItemTouchHelperCallback(private val moveListener: ItemTouchMoveListener) : ItemTouchHelper.Callback() {
    /**
     * 监听哪些方向的移动
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        //要监听的拖拽是哪个方向，如果为UP，那么只会对向上的拖拽反应
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        //要监听的侧滑是哪个方向，如果为LEFT，那么只会对想像左的拖拽反应
        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT

        val flags = makeMovementFlags(dragFlags, swipeFlags)
        return flags
    }

    /**
     * 是否支持长按实现拖拽
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    /**
     * 是否支持侧滑
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    /**
     * 当拖拽的位置越过了条目，才会回调该方法
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        LogUtil.d("onMove被调用, original position: ${viewHolder.adapterPosition}, target position: ${target.adapterPosition}")

        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        val result = moveListener.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return result
    }

    /**
     * 当item被侧滑删除掉了，才会回调该方法
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        LogUtil.d("onSwiped被调用, position: ${viewHolder.adapterPosition}")
        moveListener.onItemRemove(viewHolder.adapterPosition)
    }

    /**
     * 状态发生改变的时候回调该方法
     * 一共有3种状态，空闲0，侧滑中1，拖拽中2
     *
     * 侧滑 / 拖转 -> 空闲，viewHolder将为null
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        LogUtil.d("onSelectedChanged被调用, position: ${viewHolder?.adapterPosition}, actionState: ${actionState}")

        //当状态变为侧滑或拖拽时，修改背景颜色
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder?.itemView?.setBackgroundColor(0xFFCCCCCC.toInt())
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    /**
     * 当拖拽或侧滑完成后（无论最终是否发生实际结果），会回调该方法，可以在该方法中进行一些清除选中效果
     *
     * 如果侧滑最终发生实际结果，那么位置为-1
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        LogUtil.d("clearView被调用，position: ${viewHolder.adapterPosition}")

//        viewHolder.itemView.setBackgroundColor(Color.GREEN)
//        viewHolder.itemView.alpha = 1f
        super.clearView(recyclerView, viewHolder)
    }

    /**
     * item的变动都会回调该方法。可以在Item底部绘制图形
     *
     * dX x轴上的变化
     * dY y轴上的变化
     * actionState 此时的状态：只有侧滑中1，拖拽中2
     * isCurrentlyActive 如果该视图当前由用户控制，则为True;如果为false，则只是动画返回到原始状态。
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        LogUtil.d("onChildDraw","onChildDraw被调用，position: ${viewHolder.adapterPosition}, dx: $dX, dy: $dY, actionState: $actionState" +
            ", isCurrentlyActive: $isCurrentlyActive")

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width
            viewHolder.itemView.alpha = alpha
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    /**
     * 拖拽移动item时，发生实际结果的阈值百分比
     */
    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return .8f
    }

    /**
     * 侧滑移动item时，发生侧滑结果的阈值百分比
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return .8f
    }

    /**
     * 在移动完成后用于执行额外的反馈或更新操作
     */
    override fun onMoved(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        fromPos: Int,
        target: RecyclerView.ViewHolder,
        toPos: Int,
        x: Int,
        y: Int
    ) {
        LogUtil.d("onMoved被调用，fromPos：$fromPos, toPos: $toPos")
        super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y)
    }

    /**
     * 可以设置动画时间
     */
    override fun getAnimationDuration(
        recyclerView: RecyclerView,
        animationType: Int,
        animateDx: Float,
        animateDy: Float
    ): Long {
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy)
    }

    /**
     * 在Item上面绘制图形
     */
    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}