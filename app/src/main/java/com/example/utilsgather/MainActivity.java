package com.example.utilsgather;

import android.os.Bundle;
import android.view.View;

import com.example.utilsgather.databinding.ActivityMainBinding;
import com.example.utilsgather.lifecycle_callback.CallbackActivity;
import com.example.utilsgather.package_info.PackageInfoUtil;

public class MainActivity extends CallbackActivity {

    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.btnTestAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageInfoUtil.getAppNameByUrl(MainActivity.this, "xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage");
            }
        });
    }
}