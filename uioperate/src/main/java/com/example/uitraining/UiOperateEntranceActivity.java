package com.example.uitraining;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;

public class UiOperateEntranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_operate_entrance);

        GuideSettings.set(findViewById(R.id.uioperate_lv_launcher), new GuideItemEntity[]{
                new GuideItemEntity("替换ViewStud", new Runnable() {
                    @Override
                    public void run() {

                    }
                })
        });

    }
}