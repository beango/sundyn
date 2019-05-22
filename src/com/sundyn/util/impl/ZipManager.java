package com.sundyn.util.impl;

import java.util.zip.*;
import java.io.*;
import java.util.*;

public class ZipManager
{
    public void createZip(final String baseDir, final String objFileName) throws Exception {
        final File folderObject = new File(baseDir);
        if (folderObject.exists()) {
            final List fileList = this.getSubFiles(new File(baseDir));
            final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFileName));
            ZipEntry ze = null;
            final byte[] buf = new byte[1024];
            int readLen = 0;
            for (int i = 0; i < fileList.size(); ++i) {
                final File f = (File) fileList.get(i);
                ze = new ZipEntry(this.getAbsFileName(baseDir, f));
                ze.setSize(f.length());
                ze.setTime(f.lastModified());
                zos.putNextEntry(ze);
                final InputStream is = new BufferedInputStream(new FileInputStream(f));
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    zos.write(buf, 0, readLen);
                }
                is.close();
            }
            zos.close();
            return;
        }
        throw new Exception("this folder isnot exist!");
    }
    
    public long createFileToZip(final String zipFilename, final String sourceFileName) throws Exception {
        final File sourceFile = new File(sourceFileName);
        final byte[] buf = new byte[1024];
        final File objFile = new File(zipFilename);
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFile));
        ZipEntry ze = null;
        ze = new ZipEntry(sourceFile.getName());
        ze.setSize(sourceFile.length());
        ze.setTime(sourceFile.lastModified());
        zos.putNextEntry(ze);
        final InputStream is = new BufferedInputStream(new FileInputStream(sourceFile));
        int readLen = -1;
        while ((readLen = is.read(buf, 0, 1024)) != -1) {
            zos.write(buf, 0, readLen);
        }
        is.close();
        zos.close();
        return objFile.length();
    }
    
    public long createFileToZip(final File sourceFile, final File zipFile) throws IOException {
        final byte[] buf = new byte[1024];
        final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
        ZipEntry ze = null;
        ze = new ZipEntry(sourceFile.getName());
        ze.setSize(sourceFile.length());
        ze.setTime(sourceFile.lastModified());
        zos.putNextEntry(ze);
        final InputStream is = new BufferedInputStream(new FileInputStream(sourceFile));
        int readLen = -1;
        while ((readLen = is.read(buf, 0, 1024)) != -1) {
            zos.write(buf, 0, readLen);
        }
        is.close();
        zos.close();
        return zipFile.length();
    }
    
    public void releaseZipToFile(final String sourceZip, final String outFileName) throws IOException {
        final ZipFile zfile = new ZipFile(sourceZip);
        final Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        final byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                continue;
            }
            final OutputStream os = new BufferedOutputStream(new FileOutputStream(this.getRealFileName(outFileName, ze.getName())));
            final InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }
    
    private List getSubFiles(final File baseDir) {
        final List ret = new ArrayList();
        final File[] tmp = baseDir.listFiles();
        for (int i = 0; i < tmp.length; ++i) {
            if (tmp[i].isFile()) {
                ret.add(tmp[i]);
            }
            if (tmp[i].isDirectory()) {
                ret.addAll(this.getSubFiles(tmp[i]));
            }
        }
        return ret;
    }
    
    private File getRealFileName(final String baseDir, final String absFileName) {
        final String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; ++i) {
                ret = new File(ret, dirs[i]);
            }
        }
        if (!ret.exists()) {
            ret.mkdirs();
        }
        ret = new File(ret, dirs[dirs.length - 1]);
        return ret;
    }
    
    private String getAbsFileName(final String baseDir, final File realFileName) {
        File real = realFileName;
        final File base = new File(baseDir);
        String ret = real.getName();
        while (true) {
            real = real.getParentFile();
            if (real == null) {
                break;
            }
            if (real.equals(base)) {
                break;
            }
            ret = String.valueOf(real.getName()) + "/" + ret;
        }
        return ret;
    }
    
    public void testReadZip() throws Exception {
        final String baseDir = "d://temp//zipout";
        final ZipFile zfile = new ZipFile("d://download//src.zip");
        final Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        final byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.isDirectory()) {
                continue;
            }
            final OutputStream os = new BufferedOutputStream(new FileOutputStream(this.getRealFileName(baseDir, ze.getName())));
            final InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
    }
    
    public static void main(final String[] args) {
        final ZipManager manager = new ZipManager();
        try {
            final String source = "C:\\tomcat6\\webapps\\pingjia2\\m7app\\104";
            final String target = "E:\\zip\\new.zip";
            manager.createZip(source, target);
        }
        catch (Exception e) {
            System.out.println("error");
        }
        System.out.println("over");
    }
}
