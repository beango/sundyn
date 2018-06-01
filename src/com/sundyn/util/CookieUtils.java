package com.sundyn.util;

import com.sundyn.service.ManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

public class CookieUtils {
    public static final String USER_COOKIE = "user.cookie";

    // 添加一个cookie
    public Cookie addCookie(Map user) {
        Cookie cookie = new Cookie(USER_COOKIE, user.get("name") + ","
                + user.get("password"));
        System.out.println("添加cookie");
        cookie.setMaxAge(60 * 60 * 24 * 14);// cookie保存两周
        return cookie;
    }

    // 得到cookie
    public boolean getCookie(HttpServletRequest request) {
        //ApplicationContext ac = new ClassPathXmlApplicationContext("/WEB-INF/applicationContext-service.xml");
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        ManagerService managerService = (ManagerService)ctx.getBean("managerService");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CookieUtils.USER_COOKIE.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        String[] split = value.split(",");
                        String username = split[0];
                        String password = split[1];
                        System.out.println("cookie: ////////////////////\\" + username + ", " + password);
                        try {
                            Map user = managerService.findManageBy(username, password);
                            System.out.println("添加用户到session中：" + (user == null));
                            HttpSession session = request.getSession();
                            if(user == null)
                                return false;
                            else {
                                session.setAttribute("manager", user);// 添加用户到session中
                                System.out.println("成功添加用户到session中");
                            }
                            return true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
                        /*User user = userDAO.checkUser(username, password);
                        if (user != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute(LoginAction.USER_SESSION, user);// 添加用户到session中
                            return true;
                        }*/
                    }
                }
            }
        }
        return false;
    }

    // 删除cookie
    public Cookie delCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (USER_COOKIE.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    return cookie;
                }
            }
        }
        return null;
    }
}
