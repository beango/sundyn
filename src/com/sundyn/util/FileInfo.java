package com.sundyn.util;

import java.io.*;

public class FileInfo
{
    byte[] filelength;
    byte[] filenamelength;
    InputStream file;
    byte[] filename;
    long filesize;
    
    public FileInfo(final long filesize, final byte[] filenamelength, final InputStream file, final byte[] filename) {
        this.filenamelength = filenamelength;
        this.file = file;
        this.filename = filename;
        this.filesize = filesize;
        this.filelength = long2bytes(filesize);
        final byte[] temp = new byte[4];
        for (int j = 0; j < temp.length; ++j) {
            temp[j] = this.filelength[j + 4];
        }
        for (int j = 0; j < temp.length / 2; ++j) {
            final byte t = temp[j];
            temp[j] = temp[3 - j];
            temp[3 - j] = t;
        }
        this.filelength = temp;
    }
    
    public byte[] getFilelength() {
        return this.filelength;
    }
    
    public void setFilelength(final byte[] filelength) {
        this.filelength = filelength;
    }
    
    public byte[] getFilenamelength() {
        return this.filenamelength;
    }
    
    public void setFilenamelength(final byte[] filenamelength) {
        this.filenamelength = filenamelength;
    }
    
    public InputStream getFile() {
        return this.file;
    }
    
    public void setFile(final InputStream file) {
        this.file = file;
    }
    
    public byte[] getFilename() {
        return this.filename;
    }
    
    public void setFilename(final byte[] filename) {
        this.filename = filename;
    }
    
    public long getFilesize() {
        return this.filesize;
    }
    
    public void setFilesize(final long filesize) {
        this.filesize = filesize;
    }
    
    public static byte[] long2bytes(final long num) {
        final byte[] b = new byte[8];
        for (int i = 0; i < b.length; ++i) {
            b[i] = (byte)(num >>> 56 - i * 8);
        }
        return b;
    }
}
