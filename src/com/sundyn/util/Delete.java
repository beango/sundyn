package com.sundyn.util;

import java.io.*;

public class Delete
{
    private File file;
    private boolean flag;
    private String message;
    private static String matches;
    
    static {
        Delete.matches = "[A-Za-z]:\\\\[^:?\"><*]*";
    }
    
    public Delete() {
        this.message = "";
    }
    
    public static void main(final String[] args) {
        boolean result = false;
        String path = "";
        final Delete hfc = new Delete();
        path = "E:\\Abc\\124\\123.txt";
        result = hfc.DeleteFolder(path);
        if (result) {
            System.out.println("\u5220\u9664\u6210\u529f");
        }
        else {
            System.out.println("\u5220\u9664\u5931\u8d25");
        }
    }
    
    public boolean deleteFile(final String sPath) {
        this.flag = false;
        this.file = new File(sPath);
        if (this.file.isFile() && this.file.exists()) {
            this.file.delete();
            System.out.println("\u6587\u4ef6\u5220\u9664\u6210\u529f----" + sPath);
            this.flag = true;
        }
        else {
            System.out.println("\u8be5\u6587\u4ef6\u4e0d\u5b58\u5728----" + sPath);
        }
        return this.flag;
    }
    
    public boolean DeleteFolder(final String sPath) {
        this.flag = false;
        if (!sPath.matches(Delete.matches)) {
            System.out.println("\u8def\u5f84\u4e0d\u5408\u6cd5\uff0c\u5220\u9664\u53d6\u6d88----" + sPath);
            return false;
        }
        this.file = new File(sPath);
        if (!this.file.exists()) {
            System.out.println("\u6587\u4ef6\u5939\u8def\u5f84\u4e0d\u5b58\u5728----" + sPath);
            return this.flag;
        }
        if (this.file.isFile()) {
            return this.deleteFile(sPath);
        }
        System.out.println("\u8def\u5f84\u662f\u6587\u4ef6\u5939\uff0c\u5220\u9664\u53d6\u6d88----" + sPath);
        return false;
    }
    
    public boolean deleteDirectory(String sPath) {
        if (!sPath.endsWith(File.separator)) {
            sPath = String.valueOf(sPath) + File.separator;
        }
        final File dirFile = new File(sPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        this.flag = true;
        final File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isFile()) {
                if (!(this.flag = this.deleteFile(files[i].getAbsolutePath()))) {
                    break;
                }
            }
            else if (!(this.flag = this.deleteDirectory(files[i].getAbsolutePath()))) {
                break;
            }
        }
        return this.flag && dirFile.delete();
    }
}
