package com.sundyn.utils;

import com.sundyn.vo.*;
import java.util.*;

public class StringUtils
{
    public static String[] getAnswer(final String string) {
        String s = "";
        String[] str = new String[0];
        if (string != null) {
            s = string.replace(";", "\uff1b");
            String strr = "";
            if (s.endsWith("\uff1b")) {
                strr = s.substring(0, string.length() - 1);
            }
            else {
                strr = s;
            }
            str = strr.split("\uff1b");
        }
        return str;
    }
    
    public static String getInString(final List<Integer> ids) {
        String str = "";
        for (int i = 0; i < ids.size(); ++i) {
            str = String.valueOf(str) + ((i == 0) ? new StringBuilder().append(ids.get(i)).toString() : ("," + ids.get(i)));
        }
        return str;
    }
    
    public static List<AnswerVo> getListAnswers(final String answerString) {
        final List<AnswerVo> list = new ArrayList<AnswerVo>();
        final String[] strs = getAnswer(answerString);
        String[] array;
        for (int length = (array = strs).length, i = 0; i < length; ++i) {
            final String s = array[i];
            final AnswerVo a = new AnswerVo();
            a.setAnswer(s);
            list.add(a);
        }
        return list;
    }
    
    public static double getDoubleRate(final int a, final int b) {
        final String c = new StringBuilder(String.valueOf(Math.round(a*1.0 / b * 1000.0))).toString();
        final StringBuilder s = new StringBuilder(c);
        s.insert(c.length() - 1, ".");
        return Double.valueOf(s.toString());
    }

    public static String getNotNullString(String orgin){
        if(orgin==null)
            return "";
        else
            return orgin;

    }
    public static void main(final String[] args) {
        final List<Integer> ids = new ArrayList<Integer>();
        ids.add(1);
        ids.add(3);
        ids.add(8);
        System.out.println(getInString(ids));
    }
}
