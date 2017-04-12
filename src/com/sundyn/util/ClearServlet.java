package com.sundyn.util;

import javax.servlet.http.*;

public class ClearServlet extends HttpServlet
{
    public void init() {
        EmployeeList.getInstance(0);
        M7List.getInstance(0);
    }
}
