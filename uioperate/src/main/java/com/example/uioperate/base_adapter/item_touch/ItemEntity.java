package com.example.uioperate.base_adapter.item_touch;

/**
 * Created by Lin Yaotian on 2018/2/8.
 */

public class ItemEntity {
    private String text;
    private boolean isChecked;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
