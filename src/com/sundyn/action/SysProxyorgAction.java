package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysProxy;
import com.sundyn.entity.SysProxyorg;
import com.sundyn.service.ISysProxyorgService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class SysProxyorgAction extends MainAction {
    @Resource
    private ISysProxyorgService sysProxyorgService;

    /*
    字典配置
     */
    public String proxyOrgQuery() {
        try {
            String orgcode = req.getString("orgcode");
            String orgname = req.getString("orgname");
            Wrapper<SysProxyorg> ew =new EntityWrapper<SysProxyorg>();

            if (null!=orgname && !"".equals(orgname))
                ew = ew.where("orgname like '%"+orgname+"%'");
            if (null!=orgcode && !"".equals(orgcode))
                ew = ew.where("orgcode like '%"+orgcode+"%'");

            Page<SysProxyorg> queryData = sysProxyorgService.selectPage(new Page<SysProxyorg>(pageindex, pageSize), ew.orderBy("ctime desc"));
            request.setAttribute("queryData", queryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /*
    添加/修改大厅
     */
    public String proxyOrgAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        SysProxyorg entity = null;
        if(id!=0){
            entity = sysProxyorgService.selectById(id);
        }

        request.setAttribute("entity", entity);
        return "success";
    }

    /*
   添加/修改大厅  提交数据
    */
    public String proxyOrgPost(){
        SysProxyorg entity = new SysProxyorg();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            System.out.println("null:" + (entity==null) + ":" + (new JSONObject(request.getParameterMap()).toString()));

            boolean succ = false;
            if(entity.getId()==null || entity.getId() == 0){
                if (sysProxyorgService.selectCount(new EntityWrapper<SysProxyorg>().where("orgcode={0}", entity.getOrgcode()))>0){
                    request.setAttribute("msg", "机构已经存在");
                    return "success";
                }
                entity.setCtime(new Date());
                succ = entity.insert();
            }
            else{
                if (sysProxyorgService.selectCount(new EntityWrapper<SysProxyorg>().where("orgcode={0} and id!={1}", entity.getOrgcode(), entity.getId()))>0){
                    request.setAttribute("msg", "机构已经存在");
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
    public String proxyOrgDel(){
        int id = req.getInt("id");
        boolean succ = sysProxyorgService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }

    public String proxyOrgReview(){
        int id = req.getInt("id");
        boolean succ = sysProxyorgService.updateForSet("status=1", new EntityWrapper<SysProxyorg>().where("id={0}", id));
        if (!succ){
            request.setAttribute("msg", "审核失败");
        }
        return "success";
    }

    public String proxyOrgCancel(){
        int id = req.getInt("id");
        boolean succ = sysProxyorgService.updateForSet("status=-1", new EntityWrapper<SysProxyorg>().where("id={0}", id));
        if (!succ){
            request.setAttribute("msg", "注销失败");
        }
        return "success";
    }

    public String proxyOrgRestore(){
        int id = req.getInt("id");
        boolean succ = sysProxyorgService.updateForSet("ispause=0", new EntityWrapper<SysProxyorg>().where("id={0} and ispause=1", id));
        if (!succ){
            request.setAttribute("msg", "恢复失败");
        }
        return "success";
    }

    public String proxyOrgPause(){
        int id = req.getInt("id");
        boolean succ = sysProxyorgService.updateForSet("ispause=1", new EntityWrapper<SysProxyorg>().where("id={0} and (ispause=0 or ispause is null)", id));
        if (!succ){
            request.setAttribute("msg", "暂停失败");
        }
        return "success";
    }
}
