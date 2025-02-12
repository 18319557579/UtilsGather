package com.example.utilsuser.immersion.atomic;

import android.os.Bundle;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.utilsuser.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTabViewpagerActivity extends AppCompatActivity{

    // 如果要传指定的布局，那么请重写这个方法
    int customContentView() {
        return R.layout.activity_composite;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customContentView());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewPager2 vp2 = findViewById(R.id.vp_body);
        TabLayout tabLayout = findViewById(R.id.tl_head);
        TabAdapter tabAdapter = new TabAdapter(this);
        vp2.setAdapter(tabAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp2.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.setScrollPosition(position, 0, true);
            }
        });

        List<Pair<String, ShowFragment>> pairs = new ArrayList<>();

        addPairs(pairs);
        for (Pair<String, ShowFragment> pair : pairs) {
            tabLayout.addTab(tabLayout.newTab().setText(pair.first));
            tabAdapter.addFragment(pair.second);
        }
    }

    // 这里用于配置主体内容
    abstract void addPairs(List<Pair<String, ShowFragment>> pairs);


}
