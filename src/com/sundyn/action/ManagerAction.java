package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.entity.AuditLock;
import com.sundyn.entity.AuditLog;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.listener.ActiveSessionsListener;
import com.sundyn.service.*;
import com.sundyn.util.CookieUtils;
import com.sundyn.util.IDCardUtils;
import com.sundyn.util.Mysql;
import com.sundyn.util.Pager;
import com.sundyn.util.impl.util;
import com.sundyn.utils.ReqUtils;
import com.sundyn.vo.LogTypeEnum;
import com.sundyn.vo.LoginResultEnum;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.DESUtils;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.StringUtils;
import com.xuan.xutils.utils.UUIDUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
*
*
* */
public class ManagerAction extends MainAction
{
    @Override
    public void init() {
        super.init();
    }
    @Resource
    private IAppriesManagerpowerService managerpowerService;
    @Resource
    private IAppriesPowerfuncService appriesPowerFuncService;
    private List list;
    private ManagerService managerService;
    private DeptService deptService;
    private ManagerVo managerVo;
    private String msg,type;
    private Pager pager;
    @Getter
    @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();
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

    /**
     *
     * @return
     */
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
    @Resource
    private IAuditLogService auditLogService;
    @Resource
    private IAuditLockService auditLockService;
    @Resource
    private ISysDictinfoService dictinfoService;

    public String managerAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = req.getString("name");
        int dept = req.getInt("dept", 0);
        int localuser = req.getInt("localuser");
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
        this.managerVo.setLocaluser(localuser);
        boolean addsucc = this.managerService.AddManager(this.managerVo)>0;

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

    public String managerChangePsw()  {
        request.setAttribute("name", ReqUtils.format(req.getString("name")));
        request.setAttribute("type", ReqUtils.format(req.getString("type")));
        return "success";
    }

