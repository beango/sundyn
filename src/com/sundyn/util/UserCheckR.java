package com.sundyn.util;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class UserCheckR extends AbstractInterceptor
{
    public String intercept(final ActionInvocation arg0) throws Exception {
        Map session = arg0.getInvocationContext().getSession();
        Object manager = session.get("manager");

        if (manager == null){
            HttpServletRequest request = ServletActionContext.getRequest();
            CookieUtils cookieUtils = new CookieUtils();
            if (cookieUtils.getCookie(request)){
                HttpSession csession = request.getSession();
                manager = csession.getAttribute("manager");
            }
        }
        if (manager != null) {
            return arg0.invoke();
        }
        return "login";
    }
}
