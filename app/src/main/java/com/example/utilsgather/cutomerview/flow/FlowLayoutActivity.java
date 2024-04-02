package com.example.utilsgather.cutomerview.flow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.R;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;
    private LinearLayout mLlRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_flow);

        mFlowLayout = findViewById(R.id.flowlayout);
        mLlRoot = findViewById(R.id.ll_root);

//        mFlowLayout.addView(generateButton());
    }

    private Button generateButton() {
        Button button = new Button(this);
        button.setText("add");

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);

        return button;
    }

    public void addTag(View view) {
        List<String> mDataList = Arrays.asList("Android", "hyman", "imooc.com", "Intellij IDEA Intellij IDEA Intellij IDEA");

        TextView tag = (TextView) LayoutInflater.from(this).inflate(R.layout.item_tag, mFlowLayout, false);
        tag.setText(mDataList.get(new Random().nextInt(mDataList.size())));

        mFlowLayout.addView(tag);
    }

    public void addTag2(View view) {
        Button button = new Button(this);
        button.setText("add");

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);

        mFlowLayout.addView(button);
    }

    public void addTag3(View view) {
        Button button = new Button(this);
        button.setText("add");
        mFlowLayout.addView(button);
    }

    public void addTag4(View view) {
        Button button = new Button(this);
        button.setText("add");

        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(lp);

        mFlowLayout.addView(button);
    }
}