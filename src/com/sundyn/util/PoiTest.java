package com.sundyn.util;

import java.io.*;
import java.util.*;

public class PoiTest
{
    public static void main(final String[] args) throws Exception {
        final List ls = new ArrayList();
        for (int i = 0; i < 10; ++i) {
            final Map<String, String> m = new HashMap<String, String>();
            for (int j = 0; j < 10; ++j) {
                String v = "";
                v = String.valueOf(v) + i + j;
                m.put(v, v);
            }
            ls.add(m);
        }
        Poi poi = new Poi();
        poi.addTitle("this is a test", 0, 4);
        poi.addList(ls);
        poi.addTitle("this is a test", 2, 5);
        poi.addPic(new File("C:/Documents and Settings/Administrator/\u684c\u9762/\u6295\u7968.jpg"), 2, 0);
        poi.createFile("d:/abc.xls");
        String abc = "\t\t\t\t\t\t\t\t\tabc\n";
        abc = String.valueOf(abc) + "abc\tabc\tabc\tabc\tabc\tabc\n";
        abc = String.valueOf(abc) + "abc\tabc\tabc\tabc\tabc\tabc\n";
        final String[] temp = abc.split("\n");
        final List lss = new ArrayList();
        for (int k = 0; k < temp.length; ++k) {
            final String[] smallTemp = temp[k].split("\t");
            final Map l = new HashMap();
            for (int j2 = 0; j2 < smallTemp.length; ++j2) {
                l.put(new StringBuilder(String.valueOf(Math.random())).toString(), smallTemp[j2]);
            }
            lss.add(l);
        }
        poi = new Poi();
        poi.addList(lss);
        poi.createFile("d:/a.xls");
    }
}
