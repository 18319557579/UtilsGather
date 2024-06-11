package com.example.utilsuser.file.list.database.state;

public class PausedState extends BaseState{
    public PausedState() {
        this.text = "暂停";
        this.resId = BaseState.resumeResId;
    }
}
