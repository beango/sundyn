package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.Realm.CheckPermission;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.entity.SysJxdata;
import com.sundyn.service.DeptService;
import com.sundyn.service.EmployeeService;
import com.sundyn.service.ISysDictinfoService;
import com.sundyn.service.ISysJxdataService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import com.xuan.xutils.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SysJxDataAction extends MainAction {
    @Resource
    private ISysJxdataService sysJxdataService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private DeptService deptService;
    @Resource
    private ISysDictinfoService dictinfoService;

    /*
    绩效数据配置
     */
    //@RequiresPermissions("account:create")
    //@CheckPermission(permission={"123"})
    public String jxDataQuery() {
        try {
            String key = req.getString("key");
            String servicedate = req.getString("servicedate");
            Wrapper<Map> ew =new EntityWrapper<>();

            if (null!=key && !"".equals(key))
                ew = ew.where("ename like '%"+key+"%'").or("eno like '%"+key+"%'");
            if (null!=servicedate && !"".equals(servicedate))
                ew = ew.where("servicedate='"+servicedate+"'");

            Page<Map> queryData = sysJxdataService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy("ctime desc"));
            request.setAttribute("queryData", queryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /*
    添加/修改大厅
     */
    public String jxDataAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        List<SysDictinfo> ypfjGroup = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup='jxypfj'").orderBy("dictvalue"));
        request.setAttribute("ypfjGroup", ypfjGroup);
        SysJxdata entity = null;
        if(id!=0){
            entity = sysJxdataService.selectById(id);
        }

        request.setAttribute("entity", entity);
        return "success";
    }

    /*
   添加/修改大厅  提交数据
    */
    public String jxDataPost(){
        SysJxdata entity = new SysJxdata();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            String enostr = req.getString("enostr");
            if (StringUtils.isNotBlank(enostr) && StringUtils.isBlank(entity.getEno()))
                entity.setEno(enostr);
            ValidateUtil.validate(entity);
            Map employeemap = null;
            if (StringUtils.isNotBlank(entity.getEno()) && (entity.getDeptid() == null || entity.getDeptid().equals(0))){
                employeemap = employeeService.findByCardnum(entity.getEno());
            }
            if (StringUtils.isNotBlank(entity.getEno()) && (entity.getDeptid() != null && !entity.getDeptid().equals(0))) {
                employeemap = employeeService.findByCardnumAndDeptID(entity.getEno(), entity.getDeptid().toString());
            }
            if (employeemap == null){
                request.setAttribute("msg", "该工号不存在!");
                return "success";
            }

            if (employeemap == null)
            {
                request.setAttribute("msg", "该员工工号不存在!");
                return "success";
            }
            entity.setEname(employeemap.get("name").toString());
            Map deptMap = deptService.findDeptById((int)employeemap.get("deptid"));
            if (deptMap == null)
            {
                request.setAttribute("msg", "该员工部门不存在!");
                return "success";
            }
            if (entity.getYpfjset()!=null && entity.getYpfjset().equalsIgnoreCase("on")){
                if (entity.getYpfj() == null){
                    request.setAttribute("msg", "请选择否决原因");
                    return "success";
                }
            }
            entity.setDeptid(MapUtils.getInteger(deptMap, "id"));
            entity.setDeptname(deptMap.get("name").toString());
            boolean succ = false;
            if(entity.getId()==null || entity.getId() == 0){
                if (sysJxdataService.selectCount(new EntityWrapper<SysJxdata>().where("eno={0} and deptid={1}", entity.getEno(), entity.getDeptid()))>0){
                    request.setAttribute("msg", "该员工的数据已经存在");
                    return "success";
                }
                entity.setCtime(new Date());
                entity.setCuser(super.UserID==null?0:super.UserID);
                succ = entity.insert();
            }
            else{
                if (sysJxdataService.selectCount(new EntityWrapper<SysJxdata>().where("eno={0} and deptid={1} and id!={2}", entity.getEno(), entity.getDeptid(), entity.getId()))>0){
                    request.setAttribute("msg", "该员工的数据已经存在");
                    return "success";
                }
                succ = entity.updateById();
            }
            if (!succ){
                request.setAttribute("msg", "提交失败");
                return "success";
            }
        } catch (IllegalAccessException e) {e.printStackTrace();
            request.setAttribute("msg", "提交失败，系统错误");
        } catch (InvocationTargetException e) {e.printStackTrace();
            request.setAttribute("msg", "提交失败，系统错误");
        }  catch (ValidException e) {
            request.setAttribute("msg", e.getMessage());
        } catch (Exception e) { e.printStackTrace();
            request.setAttribute("msg", "提交失败，系统错误");
        }
        return "success";
    }

    /*
    删除
     */
    public String jxDataDel(){
        int id = req.getInt("id");
        boolean succ = sysJxdataService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }

    public String proxyOrgReview(){
        int id = req.getInt("id");
        boolean succ = sysJxdataService.updateForSet("status=1", new EntityWrapper<SysJxdata>().where("id={0}", id));
        if (!succ){
            request.setAttribute("msg", "审核失败");
        }
        return "success";
    }

    public String proxyOrgCancel(){
        int id = req.getInt("id");
        boolean succ = sysJxdataService.updateForSet("status=-1", new EntityWrapper<SysJxdata>().where("id={0}", id));
        if (!succ){
            request.setAttribute("msg", "注销失败");
        }
        return "success";
    }

    public String proxyOrgRestore(){
        int id = req.getInt("id");
        boolean succ = sysJxdataService.updateForSet("ispause=0", new EntityWrapper<SysJxdata>().where("id={0} and ispause=1", id));
        if (!succ){
            request.setAttribute("msg", "恢复失败");
        }
        return "success";
    }

    public String proxyOrgPause(){
        int id = req.getInt("id");
        boolean succ = sysJxdataService.updateForSet("ispause=1", new EntityWrapper<SysJxdata>().where("id={0} and (ispause=0 or ispause is null)", id));
        if (!succ){
            request.setAttribute("msg", "暂停失败");
        }
        return "success";
    }
}
