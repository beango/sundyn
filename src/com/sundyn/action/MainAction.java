package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.DeptService;
import com.sundyn.service.PowerService;
import com.sundyn.util.ConfigHelper;
import com.sundyn.util.EhCacheHelper;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Map;

public class MainAction extends ActionSupport {
    private static Logger logger = Logger.getLogger("ManagerService");
    private DeptService deptService;
    private PowerService powerService;

    private HttpServletRequest request;
    private Map UserManager = null;
    private String UserName = null;
    protected String UserDeptIds = null;
    protected int pageSize = 20;

    public String getUserDeptIds() {
        String KEY_FINDMANAGERBY= "com.sundyn.action.MainAction:getUserDeptIds";
        KEY_FINDMANAGERBY = KEY_FINDMANAGERBY + "_" + UserName;
        Object data = EhCacheHelper.getCache(KEY_FINDMANAGERBY);
        if (data != null ){
            logger.info("getUserDeptIds获取登录用户部门列表");
            return (String)data;
        }
        logger.info("getUserDeptIds获取登录用户部门列表失败,从数据库获取");
        final Integer groupid = Integer.valueOf(UserManager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        try {
            UserDeptIds = this.deptService.findChildALLStr123(deptIdGroup);//low
            if(null!=UserDeptIds)
                EhCacheHelper.putCache(KEY_FINDMANAGERBY, UserDeptIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserDeptIds;
    }

    public MainAction(){
        request = ServletActionContext.getRequest();
        UserManager = (Map)request.getSession().getAttribute("manager");
        if(null!=UserManager)
            UserName = (String) UserManager.get("name");
        try{
            pageSize = Integer.valueOf(ConfigHelper.getValue("PageSizeDef"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init(){
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        powerService = (PowerService)ctx.getBean("powerService");
        deptService = (DeptService)ctx.getBean("deptService");

        final Integer groupid = Integer.valueOf(UserManager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        try {
            UserDeptIds = this.deptService.findChildALLStr123(deptIdGroup);//low
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public enum ENUM_DEPTTYPE{
        DEPT("部门",2),
        DATING("大厅",1),
        EMPLOYEE("人员",0);
        // 成员变量
        private String name;
        private int val;
        // 构造方法
        private ENUM_DEPTTYPE(String name, int index) {
            this.name = name;
            this.val = index;
        }
        // 普通方法
        public static String getName(int index) {
            for (ENUM_DEPTTYPE c : ENUM_DEPTTYPE.values()) {
                if (c.getValue() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // get set 方法
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getValue() {
            return val;
        }
        public void setIndex(int index) {
            this.val = index;
        }
        }
}
