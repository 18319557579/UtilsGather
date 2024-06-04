package com.example.utilsuser.file.list;

import com.example.utilsgather.format_trans.FormatTransfer;

public class FileInfoBean {
    private String name;  //名字
    private String path;  //路径
    private long length;  //大小
    private long lastModified;  //上次修改时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getLength() {
        return length;
    }

    /**
     * 格式化长度，例如 KB MB
     */
    public String getFormattedLength() {
        return FormatTransfer.byteFormat(length);
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * 获得格式化的上次修改时间
     */
    public String getFormattedLastModified() {
        return FormatTransfer.timeStampFormat(lastModified);
    }
}
