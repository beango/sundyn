package com.sundyn.utils;

public class ReqUtils {
    public static String format(String s){
        //String reg = "[/x00/-//x08/x0b/-//x0c//x0e/-//x1f]";//过滤掉非法字符
        if (s == null )
            return "";
        else{
            s=s.replaceAll("&","&amp;").replaceAll("<","&lt;")
                    .replaceAll(">","&gt;").replaceAll("/","&quot;")
                    .replaceAll(";","")
                    .replaceAll(",","")
                    .replaceAll("=","")
                    .replaceAll("\\(","")
                    .replaceAll("\\)","")
                    .replaceAll("'","");
            //.replaceAll(reg,"");;
            return s;
        }
    }

    public static void main(String[] ar){
        System.out.println(format("123>;'=a(n)"));
    }
}
