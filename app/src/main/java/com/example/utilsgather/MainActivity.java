package com.example.utilsgather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.uioperate.UiOperateEntranceActivity;
import com.example.utilsgather.application_store.AppStoreUtil;
import com.example.utilsgather.assets.AssetsUtil;
import com.example.utilsgather.browser.BrowserUtil;
import com.example.utilsgather.databinding.ActivityMainBinding;
import com.example.utilsgather.handler.HandlerUI;
import com.example.utilsgather.lifecycle_callback.CallbackActivity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;
import com.example.utilsgather.map.MapUtil;
import com.example.utilsgather.package_info.PackageInfoUtil;
import com.example.utilsgather.source_file.SourceUtil;
import com.example.utilsgather.ui.ScreenFunctionUtils;
import com.example.utilsgather.list_guide.GuideItemEntity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends CallbackActivity {

    ActivityMainBinding mainBinding;

    public GuideItemEntity[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        GuideSettings.set(mainBinding.lvLauncher, new GuideItemEntity[] {
                new GuideItemEntity("去UIOperate", new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this, UiOperateEntranceActivity.class);
                        startActivity(intent);
                    }
                }),
                new GuideItemEntity("测试获取应用名", new Runnable() {

                    @Override
                    public void run() {
                        PackageInfoUtil.getAppNameByUrl("xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage");
                    }
                }),
                new GuideItemEntity("测试从子线程切换为UI线程", new Runnable() {

                    @Override
                    public void run() {
                        LogUtil.d("当前的线程是：" + Thread.currentThread());

                        HandlerUI.runOnUI(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.d("当前的线程是2：" + Thread.currentThread());
                            }
                        });
                    }
                }),
                new GuideItemEntity("从Assets中读出内容", new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.d("读出来的文本:\n" + AssetsUtil.readAssetsFile(MainActivity.this, "uiautomatorviewer.txt"));
                    }
                }),
                new GuideItemEntity("在谷歌中打开小红书", new Runnable() {
                    @Override
                    public void run() {
                        AppStoreUtil.jumpStore(MainActivity.this, "com.xingin.xhs", true);
                    }
                }),
                new GuideItemEntity("在浏览器中打开京东", new Runnable() {
                    @Override
                    public void run() {
                        BrowserUtil.jumpBrowser("https://m.jd.com/", MainActivity.this);
                    }
                }),
                new GuideItemEntity("能不能让我选一下跳转去哪", new Runnable() {
                    @Override
                    public void run() {
                        BrowserUtil.jumpOthers(MainActivity.this, "https://www.baidu.com");
                    }
                }),
                new GuideItemEntity("通过value找到key", new Runnable() {
                    @Override
                    public void run() {
                        Map<Float, String> myMap = new HashMap<>();
                        myMap.put(0.1f, "hsf");
                        myMap.put(0.2f, "hwt");
                        myMap.put(0.3f, "hqs");
                        LogUtil.d("找到的KEY：" + MapUtil.getKey(myMap, "hsf"));
                    }
                }),
                new GuideItemEntity("设置屏幕常亮", new Runnable() {
                    @Override
                    public void run() {
                        ScreenFunctionUtils.setScreenOn(MainActivity.this);
                    }
                }),
                new GuideItemEntity("从raw中读出图片", new Runnable() {
                    @Override
                    public void run() {
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                Bitmap bitmap = SourceUtil.getBitmapFromRaw(MainActivity.this, R.raw.set_meal);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mainBinding.ivContent.setImageBitmap(bitmap);
                                    }
                                });
                            }
                        }).start();

                    }
                })
        });
//        mainBinding.lvLauncher.setAdapter(new MainAdapter());

    }

//    private class MainAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return datas.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return datas[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder mViewHolder;
//            if (convertView == null) {
//                mViewHolder = new ViewHolder();
//                View mView = LayoutInflater.from(parent.getContext()).inflate(
//                        android.R.layout.simple_list_item_1, parent, false);
//                mViewHolder.mTextView = mView.findViewById(android.R.id.text1);
//                mView.setTag(mViewHolder);
//                convertView = mView;
//            } else {
//                mViewHolder = (ViewHolder) convertView.getTag();
//            }
//            mViewHolder.mTextView.setText(datas[position].getGuideTitle());
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    datas[position].getRunnable().run();
//                }
//            });
//            return convertView;
//        }
//
//        private class ViewHolder {
//            TextView mTextView;
//        }
//    }
}