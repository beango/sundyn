package com.sundyn.util;

public class ValidException extends Exception {
    public String getValidMsg() {
        return ValidMsg;
    }

    public void setValidMsg(String validMsg) {
        ValidMsg = validMsg;
    }

    private String ValidMsg = null;

    public ValidException(String str) {
        super();
        ValidMsg = str;
    }

    @Override
    public String getMessage() {
        return ValidMsg;
    }
}