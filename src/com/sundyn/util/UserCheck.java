package com.sundyn.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UserCheck extends HttpServlet implements Filter
{
    private static final long serialVersionUID = 5256301363755936278L;

    public void init(final FilterConfig filtercfg) throws ServletException {
    }

    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterchain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)request;
        final HttpServletResponse resp = (HttpServletResponse)response;
        final HttpSession session = req.getSession();
        try {
            final String uri = req.getRequestURI();
            if (uri.indexOf("/js/") == 0 || uri.endsWith("login.jsp") || uri.endsWith("404.jsp")
                    || uri.endsWith("login2.jsp") || uri.endsWith("nologin.jsp") || uri.indexOf("/update/") == 0 || uri.indexOf("/upload/") == 0
            		|| uri.endsWith("managerLogin.action") || uri.endsWith("image.jsp") || uri.indexOf("image") > 0
            		|| uri.indexOf("css") > 0 || uri.endsWith("error.jsp")
            		|| uri.endsWith("js/ueditor/jsp/controller.jsp")) {

                filterchain.doFilter(request, response);
            }
            else if (uri.endsWith("/employeeUpdate.actionM7Update.bin")) {
                resp.sendRedirect("/update/M7Update.bin");
            }
            else {
                CookieUtils cookieUtils = new CookieUtils();
                cookieUtils.getCookie(req);

                final Object mm = session.getAttribute("manager");
                if (mm == null) {
                    resp.sendRedirect("login.jsp");
                }
                else {
                    filterchain.doFilter(request, response);
                }
            }
        }
        catch (Exception e) {
            resp.sendRedirect("login.jsp");
        }
    }
}
