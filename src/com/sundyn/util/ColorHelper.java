package com.sundyn.util;

public class ColorHelper
{
    public static String getColor() {
        final StringBuffer stb = new StringBuffer();
        for (int i = 0; i < 6; ++i) {
            final String s = new StringBuilder(String.valueOf(Math.random() * 16.0)).toString();
            final String indexStr = s.substring(0, s.indexOf("."));
            if (indexStr.equals("10")) {
                stb.append("A");
            }
            else if (indexStr.equals("11")) {
                stb.append("B");
            }
            else if (indexStr.equals("12")) {
                stb.append("C");
            }
            else if (indexStr.equals("13")) {
                stb.append("D");
            }
            else if (indexStr.equals("14")) {
                stb.append("E");
            }
            else if (indexStr.equals("15")) {
                stb.append("F");
            }
            else {
                stb.append(indexStr);
            }
        }
        return stb.toString();
    }
    
    public static void main(final String[] args) {
        getColor();
    }
}
