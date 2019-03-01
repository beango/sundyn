package com.sundyn.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.ISysDictinfoService;
import com.xuan.xutils.utils.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class UserCheckR extends AbstractInterceptor
{
    @Resource
    private ISysDictinfoService dictinfoService;
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public String intercept(final ActionInvocation arg0) throws Exception {
        Map session = arg0.getInvocationContext().getSession();
        HttpServletRequest req = ServletActionContext.getRequest();
        String ipaddr = getRemoteIpAddr(req);//获取系统操作远程ip
        //ipaddr = "68.36.18.226";
        SysDictinfo enableAccessIP = dictinfoService.selectOne(new EntityWrapper<SysDictinfo>().where("isEnable=1 and dictkey='enableAccessIP'"));
        if (enableAccessIP==null){
            logger.info("没有找到IP过滤参数");
        }
        else
        {
            //logger.info("访问IP：" + ipaddr + ", IP过滤参数:" + enableAccessIP.getDictvalue());
            boolean hasauth = checkAuthIp(ipaddr, enableAccessIP.getDictvalue());
            if (!hasauth)
                return "noauth";
        }
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
    private boolean checkAuthIp(String ipaddr, String enableAccessIP){
        boolean hasauth = false;
        if (StringUtils.isNotBlank(ipaddr)){
            String[] ipArr = ipaddr.split("\\.");
            if (ipArr.length==4) {
                if (ipaddr.equals("127.0.0.1"))
                    hasauth = true;
                if (ipArr[0].equals("192") && ipArr[1].equals("168"))
                    hasauth = true;
                for (String ipSplit : enableAccessIP.split(",")) {
                    if(hasauth)
                        break;
                    if (ipaddr.indexOf(ipSplit.replace("*",""))>-1)
                    {
                        hasauth = true;
                        break;
                    }
                    else{
                    }
                }
            }
            else
                hasauth = false;
        }
        return hasauth;
    }

    public static void main(String[] arg1){
        boolean hasauth = new UserCheckR().checkAuthIp("121.32.198.243", "68.36.28.*,68.36.18.*");
        System.out.println("结果：" + hasauth);
    }

    protected String getRemoteIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip= request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) { //"127.0.0.1".equals(ip) ||
            try {
                ip= InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip.split(",")[0];
    }
}
