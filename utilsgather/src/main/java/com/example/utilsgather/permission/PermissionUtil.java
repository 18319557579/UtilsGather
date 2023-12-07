package com.example.utilsgather.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    /**
     * 单个权限判断是否授权
     */
    public boolean isGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 多个权限可以一起授权
     */
    public void applyPermissions(Activity activity, int requestCode, String[] permissionArray) {
        ActivityCompat.requestPermissions(activity, permissionArray, requestCode);
    }

}
