package com.sundyn.util;

import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.*;
import com.sun.image.codec.jpeg.*;

public class CompressPicDemo
{
    private File file;
    private String inputDir;
    private String outputDir;
    private String inputFileName;
    private String outputFileName;
    private int outputWidth;
    private int outputHeight;
    private boolean proportion;
    
    public CompressPicDemo() {
        this.file = null;
        this.outputWidth = 100;
        this.outputHeight = 100;
        this.proportion = true;
        this.inputDir = "";
        this.outputDir = "";
        this.inputFileName = "";
        this.outputFileName = "";
        this.outputWidth = 100;
        this.outputHeight = 100;
    }
    
    public long getPicSize(final String path) {
        this.file = new File(path);
        return this.file.length();
    }
    
    public String compressPic() {
        try {
            this.file = new File(String.valueOf(this.inputDir) + this.inputFileName);
            if (!this.file.exists()) {
                return "";
            }
            final Image img = ImageIO.read(this.file);
            if (img.getWidth(null) == -1) {
                return "no";
            }
            int newHeight;
            int newWidth;
            if (this.proportion) {
                final double rate1 = img.getWidth(null) / this.outputWidth + 0.1;
                final double rate2 = img.getHeight(null) / this.outputHeight + 0.1;
                final double rate3 = (rate1 > rate2) ? rate1 : rate2;
                newHeight = (int)(img.getHeight(null) / rate1);
                newWidth = this.outputWidth;
            }
            else {
                newWidth = this.outputWidth;
                newHeight = this.outputHeight;
            }
            final BufferedImage tag = new BufferedImage(newWidth, newHeight, 1);
            tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, 4), 0, 0, null);
            final FileOutputStream out = new FileOutputStream(String.valueOf(this.outputDir) + this.outputFileName);
            final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);
            out.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return "ok";
    }
    
    public String compressPic(final String inputDir, final String outputDir, final String inputFileName, final String outputFileName) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        return this.compressPic();
    }
    
    public String compressPic(final String inputDir, final String outputDir, final String inputFileName, final String outputFileName, final int width, final int height, final boolean gp) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.setWidthAndHeight(width, height);
        this.proportion = gp;
        return this.compressPic();
    }
    
    public void dealPic(final String from, final String to, final String sourname, final String desname, final int width, final int height) {
        this.compressPic(from, to, sourname, desname, width, height, false);
    }
    
    public static void main(final String[] arg) {
        final CompressPicDemo mypic = new CompressPicDemo();
        int count = 0;
        for (int i = 0; i < 2; ++i) {
            final int start = (int)System.currentTimeMillis();
            mypic.compressPic("e:\\", "e:\\test\\", "1.jpg", "r1" + i + ".jpg", 240, 240, true);
            final int end = (int)System.currentTimeMillis();
            final int re = end - start;
            count += re;
        }
    }
    
    public void setInputDir(final String inputDir) {
        this.inputDir = inputDir;
    }
    
    public void setOutputDir(final String outputDir) {
        this.outputDir = outputDir;
    }
    
    public void setInputFileName(final String inputFileName) {
        this.inputFileName = inputFileName;
    }
    
    public void setOutputFileName(final String outputFileName) {
        this.outputFileName = outputFileName;
    }
    
    public void setOutputWidth(final int outputWidth) {
        this.outputWidth = outputWidth;
    }
    
    public void setOutputHeight(final int outputHeight) {
        this.outputHeight = outputHeight;
    }
    
    public void setWidthAndHeight(final int width, final int height) {
        this.outputWidth = width;
        this.outputHeight = height;
    }
}
