package com.sundyn.interceptor;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.entity.SysLog;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.service.ManagerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OperationInterceptor extends AbstractInterceptor {

    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();
    @Resource
    private IAppriesMenuService appriesMenuService;
    @Resource
    private ManagerService managerService;
    //无须记录Action调用日志的方法
    private static String[] unRecordLogResources={"validateTeaSnapNum",//校验教师创建的快照数量是否超过场景限制
            "validateServSnapNum",//校验应用服务创建的快照数量是否超过场景限制
            "getRoleList",//获取角色列表
            "initTaeCfg",//初始化登录配置
            "getIdvEnable",//IDV功能是否开启
            "getSysOpLogListByPage",//查询系统操作日志
            "getSysOpType",//得到系统操作方法
            "getStaticSwitch",//获取统计页面的开关状态
            "init",//登录初始化,
            "initEnv",//初始化登录环境
            "getInitUrl",//初始化登录环境
            "getAllUrl",//获取线上请求地址url
            "initCfgInfo",//正常登录
            "getURLfromConf",//获取配置文件信息
            "loginStatus",//检测用户登录状态
            "localAccountValidate",//本地账号验证模式
            "verifyLocal",//本地登录账号校验
            "verifyRemoteByQQ",//第三方登录远程同步并返回用户角色
            "verifyRemote",//用户登录同步到本地
            "buildTerLoginRelation"//添加终端登录关系
    };

    private static HashMap<String,String> actionNameMap = new HashMap<String, String>(){{
            put("managerLogin", "用户登录");
            put("managerLogout", "用户退出"); }
    };

    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest req = ServletActionContext.getRequest();
        String ipaddr = getRemoteIpAddr(req);//获取系统操作远程ip
        int userid=0;
        Map UserManager = (Map)req.getSession().getAttribute("manager");
        if(null!=UserManager) {
            userid = (Integer) UserManager.get("id");
        }
        else
            System.out.println("usermanager==null");
        try{
            if (userid==0 && req.getParameter("managerVo.name")!=null){
                System.out.println("没有便用户ID" + req.getParameter("managerVo.name"));
                Map managermap = managerService.findByName(req.getParameter("managerVo.name"));
                userid = Integer.valueOf(managermap.get("id").toString());
            }
        }
        catch (Exception e){

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
            if (reqmethod.toLowerCase().equals("get")){
                String u = req.getRequestURI().replace("/","");
                AppriesMenu menu = appriesMenuService.selectOne(new EntityWrapper<AppriesMenu>().like("nav",u));
                JSONArray menuStrs = new JSONArray();
                if (null!=menu){
                    AppriesMenu pmenu = appriesMenuService.selectOne(new EntityWrapper<AppriesMenu>().where("id={0}", menu.getParentId()));
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
                req.setAttribute("navbar_menuname", menuStrs);
            }
            //实际方法执行阶段
            invocation.invoke();
        } catch(Exception exception){
            exception.printStackTrace();
        }finally{
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
                //sysOperationLogManager.saveSysOperationLog(userName,actionClass,actionName,url,ipaddr,startTime,endTime,costTime,SysOpConstant.SYS_LOG_ACTION);
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

        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                ip= InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ip.split(",")[0];
    }
}