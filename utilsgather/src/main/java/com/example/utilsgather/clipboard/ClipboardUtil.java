package com.example.utilsgather.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil {
    /**
     * 将内容复制到剪切板
     */
    public static void copyToClipboard(String copyStr, Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", copyStr);
        cm.setPrimaryClip(mClipData);
    }
}
