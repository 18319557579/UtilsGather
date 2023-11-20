package com.example.utilsgather.permission.permissionX;

import java.util.List;

public interface ApplyResultCallback {
    void handle(boolean allGranted, List<String> deniedList);
}
