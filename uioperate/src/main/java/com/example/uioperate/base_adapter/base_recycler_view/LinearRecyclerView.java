package com.example.uioperate.base_adapter.base_recycler_view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LinearRecyclerView extends RecyclerView {
    public LinearRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LinearRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LinearRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        setLayoutManager(layoutManager);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }
}
