package com.example.uioperate.base_adapter;

import static kotlin.random.RandomKt.Random;

import android.graphics.Color;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.AnimRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.uioperate.R;
import com.example.utilsgather.context.ApplicationGlobal;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    private AnimationType animatorType;

    public void setAnimatorType(AnimationType animatorType) {
        this.animatorType = animatorType;
    }

    private List<T> dataList;
    public List<T> getDataList() {
        return dataList;
    }
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    private int currentMillons = 0;
    private int delyTimePosi = 1;


    final int TYPE_HEAD = -100;
    final int TYPE_FOOT = 100;
    private List<View> headViews = new ArrayList<>();  // 头部存储器
    private List<View> footViews = new ArrayList<>();  // 尾部存储器

    /*private Map<Integer, View> headViewMap = new LinkedHashMap<>();
    private Map<Integer, View> footViewMap = new LinkedHashMap<>();

    public void addHeadView(View view) {
        headViewMap.put(headIndex++, view);
        notifyDataSetChanged();
    }*/

    // todo 我发现，无论是添加/移除 头部/底部 的哪个Index上的View，始终显示的都是List中前面的那几个View，这里肯定是涉及了某些复用的问题
    public void addHeadView(View view) {
        headViews.add(view);
        notifyDataSetChanged();
    }
    public void removeHeadView(int index) {
        if (index < headViews.size()) {
            headViews.remove(index);
            notifyDataSetChanged();
        }
    }

    public void addFootView(View view) {
        footViews.add(view);
        notifyDataSetChanged();
    }
    public void removeFootView(int index) {
        if (index < footViews.size()) {
            footViews.remove(index);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LogUtil.d("回调onBindViewHolder：" + position);
        if (getItemViewType(position) <= TYPE_HEAD) {
            return;
        }
        if (getItemViewType(position) >= TYPE_FOOT) {
            return;
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtil.d("position: " + position +
                            ", getBindingAdapterPosition: " + holder.getBindingAdapterPosition() +
                            ", getLayoutPosition: " + holder.getLayoutPosition() +
                            ", getAbsoluteAdapterPosition: " + holder.getAbsoluteAdapterPosition() +
                            ", getOldPosition: " + holder.getOldPosition());

                    // todo 这里的 position 到底使用哪一个
                    onItemClickListener.onItemClick(dataList.get(getRealPosition(holder)), getRealPosition(holder));
                }
            });
        }
        onMyBindViewHolder(holder, getRealPosition(holder));
        addItemAnimation(holder, position);
    }
    // bind 的时候有自定义需求，重写这个方法
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    // todo 增加 只有未加载的才有动画 的情况
    private void addItemAnimation(RecyclerView.ViewHolder viewHolder, int position) {
        if (animatorType == null) return;

        Animation animator = AnimationUtils.loadAnimation(viewHolder.itemView.getContext(), animatorType.resId);
        viewHolder.itemView.startAnimation(animator);
    }

    // 其实就是减去头布局的数量
    private int getRealPosition(RecyclerView.ViewHolder holder) {
        return holder.getBindingAdapterPosition() - headViews.size();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.d("回调onCreateViewHolder：" + viewType);
        if (viewType <= TYPE_HEAD) {
            // 就是 viewType = TYPE_HEAD - position; 公式推导过来的
            return new HeadHolder(headViews.get(TYPE_HEAD - viewType));
        } else if (viewType >= TYPE_FOOT) {
            // 例如 viewType == 101，TYPE_FOOT == 100，这拿的就是 footViews 的index为1的元素
            return new HeadHolder(footViews.get(viewType - TYPE_FOOT));
        }else {
            return onMyCreateViewHolder(parent, viewType);
        }
    }
    public abstract RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType);

    // 整体点击事件相关
    public OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T item, int position);
    }

    @Override
    public int getItemCount() {
        int dataListCount = dataList == null ? 0 : dataList.size();
        return headViews.size() + dataListCount + footViews.size();
    }

    public View getResId(ViewGroup viewGroup, int resId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.d("回到getItemViewType：" + position);
        // 是head
        int viewType;
        if (position <= headViews.size() - 1) {
            viewType = TYPE_HEAD - position;
        } else if(position >= headViews.size() + dataList.size()) {
            viewType = TYPE_FOOT + position - (headViews.size() + dataList.size());
        } else {
            viewType = getMyItemViewType(position);
        }
        LogUtil.d("得到的ViewType: " + viewType);
        return viewType;
    }

    // 如果有多布局的要求，重写这个方法
    public int getMyItemViewType(int position) {
        return 0;
    }

    // todo 最前面和最后面的间隔，可以使用开关来控制是否开启
    // 设置间隔
    public void setGridSpace(RecyclerView recyclerView, int spacing) {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);  // 获取当前item的位置
                // 头布局和底布局不需要增加间隔
                if (getItemViewType(position) <= TYPE_HEAD || getItemViewType(position) >= TYPE_FOOT) {
                    return;
                }

                position = position - headViews.size();
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();

                if (layoutManager instanceof GridLayoutManager) {
                    int spanCount = ((GridLayoutManager) layoutManager).getSpanCount();

                    GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                    // 当前列的位置
                    int spanIndex = layoutParams.getSpanIndex();

                    // 计算左右间距
                    outRect.left = spacing - spanIndex * spacing / spanCount;
                    outRect.right = (spanIndex + 1) * spacing / spanCount;

                    // 为了使间隔看起来均等，需要调整顶部和底部的间隔
                    if (position < spanCount) {  // 如果是第一行，添加顶部间隔
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing;  // item底部间隔

                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    int spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();

                    StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                    // 当前列的位置
                    int spanIndex = layoutParams.getSpanIndex();

                    // 计算左右间距
                    outRect.left = spacing - spanIndex * spacing / spanCount;
                    outRect.right = (spanIndex + 1) * spacing / spanCount;

                    // 为了使间隔看起来均等，需要调整顶部和底部的间隔
                    if (position < spanCount) {  // 如果是第一行，添加顶部间隔
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing;  // item底部间隔

                    // 像LinearLayoutManager这种，可以在Item的内边距等下功夫，因为用间隔的话无法触发点击事件
                }else if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    if (linearLayoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                        if (position == 0) {
                            outRect.top = spacing;
                        }
                        outRect.bottom = spacing;
                    } else {
                        if (position == 0) {
                            outRect.left = spacing;
                        }
                        outRect.right = spacing;
                    }
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getItemViewType(position) <= TYPE_HEAD || getItemViewType(position) >= TYPE_FOOT) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return 1;
                }
            });

        } else if (layoutManager instanceof StaggeredGridLayoutManager) { //这里是防止瀑布流自带动画，引起子view乱跳。第一行出现空白格
            recyclerView.setAnimation(null);
            recyclerView.setItemAnimator(null);
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        }

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                // 检测当前屏幕上可见的item
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

                // 为进入屏幕的item运行动画
                for (int position = firstVisibleItemPosition; position <= lastVisibleItemPosition; position++) {
                    View itemView = recyclerView.getLayoutManager().findViewByPosition(position);
                    if (itemView != null) {
                        itemView.startAnimation(animator);
                    }
                }
            }
        });*/
    }

    // 使得头部、底部布局可以完整展示
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams && holder instanceof HeadHolder) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }
}
