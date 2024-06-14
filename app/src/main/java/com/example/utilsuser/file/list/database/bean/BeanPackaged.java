package com.example.utilsuser.file.list.database.bean;

import com.example.utilsuser.file.list.database.bean.state.BaseState;

public class BeanPackaged {
    public DownloadTaskBean downloadTaskBean;
    public BaseState baseState;

    @Override
    public String toString() {
        return "BeanPackaged{" +
                "downloadTaskBean=" + downloadTaskBean +
                ", baseState=" + baseState +
                '}';
    }
}
