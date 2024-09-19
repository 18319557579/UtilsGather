package com.example.uioperate.base_adapter.item_touch;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uioperate.R;

public class JobOrientationAdapter extends BaseItemTouchAdapter<ItemEntity>{
    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = getResId(viewGroup, R.layout.item_list_touch);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.text.setText(getDataList().get(position).getText());
        itemViewHolder.switchCompat.setChecked(getDataList().get(position).isChecked());
        itemViewHolder.switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = itemViewHolder.switchCompat.isChecked();
                getDataList().get(position).setChecked(b);
            }
        });
        itemViewHolder.menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mOnStartDragListener != null) {
                        mOnStartDragListener.onStartDrag(holder);
                    }
                }
                return false;
            }
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private ImageView menu;
        private SwitchCompat switchCompat;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_list_text_textView);
            menu = itemView.findViewById(R.id.item_list_menu_imageView);
            switchCompat = itemView.findViewById(R.id.item_list_switchCompat);
        }
    }
}
