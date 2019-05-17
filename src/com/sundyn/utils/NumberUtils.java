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

    public static boolean isInteger2(final String value) {
        try {
            int vale = Integer.parseInt(value);
            return vale>0;
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

    public static boolean isDouble2(final String value) {
        try {
            double val = Double.parseDouble(value);
            return value.contains(".") && val>0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isNumber(final String value) {
        return isInteger(value) || isDouble(value);
    }

    public static boolean isNumber2(final String value) {
        return isInteger2(value) || isDouble2(value);
    }

    public static void main(final String[] arg) {
        System.out.println(isInteger("123"));
    }
}
