package com.sundyn.util;

import java.util.*;

public class DateComparator implements Comparator
{
    @Override
    public int compare(final Object arg0, final Object arg1) {
        final Map m0 = (Map)arg0;
        final Map m2 = (Map)arg1;
        return m0.get("date").toString().compareTo(m2.get("date").toString());
    }
}
