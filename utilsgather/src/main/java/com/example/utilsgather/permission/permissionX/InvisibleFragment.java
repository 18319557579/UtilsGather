package com.example.utilsgather.permission.permissionX;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class InvisibleFragment extends Fragment {
    private ApplyResultCallback callback = null;

    public void requestNow(ApplyResultCallback cb, String... permissions) {
        callback = cb;
        requestPermissions(permissions, 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            List<String> deniedList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedList.add(permissions[i]);
                }
            }
            boolean allGranted = deniedList.isEmpty();
            callback.handle(allGranted, deniedList);
        }
    }
}
