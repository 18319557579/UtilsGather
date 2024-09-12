package com.example.uioperate.base_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    public List<T> dataList;

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
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
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return onMyCreateViewHolder(parent, viewType);
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
        return dataList == null ? 0 : dataList.size();
    }

    public View getResId(ViewGroup viewGroup, int resId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(resId, viewGroup, false);
    }
}
