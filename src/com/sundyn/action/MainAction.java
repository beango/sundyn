package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.DeptService;
import com.sundyn.service.PowerService;
import com.sundyn.util.ConfigHelper;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static com.sundyn.util.MyFile.copy;
import static org.apache.struts2.ServletActionContext.getServletContext;

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
        final Integer groupid = Integer.valueOf(UserManager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        try {
            UserDeptIds = this.deptService.findChildALLStr123(deptIdGroup);//low
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

    protected JSONObject uploadFile(String _fileType, String dir) throws MultipartException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
        MultipartFile file1 = multipartRequest.getFile("file");
        String key = multipartRequest.getParameter("key");

        String[] fileTypes = null;
        if(null!=_fileType)fileTypes = _fileType.split(",");
        JSONObject jo = new JSONObject();
        String dstPath = getServletContext().getRealPath("\\");
        // 检查form中是否有enctype="multipart/form-data"
        if (resolver.isMultipart(request)) {
            // 将request变成多部分request
            MultiPartRequestWrapper multiRequest = (MultiPartRequestWrapper) request;
            // 获取multiRequest 中所有的文件名
            File[] iter = multiRequest.getFiles("file");
            String[] nFiles = new String[iter.length],orgNames = new String[iter.length];
            for(int i=0; i<iter.length; i++) {
                File file = iter[i];
                String orgname = multiRequest.getFileNames("file")[i];
                String fileExt = orgname.substring(orgname.lastIndexOf(".") + 1).toLowerCase();
                logger.info("原始文件名:" + orgname + ",扩展名：" + fileExt);
                orgNames[i] = orgname;
                // 检查扩展名
                if (fileTypes!=null && !Arrays. asList(fileTypes).contains(fileExt)) {
                    jo.put("rst", "error");
                    jo.put("msg", "只允许上传扩展名为"+_fileType+"的图片。");
                    request.setAttribute("msg", jo.toString());
                    return jo;
                }
                String nFileName = String.valueOf(System.currentTimeMillis()) + "." + fileExt;
                String path = String.valueOf(dstPath) + dir + "/" + nFileName;
                final File dst = new File(path);
                copy(file, dst);
                nFiles[i] = dir + "/" + nFileName;
            }
            jo.put("rst", "success");
            jo.put("path", nFiles);
            jo.put("orgin", orgNames);
            return jo;
        }
        return null;
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
