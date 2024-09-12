package com.example.utilsuser.toast;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.example.utilsgather.logcat.LogUtil;

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
    public static void showToast(Context context, String text, int duration) {
        getInstance().enqueueToast(context, text, duration);
    }

    private Toast currentToast;  // 目前展示的Toast，既可以用来标识当前是否有展示的Toast，也可以用来取消当前展示的Toast
    private final Queue<String> toastQueue = new ArrayDeque<>();  // 队列
    private final Handler handler = new Handler();  // 用来延迟1000ms后展示
    private static final int MIN_DISPLAY_TIME = 1000; // 最短展示时间设置为1000毫秒

    // 递归开始的初始动力，递归停止后，也是靠它来重新运作
    private void enqueueToast(Context context, String text, int duration) {
        toastQueue.add(text);  // 无论文本如何都添加到队列中
        if (currentToast == null) {  // 如果当前展示的Toast为空，代表没有递归在运作了，可以直接开始展示
            showNextToast(context, duration);
        }
    }

    // 这是一个递归函数，用于队列中的Toast逐个展示
    private void showNextToast(Context context, int duration) {
        if (toastQueue.isEmpty()) {  // 队列中没有内容了，递归运作停止，这是递归的出口条件
            currentToast = null;
            return;
        }

        String text = toastQueue.poll();
        if (currentToast != null) {
            currentToast.cancel();  // 取消前一个，这是避免一直Toast维持太久的关键
        }

        currentToast = Toast.makeText(context, text, duration);
        currentToast.show();  // Toast的真正展示位置

        // 延迟处理队列中的下一个Toast，保证多个Toast被点击要展示时，有1000ms的最短展示时间
        handler.postDelayed(() -> showNextToast(context, duration), MIN_DISPLAY_TIME);
    }

    // 看具体业务调用这个方法取消Toast展示吧。取消的同时，这个单例也要别重置了
    public static void cancelAllToast() {
        if (instance == null) return;  // 没使用过ToastManager

        if (instance.currentToast != null) {
            instance.currentToast.cancel();
            instance.currentToast = null;
            instance.handler.removeCallbacksAndMessages(null); // 移除所有回调和消息，防止新的Toast弹出
        }
        instance = null;
    }

    /* todo 其实取消这里存在一点点问题，就是前面 currentToast = null; 会导致currentToast为空，因此会有可能取消不了在展示的，
        造成退出时如果正好是最后一个Toast被置null，那它是cancel不了的。不过问题不大吧
     */
}
