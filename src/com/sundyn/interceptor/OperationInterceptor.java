package com.sundyn.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.entity.AuditLog;
import com.sundyn.entity.SysLog;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.service.IAuditLogService;
import com.sundyn.service.ManagerService;
import com.sundyn.util.impl.util;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

public class OperationInterceptor extends AbstractInterceptor {

    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    @Resource
    private IAppriesMenuService appriesMenuService;
    @Resource
    private ManagerService managerService;
    @Resource
    private IAuditLogService auditLogService;

    //无须记录Action调用日志的方法
    private static String[] unRecordLogResources={
            "getQueueTotal",
            "getQueueDeptAnysleAjax",
            "getQueueTotalOnce",
            "authDeptTree",
            "menuQueryJson",
            "deptReg",
            "authQueryJSON"
    };

    private static HashMap<String,String> actionNameMap = new HashMap<String, String>(){{
        put("managerLogin", "用户登录");
        put("managerLogout", "用户退出");
        put("hall", "大厅管理");
        put("authDeptTree", "部门组织权树");
        put("dept", "部门查询");
        put("deptEditDialog", "部门修改");
        put("serial", "业务管理");
        put("counter", "窗口管理");
        put("vip", "VIP管理");
        put("black", "黑名单管理");
        put("proxyorg", "代理人机构管理");
        put("proxy", "代理人管理");
        put("employee", "员工管理");
        put("sysdict", "参数管理");
        put("dict", "参数管理");
        put("notice", "通知公告管理");
        put("jx", "绩效管理");
        put("menu", "菜单管理");
        put("auth", "权限管理");
        put("manager", "用户管理");
        put("managerEditDialog", "用户修改");
        put("role", "角色管理");
        put("lowerPowerQuery", "角色查询");
        put("powerEditDialog", "角色修改");
        put("auditlock", "锁定管理");
        put("managerlogin", "登录日志查询");
        put("syslog", "操作日志查询");
        put("auditlogin", "安全日志查询");
        put("syslogrpt", "操作日志统计");
        put("securitylogrpt", "登录日志统计");
        put("auditlogrpt", "审计日志统计");
        put("queue", "排队数据查询");
    }
    };
    
