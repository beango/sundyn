package com.sundyn.util;

import org.jfree.data.general.*;
import org.jfree.chart.*;
import java.util.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;
import java.io.*;

public class JfreeChart
{
    public static void main(final String[] args) throws Exception {
        final Map m = new HashMap();
        m.put("key0", 10);
        m.put("key1", 20);
        m.put("key2", 25);
        m.put("key3", 30);
        m.put("key4", 40);
        m.put("key5", 50);
        m.put("key6", 60);
        final Map demo = new HashMap();
        demo.put("key0", "\u4e2d\u56fd");
        demo.put("key1", "\u65e5");
        demo.put("key2", "\u67dc");
        demo.put("key3", "\u4e2d");
        demo.put("key4", "\u80af");
        demo.put("key5", "\u5728");
        demo.put("key6", "\u4ee5");
        try {
            createPie("\u54c8\u54c8", m, demo, "d:/d.png");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void createPie(final String title, final Map m, final Map demo, final String path) throws Exception {
        if (m != null) {
            final DefaultPieDataset dataset = new DefaultPieDataset();
            if (m.get("key0") != null && demo.get("key0") != null) {
                dataset.setValue((Comparable)demo.get("key0").toString(), (Number)Double.valueOf(m.get("key0").toString()));
            }
            if (m.get("key1") != null && demo.get("key1") != null) {
                dataset.setValue((Comparable)demo.get("key1").toString(), (Number)Double.valueOf(m.get("key1").toString()));
            }
            if (m.get("key2") != null && demo.get("key2") != null) {
                dataset.setValue((Comparable)demo.get("key2").toString(), (Number)Double.valueOf(m.get("key2").toString()));
            }
            if (m.get("key3") != null && demo.get("key3") != null) {
                dataset.setValue((Comparable)demo.get("key3").toString(), (Number)Double.valueOf(m.get("key3").toString()));
            }
            if (m.get("key4") != null && demo.get("key4") != null) {
                dataset.setValue((Comparable)demo.get("key4").toString(), (Number)Double.valueOf(m.get("key4").toString()));
            }
            if (m.get("key5") != null && demo.get("key5") != null) {
                dataset.setValue((Comparable)demo.get("key5").toString(), (Number)Double.valueOf(m.get("key5").toString()));
            }
            if (m.get("key6") != null && demo.get("key6") != null) {
                dataset.setValue((Comparable)demo.get("key6").toString(), (Number)Double.valueOf(m.get("key6").toString()));
            }
            final JFreeChart chart = ChartFactory.createPieChart(title, (PieDataset)dataset, true, false, false);
            try {
                OutputStream fos_png = null;
                fos_png = new FileOutputStream(path);
                ChartUtilities.writeChartAsPNG(fos_png, chart, 500, 400);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void createBar(final String title, final String x, final String y, final List list, final String path) {
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                final Map m = (Map) list.get(i);
                dataset.setValue((Number)Double.valueOf(m.get("num").toString()), (Comparable)m.get("category").toString(), (Comparable)m.get("item").toString());
            }
        }
        final JFreeChart chart = ChartFactory.createBarChart(title, x, y, (CategoryDataset)dataset, PlotOrientation.VERTICAL, false, true, false);
        try {
            ChartUtilities.saveChartAsJPEG(new File(path), chart, 500, 300);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
