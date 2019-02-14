package com.sundyn.util;

import java.util.*;
import java.io.*;
import java.util.zip.*;

public class Update
{
    private List<FileInfo> ls;
    private static String header;
    
    static {
        Update.header = "M7UDP";
    }
    
    public Update(final String[] file) throws FileNotFoundException {
        this.ls = new ArrayList<FileInfo>();
        System.out.println("update-传入数组-直接初始化");
        this.init(file);
    }
    
    public Update() {
        this.ls = new ArrayList<FileInfo>();
    }
    
    public Update(String path) throws FileNotFoundException {
        this.ls = new ArrayList<FileInfo>();
        if (!path.endsWith("/")) {
            path = String.valueOf(path) + File.separator;
        }
        System.out.println("update-\u4f20\u5165\u8def\u5f84-\u5148\u5904\u7406\u8def\u5f84\u518d\u521d\u59cb\u5316");
        final List temp = new ArrayList();
        final File f = new File(path);
        System.out.println("Update-path=" + path);
        if (f.exists() && f.isDirectory()) {
            final String[] file = f.list();
            for (int i = 0; i < file.length; ++i) {
                final String filename = String.valueOf(path) + file[i];
                System.out.println("Update-add-filename=" + filename);
                if (!file[i].endsWith("NEWCONFIG.XML") && !new File(filename).isDirectory()) {
                    temp.add(filename);
                }
            }
        }
        final String[] file = new String[temp.size()];
        for (int i = 0; i < temp.size(); ++i) {
            file[i] = (String) temp.get(i);
        }
        this.init(file);
    }
    
    public void add(final String path) throws FileNotFoundException {
        final String[] s_path = { path };
        this.init(s_path);
    }
    
    public void add(final String[] path) throws FileNotFoundException {
        this.init(path);
    }
    
    public void init(final String[] file) throws FileNotFoundException {
        final String[] file2 = new String[file.length];
        for (int j = 0; j < file2.length; ++j) {
            file2[j] = file[j].substring(file[j].lastIndexOf("\\") + 1);
        }
        this.init(file, file2);
    }
    
    public void init(final String[] file, final String[] file2) throws FileNotFoundException {
        for (int i = 0; i < file.length; ++i) {
            final File f = new File(file[i]);
            System.out.println("init-file[" + i + "]=" + file[i]);
            if (f.exists()) {
                if (f.isFile()) {
                    final long filesize = f.length();
                    final String fn = file2[i];
                    final byte[] filenamelength = intToByte(fn.getBytes().length);
                    final byte[] filename = fn.getBytes();
                    final InputStream in = new FileInputStream(file[i]);
                    final FileInfo fi = new FileInfo(filesize, filenamelength, in, filename);
                    this.ls.add(fi);
                }
                else {
                    final File[] files = f.listFiles();
                    final String[] fileNames = new String[files.length];
                    for (int j = 0; j < fileNames.length; ++j) {
                        fileNames[j] = files[j].getAbsolutePath();
                    }
                    this.init(fileNames);
                }
            }
        }
    }
    
    public void createUpdateFile(final String path) throws IOException {
        final FileOutputStream fo = new FileOutputStream(path);
        fo.write(Update.header.getBytes());
        fo.write(intToByte(this.ls.size()));
        for (int i = 0; i < this.ls.size(); ++i) {
            final FileInfo fi = this.ls.get(i);
            fo.write(fi.getFilenamelength());
            fo.write(fi.getFilelength());
        }
        for (int i = 0; i < this.ls.size(); ++i) {
            final FileInfo fi = this.ls.get(i);
            fo.write(fi.getFilename());
            System.out.println("createUpdateFile-fi.getFilename()=" + fi.getFilename());
            final InputStream in = fi.getFile();
            byte[] r = new byte[1024];
            final long filesize = fi.getFilesize();
            if (filesize >= 1024L) {
                final int times = (int)(filesize / 1024L);
                final int end = (int)(filesize % 1024L);
                for (int j = 0; j < times; ++j) {
                    in.read(r);
                    fo.write(r);
                }
                r = new byte[1024];
                in.read(r);
                final byte[] t1 = new byte[end];
                for (int k = 0; k < end; ++k) {
                    t1[k] = r[k];
                }
                fo.write(t1);
            }
            else {
                final byte[] t2 = new byte[(int)filesize];
                in.read(t2);
                fo.write(t2);
            }
            fo.flush();
            in.close();
        }
        fo.close();
    }
    
