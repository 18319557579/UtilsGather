package com.example.utilsgather.ui.toast;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.utilsgather.context.ApplicationGlobal;

import java.util.ArrayDeque;
import java.util.Queue;

public class ToastManager {
    private static ToastManager instance;
    private ToastManager() {}  // 私有构造方法
    private static ToastManager getInstance() {
        if (instance == null) {
            instance = new ToastManager();
        }
        return instance;
    }

    // 由于改造成单例模式，所以对外静态方法中，要getInstance()以获取单例
    public static void showToast(String text) {
        getInstance().enqueueToast(ApplicationGlobal.getInstance(), text, Toast.LENGTH_SHORT);
    }

    private Toast currentToast;  // 目前展示的Toast
    private final Queue<String> toastQueue = new ArrayDeque<>();  // 队列
    private final Handler handler = new Handler();  // 用来延迟1000ms后展示
    private static final long MIN_DISPLAY_TIME = 1000L; // 最短展示时间设置为1000毫秒
    private boolean isWorking = false;  // 记录是否有递归正在运转

    // 递归开始的初始动力，递归停止后，也是靠它来重新运作
    private void enqueueToast(Context context, String text, int duration) {
        toastQueue.add(text);  // 无论如何文本如何都添加到队列中
        if (! isWorking) {  // 如果当前没有递归在运作了，可以直接开始展示
//            LogUtil.d("进入next: " + text);
            isWorking = true;
            showNextToast(context, duration);
        }
    }

    // 这是一个递归函数，用于队列中的Toast逐个展示
    private void showNextToast(Context context, int duration) {
        if (toastQueue.isEmpty()) {  // 队列中没有内容了，递归运作停止，这是递归的出口条件
            isWorking = false;
            return;
        }

        String text = toastQueue.poll();
        if (currentToast != null) {
//            LogUtil.d("此时的text: " + text);
            currentToast.cancel();  // 取消前一个，这是避免一直Toast维持太久的关键
        }

        currentToast = Toast.makeText(context, text, duration);
        currentToast.show();  // Toast的真正展示位置

        // 延迟处理队列中的下一个Toast，保证多个Toast被点击要展示时，有1000ms的最短展示时间
        handler.postDelayed(() -> {
//            LogUtil.d("等待了1000ms");
            showNextToast(context, duration);
        }, MIN_DISPLAY_TIME);
    }

    // 看具体业务调用这个方法取消Toast展示吧
    public static void cancelAllToast() {
        if (instance == null) return;  // 没使用过ToastManager的情况

        if (instance.currentToast != null) {
            instance.currentToast.cancel();  // 如果取消Toast展示时，最后一个Toast不要直接关掉，那么把这里给干掉
        }
        instance.handler.removeCallbacksAndMessages(null); // 移除所有回调和消息，防止新的Toast弹出
        instance.toastQueue.clear();
        instance.currentToast = null;
        instance.isWorking = false;
    }
}
