package com.sundyn.util;

import java.io.*;
import java.util.*;
import java.security.*;

public class MD5toPic
{
    private ArrayList<Map> filelist;
    private static Map tempMap;
    private static String tempPath;
    
    static {
        MD5toPic.tempMap = null;
        MD5toPic.tempPath = "/";
    }
    
    public MD5toPic() {
        this.filelist = new ArrayList<Map>();
    }
    
    public String MD5(final String fileName) {
        final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        final File file = new File(fileName);
        final byte[] b = new byte[(int)file.length()];
        try {
            final InputStream in = new FileInputStream(fileName);
            in.read(b);
            final MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(b);
            final byte[] md = mdTemp.digest();
            final int j = md.length;
            final char[] str = new char[j * 2];
            int k = 0;
            for (final byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xF];
                str[k++] = hexDigits[byte0 & 0xF];
            }
            return new String(str).toUpperCase();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Map> refreshFileList(final String strPath) {
        final File dir = new File(strPath);
        final File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isDirectory()) {
                this.refreshFileList(files[i].getAbsolutePath());
            }
            else {
                String strFileName = files[i].getAbsolutePath();
                final MD5toPic m = new MD5toPic();
                (MD5toPic.tempMap = new HashMap()).put("hash", m.MD5(strFileName));
                strFileName = strFileName.substring(34, strFileName.length());
                strFileName = strFileName.replace("\\", "/");
                MD5toPic.tempMap.put("filename", strFileName);
                final String[] temp = strFileName.split("/");
                for (int k = 2; k < temp.length - 1; ++k) {
                    MD5toPic.tempPath = String.valueOf(MD5toPic.tempPath) + temp[k] + "/";
                }
                MD5toPic.tempMap.put("filePath", MD5toPic.tempPath);
                MD5toPic.tempPath = "/";
                this.filelist.add(MD5toPic.tempMap);
            }
        }
        return this.filelist;
    }
    
    public ArrayList getFilelist() {
        return this.filelist;
    }
    
    public void setFilelist(final ArrayList filelist) {
        this.filelist = (ArrayList<Map>)filelist;
    }
    
    public static void main(final String[] args) throws NoSuchAlgorithmException {
    }
}