    public void createUpdateFileZip(final String path) throws IOException {
        final ZipOutputStream fo = new ZipOutputStream(new FileOutputStream(path));
        fo.write(Update.header.getBytes());
        fo.write(intToByte(this.ls.size()));
        for (int i = 0; i < this.ls.size(); ++i) {
            final FileInfo fi = this.ls.get(i);
            fo.write(fi.getFilenamelength());
            fo.write(fi.getFilelength());
        }
        for (int i = 0; i < this.ls.size(); ++i) {
            final FileInfo fi = this.ls.get(i);
            fo.write(fi.getFilename());
            final InputStream in = fi.getFile();
            byte[] r = new byte[1024];
            final long filesize = fi.getFilesize();
            if (filesize >= 1024L) {
                final int times = (int)(filesize / 1024L);
                final int end = (int)(filesize % 1024L);
                for (int j = 0; j < times; ++j) {
                    in.read(r);
                    fo.write(r);
                }
                r = new byte[1024];
                in.read(r);
                final byte[] t1 = new byte[end];
                for (int k = 0; k < end; ++k) {
                    t1[k] = r[k];
                }
                fo.write(t1);
            }
            else {
                final byte[] t2 = new byte[(int)filesize];
                in.read(t2);
                fo.write(t2);
            }
            fo.flush();
            in.close();
        }
        fo.close();
    }
    
    public static byte[] intToByte(final int i) {
        final byte[] bt = { (byte)(0xFF & i), (byte)((0xFF00 & i) >> 8), (byte)((0xFF0000 & i) >> 16), (byte)((0xFF000000 & i) >> 24) };
        return bt;
    }
    
    public static void main(final String[] args) throws IOException {
        final Update up = new Update("C:\\Documents and Settings\\Administrator\\\u684c\u9762\\test\\UpdateFile\\");
        up.createUpdateFile("C:\\Documents and Settings\\Administrator\\\u684c\u9762\\test\\M7Update.bin");
    }
    
    public void makeZip(final String path, final String[] fileName) {
        final byte[] buf = new byte[1024];
        try {
            final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));
            for (int i = 0; i < 2; ++i) {
                final FileInputStream in = new FileInputStream(fileName[i]);
                out.putNextEntry(new ZipEntry(fileName[i]));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void makeZip(final String path, final String[] file, final String[] file2) {
        try {
            final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));
            for (int i = 0; i < file.length; ++i) {
                if(file[i]!=null && !"".equals(file[i]))
                    this.zip(out, file[i], file2[i]);
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void zip(final ZipOutputStream out, final String file, final String file2) throws IOException {
        final byte[] buf = new byte[1024];
        System.out.println("打包文件:file:" + file + "---file2:" + file2 );
        File f=new File(file);
        if(!f.exists())
            return;
        if (f.isFile()) {
            final FileInputStream in = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file2));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
        }
        else {
            final File[] child = new File(file).listFiles();
            for (int j = 0; j < child.length; ++j) {
                this.zip(out, child[j].getAbsolutePath(), String.valueOf(file2) + "/" + child[j].getName());
            }
        }
    }
    
    public void makeZip2(final String path, final String[] file, final String[] file2) {
        final byte[] buf = new byte[1024];
        try {
            final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(path));
            for (int i = 0; i < file.length; ++i) {
                final FileInputStream in = new FileInputStream(file[i]);
                out.putNextEntry(new ZipEntry("ads//" + file2[i]));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

