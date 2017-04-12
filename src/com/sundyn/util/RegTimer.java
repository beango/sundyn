package com.sundyn.util;

import java.util.*;

public class RegTimer extends TimerTask
{
    @Override
    public void run() {
        System.out.println("==");
        Reg.reset();
    }
}
