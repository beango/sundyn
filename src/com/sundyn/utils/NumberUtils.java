package com.sundyn.utils;

public class NumberUtils
{
    public static boolean isInteger(final String value) {
        try {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isDouble(final String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isNumber(final String value) {
        return isInteger(value) || isDouble(value);
    }
    
    public static void main(final String[] arg) {
        System.out.println(isInteger("123"));
    }
}
