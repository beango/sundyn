package com.sundyn.util.impl;

import com.xuan.xutils.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {

    public static void main(String[] args){
        //System.out.println(util.md5("FF80818169B4506F0169B4506FE10000|202cb962ac59075b964b07152d234b70|a12|a122||1"));
        try {
            //513635e449692c687b63d96b13aaaf8b   15e10adc3949ba59abbe56e057f20f883ecgscencun  593ed1de9a4e6cadebb41c3cf6bea79c
            //593ed1de9a4e6cadebb41c3cf6bea79c  nvarchar
            System.out.println(DigestUtils.md5Hex("15e10adc3949ba59abbe56e057f20f883ecgscencun岑村车管总所".getBytes("UTF-8")));//d3f8628f352650df6dd7942e11284533
            System.out.println(DigestUtils.md5Hex(("15e10adc3949ba59abbe56e057f20f883ecgscencun" + utf8Togb2312("岑村车管总所")).getBytes("ISO-8859-1")));//d3f8628f352650df6dd7942e11284533
            System.out.println(DigestUtils.md5Hex(utf8Togb2312("15e10adc3949ba59abbe56e057f20f883ecgscencun岑村车管总所")));//d3f8628f352650df6dd7942e11284533
            System.out.println(DigestUtils.md5Hex("15e10adc3949ba59abbe56e057f20f883ecgscencun"));//d3f8628f352650df6dd7942e11284533
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(gbEncoding("岑村车管总所"));
    }

    //UTF-8->GB2312
    public static String utf8Togb2312(String str){

        StringBuffer sb = new StringBuffer();

        for ( int i=0; i<str.length(); i++) {

            char c = str.charAt(i);
            switch (c) {
                case '+' :
                    sb.append( ' ' );
                    break ;
                case '%' :
                    try {
                        sb.append(( char )Integer.parseInt (
                                str.substring(i+1,i+3),16));
                    }
                    catch (NumberFormatException e) {
                        throw new IllegalArgumentException();
                    }

                    i += 2;

                    break ;

                default :

                    sb.append(c);

                    break ;

            }

        }

        String result = sb.toString();

        String res= null ;

        try {

            byte [] inputBytes = result.getBytes( "ISO-8859-1" );

            res= new String(inputBytes, "UTF-8" );

        }

        catch (Exception e){}

        return res;

    }


    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    public static String getRemoteIpAddr() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip= request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) { //"127.0.0.1".equals(ip) ||
            try {
                ip= InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip.split(",")[0];
    }

    public static String getRemoteBowser() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String Agent = request.getHeader("User-Agent");
        return Agent;
    }

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text);
        return encodeStr;
    }

    public static boolean validpwd(String str) {
        if (StringUtils.isBlank(str))
            return false;
        if (str.length()<8 || str.length()>20)
            return false;
        String regEx = "^^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*_-]+$)(?![a-zA-z\\d]+$)(?![a-zA-z!@#$%^&*_-]+$)(?![\\d!@#$%^&*_-]+$)[a-zA-Z\\d!@#$%^&*_-]+$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
