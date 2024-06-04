package com.example.utilsuser.file.list;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utilsgather.file_system.FileInfoGainUtil;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsuser.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView rv;
    List<FileInfoBean> fileInfoBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initView();
        gainFileInfo();
    }

    private void initView() {
        rv = findViewById(R.id.rv_file_info_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FileInfoAdapter fileInfoAdapter = new FileInfoAdapter(fileInfoBeans);
        rv.setAdapter(fileInfoAdapter);
    }

    private void gainFileInfo() {
        File internalFile = getFilesDir();
        File downloadFile = new File(internalFile, "dl");
        LogUtil.d("打印下载路径: " + downloadFile);

        File[] listFiles = downloadFile.listFiles();
        for (File file: listFiles) {
            FileInfoBean bean = new FileInfoBean();
            bean.setName(file.getName());
            bean.setPath(file.getPath());
            bean.setLength(file.length());
            bean.setLastModified(file.lastModified());

            fileInfoBeans.add(bean);
        }
    }

}