package com.example.utilsuser.file.list.database.state;

public class DownloadingState extends BaseState{
    public DownloadingState() {
        this.text = "下载中";
        this.resId = pauseResId;
    }
}
