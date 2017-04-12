package com.sundyn.utils;

import java.io.*;

public class FileMove
{
    public static void copy(final String src, final String dest) {
        try {
            int bytesum = 0;
            int byteread = 0;
            final File file = new File(dest);
            if (!file.exists()) {
                file.createNewFile();
            }
            final File oldfile = new File(src);
            if (!oldfile.exists()) {
                oldfile.createNewFile();
            }
            if (oldfile.exists()) {
                final FileInputStream inStream = new FileInputStream(oldfile);
                final FileOutputStream fs = new FileOutputStream(file);
                final byte[] buffer = new byte[5120];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
