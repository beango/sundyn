package com.sundyn.util;

import com.opensymphony.xwork2.interceptor.*;
import com.opensymphony.xwork2.*;
import java.util.*;

public class UserCheckR extends AbstractInterceptor
{
    public String intercept(final ActionInvocation arg0) throws Exception {
        final Map session = arg0.getInvocationContext().getSession();
        final Object manager = session.get("manager");
        if (manager != null) {
            return arg0.invoke();
        }
        return "login";
    }
}
