package com.example.utilsgather.cutomerview.flow;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.R;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;
    private LinearLayout mLlRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_flow);

        mFlowLayout = findViewById(R.id.flowlayout);
        mLlRoot = findViewById(R.id.ll_root);

        mFlowLayout.addView(generateButton());
    }

    private Button generateButton() {
        Button button = new Button(this);
        button.setText("add");

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);

        return button;
    }
}