    private String getMoudleName(String actionname){
        Iterator iter = actionNameMap.entrySet().iterator();
        while (iter.hasNext()){
            Map.Entry entry = (Map.Entry)iter.next();
            Object key = entry.getKey();
            if (actionname.toLowerCase().contains(key.toString()))
                return entry.getValue().toString();
        }
        return null;
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest req = ServletActionContext.getRequest();
        String ipaddr = util.getRemoteIpAddr();//获取系统操作远程ip
        int userid=0;
        Map UserManager = (Map)req.getSession().getAttribute("manager");
        if(null!=UserManager) {
            userid = (Integer) UserManager.get("id");
        }
        try{
            if (userid==0 && (req.getParameter("managerVo.name")!=null || req.getParameter("name")!=null)){
                String reqname = req.getParameter("managerVo.name");
                if (StringUtils.isBlank(reqname))
                    reqname = req.getParameter("name");

                Map managermap = managerService.findByNameCache(reqname);
                if(null!=managermap)
                    userid = Integer.valueOf(managermap.get("id").toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        String actionClass = invocation.getProxy().getAction().getClass().getName();
        if(actionClass.indexOf("$") != -1){
            actionClass = actionClass.substring(0, actionClass.indexOf("$"));
        }
        if(actionClass.indexOf(".") != -1){
            actionClass = actionClass.substring(actionClass.lastIndexOf(".")+1);
        }
        String actionName = invocation.getProxy().getActionName();

        String url="";
        if(StringUtils.isNotBlank(req.getQueryString())){
            url = req.getRequestURI()+"?"+URLDecoder.decode(req.getQueryString(),"UTF-8");
        }else{
            url = req.getRequestURI();
        }

        Date startTime = new Date();
        String menuName = "";

        try{
            String reqmethod = req.getMethod();
            if (reqmethod.toLowerCase().equals("get") && UserManager!=null){
                String u = req.getRequestURI().replace("/","");
                AppriesMenu menu = null;//appriesMenuService.selectOne(new EntityWrapper<AppriesMenu>().like("nav",u));
                List<AppriesMenu> allMenus = appriesMenuService.getAllCache();
                for(AppriesMenu m : allMenus){
                    if (StringUtils.isNotBlank(m.getNav()) && m.getNav().indexOf(u)>-1)
                        menu = m;
                }

                JSONArray menuStrs = new JSONArray();
                if (null!=menu){
                    if (menu.getIsnotgeneral() != null && menu.getIsnotgeneral() == 1){
                        AuditLog t = new AuditLog();
                        t.setName(UserManager.get("name").toString());
                        t.setCtime(new Date());
                        t.setIpadd(util.getRemoteIpAddr());
                        t.setLogrst("访问非常规业务");
                        t.setLogtype("访问非常规业务");
                        String logdevice = util.getRemoteBowser();
                        if (StringUtils.isBlank(logdevice) && logdevice.length()>150)
                            logdevice = logdevice.substring(0, 149);
                        t.setLogdevice(logdevice);
                        t.setLogrstdesc("用户"+UserManager.get("name")+"访问非常规业务:\"" + menu.getMenuName() + "\"");
                        auditLogService.insert(t);
                    }
                    if (menu.getIscore() != null && menu.getIscore() == 1){
                        AuditLog t = new AuditLog();
                        t.setName(UserManager.get("name").toString());
                        t.setCtime(new Date());
                        t.setIpadd(util.getRemoteIpAddr());
                        t.setLogrst("访问核心功能");
                        t.setLogtype("访问核心功能");
                        String logdevice = util.getRemoteBowser();
                        if (StringUtils.isBlank(logdevice) && logdevice.length()>150)
                            logdevice = logdevice.substring(0, 149);
                        t.setLogdevice(logdevice);
                        t.setLogrstdesc("用户"+UserManager.get("name")+"访问核心功能:\"" + menu.getMenuName() + "\"");
                        auditLogService.insert(t);
                    }
                    AppriesMenu pmenu = null; //appriesMenuService.selectOne(new EntityWrapper<AppriesMenu>().where("id={0}", menu.getParentId()));
                    for(AppriesMenu m : allMenus){
                        if (m.getId().equals(menu.getParentId()))
                            pmenu = m;
                    }
                    if (null!=pmenu){
                        JSONObject jo = new JSONObject();
                        jo.put("name", pmenu.getMenuName());
                        jo.put("url", pmenu.getNav());
                        menuStrs.add(jo);
                    }
                    JSONObject jo = new JSONObject();
                    jo.put("name", menu.getMenuName());
                    jo.put("url", menu.getNav());
                    menuName = menu.getMenuName();
                    menuStrs.add(jo);
                }
                req.setAttribute("navbar_menuname", menuStrs);//所有上级菜单，用于显示在页面面包屑位置
                req.setAttribute("Locale", req.getLocale());
            }
            //实际方法执行阶段
            invocation.invoke();
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            if(!isNoRecordResource(actionName)){
                Date endTime = new Date();
                long costTime = endTime.getTime() - startTime.getTime();
                //生成一条用户操作记录
                String param = JSONObject.fromObject(req.getParameterMap()).toString();
                if (param==null || param.length()==0)
                    param = " ";
                int len = 1000;
                while(param.length()>0){
                    SysLog log = new SysLog();
                    log.setAction(actionName);
                    log.setMoudlename(actionNameMap.containsKey(actionName)?actionNameMap.get(actionName).toString():menuName);
                    if (actionNameMap.containsKey(actionName))
                        log.setMoudlename(actionNameMap.get(actionName).toString());
                    else if (StringUtils.isNotBlank(menuName))
                        log.setMoudlename(menuName);
                    else {
                        log.setMoudlename(getMoudleName(actionName));
                    }
                    if (StringUtils.isNotBlank(req.getParameter("aname")))
                        log.setActionname(req.getParameter("aname"));
                    else
                        log.setActionname(actionNameMap.containsKey(actionName)?actionNameMap.get(actionName).toString():menuName);
                    log.setActiontime(startTime);
                    log.setActionurl(url);
                    if (param.length()>len){
                        log.setActionparam(param.substring(0,len));
                        param = param.substring(len);
                    }
                    else{
                        log.setActionparam(param);
                        param = "";
                    }
                    log.setIpaddress(ipaddr);
                    log.setManagerid(userid);
                    log.setActionmethod(req.getMethod());
                    log.setNote("处理时长：" + costTime);
                    log.insert();
                }
            }
        }
        return null;
    }

    //是否是无需记录的Action日志类型
    private  boolean isNoRecordResource(String actionName){
        for(int i=0;i<unRecordLogResources.length;i++){
            if(StringUtils.equals(actionName, unRecordLogResources[i])) {
                return true;
            }
        }
        return false;
    }
}