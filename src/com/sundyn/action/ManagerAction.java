package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.sundyn.entity.*;
import com.sundyn.listener.ActiveSessionsListener;
import com.sundyn.service.*;
import com.sundyn.util.*;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
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
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.struts2.ServletActionContext.getResponse;
import static org.apache.struts2.ServletActionContext.getServletContext;

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
    @Resource
    private DeviceService deviceService;
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
    @Resource
    private IAppriesManagerextService managerextService;
    @Resource
    private IOrderProductService productService;
    @Resource
    IOrderDetailService orderDetailService;
    @Resource
    IOrderProductdetailService orderProductdetailService;
    @Resource
    IOrderLicensedetailService orderLicensedetailService;
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

    private String getExtFileName(final String fileName) {
        if(StringUtils.isBlank(fileName))
            return "";
        if (fileName.lastIndexOf(".")==-1)
            return "";
        return fileName.substring(fileName.lastIndexOf("."));
    }

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
            this.msg =  this.getText("manager.field.name.exists");
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
            request.setAttribute("msg", this.getText("main.save.fail"));
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
                request.setAttribute("msg", this.getText("main.systemerror"));
                return SUCCESS;
            }
            orgpwd = manager.get("ext1").toString();
        }
        else{

        }
        if(manager == null){
            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", this.getText("main.systemerror"));
            return SUCCESS;
        }
        String oldpsw = request.getParameter("oldPsw");
        final String newpsw = request.getParameter("newPsw");
        final String newpsw2 = request.getParameter("newPsw2");
        if (com.xuan.xutils.utils.StringUtils.isBlank(oldpsw)){
            this.msg = this.getText("manager.changepwd.oldpwd.notnull");
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
            this.msg = this.getText("manager.changepwd.newpwd.confirmerror");
            this.type = "input";
            return "input";
        }
        if (!oldpsw.equalsIgnoreCase(manager.get("ext1").toString())) {
            this.msg = this.getText("manager.changepwd.oldpwd.error");
            request.setAttribute("msg", this.msg);
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.msg);
            return SUCCESS;
        }
        try {
            Integer Id = 0;
            if (type==1){
                Map m = this.managerService.findByName(name);
                if (null == m) {
                    this.msg = this.getText("main.systemerror");
                    this.type = "input";
                    return "input";
                }
                Id = NumberUtils.toInt(m.get("id").toString());
            }
            else
                Id = Integer.valueOf(manager.get("id").toString());
            boolean change = this.managerService.UpdateManage(Id, newpsw, type);
            if (change) {
                this.msg = this.getText("manager.changepwd.succ");
                jsonData.clear();
                jsonData.put("succ", true);
                jsonData.put("msg", this.msg);
                jsonData.put("home", type==1?true:false);

                manager.put("password", newpsw);
                request.getSession().setAttribute("manager", (Object)manager);
                return SUCCESS;
            }
            this.msg = this.getText("manager.changepwd.fail");
            this.type = "error";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.msg);
            return SUCCESS;
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = this.getText("main.systemerror");
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
        boolean b = this.managerService.reSetManage(id,util.md5("123456"));
        if (!b)
            request.setAttribute("msg", this.getText("manager.password.reset.fail"));
        return b ? "success" : "input";
    }

    public String managerResetStatus() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        boolean b = this.managerService.reSetManageStatus(id);
        if (!b)
            request.setAttribute("msg", this.getText("manager.status.reset.fail"));
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
                System.out.println("aa security_pwdcheckaaaa:" + security_pwdcheck);
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
                password = req.getString("password"),
                isremote = req.getString("isremote");
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
                    errmsg = this.getText("logintype.accountnotexists") + this.getText("auditlog.agentLoginErrorCount", new String[]{ipadd, String.valueOf(failcount), String.valueOf(times_ip-failcount)});//",当前IP地址" + ipadd + "连续登录失败次数已达"+ failcount + "次,离冻结次数还剩" + (times_ip-failcount) + "次";
                else {
                    errmsg = this.getText("logintype.accountnotexists") + this.getText("auditlog.agentLoginErrorMAX", new String[]{ipadd, String.valueOf(failcount)});//",当前IP地址" + ipadd + "连续登录失败次数已达" + failcount + "次,终端已冻结";
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
                    errmsg = this.getText("logintype.passerror") + this.getText("auditlog.accountLoginErrorCount", new String[]{name, String.valueOf(failcount), String.valueOf(times_account-failcount)});// + ",当前用户" + name + "连续登录失败次数已达"+ failcount + "次,离冻结次数还剩" + (times_account-failcount) + "次";
                }
                else{
                    errmsg = this.getText("logintype.passerror") + this.getText("auditlog.accountLoginErrorMAX", new String[]{name, String.valueOf(failcount)}); //",当前用户" + name + "连续登录失败次数已达"+ failcount + "次,账号已冻结";
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
            //e.printStackTrace();
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

    public String RemoteManagerQuery() throws Exception {
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
        this.list = this.managerService.findRemoteManagerByName(name, deptGroups, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowNum);
        this.pager = new Pager("currentPage", pageSize, rowNum[0], request, "lowerManagerPage", this);
        this.pager.setPageList(this.list);

        List prdlicensedetails = this.managerService.prdlicensedetail(null);

        for (Object m : this.pager.getPageList()){
            Map map = (Map)m;
            int totalnum = 0;
            int usednum=0;
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (Object m2 : prdlicensedetails){
                Map map2 = (Map)m2;
                if (map2.get("managerid").toString().equals(map.get("id").toString()))
                {
                    totalnum += Integer.valueOf(map2.get("totalnum").toString());
                    usednum += Integer.valueOf(map2.get("usednum").toString());
                    sb1.append("<br>"+map2.get("productname").toString() + ": " + map2.get("totalnum").toString());
                    sb2.append("<br>"+map2.get("productname").toString() + ": " + map2.get("usednum").toString());
                }
            }
            map.put("totalnum", totalnum);
            map.put("totalnumdetail", sb1.toString());
            map.put("usednumdetail", sb2.toString());
            map.put("usednum", usednum);
        }
        request.setAttribute("prdlicensedetails", prdlicensedetails);

        return "success";
    }

    public String remoteManagerEdit(){
        int managerid = req.getInt("managerid");
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

        Map user = this.managerService.findManageById(managerid);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }

        request.setAttribute("managerentity", user);
        request.setAttribute("managerextentity", managerextService.selectMap(new EntityWrapper<AppriesManagerext>().where("userid={0}", managerid)));

        return SUCCESS;
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
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst={2} and datediff(d,audit_log.ctime,getdate())=0"
                , LogTypeEnum.登录.getCode(), super.UserName, agentLoginSucc);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1){
            request.setAttribute("data2", data2.get(1));
            ew = new EntityWrapper<>();
            ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst in ({2}) " +
                            "and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log " +
                            "where name={3} and logrst={4} and logtype={5} and ctime<{6})"
                    , LogTypeEnum.登录.getCode(), super.UserName, agentLoginError, super.UserName
                    , agentLoginSucc, LogTypeEnum.登录.getCode(), data2.get(1).get("ctime").toString());
            List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
            request.setAttribute("data", data);
        }

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

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

    public String saveManagerExt(){
        try{
            String realname = req.getString("realname");
            String orgname = req.getString("orgname");
            String contact = req.getString("contact");
            String telphone = req.getString("telphone");
            String email = req.getString("email");
            String password = req.getString("password");

            managerService.updateManagerExt(super.UserID, password, realname, orgname, contact, telphone, email);
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "资料变更成功！");
        }
        catch (Exception e){
            e.printStackTrace();
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "资料变更失败！");
        }
        return SUCCESS;
    }

    public String saveRemoteManagerExt(){
        try{
            int id = req.getInt("id");
            String realname = req.getString("realname");
            String orgname = req.getString("orgname");
            String contact = req.getString("contact");
            String telphone = req.getString("telphone");
            String email = req.getString("email");
            String password = req.getString("password");

            managerService.updateManagerExt(id, password, realname, orgname, contact, telphone, email);
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "资料变更成功！");
        }
        catch (Exception e){
            e.printStackTrace();
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "资料变更失败！");
        }
        return SUCCESS;
    }

    public String saveOrder(){
        try{
            int[] idArray = req.getIntArray("id", 0);
            int[] numArray = req.getIntArray("txtprodnum", 0);
            String comment = req.getString("comment");

            if (idArray.length != numArray.length)
            {
                this.jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", "系統錯誤！");
                return SUCCESS;
            }
            List<OrderProduct> productList = productService.selectList(null);
            List<AppriesManagerrate> rateList = managerrateService.selectList(new EntityWrapper<AppriesManagerrate>().where("managerid={0}", super.UserID));
            List<Map> orderprods = new ArrayList<>();
            float totalfee = 0f;
            for (int i=0; i< idArray.length; i++){
                if (numArray[i]==0)
                    continue;
                OrderProduct product = null;
                for(OrderProduct pro : productList){
                    if (pro.getId() == idArray[i])
                        product = pro;
                }
                AppriesManagerrate rate = null;
                for(AppriesManagerrate _rate : rateList){
                    if (_rate.getProductid() == idArray[i]) {
                        rate = _rate;
                        break;
                    }
                }
                if (product!=null){
                    Map map = new HashMap();
                    map.put("productid", idArray[i]);
                    map.put("num", numArray[i]);
                    map.put("prdprice", product.getPrice());
                    map.put("rate", rate==null? 1.0f : rate.getRate());
                    map.put("realprice", product.getPrice().floatValue() * (rate==null? 1.0f : rate.getRate()));
                    orderprods.add(map);
                    totalfee += product.getPrice().floatValue() * numArray[i] * (rate==null? 1.0f : rate.getRate());
                }
            }
            Map orders = new HashMap();
            orders.put("orderno", CommonUtil.getOrderIdByTime());
            orders.put("managerid", super.UserID);
            orders.put("totalfee", CommonUtil.formatFloat(totalfee));
            orders.put("status", 0);
            orders.put("comment", "");
            orders.put("cuser", super.UserID);
            orders.put("ctime", new Date());
            orders.put("comment", comment);
            managerService.addOrder(orders, orderprods);
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "下单成功！");
        }
        catch (Exception e){
            e.printStackTrace();
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "下单失败！");
        }
        return SUCCESS;
    }

    public String order()
    {
        /*HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst={2} and datediff(d,audit_log.ctime,getdate())=0"
                , LogTypeEnum.登录.getCode(), super.UserName, agentLoginSucc);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1){
            request.setAttribute("data2", data2.get(1));
            ew = new EntityWrapper<>();
            ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst in ({2}) " +
                            "and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log " +
                            "where name={3} and logrst={4} and logtype={5} and ctime<{6})"
                    , LogTypeEnum.登录.getCode(), super.UserName, agentLoginError, super.UserName
                    , agentLoginSucc, LogTypeEnum.登录.getCode(), data2.get(1).get("ctime").toString());
            List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
            request.setAttribute("data", data);
        }

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

        Map user = this.managerService.findByName(super.UserName);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
        request.setAttribute("activeSessions", map);

        request.setAttribute("managerentity", managerService.findByName(super.UserName));
        request.setAttribute("managerextentity", managerextService.selectMap(new EntityWrapper<AppriesManagerext>().where("userid={0}", super.UserID)));
        request.setAttribute("productlist", productService.selectList(new EntityWrapper<OrderProduct>().where("canbuy=1")));
        List orderlist = managerService.getOrders(super.UserID);
        request.setAttribute("orderlist", orderlist);
        List orderprdlist = managerService.getOrdersProduct(super.UserID);
        request.setAttribute("orderprdlist", orderprdlist);


        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] totalrecords = new int[]{0};
        this.list = this.managerService.orderlicense(super.UserID, "", totalrecords, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager = new Pager("currentPage", pageSize, totalrecords[0], request, this);
        this.pager.setPageList(this.list);
        request.setAttribute("licenselist", this.pager);

        List useridAy = new ArrayList();
        for (Object detail : pager.getPageList())
            useridAy.add(((Map)detail).get("id"));

        List details = this.managerService.orderlicensedetail(useridAy);
        request.setAttribute("licensedetails", details);*/

        m_order();
        mlicense();
        mlogin();
        mprofile();
        return SUCCESS;
    }

    public String morder() {
        m_order();
        return SUCCESS;
    }

    public void m_order() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst={2} and datediff(d,audit_log.ctime,getdate())=0"
                , LogTypeEnum.登录.getCode(), super.UserName, agentLoginSucc);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1){
            request.setAttribute("data2", data2.get(1));
            ew = new EntityWrapper<>();
            ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst in ({2}) " +
                            "and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log " +
                            "where name={3} and logrst={4} and logtype={5} and ctime<{6})"
                    , LogTypeEnum.登录.getCode(), super.UserName, agentLoginError, super.UserName
                    , agentLoginSucc, LogTypeEnum.登录.getCode(), data2.get(1).get("ctime").toString());
            List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
            request.setAttribute("data", data);
        }

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

        Map user = this.managerService.findByName(super.UserName);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) context.getAttribute("activeSessions");
        request.setAttribute("activeSessions", map);

        request.setAttribute("managerentity", managerService.findByName(super.UserName));
        request.setAttribute("managerextentity", managerextService.selectMap(new EntityWrapper<AppriesManagerext>().where("userid={0}", super.UserID)));

        //request.setAttribute("productrate", );
        List<Map> productList = productService.querymap(new EntityWrapper<OrderProduct>().where("canbuy=1", super.UserID));
        List<AppriesManagerrate> rateList = managerrateService.selectList(new EntityWrapper<AppriesManagerrate>().where("managerid={0}", super.UserID));
        if(null!=rateList)
            for (int i=0; i<rateList.size(); i++){
                for (int j=0; j<productList.size(); j++){
                    if (rateList.get(i).getProductid().toString().equals(productList.get(j).get("id").toString()))
                    {
                        float f = Float.parseFloat(productList.get(j).get("price").toString())  * rateList.get(i).getRate();
                        productList.get(j).put("rateprice", CommonUtil.formatFloat(f));
                        break;
                    }
                }
            }
        for (int j=0; j<productList.size(); j++){
            if (!productList.get(j).containsKey("rateprice"))
            {
                productList.get(j).put("rateprice",  productList.get(j).get("price"));
            }
        }
        request.setAttribute("productlist", productList);
        for (int j=0; j<productList.size(); j++){
            System.out.println(new JSONObject(productList.get(j)).toString());
            System.out.println("rateprice//" + productList.get(j).get("rateprice"));
            Map m = productList.get(j);
        }

        List orderlist = managerService.getOrders(super.UserID);
        request.setAttribute("orderlist", orderlist);
        List orderprdlist = managerService.getOrdersProduct(super.UserID);
        request.setAttribute("orderprdlist", orderprdlist);
    }

    @Autowired
    private IAppriesManagerrateService managerrateService;

    public String mlicense()
    {
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] totalrecords = new int[]{0};
        this.list = this.managerService.orderlicense(super.UserID, "", totalrecords, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager = new Pager("currentPage", pageSize, totalrecords[0], request, this);
        this.pager.setPageList(this.list);
        request.setAttribute("licenselist", this.pager);

        List useridAy = new ArrayList();
        for (Object detail : pager.getPageList())
            useridAy.add(((Map)detail).get("id"));

        List prdlicensedetails = this.managerService.prdlicensedetail(useridAy);
        request.setAttribute("prdlicensedetails", prdlicensedetails);
        request.setAttribute("licensedetails", orderLicensedetailService.selectList(new EntityWrapper<OrderLicensedetail>().where("type=2 and managerid={0}", super.UserID)));
        return SUCCESS;
    }

    public String mlogin()
    {
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst={2} and datediff(d,audit_log.ctime,getdate())=0"
                , LogTypeEnum.登录.getCode(), super.UserName, agentLoginSucc);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1){
            request.setAttribute("data2", data2.get(1));
            ew = new EntityWrapper<>();
            ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst in ({2}) " +
                            "and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log " +
                            "where name={3} and logrst={4} and logtype={5} and ctime<{6})"
                    , LogTypeEnum.登录.getCode(), super.UserName, agentLoginError, super.UserName
                    , agentLoginSucc, LogTypeEnum.登录.getCode(), data2.get(1).get("ctime").toString());
            List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
            request.setAttribute("data", data);
        }

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

        Map user = this.managerService.findByName(super.UserName);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }

        request.setAttribute("managerentity", managerService.findByName(super.UserName));
        request.setAttribute("managerextentity", managerextService.selectMap(new EntityWrapper<AppriesManagerext>().where("userid={0}", super.UserID)));

        return SUCCESS;
    }

    public String mprofile()
    {
        HttpSession session = ServletActionContext.getRequest().getSession();
        ServletContext context = ServletActionContext.getServletContext();
        String agentLoginError = this.getText("auditlog.agentLoginError");
        String agentLoginSucc = this.getText("auditlog.agentLoginSucc");
        Date startDate  = req.getDate("startDate");
        Date endDate  = req.getDate("endDate");

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);

        EntityWrapper<AuditLog> ew = new EntityWrapper<>();

        ew = new EntityWrapper<>();
        ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst={2} and datediff(d,audit_log.ctime,getdate())=0"
                , LogTypeEnum.登录.getCode(), super.UserName, agentLoginSucc);
        List<Map> data2 = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
        if (data2!=null && data2.size()>1){
            request.setAttribute("data2", data2.get(1));
            ew = new EntityWrapper<>();
            ew.where("logtype={0} and audit_log.name={1} and audit_log.logrst in ({2}) " +
                            "and audit_log.ctime>(select isnull(max(ctime), convert(varchar(50), getdate(), 23)) from audit_log " +
                            "where name={3} and logrst={4} and logtype={5} and ctime<{6})"
                    , LogTypeEnum.登录.getCode(), super.UserName, agentLoginError, super.UserName
                    , agentLoginSucc, LogTypeEnum.登录.getCode(), data2.get(1).get("ctime").toString());
            List<Map> data = auditLogService.querymap(ew.orderBy("audit_log.ctime desc"));
            request.setAttribute("data", data);
        }

        Map manager = (Map) session.getAttribute("manager");
        request.setAttribute("curlogintime", manager.get("logintime"));
        request.setAttribute("curlogindevice", manager.get("logindevice"));
        request.setAttribute("curloginadd", manager.get("loginadd"));
        request.setAttribute("powers", manager.get("powers"));

        Map user = this.managerService.findByName(super.UserName);

        if (null != user){
            request.setAttribute("uexpired", user.get("uexpired"));
            request.setAttribute("pwdexpired", user.get("pwdexpired"));
        }

        request.setAttribute("managerentity", managerService.findByName(super.UserName));
        request.setAttribute("managerextentity", managerextService.selectMap(new EntityWrapper<AppriesManagerext>().where("userid={0}", super.UserID)));

        return SUCCESS;
    }

    public String orderCancel(){
        int orderid = req.getInt("id");
        String canceldesc = req.getString("canceldesc");

        int r = managerService.cancelOrder(orderid, canceldesc, super.UserID);
        if (r>0){
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "订单取消成功！");
        }
        else{
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "订单取消失败！");
        }
        return SUCCESS;
    }

    public String orderClose(){
        int orderid = req.getInt("id");
        String closedesc = req.getString("closedesc");

        int r = managerService.orderClose(orderid, closedesc, super.UserID);
        if (r>0){
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "订单已经关闭！");
        }
        else{
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "订单关闭失败！");
        }
        return SUCCESS;
    }

    public String orderAudit(){
        int orderid = req.getInt("id");
        float pay = req.getFloat("pay");
        String auditdesc = req.getString("auditdesc");
        if (pay <= 0){
            /*this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "订单审核失败，付款金额不能为0！");
            return SUCCESS;*/
        }
        Object[] rst = managerService.orderAudit(orderid, pay, auditdesc, super.UserID);
        if ((boolean)rst[0]){
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "订单审核成功！");
            return SUCCESS;
        }
        else{
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "订单审核失败:"+rst[1]);
            return SUCCESS;
        }
    }

    public String orderquery(){
        Date startDate  = req.getTime("startDate");
        Date endDate  = req.getTime("endDate");
        Integer status = req.getInt("status", null),
                ispay = req.getInt("ispay", null);

        request.setAttribute("startDate", DateUtils.date2String(startDate));
        request.setAttribute("endDate", DateUtils.date2String(endDate));
        request.setAttribute("status", status);
        request.setAttribute("ispay", ispay);

        Wrapper<OrderDetail> ew =new EntityWrapper<OrderDetail>();
        if (null!=status && !"".equals(status))
            ew = ew.eq("status", status);
        if (null!=ispay && !"".equals(ispay))
            ew = ew.eq("ispay", ispay);
        if (startDate!=null)
            ew = ew.where("ctime>={0}", startDate);
        if (endDate!=null)
            ew = ew.where("ctime<={0}", endDate);


        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] totalrecords = new int[]{0};
        this.list = this.managerService.findOrderDetail(status, ispay, startDate, endDate, totalrecords, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager = new Pager("currentPage", pageSize, totalrecords[0], request, this);
        this.pager.setPageList(this.list);
        request.setAttribute("orderlist", this.pager);

        List orderidAy = new ArrayList();
        if (null!=this.pager){
            for (Object detail : this.pager.getPageList()){
                orderidAy.add(((Map)detail).get("id"));
            }
        }
        //Page<OrderDetail> queryData = orderDetailService.selectPage(new Page<OrderDetail>(pageindex, pageSize), ew.orderBy("id desc"));
        //request.setAttribute("orderlist", queryData);

        //for (OrderDetail detail : queryData.getRecords())
        //    orderidAy.add(detail.getId());

        List orderprdlist = orderProductdetailService.selectList(new EntityWrapper<OrderProductdetail>().in("orderid", orderidAy));

        List details = this.managerService.getListOfproductdetail(orderidAy);
        request.setAttribute("orderprdlist", details);
        return SUCCESS;
    }

    public String dialoglicensedetail() {
        int productid= req.getInt("productid");
        request.setAttribute("details", orderLicensedetailService.selectList(new EntityWrapper<OrderLicensedetail>().where("type=2 and managerid={0} and productid={1}", super.UserID, productid)));
        return SUCCESS;
    }

    public String dialogdown() {
        int productid= req.getInt("productid");
        request.setAttribute("details", productversionService.selectList(new EntityWrapper<OrderProductversion>().where("productid={0}", productid)));
        return SUCCESS;
    }

    @Getter @Setter
    private String fileName;

    public String cerdownload() throws Exception {
        String mac = req.getString("mac");
        String prdcode = request.getParameter("prdcode"); // ZXEval-XF
        String androidVer = request.getParameter("androidVer");
        if (StringUtils.isNotBlank(mac) && StringUtils.isNotBlank(prdcode))
        {
            OrderProduct prd = productService.selectOne(new EntityWrapper<OrderProduct>().where("productcode={0}", prdcode));
            if (prd == null){
                request.setAttribute("msg", "不存在该产品编码！");
                getResponse().sendError(299, "不存在该产品编码！");
                return ERROR;
            }
            OrderLicensedetail detail = orderLicensedetailService.selectOne(new EntityWrapper<OrderLicensedetail>()
                    .where("type=2 and devicemac={0} and productid={1}", mac, prd.getId()));
            if (detail == null){
                request.setAttribute("msg", "设备未授权，请在授权页面对该设备进行授权！");
                getResponse().sendError(299, "设备未授权，请在授权页面对该设备进行授权！");
                return ERROR;
            }
            if (detail.getDownedtimes()>=detail.getTotaldowntimes()){
                request.setAttribute("msg", "该设备授权文件下载次数已经达到最大值，请与管理员联系！");
                getResponse().sendError(299, "该设备授权文件下载次数已经达到最大值，请与管理员联系！");
                return ERROR;
            }

            this.fileName = mac + ".cer" ;
            String cerfile = getServletContext().getRealPath("/") + "WEB-INF/cer/" + this.fileName;
            File f = new File(cerfile);
            if (!f.exists()){
                request.setAttribute("msg", "该设备授权文件不存在，请与管理员联系！");
                getResponse().sendError(299, "该设备授权文件不存在，请与管理员联系！");
                return ERROR;
            }
            detail.setLstdowntime(new Date());
            detail.setDownedtimes(detail.getDownedtimes() + 1);
            orderLicensedetailService.updateById(detail);
            return SUCCESS;
        }
        else{
            request.setAttribute("msg", "参数不正确！");
            getResponse().sendError(299, "参数不正确！");
            return ERROR;
        }
    }

    public InputStream getCerFile() throws Exception{
        String mac = req.getString("mac");
        if (StringUtils.isNotBlank(mac))
        {
            this.fileName = mac + ".cer" ;
            String cerfile = getServletContext().getRealPath("/") + "WEB-INF/cer/" + this.fileName;
            System.out.println(cerfile);
            File f = new File(cerfile);
            if (!f.exists()){
                return null;
            }
            try {
                return new FileInputStream(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String download() throws Exception {
        final HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        String Agent = request.getHeader("User-Agent");
        request.setAttribute("agent", Agent);
        String filepath = req.getString("filepath");
        if (StringUtils.isNotBlank(filepath))
        {
            String file = getServletContext().getRealPath("/") + filepath;
            File f = new File(file);
            if (!f.exists()){
                request.setAttribute("msg", "文件不存在！");
                getResponse().sendError(298, "文件不存在！");
                return ERROR;
            }
            String filename = req.getString("filename");
            filename = java.net.URLDecoder.decode(filename,"UTF-8");
            //byte filenamebuf[] = request.getParameter("filename").getBytes("iso8859-1");
            //String filename = new String(filenamebuf);

            if (StringUtils.isNotBlank(filename))
                this.fileName = filename;
            else
                this.fileName = filepath.lastIndexOf("/")>-1? filepath.substring(filepath.lastIndexOf("/")+1):filepath;
            System.out.println(this.fileName);
            try {
                //this.fileName = new String(this.fileName.getBytes("UTF-8"), "ISO-8859-1");
                this.fileName = java.net.URLEncoder.encode(this.fileName, "utf-8");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            //response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"iso-8859-1")+".xls");
            return SUCCESS;
        }
        else{
            request.setAttribute("msg", "参数不正确！");
            getResponse().sendError(298, "参数不正确！");
            return ERROR;
        }
    }

    public InputStream getDownloadFile() throws Exception{
        String filepath = req.getString("filepath");
        if (StringUtils.isNotBlank(filepath))
        {
            String file = getServletContext().getRealPath("/") + filepath;
            File f = new File(file);
            if (!f.exists()){
                return null;
            }
            try {
                return new FileInputStream(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String productmanage(){
        List list = productService.querymap(new EntityWrapper<Map>());
        request.setAttribute("list", list);
        return SUCCESS;
    }

    @Getter @Setter
    private File file;

    public String productFileUpload() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String dstPath = getServletContext().getRealPath("/");
        String contentType = request.getContentType();

        final long curTime = System.currentTimeMillis();
        String filename = request.getParameter("filename");
        final String nfilename = "upload/" + curTime + Math.round(Math.random() * 100.0) + this.getExtFileName(filename);
        if (this.file != null) {
            dstPath = dstPath.replaceAll("%20", " ");
            final File dst = new File(String.valueOf(dstPath) + nfilename);
            CommonUtil.copy(this.file, dst);

            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("filepath", nfilename);
            jsonData.put("filename", filename);
            jsonData.put("msg", this.getText("main.upload.succ"));
        }
        else{
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("main.upload.fail"));
        }
        return SUCCESS;
    }

    public String macUpload() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String dstPath = getServletContext().getRealPath("/");
        String contentType = request.getContentType();

        final long curTime = System.currentTimeMillis();
        final String nfilename = "upload/" + curTime + Math.round(Math.random() * 100.0) + ".txt";
        if (this.file != null) {
            FileInputStream inputStream = new FileInputStream(this.file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            StringBuffer sb = new StringBuffer();

            while((str = bufferedReader.readLine()) != null)
            {
                sb.append(str).append(",");
            }

            //close
            inputStream.close();
            bufferedReader.close();

            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("filecontent", sb.toString());
            jsonData.put("msg", this.getText("main.upload.succ"));
        }
        else{
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("main.upload.fail"));
        }
        return SUCCESS;
    }

    public String productversion(){
        int prdid = req.getInt("productid");
        request.setAttribute("list", productversionService.selectList(new EntityWrapper<OrderProductversion>().where("productid={0}", prdid)));
        return SUCCESS;
    }

    @Autowired
    private IOrderProductversionService productversionService;

    public String productversionpost(){
        Integer prdid = req.getInt("productid", 0);
        String filename = req.getString("filename");
        String filepath = req.getString("filepath");
        String comment = req.getString("comment");
        if (prdid == 0){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "系统错误");
        }
        if (StringUtils.isBlank(filename)){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "上传文件错误");
        }
        if (StringUtils.isBlank(filepath)){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "上传文件错误");
        }
        OrderProductversion orderProductversion = new OrderProductversion();
        orderProductversion.setProductid(prdid);
        orderProductversion.setFilename(filename);
        orderProductversion.setFilepath(filepath);
        orderProductversion.setCtime(new Date());
        if (filename.indexOf(".") == -1)
            orderProductversion.setApkver(filename);
        else
            orderProductversion.setApkver(filename.substring(0, filename.lastIndexOf(".")));
        orderProductversion.setComment(comment);
        orderProductversion.insert();

        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("msg", "成功");
        return SUCCESS;
    }

    public String productversiondel(){
        int id = req.getInt("id");
        OrderProductversion orderProductversion = productversionService.selectById(id);
        if (null!=orderProductversion){
            String dstPath = getServletContext().getRealPath("/");
            File f = new File(dstPath + orderProductversion.getFilepath());
            f.deleteOnExit();
            productversionService.deleteById(id);
            jsonData.clear();
            jsonData.put("succ", true);
        }
        else
        {
            jsonData.clear();
            jsonData.put("succ", false);
        }
        return SUCCESS;
    }

    public String productform(){
        int productid = req.getInt("productid");
        request.setAttribute("model", productService.selectById(productid));
        return SUCCESS;
    }

    public String productformpost(){
        OrderProduct entity = new OrderProduct();

        try {
            BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity, this.getLocale());
            if(entity.getId() == null || entity.getId()==0)
            {
                entity.setCuser(super.UserID);
                entity.setCtime(new Date());
            }

            boolean b = entity.insertOrUpdate();

            if (b){
                jsonData.clear();
                jsonData.put("succ", true);
                jsonData.put("msg", "保存成功");
            }
            else{
                jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", "保存失败");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "保存失败" + e.getMessage());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "保存失败" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "保存失败" + e.getMessage());
        }
        return SUCCESS;
    }

    public String managerrate(){
        int managerid = req.getInt("managerid");
        List list = managerService.findManagerRate(managerid);
        request.setAttribute("list", list);
        return SUCCESS;
    }

    public String managerratepost(){
        int managerid = req.getInt("managerid");
        int[] id = req.getIntArray("id", 0);
        int[] productid = req.getIntArray("productid", 0);
        float[] rates = req.getFloatArray("rate", 1);

        for (int i=0; i< productid.length; i++){
            AppriesManagerrate rate = new AppriesManagerrate();
            if(id[i]>0)
                rate.setId(id[i]);
            rate.setManagerid(managerid);
            rate.setProductid(productid[i]);
            rate.setRate(rates[i]);
            rate.insertOrUpdate();
        }
        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("msg", this.getText("main.save.succ"));
        return SUCCESS;
    }
}
