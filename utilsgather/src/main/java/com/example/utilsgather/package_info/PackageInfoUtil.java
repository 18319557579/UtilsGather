package com.example.utilsgather.package_info;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.example.utilsgather.logcat.LogUtil;

public class PackageInfoUtil {
    /**
     * 获取该url是要跳转到哪一个App的（通常这类url都是该app自己定义的，例如：
     * xhsdiscover://search/result?keyword=%E5%B9%BF%E4%B8%9C%E6%94%BB%E7%95%A5&open_url=baidu&groupid=60f954717ae4040001eabf05&mode=openurl&source=landingpage）
     * @param context
     * @param url
     * @return app的包名
     */
    public static String getAppNameByUrl(Context context, String url) {
        String appName = null;

        Uri myUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
        PackageManager mPackageManager = context.getPackageManager();

        //如果不申请QUERY_ALL_PACKAGES权限，那么得到的componentName将会为null
        ComponentName componentName = intent.resolveActivity(mPackageManager);
        try {
//            List<ResolveInfo> mResolveInfos = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//            ResolveInfo info = mPackageManager.resolveActivity(intent, PackageManager.MATCH_ALL);

            String packageName = componentName.getPackageName();
            ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
            appName = (String) mPackageManager.getApplicationLabel(applicationInfo);
            LogUtil.d("应用名：" + appName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }
}
