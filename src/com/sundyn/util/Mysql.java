package com.sundyn.util;

public class Mysql
{
    public static String mysql(String text) {
        if (text != null) {
            text = text.trim();
            text = text.replace("\\", "\\\\");
            text = text.replace("'", "\\'");
        }
        return text;
    }
    
    public static void main(final String[] args) {
        System.out.println(mysql("asdf'"));
    }
}
