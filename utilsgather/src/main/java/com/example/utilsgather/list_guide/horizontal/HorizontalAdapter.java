package com.example.utilsgather.list_guide.horizontal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.R;
import com.example.utilsgather.list_guide.GuideItemEntity;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder>{
    private final GuideItemEntity[] datas;

    public HorizontalAdapter(GuideItemEntity[] datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_horizontal_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.btnItem.setOnClickListener(v -> {
            datas[holder.getBindingAdapterPosition()].getRunnable().run();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.btnItem.setText(datas[position].getGuideTitle());
    }

    @Override
    public int getItemCount() {
        return datas.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final Button btnItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btnItem = itemView.findViewById(R.id.btn_item);
        }
    }
}
