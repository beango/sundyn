package com.sundyn.util;

import java.nio.channels.*;
import java.io.*;
import java.util.zip.*;

public class MyFile
{
    private static final int BUFFER_SIZE = 1024;
    
    public static void copy(final File src, final File dst) {
        try {
            final FileChannel srcChannel = new FileInputStream(src).getChannel();
            final FileChannel dstChannel = new FileOutputStream(dst).getChannel();
            dstChannel.transferFrom(srcChannel, 0L, srcChannel.size());
            srcChannel.close();
            dstChannel.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void copy(final String src, final String dst) {
        copy(new File(src), new File(dst));
    }
    
    public static String getExtFileName(final String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    
    public static void extZipFileList(final String zipFileName, final String extPlace) {
        try {
            final ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry entry = null;
            while ((entry = in.getNextEntry()) != null) {
                final String entryName = entry.getName();
                if (entry.isDirectory()) {
                    final File file = new File(String.valueOf(extPlace) + File.separator + entryName);
                    file.mkdirs();
                    System.out.println("\u521b\u5efa\u6587\u4ef6\u5939:" + entryName);
                }
                else {
                    final FileOutputStream os = new FileOutputStream(String.valueOf(extPlace) + File.separator + entryName);
                    byte[] buf;
                    long filesize;
                    int len;
                    for (buf = new byte[1024], filesize = entry.getSize(); (len = in.read(buf)) > 0 && filesize >= 1024L; filesize -= 1024L) {
                        os.write(buf, 0, len);
                    }
                    if (filesize > 0L) {
                        buf = new byte[Integer.parseInt(new StringBuilder().append(filesize).toString())];
                        len = in.read(buf);
                        os.write(buf, 0, len);
                    }
                    os.close();
                    in.closeEntry();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\u89e3\u538b\u6587\u4ef6\u6210\u529f");
    }
    
    public static void extZipFileList(final File zipF, final String extPlace) {
        try {
            final ZipInputStream in = new ZipInputStream(new FileInputStream(zipF));
            ZipEntry entry = null;
            while ((entry = in.getNextEntry()) != null) {
                final String entryName = entry.getName();
                if (entry.isDirectory()) {
                    final File file = new File(String.valueOf(extPlace) + File.separator + entryName);
                    file.mkdirs();
                    System.out.println("extZipFileList-\u521b\u5efa\u6587\u4ef6\u5939:" + extPlace + File.separator + entryName);
                }
                else {
                    System.out.println("extZipFileList-\u521b\u5efa\u6587\u4ef6:" + extPlace + File.separator + entryName);
                    final FileOutputStream os = new FileOutputStream(String.valueOf(extPlace) + File.separator + entryName);
                    final byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        os.write(buf, 0, len);
                    }
                    os.close();
                    in.closeEntry();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("extZipFileList-\u89e3\u538b\u6587\u4ef6\u6210\u529f");
    }
    
    public static void extZipFileList2(final File zipF, final String extPlace) {
        try {
            final ZipInputStream in = new ZipInputStream(new FileInputStream(zipF));
            ZipEntry entry = null;
            while ((entry = in.getNextEntry()) != null) {
                final String entryName = entry.getName();
                System.out.println("entryName=" + entryName);
                final File file = new File(String.valueOf(extPlace) + File.separator + entryName);
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                final FileOutputStream os = new FileOutputStream(String.valueOf(extPlace) + File.separator + entryName);
                final byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    os.write(buf, 0, len);
                }
                os.close();
                in.closeEntry();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("extZipFileList-\u89e3\u538b\u6587\u4ef6\u6210\u529f");
    }
    
    public static void copyDirectory(final File src, final File dst) {
        try {
            if (!dst.exists()) {
                dst.mkdirs();
            }
            final File[] fileList = src.listFiles();
            for (int i = 0; i < fileList.length; ++i) {
                copy(fileList[i], new File(String.valueOf(dst.getAbsolutePath()) + File.separator + fileList[i].getName()));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void copyDirectory(final String src, final String dst) {
        copyDirectory(new File(src), new File(dst));
    }
    
    public static void main(final String[] args) {
    }
    
    public static void delete(final File f) {
        if (f.exists()) {
            if (f.isDirectory()) {
                final File[] fileList = f.listFiles();
                for (int i = 0; i < fileList.length; ++i) {
                    delete(fileList[i]);
                }
                f.delete();
            }
            else {
                f.delete();
            }
        }
    }
    
    public static void delete(final String f) {
        delete(new File(f));
    }
}

