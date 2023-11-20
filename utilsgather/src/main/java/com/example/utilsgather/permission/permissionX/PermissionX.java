package com.example.utilsgather.permission.permissionX;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class PermissionX {
    private static final String TAG = "InvisibleFragment";

    public static void request(FragmentActivity activity, String[] permissions, ApplyResultCallback callback) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment existedFragment = fragmentManager.findFragmentByTag(TAG);
        InvisibleFragment invisibleFragment = null;
        if (existedFragment != null) {
            invisibleFragment = (InvisibleFragment) existedFragment;
        } else {
            InvisibleFragment newInvisibleFragment = new InvisibleFragment();
            fragmentManager.beginTransaction().add(newInvisibleFragment, TAG).commitNow();
            invisibleFragment = newInvisibleFragment;
        }
        invisibleFragment.requestNow(callback, permissions);
    }
}
