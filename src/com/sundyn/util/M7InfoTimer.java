package com.sundyn.util;

import java.util.*;

public class M7InfoTimer extends TimerTask
{
    private int index;
    
    public M7InfoTimer() {
        this.index = 0;
    }
    
    @Override
    public void run() {
        new Path();
        final String path = Path.getRootPath();
        try {
            if (this.index++ > 0) {
                final M7Info info = M7Info.getInstance();
                M7Info.Save();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
