package com.example.utilsuser.recyclerview.swipe

import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.utilsgather.logcat.LogUtil
import kotlin.math.abs

class SwipeTouchHelperCallback(private val moveListener: ItemSwipeListener) : ItemTouchHelper.Callback() {
    //限制ImageView长度所能增加的最大值
    private val ICON_MAX_SIZE = 40.0

    //ImageView的初始长宽
    private val fixedWidth = 120

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
        val swipeFlags = ItemTouchHelper.LEFT

        val flags = makeMovementFlags(dragFlags, swipeFlags)
        return flags
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

        viewHolder.itemView.setBackgroundColor(Color.WHITE)
        viewHolder.itemView.alpha = 1f
        viewHolder.itemView.scrollX = 0
        val itemViewHolder = viewHolder as SwipeAdapter.ItemViewHolder
        itemViewHolder.tvDelete.text = "左滑删除"
        val params = itemViewHolder.ivDelete.layoutParams.apply {
            width = 150
            height = 150
        }
        itemViewHolder.ivDelete.layoutParams = params
        itemViewHolder.ivDelete.visibility = View.INVISIBLE
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
            val itemViewHolder = viewHolder as SwipeAdapter.ItemViewHolder
            if (Math.abs(dX) <= getSlideLimitation(viewHolder)) {
                viewHolder.itemView.scrollTo((-dX).toInt(), 0)

            } else if(abs(dX.toDouble()) <= recyclerView.width / 2) {
                val distance = recyclerView.width / 2 - getSlideLimitation(viewHolder)
                val factor = ICON_MAX_SIZE / distance
                var diff = (Math.abs(dX) - getSlideLimitation(viewHolder)) * factor
                if (diff > ICON_MAX_SIZE)
                    diff = ICON_MAX_SIZE
                itemViewHolder.tvDelete.setText("")
                val params = itemViewHolder.ivDelete.layoutParams.apply {
                    width = (fixedWidth + diff).toInt()
                    height = (fixedWidth + diff).toInt()
                }
                itemViewHolder.ivDelete.layoutParams = params
                itemViewHolder.ivDelete.visibility = View.VISIBLE

            }

        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    /**
     * 获取删除方块的宽度
     */
    private fun getSlideLimitation(viewHolder: RecyclerView.ViewHolder) : Int{
        val viewGroup = viewHolder.itemView as ViewGroup
        return viewGroup.getChildAt(viewGroup.childCount - 1).layoutParams.width
    }
}