package com.example.utilsgather.ui.custom_view;

import android.graphics.Paint;
import android.graphics.Rect;

public class CustomViewTextUtil {
    /**
     * 获取推荐的行距，也就是两行文字baseline的距离。可以用于自动手动绘制多行文字
     */
    public static float getRecommendLineSpacing(Paint textPaint) {
        return textPaint.getFontSpacing();
    }

    /**
     * 常规字符的高度
     */
    public static float getNormalCharacterHeight(Paint textPaint) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    /**
     * 适用于所有字符的高度
     */
    public static float getWholeCharacterHeight(Paint textPaint) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

    /**
     * 获得实际内容的包裹高度，不同的字符串会有不同的包裹高度
     */
    public static int getActualWrapHeight(String text,Paint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);  // bounds是包裹text的相对于(0,0)的偏移
        return bounds.bottom - bounds.top;
    }

    /**
     * 获得测量的字符串宽度（算上了留白，相当于实际所占的宽度）
     */
    public static float getMeasureTextWidth(String text, Paint textPaint) {
        return textPaint.measureText(text);
    }

    /**
     * 获得实际内容的包裹宽度
     */
    public static int getActualWrapWidth(String text, Paint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);  // bounds是包裹text的相对于(0,0)的偏移
        return bounds.right - bounds.left;
    }

    /**
     * 控件想要drawText()在垂直居中的位置，所baseline的位置
     */
    public static float getBaselineY(int viewHeight, Paint textPaint) {
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        // 该值为文本中心位置相对到baseline的位置，为负值，在baseline上方
        float baselineY = (fontMetrics.descent + fontMetrics.ascent) / 2;
        // 用控件的中心位置去 和 文本中心位置到baseline距离 相加，这样做的结果是文本的垂直中心会精确地对齐到控件的中心线
        // 得到的结果为，对于该控件我们应该设置的baseline的位置。换言之，我们以该baseline进行drawText()，文本会准确地垂直居中
        return viewHeight / 2f - baselineY;
    }
}
