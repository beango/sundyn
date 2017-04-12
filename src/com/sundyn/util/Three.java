package com.sundyn.util;

import java.util.*;
import java.io.*;

public class Three
{
    List<String[]> ls;
    File f;
    
    public Three(final File file) throws Exception {
        this.ls = new ArrayList<String[]>();
        this.Dtest(file);
    }
    
    public Three(final String filePath) throws Exception {
        this.ls = new ArrayList<String[]>();
        final File f = new File(filePath);
        this.Dtest(f);
    }
    
    public void Dtest(final File file) throws Exception {
        this.f = file;
        final BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = "";
        while (br.ready()) {
            line = br.readLine();
            final String[] s_array = line.split("\t");
            this.ls.add(s_array);
        }
    }
    
    public String[] getTableTitle() {
        if (this.ls.size() > 0) {
            final String[] temp = this.ls.get(0);
            if (temp[0].equals("\u4e1a\u52a1\u6d41\u6c34\u53f7")) {
                return temp;
            }
        }
        return null;
    }
    
    public List<String[]> getTableData() {
        final List<String[]> temp = new ArrayList<String[]>();
        for (int i = 0; i < this.ls.size(); ++i) {
            final String[] temp_array = this.ls.get(i);
            if (!temp_array[0].equals("\u4e1a\u52a1\u6d41\u6c34\u53f7")) {
                String s_temp = temp_array[8];
                if (s_temp.startsWith(",")) {
                    s_temp = s_temp.substring(1);
                }
                s_temp = String.valueOf(s_temp.substring(0, 4)) + "-" + s_temp.substring(4, 6) + "-" + s_temp.substring(6, 8) + " " + s_temp.substring(8, 10) + ":" + s_temp.substring(10, 12) + ":" + s_temp.substring(12, 14);
                temp_array[8] = s_temp;
                temp.add(temp_array);
            }
        }
        return temp;
    }
    
    public static void main(final String[] args) throws Exception {
        final Three ds = new Three("C:\\Documents and Settings\\Administrator\\\u684c\u9762\\abc.txt");
        ds.getTableData();
    }
}
