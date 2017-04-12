package com.sundyn.util;

import org.apache.poi.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import java.io.*;

public class Poi
{
    HSSFWorkbook wb;
    HSSFSheet sheet;
    
    public Poi() {
        this.wb = null;
        this.sheet = null;
        this.wb = new HSSFWorkbook();
        (this.sheet = this.wb.createSheet()).setAutobreaks(true);
    }
    
    public void addList(final List ls) {
        for (int rNum = this.sheet.getLastRowNum(), i = rNum + 1; i < ls.size() + rNum + 1; ++i) {
            final Map m = (Map) ls.get(i - rNum - 1);
            final Iterator ikey = m.keySet().iterator();
            int j = 0;
            final HSSFRow r = this.sheet.createRow(i);
            while (ikey.hasNext()) {
                final String temp = (String) ikey.next();
                try {
                    final Cell c = (Cell)r.createCell(j);
                    c.setCellValue(m.get(temp).toString());
                    final HSSFCellStyle cellStylename = this.wb.createCellStyle();
                    cellStylename.setAlignment((short)2);
                    cellStylename.setBorderBottom((short)1);
                    cellStylename.setBottomBorderColor((short)8);
                    cellStylename.setBorderLeft((short)1);
                    cellStylename.setLeftBorderColor((short)8);
                    cellStylename.setBorderRight((short)1);
                    cellStylename.setRightBorderColor((short)8);
                    cellStylename.setBorderTop((short)1);
                    cellStylename.setTopBorderColor((short)8);
                    cellStylename.setFillForegroundColor((short)44);
                    cellStylename.setFillPattern((short)1);
                    c.setCellStyle((CellStyle)cellStylename);
                }
                catch (Exception e) {
                    final Cell c2 = (Cell)r.createCell(j);
                    c2.setCellValue("");
                    final HSSFCellStyle cellStylename2 = this.wb.createCellStyle();
                    cellStylename2.setAlignment((short)2);
                    cellStylename2.setBorderBottom((short)1);
                    cellStylename2.setBottomBorderColor((short)8);
                    cellStylename2.setBorderLeft((short)1);
                    cellStylename2.setLeftBorderColor((short)8);
                    cellStylename2.setBorderRight((short)1);
                    cellStylename2.setRightBorderColor((short)8);
                    cellStylename2.setBorderTop((short)1);
                    cellStylename2.setTopBorderColor((short)8);
                    cellStylename2.setFillForegroundColor((short)44);
                    cellStylename2.setFillPattern((short)1);
                    c2.setCellStyle((CellStyle)cellStylename2);
                }
                ++j;
            }
        }
    }
    
    public void addListTitle(final String[] args, final int addRnum) {
        int rNum = this.sheet.getLastRowNum();
        rNum += addRnum;
        final HSSFRow r = this.sheet.createRow(rNum);
        for (int i = 0; i < args.length; ++i) {
            final Cell c = (Cell)r.createCell(i);
            c.setCellValue(args[i]);
            final HSSFCellStyle cellStylename = this.wb.createCellStyle();
            cellStylename.setAlignment((short)2);
            cellStylename.setBorderBottom((short)1);
            cellStylename.setBottomBorderColor((short)8);
            cellStylename.setBorderLeft((short)1);
            cellStylename.setLeftBorderColor((short)8);
            cellStylename.setBorderRight((short)1);
            cellStylename.setRightBorderColor((short)8);
            cellStylename.setBorderTop((short)1);
            cellStylename.setTopBorderColor((short)8);
            cellStylename.setFillForegroundColor((short)35);
            cellStylename.setFillPattern((short)1);
            c.setCellStyle((CellStyle)cellStylename);
        }
    }
    
    public void addPic(final File f, final int rowAddNum, final int cNum) throws Exception {
        final int rNum = this.sheet.getLastRowNum() + rowAddNum;
        final InputStream is = new FileInputStream(f);
        final byte[] bytes = IOUtils.toByteArray(is);
        final int pictureIdx = this.wb.addPicture(bytes, 5);
        is.close();
        final CreationHelper helper = this.wb.getCreationHelper();
        final Drawing drawing = (Drawing)this.sheet.createDrawingPatriarch();
        final ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(cNum);
        anchor.setRow1(rNum);
        final Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();
    }
    
    public void addTitle(final String title, final int rowAddNum, final int cNum) {
        final int rNum = this.sheet.getLastRowNum() + rowAddNum;
        final HSSFRow row = this.sheet.createRow(rNum);
        final HSSFCell c = row.createCell(cNum);
        final HSSFFont fontinfo = this.wb.createFont();
        fontinfo.setFontHeightInPoints((short)24);
        final HSSFCellStyle cellStylename = this.wb.createCellStyle();
        cellStylename.setAlignment((short)2);
        cellStylename.setFont(fontinfo);
        c.setCellStyle(cellStylename);
        c.setCellValue(title);
    }
    
