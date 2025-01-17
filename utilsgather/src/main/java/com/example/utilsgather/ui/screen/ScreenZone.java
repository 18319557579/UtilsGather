package com.example.utilsgather.ui.screen;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.utilsgather.context.ApplicationGlobal;

import java.util.ArrayList;
import java.util.List;

public class ScreenZone {
    private final List<RectF> rectFList;

    public ScreenZone(int zoneNum) {
        if (zoneNum < 1) {
            throw new IllegalArgumentException("参数必须要大于等于1");
        }

        rectFList = new ArrayList<>();

        int screenWidthReal = ScreenSizeUtil.getScreenWidthReal(ApplicationGlobal.getInstance());
        int screenHeightReal = ScreenSizeUtil.getScreenHeightReal(ApplicationGlobal.getInstance());

        for (int currentNum = 0; currentNum < zoneNum; currentNum++) {
            RectF rectF = new RectF(
                    0F,
                    screenHeightReal * (currentNum / (float)zoneNum),
                    (float)screenWidthReal,
                    screenHeightReal * ((currentNum + 1) / (float)zoneNum)
            );
            rectFList.add(rectF);
        }
    }

    public int getZoneIndex(MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        for (int i = 0; i < rectFList.size(); i++) {
            RectF rectF = rectFList.get(i);
            if (rectF.contains(x, y)) {
                return i;
            }
        }

        return -1;

    }
}
