package com.sundyn.action;

import com.sundyn.entity.AppriesManagerpower;
import com.sundyn.service.DeptService;
import com.sundyn.service.IAppriesManagerpowerService;
import com.sundyn.service.ManagerService;
import com.sundyn.service.PowerService;
import com.sundyn.util.CommonUtil;
import com.sundyn.util.impl.util;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterAction extends MainAction
{
    private static final long serialVersionUID = 7440588664847739180L;
    //@Resource
    //private Reg reg;
    @Resource
    private DeptService deptService;
    @Resource
    private ManagerService managerService;
    @Getter
    @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();

    public String registerView() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String sip = request.getHeader("x-forwarded-for");
        sip = request.getHeader("proxy-Client-IP");
        sip = request.getHeader("WL-Proxy-Client-IP");
//        this.reg = Reg.getInstance();
//        final int d = this.reg.getDay();
//        final String ser = this.reg.getSerial();
//        request.setAttribute("ser", (Object)ser);
//        final boolean check = this.reg.isRegCheck();
//        final int windwoNum = this.reg.getWindowNum();
//        final int datingNum = this.reg.getDatingNum();
//        if (check) {
//            request.setAttribute("msg", (Object)("\u60a8\u7684\u8f6f\u4ef6\u5df2\u7ecf\u6ce8\u518c<br/>\u60a8\u7684\u6ce8\u518c\u53f7\u6700\u591a\u53ea\u80fd\u4f7f\u7528" + windwoNum + "\u8bc4\u4ef7\u5668\uff0c" + datingNum + "\u5927\u5385\uff0c" + "\u5982\u679c\u8981\u4f7f\u7528\u66f4\u591a\u8d44\u6e90\uff0c\u8bf7\u518d\u6b21\u7533\u8bf7\u6ce8\u518c"));
//            return "success";
//        }
//        if (d > 0) {
//            request.setAttribute("msg", (Object)("\u4f60\u7684\u8f6f\u4ef6\u8fd8\u6709" + d + "\u5929\u5230\u671f\uff0c\u8bf7\u53ca\u65f6\u6ce8\u518c"));
//            return "success";
//        }
//        request.setAttribute("msg", (Object)"\u4f60\u7684\u8f6f\u4ef6\u5df2\u7ecf\u8fc7\u671f\uff0c\u8bf7\u53ca\u65f6\u6ce8\u518c");
        return "success";
    }

    public String mregister() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String sip = request.getHeader("x-forwarded-for");
        sip = request.getHeader("proxy-Client-IP");
        sip = request.getHeader("WL-Proxy-Client-IP");
        return "success";
    }

    public String registerReg() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String serTxt = request.getParameter("serTxt");
        //(this.reg = Reg.getInstance()).register(serTxt);
        //Reg.reset();
        return "success";
    }

    public String registerPost(){
        String name = req.getString("name");
        String password = req.getString("password");
        String password2 = req.getString("password2");
        String cellphone = req.getString("cellphone");
        String contact = req.getString("contact");
        String realname = req.getString("realname");
        String orgname = req.getString("orgname");

        if (StringUtils.isBlank(name))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.name.notull"));
            return SUCCESS;
        }

        if (!managerService.manageExist(null, name)){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.name.exists"));
            return SUCCESS;
        }

        if (StringUtils.isBlank(password))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.password.notull"));
            return SUCCESS;
        }

        if (StringUtils.isBlank(password2))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.password2.notull"));
            return SUCCESS;
        }

        if (!password.equals(password2))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.password2.notequal"));
            return SUCCESS;
        }
        if (StringUtils.isBlank(cellphone))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.field.phone.notexists"));
            return SUCCESS;
        }
        Object[] rst  = CommonUtil.isPhone(cellphone);
        if (!(Boolean)rst[0]){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", rst[1]);
            return SUCCESS;
        }
        if (StringUtils.isBlank(contact))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "联系人不能为空");
            return SUCCESS;
        }
        if (StringUtils.isBlank(realname))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "真实姓名不能为空");
            return SUCCESS;
        }
        if (StringUtils.isBlank(orgname))
        {
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "公司名称不能为空");
            return SUCCESS;
        }
        ManagerVo managerVo = new ManagerVo();
        managerVo.setName(name);
        managerVo.setPassword(util.md5(password));
        managerVo.setLocaluser(0);
        managerVo.setCtime(new Date());
        managerVo.setStatus(1);
        managerVo.setDeptid(1);
        managerVo.setTelphone(cellphone);
        managerVo.setRealname(realname);
        managerVo.setContact(contact);
        managerVo.setOrgname(orgname);
        try {
            int userid = managerService.AddManager(managerVo);
            jsonData.clear();
            jsonData.put("succ", userid>0);
            jsonData.put("msg", userid>0 ? this.getText("manager.regsucc") : this.getText("manager.regfail"));

            if (userid > 0){
                Map<String, Object> power = powerService.findByName("注册用户");
                if  (null!=power){
                    AppriesManagerpower appriesManagerpower = new AppriesManagerpower();
                    appriesManagerpower.setPowerid(Integer.valueOf(power.get("id").toString()));
                    appriesManagerpower.setManagerid(userid);
                    managerpowerService.insert(appriesManagerpower);
                }
            }
            return SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", this.getText("manager.regfail") + e.getMessage());
        }
        jsonData.clear();
        jsonData.put("succ", false);
        jsonData.put("msg", this.getText("manager.regfail") );
        return SUCCESS;
    }

    @Resource
    private PowerService powerService;

    @Resource
    private IAppriesManagerpowerService managerpowerService;
}
