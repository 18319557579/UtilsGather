package com.example.utilsuser.file.list.database.state;

public class BrokenState extends BaseState{
    public BrokenState() {
        this.text = "本地文件已损坏";
        this.resId = resumeResId;
    }
}
