package com.example.utilsgather.file_system;

import java.io.File;

public class FileOperationUtil {
    /**
     * 文件重命名
     */
    public static boolean renameFile(String oldFilePath, String newFilePath) {
        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        return oldFile.renameTo(newFile);
    }

    /**
     * 给一个文件名增加前缀
     * 例如/data/user/0/com.example.utilsuser/files/dl/jizhang.apk，增加前缀finished-
     * 变成/data/user/0/com.example.utilsuser/files/dl/finished-jizhang.apk
     */
    public static boolean renameFileWithPrefix(String filePath, String prefix) {
        File oldFile = new File(filePath);

        // 检查原文件是否存在
        if (!oldFile.exists()) {
            System.out.println("The original file does not exist.");
            return false;
        }

        // 获取父目录
        String parentDir = oldFile.getParent();
        // 构建新的文件名
        String newFileName = prefix + oldFile.getName();
        // 创建新的文件路径
        File newFile = new File(parentDir, newFileName);

        return oldFile.renameTo(newFile);
    }

    /**
     * 给一个文件的文件名添加前缀后，返回该文件（并没有真正的rename，只是得到一个File）
     */
    public static File getFileWithPrefix(File oldFile, String prefix) {
        // 获取父目录
        File parentFile = oldFile.getParentFile();
        // 构建新的文件名
        String newFileName = prefix + oldFile.getName();
        // 创建新的文件路径
        File newFile = new File(parentFile, newFileName);
        return newFile;
    }
}
