package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.service.DeptService;
import com.sundyn.service.IAppriesManagerpowerService;
import com.sundyn.service.ManagerService;
import com.sundyn.service.PowerService;
import com.sundyn.util.CookieUtils;
import com.sundyn.util.Mysql;
import com.sundyn.util.Pager;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.cache.Cache;
import com.xuan.xutils.cache.CacheManager;
import com.xuan.xutils.cache.DefaultCacheManager;
import com.xuan.xutils.cache.provider.SimpleCache;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerAction extends MainAction
{
    @Resource
    private IAppriesManagerpowerService managerpowerService;

    private List list;
    private ManagerService managerService;
    private DeptService deptService;
    private ManagerVo managerVo;
    private String msg,type;
    private Pager pager;

    private PowerService powerService;

    public DeptService getDeptService() {
        return this.deptService;
    }

    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }

    public List getList() {
        return this.list;
    }

    public ManagerService getManagerService() {
        return this.managerService;
    }

    public ManagerVo getManagerVo() {
        return this.managerVo;
    }

    public String getMsg() {
        return this.msg;
    }

    public Pager getPager() {
        return this.pager;
    }

    public PowerService getPowerService() {
        return this.powerService;
    }

    public String managerAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = req.getString("name");
        int dept = req.getInt("dept", 0);
        (this.managerVo = new ManagerVo()).setSkinid(0);
        this.managerVo.setName(name);
        String powers = req.getString("powers");
        name = Mysql.mysql(name);
        if (this.managerService.manageExist(null, name)) {
            this.msg = "";
        }
        else {
            this.msg = "该用户已经存在";
            return "error";
        }

        this.managerVo.setRealname(request.getParameter("realname"));
        this.managerVo.setPassword("123456");
        final int userGroupId = Integer.valueOf(request.getParameter("userGroupId"));
        this.managerVo.setUserGroupId(userGroupId);
        this.managerVo.setRemark(request.getParameter("remark"));
        this.managerVo.setExt1(request.getParameter("ext1"));
        this.managerVo.setExt2(request.getParameter("ext2"));
        this.managerVo.setDeptid(dept);
        boolean addsucc = this.managerService.AddManager(this.managerVo);

        if (powers!=null){
            int managerid = Integer.valueOf(managerService.findManageByName(name).get("id").toString());
            String[] powersArr = powers.split(",");
            for (String powre : powersArr) {
                if (powre.equals(""))
                    continue;
                AppriesManagerpower appriesManagerpower = new AppriesManagerpower();
                appriesManagerpower.setPowerid(Integer.valueOf(powre));
                appriesManagerpower.setManagerid(managerid);
                managerpowerService.insert(appriesManagerpower);
            }
        }
        if (addsucc)
            return "success";
        else{
            request.setAttribute("msg","添加失败!");
            return "error";
        }
    }

    public String managerAddDialog() throws Exception {
        this.list = this.powerService.getUserGroupList();
        return "success";
    }

    public String lowerManagerAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer id = Integer.valueOf(manager.get("id").toString());
        final String deptid = String.valueOf(this.powerService.getDeptIdByManagerId(id));
        final String alldept = this.deptService.findChildALLStr123(deptid);
        this.list = this.powerService.getLowerPowerByDeptId(alldept);
        return "success";
    }

    public String managerChangePsw() throws Exception {
        return "success";
    }

    public String managerChangePswDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final String oldpsw = request.getParameter("oldPsw");
        final String newpsw = request.getParameter("newPsw");
        final String newpsw2 = request.getParameter("newPsw2");
        if (newpsw.length() > 50) {
            this.msg = "your code over 50!";
            this.type = "input";
            return "input";
        }
        if (newpsw2.length() > 50) {
            this.msg = "your code over 50!";
            this.type = "input";
            return "input";
        }
        if (!newpsw2.equals(newpsw)) {
            this.msg = "your code is error";
            this.type = "input";
            return "input";
        }
        if (!oldpsw.equals(manager.get("password").toString())) {
            this.msg = "your code is error";
            this.type = "input";
            return "input";
        }
        try {
            final Integer Id = Integer.valueOf(manager.get("id").toString());
            if (this.managerService.UpdateManage(Id, newpsw)) {
                this.msg = "change password success ";
                manager.put("password", newpsw);
                request.getSession().setAttribute("manager", (Object)manager);
                this.type = "succ";
                return "success";
            }
            this.msg = "change passord faild!";
            this.type = "error";
            return "error";
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "system error";
            this.type = "error";
            return "error";
        }
    }

    public String managerChangePswDeal2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final String oldpsw = request.getParameter("oldPsw");
        final String newpsw = request.getParameter("newPsw");
        final String newpsw2 = request.getParameter("newPsw2");
        System.out.println("oldPsw=" + oldpsw);
        System.out.println("newPsw=" + newpsw);
        System.out.println("newPsw2=" + newpsw2);
        if (newpsw.length() > 50) {
            this.msg = "your code over 50!";
            this.type = "input";
            return "input";
        }
        if (newpsw2.length() > 50) {
            this.msg = "your code over 50!";
            this.type = "input";
            return "input";
        }
        if (!newpsw2.equals(newpsw)) {
            this.msg = "\u4e24\u6b21\u65b0\u5bc6\u7801\u8f93\u5165\u4e0d\u4e00\u81f4";
            this.type = "input";
            return "input";
        }
        if (!oldpsw.equals(manager.get("password").toString())) {
            this.msg = "\u5bc6\u7801\u9519\u8bef";
            this.type = "input";
            return "input";
        }
        try {
            final Integer Id = Integer.valueOf(manager.get("id").toString());
            if (this.managerService.UpdateManage(Id, newpsw)) {
                this.msg = "\u5bc6\u7801\u4fee\u6539\u6210\u529f ";
                manager.put("password", newpsw);
                request.getSession().setAttribute("manager", (Object)manager);
                this.type = "success";
                return "success";
            }
            this.msg = "\u5bc6\u7801\u4fee\u6539\u5931\u8d25!";
            this.type = "error";
            return "error";
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u7cfb\u7edf\u9519\u8bef";
            this.type = "error";
            return "error";
        }
    }

    public String managerDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.managerService.delManage(id);
        return "success";
    }

    public String managerReset() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.managerService.reSetManage(id);
        return "success";
    }

    public String managerEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int dept = req.getInt("dept", 0);
        (this.managerVo = new ManagerVo()).setId(id);
        String name = request.getParameter("name");
        String powers = req.getString("powers");
        if (this.managerService.manageExist(request.getParameter("id"), name)) {
            this.msg = "";
        }
        else {
            this.msg = "该用户已经存在";
            return "success";
        }
        this.managerVo.setSkinid(0);
        this.managerVo.setName(name);
        this.managerVo.setRealname(request.getParameter("realname"));
        this.managerVo.setPassword("123456");
        this.managerVo.setUserGroupId(req.getInt("userGroupId"));
        this.managerVo.setRemark(request.getParameter("remark"));
        this.managerVo.setExt1(request.getParameter("ext1"));
        this.managerVo.setExt2(request.getParameter("ext2"));
        this.managerVo.setDeptid(dept);
        this.managerService.UpdateManage(this.managerVo);

        if (powers!=null){
            managerpowerService.delete(new EntityWrapper<AppriesManagerpower>().where("managerId={0}", id));
            String[] powersArr = powers.split(",");
            for (String powre : powersArr) {
                if (powre.equals(""))
                    continue;
                AppriesManagerpower appriesManagerpower = new AppriesManagerpower();
                appriesManagerpower.setPowerid(Integer.valueOf(powre));
                appriesManagerpower.setManagerid(id);
                managerpowerService.insert(appriesManagerpower);
            }
        }
        return "success";
    }

    public String managerEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer id = Integer.valueOf(manager.get("id").toString());
        final Integer id2 = Integer.valueOf(request.getParameter("id"));
        final String deptid = String.valueOf(this.powerService.getDeptIdByManagerId(id));
        final String alldept = this.deptService.findChildALLStr123(deptid);
        this.list = this.powerService.getLowerPowerByDeptId(alldept);
        final Map m = this.managerService.findManageById(id2);

        List<AppriesManagerpower> managerPower = managerpowerService.selectList(new EntityWrapper<AppriesManagerpower>().where("managerid={0}", req.getInt("id")));
        for (int i=0; i<managerPower.size(); i++){
            for (int j=0; j<list.size(); j++){
                Map managerMap = (Map)list.get(j);
                if (managerMap.get("id").toString().equals(managerPower.get(i).getPowerid().toString()))
                {
                    managerMap.put("checked", true);
                }
            }
        }
        request.setAttribute("manager", (Object)m);
        return "success";
    }

    public String managerExist() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        name = Mysql.mysql(name);
        if (this.managerService.manageExist(id, name)) {
            this.msg = "";
        }
        else {
            this.msg = "该用户已经存在";
        }
        return "success";
    }

    public String managerLogin() throws Exception {
        String[] loginrst = new String[1];
        final Map manager = this.managerService.findManageBy(this.managerVo.getName(), this.managerVo.getPassword(), loginrst);
        if (manager==null){
            this.msg = loginrst[0];
            System.out.println("login:" + loginrst[0]);
            return "input";
        }
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final HttpSession session = request.getSession();
        final String rand = request.getParameter("rand");
        final InetAddress addr = InetAddress.getLocalHost();
        final String ipHouse = addr.getHostAddress();
        final Integer playListId = 1;
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final String filePath = String.valueOf(basePath) + "m7app" + File.separator + playListId.toString() + File.separator + "NEWCONFIG.XML";
        final String filePath2 = String.valueOf(basePath) + "update" + File.separator + playListId.toString() + File.separator + "NEWCONFIG.XML";
        final String filePath3 = String.valueOf(basePath) + "m7app" + File.separator + "NEWCONFIG.XML";
        final String filePath4 = String.valueOf(basePath) + "update" + File.separator + "NEWCONFIG.XML";
        final String updateFilepath = String.valueOf(basePath) + "UpdateFile" + File.separator + "NEWCONFIG.XML";
        final SAXBuilder sb = new SAXBuilder(false);
        final Document doc = sb.build(new File(filePath));
        final Document doc2 = sb.build(new File(filePath2));
        final Document doc3 = sb.build(new File(filePath3));
        final Document doc4 = sb.build(new File(filePath4));
        final Document doc5 = sb.build(new File(updateFilepath));
        final Element root = doc.getRootElement();
        final Element root2 = doc2.getRootElement();
        final Element root3 = doc3.getRootElement();
        final Element root4 = doc4.getRootElement();
        final Element root5 = doc5.getRootElement();
        final String ip = root.getChild("Server").getChildText("IP");
        final String ip2 = root2.getChild("Server").getChildText("IP");
        final String ip3 = root3.getChild("server").getChildText("ip");
        final String ip4 = root4.getChild("server").getChildText("ip");
        final String ip5 = root5.getChild("Server").getChildText("IP");
        if (ip.equals("") || ip.equals(null) || ip2.equals("") || ip2.equals(null) || ip3.equals("") || ip3.equals(null) || ip4.equals("") || ip4.equals(null) || ip5.equals("") || ip5.equals(null)) {
            root.getChild("Server").getChild("IP").setText(ipHouse);
            root2.getChild("Server").getChild("IP").setText(ipHouse);
            root3.getChild("server").getChild("ip").setText(ipHouse);
            root4.getChild("server").getChild("ip").setText(ipHouse);
            root5.getChild("Server").getChild("IP").setText(ipHouse);
            final Format format = Format.getPrettyFormat();
            format.setIndent("    ");
            format.setEncoding("gb2312");
            XMLOutputter XMLOut = new XMLOutputter(format);
            XMLOut.output(doc, (OutputStream)new FileOutputStream(filePath));
            XMLOut.output(doc2, (OutputStream)new FileOutputStream(filePath2));
            XMLOut.output(doc3, (OutputStream)new FileOutputStream(filePath3));
            XMLOut.output(doc4, (OutputStream)new FileOutputStream(filePath4));
            XMLOut.output(doc5, (OutputStream)new FileOutputStream(updateFilepath));
            XMLOut = null;
        }
        /*if (rand.equals("")) {
            this.msg = this.getText("login.identifyingCodeNotNull");
            return "input";
        }*/
        /*if (session.getAttribute("rand1") == null) {
            this.msg = this.getText("login.identifyingCodeError");
            return "input";
        }
        if (!rand.equals(session.getAttribute("rand1").toString())) {
            this.msg = this.getText("login.identifyingCodeError");
            return "input";
        }*/
        if (manager != null) {
            CookieUtils cookieUtils = new CookieUtils();
            List<AppriesManagerpower> managerPowers = managerpowerService.selectListEx(new EntityWrapper<AppriesManagerpower>().where("managerId={0}",manager.get("id").toString()));

            Map cookieMap = new HashMap();
            cookieMap.put("name", manager.get("name"));
            cookieMap.put("password", manager.get("password"));

            String cookieMapPowerStr = "";
            if (null!=managerPowers && managerPowers.size()>0){
                for (AppriesManagerpower powr : managerPowers){
                    cookieMapPowerStr += "|" + powr.getPowerName();
                }
                cookieMapPowerStr = cookieMapPowerStr.substring(1);
            }
            cookieMap.put("powers", cookieMapPowerStr);
            Cookie cookie = cookieUtils.addCookie(cookieMap);
            response.addCookie(cookie);// 添加cookie到response中
            cookie = cookieUtils.addCookie2(deptService.findChildALLStr1234(null));
            response.addCookie(cookie);

            session.setAttribute("manager", (Object)manager);
            return "success";
        }
        this.msg = "登录失败";
        return "input";
    }

    @Resource
    private CacheManager dCacheManager;

    public String managerLogout() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        session.invalidate();
        return "success";
    }

    public String ManagerQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        name = name.trim();
        final int rowsCount = this.managerService.countByName(name);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerManagerPage");
        this.list = this.managerService.findByName(name, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.list);
        return "success";
    }

    public String LowerManagerQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        name = name.trim();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final String managerId = manager.get("id").toString();
        request.setAttribute("managerId", (Object)managerId);
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr1234(deptIdGroup);
        this.pager = new Pager("currentPage", pageSize, 0, request, "lowerManagerPage");
        int[] rowNum = new int[1];
        this.list = this.managerService.findLowerManagerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowNum);
        this.pager = new Pager("currentPage", pageSize, rowNum[0], request, "lowerManagerPage");
        this.pager.setPageList(this.list);
        return "success";
    }

    public String managerQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        name = name.trim();
        final int rowsCount = this.managerService.countByName(name);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerManagerPage");
        this.list = this.managerService.findByName(name, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.list);
        request.setAttribute("name", (Object)name);
        return "success";
    }

    public String lowerManagerQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = req.getString("name","");
        name = name.trim();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptGroups = this.deptService.findChildALLStr123(deptIdGroup);
        this.pager = new Pager("currentPage", pageSize, 0, request, "lowerManagerPage");
        int[] rowNum = new int[1];
        this.list = this.managerService.findLowerManagerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowNum);
        this.pager = new Pager("currentPage", pageSize, rowNum[0], request, "lowerManagerPage");
        this.pager.setPageList(this.list);
        request.setAttribute("name", (Object)name);
        return "success";
    }

    public String managerQx() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        final Map manager = (Map)session.getAttribute("manager");
        final Integer userGroupId = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(userGroupId);
        if (power.get("baseSet").toString().equals("false")) {
            this.msg = "noqx";
        }
        else {
            this.msg = "pas";
        }
        return "success";
    }

    public void setList(final List list) {
        this.list = list;
    }

    public void setManagerService(final ManagerService managerService) {
        this.managerService = managerService;
    }

    public void setManagerVo(final ManagerVo managerVo) {
        this.managerVo = managerVo;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setPager(final Pager pager) {
        this.pager = pager;
    }

    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }

    public String createTxt() throws IOException {
        final String sPath = "E:\\Abc\\124\\123.txt";
        boolean flag = false;
        final File file = new File(sPath);
        if (file.isFile() && file.exists()) {
        }
        else {
            file.createNewFile();
            flag = true;
        }
        return "success";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
