package com.example.uioperate.storage.image_selector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.uioperate.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



public class PhotoActivity extends AppCompatActivity {
    public static void start(Context context, SelectionBean selectionBean) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra(PhotoActivity.SELECTION_BEAN, selectionBean);
        context.startActivity(intent);
    }

    public final static String SELECTION_BEAN = "SELECTION_BEAN";
    private SelectionBean selectionBean;

    List<ImageBean> imageBeanList = new ArrayList<>();

    PhotoAdapter photoAdapter;

    MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        selectionBean = getIntent().getParcelableExtra(SELECTION_BEAN);

        myHandler = new MyHandler(this);

        RecyclerView rec = findViewById(R.id.rv_photo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(staggeredGridLayoutManager);

        photoAdapter = new PhotoAdapter(imageBeanList);
        rec.setAdapter(photoAdapter);

        findViewById(R.id.img_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myHandler.sendEmptyMessage(MyHandler.FINISH_ACTIVITY);
            }
        });

        findViewById(R.id.btn_upload_rx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filePath = photoAdapter.getAbFilePath();
                if (filePath == null) {
                    Toast.makeText(PhotoActivity.this, "你还未选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("data_return", filePath);
                intent.putExtra("data_return_uri", photoAdapter.getUri());
                setResult(RESULT_OK, intent);
                myHandler.sendEmptyMessage(MyHandler.FINISH_ACTIVITY);
            }
        });

        myHandler.sendEmptyMessage(MyHandler.GET_PHOTO);
    }

    /**
     * 从手机中获得图片
     */
    private void getPhotoList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                imageBeanList = FileUtils.initAllImgInThePhone(selectionBean);
                myHandler.sendEmptyMessage(MyHandler.SET_LIST);
            }
        }).start();
    }

    /**
     * 将图片设置到适配器中
     */
    private void setList() {
        photoAdapter.setList(imageBeanList);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) myHandler.removeCallbacksAndMessages(null);
    }

    private static class MyHandler extends Handler {
        public static final int GET_PHOTO = 1;
        public static final int FINISH_ACTIVITY = 2;
        public static final int SET_LIST = 3;

        private final WeakReference<PhotoActivity> content;

        private MyHandler(PhotoActivity content) {
            super(Looper.myLooper());
            this.content = new WeakReference<>(content);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            PhotoActivity activity = content.get();
            if (activity != null) {
                switch (msg.what) {
                    case GET_PHOTO:
                        activity.getPhotoList();
                        break;
                    case FINISH_ACTIVITY:
                        activity.finish();
                        break;
                    case SET_LIST:
                        activity.setList();
                        break;
                }
            }
        }
    }

}