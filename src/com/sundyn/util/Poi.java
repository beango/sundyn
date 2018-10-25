package com.sundyn.util;

import com.xuan.xutils.utils.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Poi
{
    HSSFWorkbook wb;
    HSSFSheet sheet;
    int colnum = 0;

    public Poi() {
        this.wb = null;
        this.sheet = null;
        this.wb = new HSSFWorkbook();
        (this.sheet = this.wb.createSheet()).setAutobreaks(true);
    }

    public void addList(final List ls)
    {
        addList(ls, true);
    }

    public void addList(final List ls, boolean isauto) {
        CellStyle cellStyle = getCellStyle();
        for (int rNum = this.sheet.getLastRowNum(), i = rNum + 1; i < ls.size() + rNum + 1; ++i) {
            final Map m = (Map) ls.get(i - rNum - 1);
            final Iterator ikey = m.keySet().iterator();
            int j = 0;
            final HSSFRow r = this.sheet.createRow(i);
            r.setHeight((short)400);
            while (ikey.hasNext()) {
                final String temp = (String) ikey.next();
                final Cell c = (Cell)r.createCell(j);
                c.setCellType(CellType.STRING);
                try {
                    c.setCellValue(new HSSFRichTextString(m.get(temp).toString()));
                }
                catch (Exception e) {
                    c.setCellValue("");
                }
                c.setCellStyle(cellStyle);
                ++j;
            }
        }
        if(isauto){
            for (int i = 0; i < colnum; ++i){
                sheet.autoSizeColumn((short)i);
            }
        }
    }

    /*
    单元格样式
     */
    private CellStyle getCellStyle(){
        final HSSFCellStyle cellStyle = this.wb.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor((short)8);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor((short)8);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor((short)8);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor((short)8);
        cellStyle.setFillForegroundColor((short)44);
        return cellStyle;
    }

    /*
    表头样式
     */
    private CellStyle getHeadCellStyle(){
        final HSSFCellStyle cellStyle = this.wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor((short)8);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor((short)8);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor((short)8);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor((short)8);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        java.awt.Color color = new java.awt.Color(211,243,250) ;
        cellStyle.setFillForegroundColor((short)35);//D3F3FA
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 14); // 字体高度
        font.setFontName("黑体"); // 字体
        cellStyle.setFont(font);
        return cellStyle;
    }

    public void addListTitle(final Object[] args, final int addRnum) {
        int rNum = this.sheet.getLastRowNum();
        rNum += addRnum;
        this.colnum = args.length;
        final HSSFRow r = this.sheet.createRow(rNum);
        r.setHeight((short) 450);
        CellStyle headCellStyle = getHeadCellStyle();
        for (int i = 0; i < args.length; ++i) {
            final Cell c = (Cell)r.createCell(i);
            c.setCellType(CellType.STRING);
            c.setCellValue(new HSSFRichTextString(args[i].toString()));
            c.setCellStyle(headCellStyle);
            sheet.autoSizeColumn((short)i);
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
        addTitle(title, null, rowAddNum, cNum);
    }

    public void addTitle(final String title, String subtitle, final int rowAddNum, final int cNum) {
        final int rNum = this.sheet.getLastRowNum() + rowAddNum;
        final HSSFRow row = this.sheet.createRow(rNum);
        final HSSFCell c = row.createCell(0);
        final HSSFFont fontinfo = this.wb.createFont();
        fontinfo.setFontHeightInPoints((short)24);
        final HSSFCellStyle cellStylename = this.wb.createCellStyle();
        cellStylename.setAlignment(HorizontalAlignment.CENTER);
        if (StringUtils.isBlank(subtitle))
            c.setCellValue(title);
        else{
            row.setHeight((short)1150);
            HSSFRichTextString richString = new HSSFRichTextString(title + "\r\n" + subtitle);
            HSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short) 14); // 字体高度
            font.setFontName("黑体"); //字体
            richString.applyFont((title + "\r\n").length(), (title + "\r\n" + subtitle).length(), font);
            c.setCellValue(richString);
        }
        cellStylename.setFont(fontinfo);
        cellStylename.setWrapText(true);
        c.setCellStyle(cellStylename);
        //CellRangeAddress region1 = new CellRangeAddress(rNum, rNum, (short) 0, (short) cNum);
        //参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
        //sheet.addMergedRegion(region1);
        addMerge(rNum, (short) 0, rNum, (short) cNum);
    }

    public void addMerge(int sRow, int sCol, int eRow, int eCol){
        CellRangeAddress region1 = new CellRangeAddress(sRow, eRow, (short) sCol, (short) eCol);
        //参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号
        sheet.addMergedRegion(region1);
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
