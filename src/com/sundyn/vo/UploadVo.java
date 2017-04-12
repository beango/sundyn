package com.sundyn.vo;

import java.io.*;

public class UploadVo
{
    private File file;
    private int id;
    
    public File getFile() {
        return this.file;
    }
    
    public void setFile(final File file) {
        this.file = file;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
}
