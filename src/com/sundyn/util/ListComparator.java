package com.sundyn.util;

import java.util.*;

public class ListComparator implements Comparator
{
    @Override
    public int compare(final Object arg0, final Object arg1) {
        final Map m1 = (Map)arg0;
        final Map m2 = (Map)arg0;
        final int flag = m1.get("mac").toString().compareTo(m2.get("mac").toString());
        return flag;
    }
}
