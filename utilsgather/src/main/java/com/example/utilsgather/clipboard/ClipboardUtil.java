package com.example.utilsgather.clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class ClipboardUtil {
    /**
     * 获取剪切板上的内容
     */
    public static String getClipboardContent(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if (data != null && data.getItemCount() > 0) {
            ClipData.Item item = data.getItemAt(0);
            if (item != null) {
                return item.coerceToText(context).toString();
            }
        }
        return null;
    }

    /**
     * 将内容复制到剪切板
     */
    public static void copyToClipboard(String copyStr, Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", copyStr);
        cm.setPrimaryClip(mClipData);
    }
}