    public String managerChangePswDeal() {
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
            if (this.managerService.UpdateManage(Id, newpsw, 0)) {
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
        int type = req.getInt("type");
        String cname = req.getString("name");
        String name = new String(DESUtils.decode(cname));
        Map manager = (Map) request.getSession().getAttribute("manager");
        String orgpwd = "";
        if (type == 1)
        {
            manager = managerService.findByName(name);
            if (manager == null){
                request.setAttribute("msg", "系统错误");
                return SUCCESS;
            }
            orgpwd = manager.get("ext1").toString();
        }
        else{

        }
        if(manager == null){
            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "系统错误");
            return SUCCESS;
        }
        String oldpsw = request.getParameter("oldPsw");
        final String newpsw = request.getParameter("newPsw");
        final String newpsw2 = request.getParameter("newPsw2");
        if (com.xuan.xutils.utils.StringUtils.isBlank(oldpsw)){
            this.msg = "旧密码不能为空!";
            this.type = "input";
            return "input";
        }
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
        if (!newpsw2.equalsIgnoreCase(newpsw)) {
            this.msg = "两次新密码输入不一致";
            this.type = "input";
            return "input";
        }
        if (!oldpsw.equalsIgnoreCase(manager.get("ext1").toString())) {
            this.msg = "旧密码错误";
            request.setAttribute("msg", "旧密码错误");
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "旧密码错误");
            return SUCCESS;
        }
        try {
            Integer Id = 0;
            if (type==1){
                Map m = this.managerService.findByName(name);
                if (null == m) {
                    this.msg = "参数错误,请重新再试";
                    this.type = "input";
                    return "input";
                }
                Id = NumberUtils.toInt(m.get("id").toString());
            }
            else
                Id = Integer.valueOf(manager.get("id").toString());
            boolean change = this.managerService.UpdateManage(Id, newpsw, type);
            if (change) {
                this.msg = "密码修改成功";
                jsonData.clear();
                jsonData.put("succ", true);
                jsonData.put("msg", "密码修改成功");
                jsonData.put("home", type==1?true:false);

                manager.put("password", newpsw);
                request.getSession().setAttribute("manager", (Object)manager);
                return SUCCESS;
            }
            this.msg = "密码修改失败!";
            this.type = "error";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "密码修改失败");
            return SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "系统错误";
            this.type = "error";
            return "error";
        }
    }

    public String managerDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        this.managerService.delManage(id);
        managerService.getAllCache(true);
        return "success";
    }

    public String managerReset() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        boolean b = this.managerService.reSetManage(id);
        if (!b)
            request.setAttribute("msg", "系统错误,重置密码失败.");
        return b ? "success" : "input";
    }

    public String managerResetStatus() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        boolean b = this.managerService.reSetManageStatus(id);
        if (!b)
            request.setAttribute("msg", "状态修改失败.");
        return b ? "success" : "input";
    }

    public String managerEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int dept = req.getInt("dept", 0);
        (this.managerVo = new ManagerVo()).setId(id);
        String name = request.getParameter("name");
        String powers = req.getString("powers");
        int localuser = req.getInt("localuser");
        String idcard = req.getString("idcard");
        String pwd = req.getString("pwd");
        String accesstime1 = req.getString("accesstime1");
        String accesstime2 = req.getString("accesstime2");
        int jyflag = req.getInt("jyflag");
        String jyno = req.getString("jyno");
        Date uexpired = req.getDate("uexpired");
        Date pwdexpired = req.getDate("pwdexpired");

        String isstringverify = dictinfoService.getDictInfo("security_para", "isstringverify");
        if (dept == 0){
            this.msg = this.getText("manage.entity.validation.dept.notnull");
            return "success";
        }
        if (StringUtils.isNotBlank(isstringverify) && isstringverify.equalsIgnoreCase("1")){ //开启检测模式
            if (com.xuan.xutils.utils.StringUtils.isBlank(idcard)){
                this.msg = this.getText("manage.entity.validation.idcard.notnull");
                return "success";
            }
            String security_idcardcheck = dictinfoService.getDictInfo("security_para", "security_idcardcheck");
            if (security_idcardcheck.equals("1")) {
                IDCardUtils cc = new IDCardUtils();
                String idcardVerify = cc.IDCardValidate(idcard);
                if (!com.xuan.xutils.utils.StringUtils.isBlank(idcardVerify))
                {
                    this.msg = idcardVerify;
                    return "success";
                }
            }
            Map managermap = this.managerService.findByIdCard(idcard);
            if  (id ==0 && managermap != null) {//新增
                this.msg = this.getText("manage.entity.validation.idcard.exists");
                return "success";
            }
            if (id > 0 && managermap != null && !Integer.valueOf(managermap.get("id").toString()).equals(id)) { //修改
                this.msg = this.getText("manage.entity.validation.idcard.exists");
                return "success";
            }

            if (jyflag == 1 && com.xuan.xutils.utils.StringUtils.isBlank(jyno)){
                this.msg = this.getText("manage.entity.validation.jyno.nonull");
                return "success";
            }
            if (jyflag == 1){
                Integer jynoexists = this.managerService.existsByjyno(name, jyno);
                if  (id ==0 && jynoexists > 0) {//新增
                    this.msg = this.getText("manage.entity.validation.jyno.exists");
                    return "success";
                }
                if (id > 0 && jynoexists > 0) { //修改
                    this.msg = this.getText("manage.entity.validation.jyno.exists");
                    return "success";
                }
            }
            if (id==0 || (id>0 && !com.xuan.xutils.utils.StringUtils.isBlank(pwd))){
                String security_pwdcheck = dictinfoService.getDictInfo("security_para", "security_pwdcheck");
                if (security_pwdcheck.equals("1") && !util.validpwd(pwd)){
                    this.msg = this.getText("manage.entity.validation.password.valid");
                    return "success";
                }
            }
            if (!com.xuan.xutils.utils.StringUtils.isBlank(accesstime1) || !com.xuan.xutils.utils.StringUtils.isBlank(accesstime2) || true){
                if (com.xuan.xutils.utils.StringUtils.isBlank(accesstime1) || com.xuan.xutils.utils.StringUtils.isBlank(accesstime2)){
                    this.msg = this.getText("manage.entity.validation.accesstime.nonull");
                    return "success";
                }
            }
            if (jyflag==0){
                String[] powersArr = powers.split(",");
                for (String powerid : powersArr) {
                    if (powerid.equals(""))
                        continue;
                    if (powerService.checkIsJyAction(powerid)){
                        this.msg = this.getText("manage.entity.validation.jy.jyaction");
                        return "success";
                    }
                }
            }
            if (null == uexpired){
                this.msg = this.getText("manage.entity.validation.account.expired");
                return "success";
            }
            if (null == pwdexpired){
                this.msg = this.getText("manage.entity.validation.password.expired");
                return "success";
            }
            String accessip = req.getString("accessip");
            if (com.xuan.xutils.utils.StringUtils.isBlank(accessip)){
                this.msg = this.getText("manage.entity.validation.ipadd.notnull");
                return "success";
            }
            if (!com.xuan.xutils.utils.StringUtils.isBlank(accessip)){
                if (!com.sundyn.util.InetAddressUtils.isIPv4Address2(accessip)){
                    this.msg = this.getText("manage.entity.validation.ipadd.format");
                    return "success";
                }
            }
            if (id != 0) {
                Map map1 = managerService.findByName(name);
                if (map1 != null && map1.get("uexpired")!=null){
                    Date uexpired2 = DateUtils.string2Date(map1.get("uexpired").toString());
                    if (null != uexpired2 && DateUtils.compareDate(uexpired2, new Date()) < 0){
                        if (DateUtils.compareDate(uexpired2, uexpired) != 0)
                            this.managerVo.setNeedchangepwd(1);
                    }
                }
            }
        }

        if (this.managerService.manageExist(request.getParameter("id"), name)) {
            this.msg = "";
        }
        else {
            this.msg = this.getText("manage.entity.validation.exists");
            return "success";
        }

        if (id>0 && com.xuan.xutils.utils.StringUtils.isBlank(pwd)){
            this.managerVo.setPassword("");
        }
        else
            this.managerVo.setPassword(util.md5(pwd));
        this.managerVo.setIdcard(idcard);
        this.managerVo.setJyno(jyno);
        this.managerVo.setStatus(req.getInt("status", 1));
        this.managerVo.setUexpired(uexpired);
        this.managerVo.setPwdexpried(pwdexpired);
        this.managerVo.setJyflag(jyflag);
        this.managerVo.setSkinid(0);
        this.managerVo.setName(name);
        this.managerVo.setRealname(request.getParameter("realname"));
        this.managerVo.setUserGroupId(req.getInt("userGroupId"));
        this.managerVo.setRemark(request.getParameter("remark"));
        this.managerVo.setExt1(request.getParameter("ext1"));
        this.managerVo.setExt2(request.getParameter("ext2"));
        this.managerVo.setDeptid(dept);
        this.managerVo.setLocaluser(localuser);
        this.managerVo.setAccesstime1(accesstime1);
        this.managerVo.setAccesstime2(accesstime2);
        this.managerVo.setAccessip(req.getString("accessip"));
        this.managerVo.setCheckdigited(0);

        int newuserid =0;
        if(id == 0){
            this.managerVo.setCuser(super.UserID);
            this.managerVo.setCtime(new Date());
            this.managerVo.setUserid(UUIDUtils.uuid());
            newuserid = this.managerService.AddManager(this.managerVo);
        }
        else{
            this.managerVo.setUserid(req.getString("userid"));
            this.managerService.UpdateManage(this.managerVo);
        }

        AuditLog t = new AuditLog();
        t.setName(name);
        t.setCtime(new Date());
        t.setIpadd(util.getRemoteIpAddr());
        t.setLogrst(this.getText("main.succ"));
        t.setLogtype(id == 0 ? this.getText("auditlog.logTypeEnumAddManager") : this.getText("auditlog.logTypeEnumEditManager"));
        String desc = new JSONObject(this.managerVo).toString();
        if(desc.length()>1000)
            desc = desc.substring(0,999);
        t.setLogrstdesc(desc);
        auditLogService.insert(t);

        if (powers!=null){
            if (id==0)
                id = newuserid;
            managerpowerService.delete(new EntityWrapper<AppriesManagerpower>().where("managerId={0}", id));
            String[] powersArr = powers.split(",");
            for (String powre : powersArr) {
                if (powre.equals(""))
                    continue;
                AppriesManagerpower appriesManagerpower = new AppriesManagerpower();
                appriesManagerpower.setPowerid(Integer.valueOf(powre));
                appriesManagerpower.setManagerid(id);
                boolean insertr =  managerpowerService.insert(appriesManagerpower);
            }
        }
        managerService.getAllCache(true);
        return "success";
    }

    public String managerEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer id = Integer.valueOf(manager.get("id").toString());

        final String deptid = String.valueOf(this.powerService.getDeptIdByManagerId(id));
        final String alldept = this.deptService.findChildALLStr123(deptid);
        this.list = this.powerService.getLowerPowerByDeptId(alldept);

        String paramid = req.getString("id");
        if (!com.xuan.xutils.utils.StringUtils.isBlank(paramid)){
            final Integer id2 = Integer.valueOf(paramid);
            final Map m = this.managerService.findManageById(id2);
            request.setAttribute("manager2", (Object)m);
            List<AppriesManagerpower> managerPower = managerpowerService.selectList(new EntityWrapper<AppriesManagerpower>()
                    .where("managerid={0}", id2));
            for (int i=0; i<managerPower.size(); i++){
                for (int j=0; j<list.size(); j++){
                    Map managerMap = (Map)list.get(j);
                    if (managerMap.get("id").toString().equals(managerPower.get(i).getPowerid().toString()))
                    {
                        managerMap.put("checked", true);
                    }
                }
            }
        }
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
            this.msg = this.getText("manage.entity.validation.exists");
        }
        return "success";
    }

    /*
     * 检查用户数据是否被篡改
     */
    private Map<String, Object> checkLock(String name) {
        EntityWrapper<AuditLock> w = new EntityWrapper<>();
        w.where("name={0} and convert(varchar(50), ctime, 23)=convert(varchar(50), {1}, 23) and unlocktime>{1} and status=1", name, new Date()); //解锁时间已
        return auditLockService.selectMap(w);
    }

    /*
     * 检查账户是否被锁定
     */
    private Map<String, Object> checkcheckdigited(String name) {
        EntityWrapper<AuditLock> w = new EntityWrapper<>();
        w.where("name={0} and convert(varchar(50), ctime, 23)=convert(varchar(50), {1}, 23) and unlocktime>{1} and status=1", name, new Date()); //解锁时间已
        return auditLockService.selectMap(w);
    }

    /*
     × 最后一次被记录
     */
    private AuditLock getLstLock(String name) {
        EntityWrapper<AuditLock> w = new EntityWrapper<>();
        w.where("name={0} and convert(varchar(50), ctime, 23)=convert(varchar(50), {1}, 23) ", name, new Date())
        .orderBy("ctime", false); //解锁时间已
        return auditLockService.selectOne(w);
    }

    /*
    * 检查单位时间段内访问次数
    */
    private boolean checkMaxAccess(String name, String[] msg) {
        SysDictinfo dictinfo = dictinfoService.selectOne(new EntityWrapper<SysDictinfo>().where("dictgroup='audit_para' and dictkey='audit_accessmaxtimes'"));
        if (dictinfo!=null){
            String[] dictarr = dictinfo.getDictvalue().split(",");
            if (dictarr!=null && dictarr.length==2){
                if (NumberUtils.isNumber(dictarr[0]) && NumberUtils.isNumber(dictarr[1])){
                    int time = NumberUtils.toInt(dictarr[0]);
                    int count = NumberUtils.toInt(dictarr[1]);
                    EntityWrapper<AuditLog> w = new EntityWrapper<>();
                    w.where("name={0} and ctime > dateadd(mi ,{1}, getdate()) and logrst={2}", name, time*-1, this.getText("auditlog.agentLoginSucc"));
                    int logintimes = auditLogService.selectCount(w);
                    //msg[0] = "用户" + name + "在"+dictarr[0]+"分钟内连续访问次数达到" + logintimes + "次,超过上限" + dictarr[1] + "次";
                    msg[0] = this.getText("auditlog.audit_accessmaxtimes", new String[]{name, dictarr[0], String.valueOf(logintimes), dictarr[1]});
                    return logintimes>=count;
                }
            }
        }
       return false;
    }

    /*
    * 检查是否在允许访问时间段时
     */
    private boolean checkAccessTime(String name, String[] msg) {
        SysDictinfo dictinfo = dictinfoService.selectOne(new EntityWrapper<SysDictinfo>().where("dictgroup='audit_para' and dictkey='audit_specialaccesstime'"));
        if (dictinfo!=null){
            String[] dictarr2 = dictinfo.getDictvalue().split(",");

            if(dictarr2!=null){
                for (String times : dictarr2) {
                    if (!com.xuan.xutils.utils.StringUtils.isBlank(times)){
                        String[] dictarr = times.split("~");
                        if (dictarr!=null && dictarr.length==2){
                            if (DateUtils.compareHourAndMinute(new Date(), DateUtils.string2Date(dictarr[0],"HH:mm"))>0
                                && DateUtils.compareHourAndMinute(new Date(), DateUtils.string2Date(dictarr[1],"HH:mm"))<0) {
                                //msg[0] = "用户" + name + "在特殊时间段" + dictarr[0] + "~" + dictarr[1] + "访问系统";
                                msg[0] = this.getText("auditlog.audit_specialaccesstime", new String[]{name, dictarr[0], dictarr[1]});
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /*
    检查最大在线人数
    */
    public boolean checkSessionMax(){
        ServletContext context = ServletActionContext.getServletContext();
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
        String config_sessionmax = dictinfoService.getDictInfo("config", "config_sessionmax");
        if (null != map && !com.xuan.xutils.utils.StringUtils.isBlank(config_sessionmax) && NumberUtils.isNumber(config_sessionmax)){
            Integer config_sessionmax_int = Integer.valueOf(config_sessionmax);
            if (config_sessionmax_int <= map.size()) {
                return true;
            }
        }
        return false;
    }

    public String managerLogin() throws Exception {
        managerService.initPwdForCheck();
        String[] loginrst = new String[1];
        String name= req.getString("name"),
                password = req.getString("password");
        String ipadd = util.getRemoteIpAddr();
        try{
            name = new String(DESUtils.decode(name));
        } catch (Exception e){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("main.systemerror"));
            return "success";
        }

        Map lockMap = checkLock(name);
        if (null != lockMap){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("login.usernamelocked", new String[]{name, lockMap.get("locktime").toString()}));
            return "success";
        }
        lockMap = checkLock(ipadd);
        if (null != lockMap){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("login.agentnamelocked", new String[]{ipadd, lockMap.get("locktime").toString()}));
            return "success";
        }

        Date lstTime = null;
        final Map manager = this.managerService.findManageBy(name, password, loginrst);
        Map dictMap = dictinfoService.selectMap(new EntityWrapper<SysDictinfo>().where("dictkey={0} and dictgroup={1}",
                "audit_logintrytimes_account", "security_para"));
        int times_account = 0;
        if (dictMap != null && dictMap.get("dictvalue")!=null && !com.xuan.xutils.utils.StringUtils.isBlank(dictMap.get("dictvalue").toString())){
            try{
                times_account = Integer.valueOf(dictMap.get("dictvalue").toString());
            }
            catch (Exception e){
                times_account = 0;
            }
        }
        dictMap = dictinfoService.selectMap(new EntityWrapper<SysDictinfo>().where("dictkey={0} and dictgroup={1}",
                "audit_logintrytimes_ip","security_para"));
        int times_ip = 0;
        if (dictMap != null && dictMap.get("dictvalue")!=null && !com.xuan.xutils.utils.StringUtils.isBlank(dictMap.get("dictvalue").toString())){
            try{
                times_ip = Integer.valueOf(dictMap.get("dictvalue").toString());
            }
            catch (Exception e){
                times_ip = 0;
            }
        }
        AuditLog t = new AuditLog();
        t.setName(name);
        Date logintime = new Date();
        String loginadd = util.getRemoteIpAddr();
        String logdevice = util.getRemoteBowser();
        if (logdevice.length()>140)
            logdevice = logdevice.substring(0,140);
        if (com.xuan.xutils.utils.StringUtils.isBlank(logdevice) && logdevice.length()>150)
            logdevice = logdevice.substring(0, 149);
        t.setLogdevice(logdevice);
        t.setCtime(logintime);
        t.setIpadd(loginadd);

        if (manager==null){
            this.msg = loginrst[0];
            String errmsg = loginrst[0];
            if (com.xuan.xutils.utils.StringUtils.isNotBlank(errmsg) && errmsg.equalsIgnoreCase(LoginResultEnum.数据被篡改.toString())){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", this.getText("login.denylogin"));
                return "success";
            }
            //if ((loginrst[0].equalsIgnoreCase("账号不存在")) && times_ip>0){
            if (times_ip>0){
                AuditLock auditLock = getLstLock(ipadd);
                if (auditLock!=null){
                    if (auditLock.getRealunlocktime() != null)
                        lstTime = auditLock.getRealunlocktime();
                    else
                        lstTime = auditLock.getUnlocktime();
                }
                String agentLoginError = this.getText("auditlog.agentLoginError");
                String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
                t.setLogtype(LogTypeEnum.登录.getCode());
                t.setLogrst(agentLoginError);

                Wrapper wrapper = new EntityWrapper<AuditLog>().where("logrst={0} and ipadd={1} and logtype={2}", agentLoginError, ipadd, LogTypeEnum.登录.getCode());
                //wrapper.where("convert(varchar(50), ctime, 23)=convert(varchar(50), {0}, 23)", new Date());
                wrapper.where("ctime>(select isnull(max(ctime), convert(varchar(50), {0}, 23)) from audit_log where ipadd={1} and logrst={2} and logtype={3})", new Date(), ipadd, agentLoginSucc, LogTypeEnum.登录.getCode());
                if (lstTime!=null)
                    wrapper.where("ctime>{0}", lstTime);
                int failcount = auditLogService.selectCount(wrapper)+1;
                if (times_ip-failcount > 0)
                    errmsg = loginrst[0] + this.getText("auditlog.agentLoginErrorCount", new String[]{ipadd, String.valueOf(failcount), String.valueOf(times_ip-failcount)});//",当前IP地址" + ipadd + "连续登录失败次数已达"+ failcount + "次,离冻结次数还剩" + (times_ip-failcount) + "次";
                else {
                    errmsg = loginrst[0] + this.getText("auditlog.agentLoginErrorMAX", new String[]{ipadd, String.valueOf(failcount)});//",当前IP地址" + ipadd + "连续登录失败次数已达" + failcount + "次,终端已冻结";
                    Map map1 = dictinfoService.selectMap(new EntityWrapper<SysDictinfo>().where("isenable=1 and dictkey='audit_locktime_ip' and dictgroup='security_para'"));
                    int locktimes = 0;
                    if (map1!=null && map1.get("dictvalue")!=null){
                        locktimes = NumberUtils.toInt(map1.get("dictvalue").toString());
                    }
                    t.setLogtype(LogTypeEnum.终端冻结.getCode());
                    t.setLogrst(LogTypeEnum.终端冻结.toString());
                    AuditLock al = new AuditLock();
                    al.setName(ipadd);
                    al.setLocktype(this.getText("auditlog.agentLocked"));
                    Date d = new Date();
                    al.setLocktime(d);
                    if (locktimes >0)
                        al.setUnlocktime(DateUtils.addMinute(d, locktimes));
                    al.setStatus(1);
                    al.setLockdesc(errmsg);
                    al.setCtime(new Date());
                    auditLockService.insert(al);
                }
                t.setLogrstdesc(errmsg);
                auditLogService.insert(t);
            }
            if (loginrst[0].equalsIgnoreCase(LoginResultEnum.密码错误.toString()) && times_account>0){
                t.setLogtype(LogTypeEnum.登录.getCode());
                t.setLogrst(this.getText("auditlog.accountloginerror"));

                AuditLock auditLock = getLstLock(name);
                if (auditLock!=null){
                    if (auditLock.getRealunlocktime() != null)
                        lstTime = auditLock.getRealunlocktime();
                    else
                        lstTime = auditLock.getUnlocktime();
                }
                String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
                Wrapper wrapper = new EntityWrapper<AuditLog>().where(AuditLog.LOGRST + "={0} and " + AuditLog.NAME+ "={1} and logtype={2}", this.getText("auditlog.accountloginerror"), name, LogTypeEnum.登录.getCode());
                //wrapper.where("convert(varchar(50), ctime, 23)=convert(varchar(50), {0}, 23)", new Date());
                wrapper.where("ctime>(select isnull(max(ctime), convert(varchar(50), {0}, 23)) from audit_log where name={1} and logrst={2} and logtype={3})", new Date(), name, agentLoginSucc, LogTypeEnum.登录.getCode());
                if (lstTime!=null)
                    wrapper.where("ctime>{0}", lstTime);
                int failcount = auditLogService.selectCount(wrapper)+1;
                if(times_account-failcount>0){
                    errmsg = loginrst[0] + this.getText("auditlog.accountLoginErrorCount", new String[]{name, String.valueOf(failcount), String.valueOf(times_account-failcount)});// + ",当前用户" + name + "连续登录失败次数已达"+ failcount + "次,离冻结次数还剩" + (times_account-failcount) + "次";
                }
                else{
                    errmsg = loginrst[0] + this.getText("auditlog.accountLoginErrorMAX", new String[]{name, String.valueOf(failcount)}); //",当前用户" + name + "连续登录失败次数已达"+ failcount + "次,账号已冻结";
                    Map map1 = dictinfoService.selectMap(new EntityWrapper<SysDictinfo>().where("isenable=1 and dictkey='audit_locktime_account' and dictgroup='security_para'"));
                    int locktimes = 0;
                    if (map1!=null && map1.get("dictvalue")!=null){
                        locktimes = NumberUtils.toInt(map1.get("dictvalue").toString());
                    }
                    t.setLogtype(LogTypeEnum.账号冻结.getCode());
                    t.setLogrst(LogTypeEnum.账号冻结.toString());
                    AuditLock al = new AuditLock();
                    al.setName(name);
                    al.setLocktype(this.getText("auditlog.accountLocked"));
                    Date d = new Date();
                    al.setLocktime(d);
                    if (locktimes >0)
                        al.setUnlocktime(DateUtils.addMinute(d, locktimes));
                    al.setStatus(1);
                    al.setLockdesc(errmsg);
                    al.setCtime(new Date());
                    auditLockService.insert(al);
                }
                t.setLogrstdesc(errmsg);
                auditLogService.insert(t);
            }
            request.setAttribute("msg", errmsg);
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", errmsg);
            return "success";
        }

        if (null!=manager.get("checkdigited") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("checkdigited").toString())){
            String checkdigited = manager.get("checkdigited").toString();
            if (checkdigited.equalsIgnoreCase("1")){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("login.denylogin"));
                return SUCCESS;
            }
        }

        if (null!=manager.get("uexpired") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("uexpired").toString())){
            Date uexpired = DateUtils.string2Date(manager.get("uexpired").toString());
            if (null != uexpired && DateUtils.compareDate(uexpired, new Date()) < 0){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("auditlog.accountExpired"));
                return SUCCESS;
            }
        }

        if (null!=manager.get("pwdexpired") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("pwdexpired").toString())){
            Date uexpired = DateUtils.string2Date(manager.get("pwdexpired").toString());
            if (null != uexpired && DateUtils.compareDate(uexpired, new Date()) < 0){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("auditlog.passwordExpired"));
                return SUCCESS;
            }
        }

        Object objStatus = manager.get("status");
        if (null!=objStatus && !com.xuan.xutils.utils.StringUtils.isBlank(objStatus.toString())){
            if (objStatus.toString().equalsIgnoreCase("0")){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("auditlog.accountdeny"));
                return SUCCESS;
            }
        }

        if (null!=manager.get("accesstime1") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("accesstime1").toString())){
            Date accesstime1 = DateUtils.string2Date(manager.get("accesstime1").toString(), "HH:mm");
            if (null != accesstime1 && DateUtils.compareHourAndMinute(new Date(), accesstime1) < 0){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("auditlog.noaccesstime"));
                return SUCCESS;
            }
        }

        if (null!=manager.get("accesstime2") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("accesstime2").toString())){
            Date accesstime2 = DateUtils.string2Date(manager.get("accesstime2").toString(), "HH:mm");
            if (null != accesstime2 && DateUtils.compareHourAndMinute(new Date(), accesstime2) > 0){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("auditlog.noaccesstime"));
                return SUCCESS;
            }
        }

        if (null!=manager.get("needchangepwd") && !com.xuan.xutils.utils.StringUtils.isBlank(manager.get("needchangepwd").toString())){
            int needchangepwd = NumberUtils.toInt(manager.get("needchangepwd").toString());
            if (needchangepwd ==1){
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", getText("login.passwordNeedChange"));
                return SUCCESS;
            }
        }
        if (checkSessionMax()){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", getText("auditlog.onlinemax"));
            return SUCCESS;
        }
        String[] maxAccessMsg = new String[1];
        if (checkMaxAccess(name, maxAccessMsg)){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", LogTypeEnum.高频访问.toString());
            t.setLogdevice(logdevice);
            t.setCtime(logintime);
            t.setIpadd(loginadd);
            t.setLogrst(LogTypeEnum.高频访问.toString());
            t.setLogtype(LogTypeEnum.高频访问.getCode());
            t.setLogrstdesc(maxAccessMsg[0]);
            auditLogService.insert(t);
        }
        maxAccessMsg = new String[1];
        if (checkAccessTime(name, maxAccessMsg)){
            t.setLogdevice(logdevice);
            t.setCtime(logintime);
            t.setIpadd(loginadd);
            t.setLogrst(LogTypeEnum.特殊访问时间段.toString());
            t.setLogtype(LogTypeEnum.特殊访问时间段.getCode());
            t.setLogrstdesc(maxAccessMsg[0]);
            auditLogService.insert(t);
        }

        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final HttpSession session = request.getSession();
        final String rand = request.getParameter("rand");
        final InetAddress addr = InetAddress.getLocalHost();
        final String ipHouse = addr.getHostAddress();
        final Integer playListId = 1;
        final String basePath = ServletActionContext.getServletContext().getRealPath(File.separator);
        final String filePath = basePath + "m7app" + File.separator + playListId.toString() + File.separator + "NEWCONFIG.XML";
        final String filePath2 = basePath + "update" + File.separator + playListId.toString() + File.separator + "NEWCONFIG.XML";
        final String filePath3 = basePath + "m7app" + File.separator + "NEWCONFIG.XML";
        final String filePath4 = basePath + "update" + File.separator + "NEWCONFIG.XML";
        final String updateFilepath = basePath + "UpdateFile" + File.separator + "NEWCONFIG.XML";
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
        if (manager != null) {
            CookieUtils cookieUtils = new CookieUtils();
            List<AppriesManagerpower> managerPowers = managerpowerService.selectListEx(new EntityWrapper<AppriesManagerpower>()
                    .where("managerId={0}",manager.get("id").toString()));

            Map cookieMap = new HashMap();
            cookieMap.put("name", manager.get("name"));
            cookieMap.put("password", manager.get("password"));

            ArrayList cookieMapPowerArr = new ArrayList();
            String cookieMapPowerStr = "";
            if (null!=managerPowers && managerPowers.size()>0){
                for (AppriesManagerpower powr : managerPowers){
                    cookieMapPowerStr += "|" + powr.getPowerName();
                    cookieMapPowerArr.add(powr.getPowerName());
                }
                cookieMapPowerStr = cookieMapPowerStr.substring(1);
            }
            cookieMap.put("powers", URLEncoder.encode(cookieMapPowerStr,"utf-8"));
            manager.put("logindevice", logdevice);
            manager.put("logintime", DateUtils.date2String(logintime));
            manager.put("loginadd", loginadd);

            session.setAttribute("manager", (Object)manager);
            session.setAttribute("powers", cookieMapPowerStr);
            //session.setAttribute("funcs", managerAllFuncs);

            Cookie cookie = cookieUtils.addCookie(cookieMap);
            response.addCookie(cookie); //添加cookie到response中
            cookie = cookieUtils.addCookie2(deptService.findChildALLStr1234(null));
            response.addCookie(cookie);

            session.setAttribute("bindingNotify", new ActiveSessionsListener(ServletActionContext.getServletContext()));
            String config_sessionexpired = dictinfoService.getDictInfo("config", "config_sessionexpired");
            if (!com.xuan.xutils.utils.StringUtils.isBlank(config_sessionexpired) && NumberUtils.isNumber(config_sessionexpired)){
                Integer config_sessionexpired_int = Integer.valueOf(config_sessionexpired);
                if (config_sessionexpired_int>10) {
                    session.setMaxInactiveInterval(config_sessionexpired_int * 60);//设置单位为秒，设置为-1永不过期
                }
            }

            t.setLogrst(this.getText("auditlog.agentLoginSucc"));
            t.setLogtype(LogTypeEnum.登录.getCode());
            t.setLogrstdesc(getText("auditlog.loginSucc"));
            auditLogService.insert(t);

            managerService.updateLoginTime(name, logintime, loginadd, logdevice);
            managerService.getAllCache(true);
            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", getText("auditlog.loginSucc"));
            login(name, password);

            return "success";
        }
        else{
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", getText("auditlog.loginError"));
        }
        this.msg = getText("auditlog.loginError");
        return SUCCESS;
    }

    public String login(String username, String password){
        //1.获取当前的用户
        Subject currentUser = SecurityUtils.getSubject();
        //2.把登录信息封装为一个 UsernamePasswordToken 对象
        UsernamePasswordToken token=new UsernamePasswordToken(username, password);
        //3.设置"记住我"功能
        //token.setRememberMe(true);
        try {
            // *登录操作!
            currentUser.login(token);
        } catch (UnknownAccountException uae) {
            return "input";
        } catch (IncorrectCredentialsException ice) {
            return "input";
        } catch (LockedAccountException lae) {
            return "input";
        } catch (AuthenticationException ae) {
            return "input";
        }

        return "success";
    }


    public String managerLogout() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        ServletContext context = ServletActionContext.getServletContext();
        final HttpSession session = request.getSession();
        Object manager = session.getAttribute("manager");
        if (null != manager){
            ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
            System.out.println("用户退出2：" + (null != map));
            if (null != map)
            {
                Map managermap = (Map)manager;
                AuditLog t = new AuditLog();
                t.setName(managermap.get("name").toString());
                Date logintime = new Date();
                String loginadd = util.getRemoteIpAddr();
                String logdevice = util.getRemoteBowser();
                if (com.xuan.xutils.utils.StringUtils.isNotBlank(logdevice) && logdevice.length()>150)
                    logdevice = logdevice.substring(0, 149);
                t.setLogdevice(logdevice);
                t.setCtime(logintime);
                t.setIpadd(loginadd);
                t.setLogtype(LogTypeEnum.退出.getCode());
                t.setLogrst(getText("auditlog.agentLoginSucc"));
                t.setLogrstdesc(getText("auditlog.loginoutSucc", new String[]{managermap.get("name").toString()}));
                auditLogService.insert(t);
                if (map.containsKey(managermap.get("name")))
                {
                    map.remove(managermap.get("name"));
                    context.setAttribute("activeSessions", map);
                }
            }
        }
        //1.获取当前的用户
        try{
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();

            session.invalidate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        /*Principal principal = UserUtils.getPrincipal();
        // 如果已经登录，则跳转到管理首页
        if(principal != null){
            UserUtils.getSubject().logout();
        }*/
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
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerManagerPage", this);
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
        final String managerId = manager.get("id").toString();
        request.setAttribute("managerId", (Object)managerId);
        final String deptGroups = this.deptService.findChildALLStr1234(super.getUserDept().toString());
        this.pager = new Pager("currentPage", pageSize, 0, request, "lowerManagerPage", this);
        int[] rowNum = new int[1];
        this.list = this.managerService.findLowerManagerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowNum);
        this.pager = new Pager("currentPage", pageSize, rowNum[0], request, "lowerManagerPage", this);
        this.pager.setPageList(this.list);
        return "success";
    }

    private String genCheckDigit(Map model){
        StringBuilder digitStr = new StringBuilder();
        digitStr.append(model.get("userid"));
        digitStr.append("|");

        digitStr.append(model.get("password"));
        digitStr.append("|");

        digitStr.append(model.get("name"));
        digitStr.append("|");

        digitStr.append(model.get("realname"));
        digitStr.append("|");

        digitStr.append(model.get("status"));
        digitStr.append("|");

        return util.md5(digitStr.toString());
    }

    public String managerQueryAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String name = request.getParameter("name");
        if (name == null) {
            name = "";
        }
        name = name.trim();
        final int rowsCount = this.managerService.countByName(name);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, "lowerManagerPage", this);
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
        this.pager = new Pager("currentPage", pageSize, 0, request, "lowerManagerPage", this);
        int[] rowNum = new int[1];
        this.list = this.managerService.findLowerManagerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowNum);
        this.pager = new Pager("currentPage", pageSize, rowNum[0], request, "lowerManagerPage", this);
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

    public String loginmsg(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();

        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype='登录' and audit_log.name={0} and audit_log.logrst='成功' and datediff(d,audit_log.ctime,getdate())=0", super.UserName);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1)
            request.setAttribute("data2", data2.get(1));

        ew = new EntityWrapper<>();
        ew.where("logtype='登录' and audit_log.name={0} and audit_log.logrst like '%失败%' and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log where name={0} and logrst='成功' and logtype='登录' and ctime<{1})", super.UserName, data2.get(1).get("ctime").toString());
        List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        request.setAttribute("data", data);



        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));

        Map user = this.managerService.findByName(super.UserName);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
        request.setAttribute("activeSessions", map);
        return SUCCESS;
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
