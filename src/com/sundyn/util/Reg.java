package com.sundyn.util;

public class Reg
{
    private static Register register;
    private int day;
    private String Serial;
    private int windowNum;
    private int datingNum;
    private boolean regCheck;
    private static Reg reg;
    
    public int getDay() {
        return this.day;
    }
    
    public void setDay(final int day) {
        this.day = day;
    }
    
    public String getSerial() {
        return this.Serial;
    }
    
    public void setSerial(final String serial) {
        this.Serial = serial;
    }
    
    public int getWindowNum() {
        return this.windowNum;
    }
    
    public void setWindowNum(final int windowNum) {
        this.windowNum = windowNum;
    }
    
    public int getDatingNum() {
        return this.datingNum;
    }
    
    public void setDatingNum(final int datingNum) {
        this.datingNum = datingNum;
    }
    
    public boolean isRegCheck() {
        return this.regCheck;
    }
    
    public void setRegCheck(final boolean regCheck) {
        this.regCheck = regCheck;
    }
    
    public Register getRegister() {
        return Reg.register;
    }
    
    public void setRegister(final Register register) {
        Reg.register = register;
    }
    
    public static Reg getReg() {
        return Reg.reg;
    }
    
    public static void setReg(final Reg reg) {
        Reg.reg = reg;
    }
    
    public static synchronized Reg getInstance() {
        if (Reg.reg == null) {
            Reg.reg = new Reg();
            Reg.reg.regCheck = Reg.register.regCheck();
            Reg.reg.day = Reg.register.day();
            Reg.reg.datingNum = Reg.register.datingNum();
            Reg.reg.windowNum = Reg.register.windowNum();
            Reg.reg.Serial = Reg.register.getSerial("");
        }
        return Reg.reg;
    }
    
    public static void reset() {
        Reg.reg = null;
    }
    
    public boolean register(final String data) {
        return Reg.register.register(data);
    }
}
