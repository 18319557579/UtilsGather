package com.example.utilsgather.ui.immersion;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

public class ImmersionUtil {
    /**
     * 表现：
     * 1.如果底部有那种虚拟导航栏的话，便隐藏了，屏幕空间变大了
     * 2.ACTION_BAR也没了
     * 3.状态栏背景变为黑色，且状态栏的文字也不显示了
     * 4.从屏幕顶部或底部往里拨动，状态的文字可以显示了，导航栏可见并可以点击了
     */
    public static void hideVirtualButton(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19 &&
                null != activity) {
            // use reflection to remove dependence of API level

            Class viewClass = View.class;
            final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION");
            final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN");
            final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_HIDE_NAVIGATION");
            final int SYSTEM_UI_FLAG_FULLSCREEN = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_FULLSCREEN");
            final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_IMMERSIVE_STICKY");
            final int SYSTEM_UI_FLAG_LAYOUT_STABLE = Cocos2dxReflectionHelper.<Integer>getConstantValue(viewClass, "SYSTEM_UI_FLAG_LAYOUT_STABLE");

            // getWindow().getDecorView().setSystemUiVisibility();
            final Object[] parameters = new Object[]{SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | SYSTEM_UI_FLAG_IMMERSIVE_STICKY};
            Cocos2dxReflectionHelper.<Void>invokeInstanceMethod(activity.getWindow().getDecorView(),
                    "setSystemUiVisibility",
                    new Class[]{Integer.TYPE},
                    parameters);
        }
    }

    public static void screenFull(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        try {
            Field field = lp.getClass().getField("layoutInDisplayCutoutMode");
            //Field constValue = lp.getClass().getDeclaredField("LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER");
            Field constValue = lp.getClass().getDeclaredField("LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES");
            field.setInt(lp, constValue.getInt(null));

            // https://developer.android.com/training/system-ui/immersive
            int flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

            flag |= View.class.getDeclaredField("SYSTEM_UI_FLAG_IMMERSIVE_STICKY").getInt(null);
            View view = activity.getWindow().getDecorView();
            view.setSystemUiVisibility(flag);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