    public void addText(final String text) {
        final String[] temp = text.split("\n");
        final List lss = new ArrayList();
        for (int i = 0; i < temp.length; ++i) {
            final String[] smallTemp = temp[i].split("\t");
            final List l = new ArrayList();
            for (int j = 0; j < smallTemp.length; ++j) {
                l.add(smallTemp[j]);
            }
            lss.add(l);
        }
        this.addListList(lss);
    }
    
    public void addListList(final List ls) {
        for (int rNum = this.sheet.getLastRowNum(), i = rNum + 1; i < ls.size() + rNum + 1; ++i) {
            final List l = (List) ls.get(i - rNum - 1);
            final HSSFRow r = this.sheet.createRow(i);
            for (int k = 0; k < l.size(); ++k) {
                final Cell c = (Cell)r.createCell(k);
                c.setCellValue(l.get(k).toString());
            }
        }
    }
    
    public void addBusinessList(final List ls) {
        int rNum = this.sheet.getLastRowNum();
        ++rNum;
        for (int i = 0; i < ls.size(); ++i) {
            final Map temp = (Map) ls.get(i);
            HSSFRow row = this.sheet.createRow(rNum);
            HSSFCell c = row.createCell(0);
            c.setCellValue(String.valueOf(i + 1) + "\u3001" + temp.get("businessName").toString());
            ++rNum;
            row = this.sheet.createRow(rNum);
            c = row.createCell(0);
            c.setCellValue("A\u3001\u5f88\u6ee1\u610f");
            c = row.createCell(1);
            c.setCellValue("B\u3001\u6ee1\u610f");
            c = row.createCell(2);
            c.setCellValue("C\u3001\u4e00\u642c");
            c = row.createCell(3);
            c.setCellValue("D\u3001\u4e0d\u6ee1\u610f");
            c = row.createCell(4);
            c.setCellValue("E\u3001\u5f88\u4e0d\u6ee1\u610f");
            ++rNum;
        }
    }
    
    public void addBusinessListTitle(final List ls) {
        int rNum = this.sheet.getLastRowNum();
        ++rNum;
        HSSFRow row = this.sheet.createRow(rNum);
        HSSFCell c = row.createCell(0);
        c.setCellValue("\u4f8b\u5b50:\u7167\u7740\u4e0b\u9762\u4f8b\u5b50\u5199");
        ++rNum;
        row = this.sheet.createRow(rNum);
        int i;
        for (i = 0; i < ls.size(); ++i) {
            final Map temp = (Map) ls.get(i);
            c = row.createCell(i);
            c.setCellValue(temp.get("businessName").toString());
        }
        c = row.createCell(i);
        c.setCellValue("\u8c03\u67e5\u4eba\u5458\u5361\u53f7");
        c = row.createCell(++i);
        c.setCellValue("\u5df2\u7ecf\u6dfb\u52a0\u7684MAC\u5730\u5740");
        c = row.createCell(++i);
        c.setCellValue("\u65e5\u671f");
        final int cNum = row.getLastCellNum();
        ++rNum;
        row = this.sheet.createRow(rNum);
        for (i = 0; i < cNum - 1 - 1; ++i) {
            c = row.createCell(i);
            c.setCellValue(3.0);
        }
        c = row.createCell(cNum - 3);
        c.setCellValue(1253.0);
        c = row.createCell(cNum - 2);
        c.setCellValue("0023AEA0D4E8");
        c = row.createCell(cNum - 1);
        c.setCellValue("2010-01-01 12:12:12");
        ++rNum;
        ++rNum;
        ++rNum;
        row = this.sheet.createRow(rNum);
        for (i = 0; i < ls.size(); ++i) {
            final Map temp2 = (Map) ls.get(i);
            c = row.createCell(i);
            c.setCellValue(temp2.get("businessName").toString());
        }
        c = row.createCell(row.getLastCellNum() + 1);
        c.setCellValue("\u8c03\u67e5\u4eba\u5458\u5361\u53f7");
        row = this.sheet.createRow(rNum);
        for (i = 0; i < ls.size(); ++i) {
            final Map temp2 = (Map) ls.get(i);
            c = row.createCell(i);
            c.setCellValue(temp2.get("businessName").toString());
        }
        c = row.createCell(i);
        c.setCellValue("\u8c03\u67e5\u4eba\u5458\u5361\u53f7");
        c = row.createCell(++i);
        c.setCellValue("\u5df2\u7ecf\u6dfb\u52a0\u7684MAC\u5730\u5740");
        c = row.createCell(++i);
        c.setCellValue("\u65e5\u671f");
    }
    
    public void createFile(final String file) throws Exception {
        int n = this.sheet.getLastRowNum();
        n = this.sheet.getRow(n).getLastCellNum();
        for (int i = 0; i < n; ++i) {}
        final FileOutputStream fileOut = new FileOutputStream(file);
        this.wb.write((OutputStream)fileOut);
        fileOut.close();
    }
}
