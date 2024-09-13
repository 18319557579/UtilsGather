package com.example.uioperate.base_adapter;

import static kotlin.random.RandomKt.Random;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    private List<T> dataList;
    public List<T> getDataList() {
        return dataList;
    }
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }


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

    public void addHeadView(View view) {
        headViews.add(view);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        LogUtil.d("回调onBindViewHolder：" + position);
        if (getItemViewType(position) <= headViews.size() - 1) return;

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo 这里的 position 到底使用哪一个
                    onItemClickListener.onItemClick(dataList.get(holder.getBindingAdapterPosition()), holder.getBindingAdapterPosition());
                }
            });
        }
        onMyBindViewHolder(holder, position);
    }
    // bind 的时候有自定义需求，重写这个方法
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtil.d("回调onCreateViewHolder：" + viewType);
        if (viewType <= TYPE_HEAD) {
            // 就是 viewType = TYPE_HEAD - position; 公式推导过来的
            return new HeadHolder(headViews.get(TYPE_HEAD - viewType));
        } else {
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
}
