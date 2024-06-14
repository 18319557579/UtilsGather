package com.example.utilsuser.file.list.database.ui;

import androidx.lifecycle.ViewModel;

import com.example.utilsuser.file.list.database.bean.BeanPackaged;

import java.util.List;

public class DownloadTaskViewHolder extends ViewModel {
    private List<BeanPackaged> downloadTaskBeans;

    public List<BeanPackaged> getDownloadTaskBeans() {
        return downloadTaskBeans;
    }


    public void setDownloadTaskBeans(List<BeanPackaged> downloadTaskBeans) {
        this.downloadTaskBeans = downloadTaskBeans;
    }

    public void addTask(BeanPackaged beanPackaged) {
        downloadTaskBeans.add(0, beanPackaged);
    }
}
