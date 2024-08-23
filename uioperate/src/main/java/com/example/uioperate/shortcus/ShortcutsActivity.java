package com.example.uioperate.shortcus;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uioperate.R;
import com.example.utilsgather.list_guide.GuideItemEntity;
import com.example.utilsgather.list_guide.GuideSettings;
import com.example.utilsgather.logcat.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShortcutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts);

        GuideSettings.set(findViewById(R.id.uioperate_lv_launcher), new GuideItemEntity[]{
                new GuideItemEntity("动态添加快捷方式", new Runnable() {
                    @Override
                    public void run() {
                        ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat.Builder(ShortcutsActivity.this, "settings")
                                .setIcon(IconCompat.createWithResource(ShortcutsActivity.this, R.mipmap.uioperate_settings_page))
                                .setShortLabel("设置页面")
                                .setLongLabel("设置的全称")
                                .setIntent(new Intent(Intent.ACTION_VIEW, null, ShortcutsActivity.this, ShortcutsOtherActivity.class)
                                        .putExtra("EXTRA_KEY", 2)
                                )
                                .build();
                        List<ShortcutInfoCompat> shortcutInfoCompatList = new ArrayList<>();
                        shortcutInfoCompatList.add(shortcutInfoCompat);
                        boolean result = ShortcutManagerCompat.addDynamicShortcuts(ShortcutsActivity.this, shortcutInfoCompatList);
                        LogUtil.d("动态添加是否成功: " + result);
                    }
                }),

                // 并不能用这个更新的方法来进行删除的操作，只能用来给List中匹配到的id的shortcuts来更新信息
                // 且它是个整体更新，就是不能用于更新某一项属性，而是全部的属性都要写上去，否则会报一些属性缺失的bug
                // 静态方式添加的shortcuts id，不能通过动态的方式进行更新
                new GuideItemEntity("更新快捷方式", new Runnable() {
                    @Override
                    public void run() {
                        ShortcutInfoCompat shortcutInfoCompat = new ShortcutInfoCompat.Builder(ShortcutsActivity.this, "settings")
                                .setIcon(IconCompat.createWithResource(ShortcutsActivity.this, R.mipmap.uioperate_settings_page_new))
                                .setShortLabel("设置新名称")
                                .setLongLabel("设置新名称之全称")
                                .setIntent(new Intent(Intent.ACTION_VIEW, null, ShortcutsActivity.this, ShortcutsOtherActivity.class)
                                        .putExtra("EXTRA_KEY", 2)
                                ).build();
                        List<ShortcutInfoCompat> shortcutInfoCompatList = new ArrayList<>();
                        shortcutInfoCompatList.add(shortcutInfoCompat);
                        boolean result = ShortcutManagerCompat.updateShortcuts(ShortcutsActivity.this, shortcutInfoCompatList);
                        LogUtil.d("更新结果: " + result);
                    }
                }),

                //如果在删除之前将快捷方式固定到了桌面上（这种称作固定快捷方式），在删除之后该快捷方式的图标不会消失，点击后依然可以完成正常跳转
                new GuideItemEntity("删除快捷方式", new Runnable() {
                    @Override
                    public void run() {
                        String settingsShortId = "settings";

                        ShortcutManagerCompat.removeDynamicShortcuts(ShortcutsActivity.this, Collections.singletonList(settingsShortId));

                        boolean isShortcutPinned = isShortcutPinned(ShortcutsActivity.this, settingsShortId);
                        if (isShortcutPinned) {
                            LogUtil.d("已经固定到了桌面上");
                            ShortcutManagerCompat.disableShortcuts(ShortcutsActivity.this, Collections.singletonList(settingsShortId),
                                    "快捷方式已移除，请使用其他功能");

                        } else {
                            LogUtil.d("没有固定到桌面上");
                        }
                    }
                }),

                new GuideItemEntity("跳转到设置页面开启权限", new Runnable() {
                    @Override
                    public void run() {
                        requestShortcutPermission(ShortcutsActivity.this);
                    }
                }),
        });
    }

    public static void requestShortcutPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                // 引导用户到应用的系统设置页面
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);

                // 检查是否有活动可以处理我们的Intent
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        }
    }

    /**
     * 判断指定的快捷方式是否被固定到了桌面上
     */
    private boolean isShortcutPinned(Context context, String shortcutId) {
        // 检查设备是否支持固定快捷方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                    LogUtil.d("设备支持固定快捷方式到桌面上");

                    List<ShortcutInfo> shortcutInfoList = shortcutManager.getPinnedShortcuts();
                    for (ShortcutInfo  shortcut : shortcutInfoList) {
                        if (shortcut.getId().equals(shortcutId)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;   // 指定的快捷方式未固定或设备不支持固定快捷方式
    }
}