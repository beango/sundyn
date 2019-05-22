package com.sundyn.util;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.ISysDictinfoService;
import com.sundyn.service.ManagerService;
import com.sundyn.util.impl.util;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCheckR extends AbstractInterceptor
{
    @Resource
    private ISysDictinfoService dictinfoService;
    @Resource
    private ManagerService managerService;
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    public String intercept(final ActionInvocation arg0) throws Exception {
        //HttpSession session = arg0.getInvocationContext().getre.get.getSession();
        HttpSession session = ServletActionContext.getRequest().getSession();
        String ipaddr = util.getRemoteIpAddr();//获取系统操作远程ip
        //ipaddr = "68.36.18.226";

        List<SysDictinfo> allData = dictinfoService.getAllCache();

        Object seO = session.getAttribute("sysversion");

        if (null != seO) {
        }
        else {
            for(SysDictinfo m : allData){
                if (m.getDictkey().equalsIgnoreCase("sysversion")){
                    seO = m.getDictvalue();
                    session.setAttribute("sysversion", seO);
                }
            }
        }
        ServletActionContext.getRequest().setAttribute("sysversion", seO);

        SysDictinfo enableAccessIP = null;
        for(SysDictinfo m : allData){
            if (m.getIsenable() && m.getDictkey().equalsIgnoreCase("enableAccessIP"))
                enableAccessIP = m;
        }
        //SysDictinfo enableAccessIP = dictinfoService.selectOne(new EntityWrapper<SysDictinfo>().where("isEnable=1 and dictkey='enableAccessIP'"));
        if (enableAccessIP==null){
            logger.info("没有找到IP过滤参数");
        }
        else
        {
            boolean hasauth = checkAuthIp(ipaddr, enableAccessIP.getDictvalue());
            if (!hasauth){
                System.out.println("IP访问限制");
                ServletActionContext.getRequest().setAttribute("loginmsg","IP访问限制");
                return "noauth";
            }
        }
        Map manager = (Map)session.getAttribute("manager");
        if (manager == null){
            System.out.println("登录超时");
            ServletActionContext.getRequest().setAttribute("loginmsg","登录超时");
            rollbacklogin();
            return "login";
            /*HttpServletRequest request = ServletActionContext.getRequest();
            CookieUtils cookieUtils = new CookieUtils();
            if (cookieUtils.getCookie(request)){
                HttpSession csession = request.getSession();
                manager = (Map)csession.getAttribute("manager");
            }*/
        }
        if (manager != null) {
            String accessip = manager.get("accessip")==null?"":manager.get("accessip").toString();
            if (StringUtils.isNotBlank(accessip)){
                boolean hasauth = checkAuthIp(ipaddr, accessip);
                if (!hasauth){
                    System.out.println("该账号不允许在该终端登录");
                    ServletActionContext.getRequest().setAttribute("msg","该账号不允许在该终端登录");
                    rollbacklogin();
                    //session.removeAttribute("manager");
                    return "noauth";
                }
            }

            Map user = managerService.findByNameCache(manager.get("name").toString());
            if (user!=null && user.get("logindevice").toString().equals(manager.get("logindevice").toString())
                    && DateUtils.compareIgnoreSecond(DateUtils.string2DateTime(user.get("logintime").toString()),
                    DateUtils.string2DateTime(manager.get("logintime").toString()) )==0
                    && user.get("loginadd").toString().equals(manager.get("loginadd").toString()))
            {

            }
            else {
                System.out.println("该账号已在其他地方登录");
                ServletActionContext.getRequest().setAttribute("msg","该账号已在其他地方登录");
                rollbacklogin();
                //session.removeAttribute("manager");
                return "noauth";
            }
            return arg0.invoke();
        }
        return "login";
    }

    private boolean checkAuthIp(String ipaddr, String enableAccessIP){
        boolean hasauth = false;
        if (StringUtils.isNotBlank(ipaddr)){
            String[] ipArr = ipaddr.split("\\.");
            if (ipArr.length==4) {
                if (ipaddr.equals("127.0.0.1") || ipaddr.equals("127.0.1.1"))
                    hasauth = true;
                //if (ipArr[0].equals("192") && ipArr[1].equals("168"))
                //    hasauth = true;
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

        String accessip = "192.168.1.103";
        System.out.println("启用IP地址绑定：" + accessip);
            hasauth = new UserCheckR().checkAuthIp("192.168.1.55", accessip);
            System.out.println(hasauth);
    }

    public void rollbacklogin() {
        try{
            final HttpServletRequest request = ServletActionContext.getRequest();
            ServletContext context = ServletActionContext.getServletContext();
            final HttpSession session = request.getSession();
            Object manager = session.getAttribute("manager");
            if (null!= manager){
                ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
                if (null != map)
                {
                    session.invalidate();
                    Map managermap = (Map)manager;
                    if (map.containsKey(managermap.get("name")))
                    {
                        map.remove(managermap.get("name"));
                        context.setAttribute("activeSessions", map);
                    }
                }
            }
            else
                session.invalidate();

            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
