package com.sundyn.util;

public interface Register
{
    boolean register(final String p0);
    
    String getSerial(final String p0);
    
    int day();
    
    int windowNum();
    
    boolean isUse();
    
    int datingNum();
    
    boolean regCheck();
}
