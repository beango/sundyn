package com.sundyn.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.service.DeptService;
import com.sundyn.service.IAppriesManagerpowerService;
import com.sundyn.service.ManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CookieUtils {
    public static final String USER_COOKIE = "user.cookie";
    public static final String USER_COOKIE2= "user.cookie2";
    public Cookie addCookie(Map user) {
        Cookie cookie = new Cookie(USER_COOKIE, user.get("name") + "," + user.get("password") + "," + user.get("powers"));
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
    public Cookie addCookie2(String value) {
        Cookie cookie = new Cookie(USER_COOKIE2, value);
        cookie.setMaxAge(60 * 60 * 24);
        return cookie;
    }
    // 得到cookie
    public boolean getCookie(HttpServletRequest request) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        ManagerService managerService = (ManagerService)ctx.getBean("managerService");
        DeptService deptService = (DeptService) ctx.getBean("deptService");
        IAppriesManagerpowerService managerPowerService = (IAppriesManagerpowerService)ctx.getBean("managerpowerService");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CookieUtils.USER_COOKIE.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        String[] split = value.split(",");
                        String username = split[0];
                        String password = split[1];
                        try {
                            Map user = managerService.findManageBy(username, password,null);
                            List<AppriesManagerpower> managerPowers = managerPowerService.selectListEx(new EntityWrapper<AppriesManagerpower>().where("managerid={0}", user.get("id")));
                            String cookieMapPowerStr = "";
                            if (null!=managerPowers && managerPowers.size()>0){
                                for (AppriesManagerpower powr : managerPowers){
                                    cookieMapPowerStr += "|" + powr.getPowerName();
                                }
                                cookieMapPowerStr = cookieMapPowerStr.substring(1);
                            }
                            user.put("powers", cookieMapPowerStr);
                            String DEPTIDS = deptService.findChildALLStr1234(null);
                            user.put("deptids", DEPTIDS);
                            HttpSession session = request.getSession();
                            if(user == null)
                                return false;
                            else {
                                session.setAttribute("manager", user);// 添加用户到session中
                            }
                            return true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                            return false;
                        }
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
