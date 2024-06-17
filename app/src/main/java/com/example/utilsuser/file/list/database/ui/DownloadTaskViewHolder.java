package com.example.utilsuser.file.list.database.ui;

import androidx.lifecycle.ViewModel;

import com.example.utilsuser.file.list.database.bean.BeanPackaged;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

public class DownloadTaskViewHolder extends ViewModel {
    private List<BeanPackaged> downloadTaskBeans = new ArrayList<>();

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
