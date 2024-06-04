package com.example.utilsuser.file;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.permission.permissionX.ApplyResultCallback;
import com.example.utilsgather.permission.permissionX.PermissionX;
import com.example.utilsuser.R;
import com.example.utilsuser.file.list.ListActivity;

import java.io.File;
import java.util.List;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
    }

    public void gainFileInfo(View view) {
        File file = new File("/data/user/0/com.example.utilsuser/files/jizhang.apk");
        LogUtil.d("文件名: " +file.getName() + ", 文件路径: " + file.getAbsolutePath());

        file = new File("/data/data/com.example.utilsuser/files/jizhang.apk");
        LogUtil.d("文件名: " +file.getName() + ", 文件路径: " + file.getAbsolutePath());

        File path = getFilesDir();
        LogUtil.d("内部存储的路径: " + path.getPath());

        File externalFile = getExternalFilesDir(null);
        LogUtil.d("外部存储的路径: " + externalFile.getPath());

        File externalDIRECTORY_MUSIC = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        LogUtil.d("外部存储DIRECTORY_MUSIC的路径: " + externalDIRECTORY_MUSIC.getPath());
    }

    public void applySD(View view) {
        PermissionX.request(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                new ApplyResultCallback() {
                    @Override
                    public void handle(boolean allGranted, List<String> deniedList) {
                        if (allGranted) {
                            LogUtil.d("所有权限都已授权");
                        } else {
                            LogUtil.d("没有通过的权限: " + deniedList);
                        }
                    }
                });
    }

    public void jumpListActivity(View view) {
        startActivity(new Intent(this, ListActivity.class));
    }
}