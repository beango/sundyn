package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.InteLog;
import com.sundyn.entity.SysBlacklist;
import com.sundyn.entity.SysProxy;
import com.sundyn.service.DeptService;
import com.sundyn.service.ISysBlacklistService;
import com.sundyn.service.ISysProxyService;
import com.sundyn.service.ISysQueuehallService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

public class BlackListAction extends MainAction
{
    @Resource
    private ISysBlacklistService sysBlacklistService;
    @Resource
    private ISysProxyService sysProxyService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;
    /*
    大厅查询
     */
    public String blackQuery() throws Exception {
        String key_idcard = req.getString("idcard");
        String key_name = req.getString("name");
        Wrapper<SysBlacklist> ew =new EntityWrapper<>();
        if (null!=key_idcard && !"".equals(key_idcard))
            ew = ew.where("idcard={0}", key_idcard);
        if (null!=key_name && !"".equals(key_name))
            ew = ew.like("name", key_name);
        Page<SysBlacklist> queryData = sysBlacklistService.selectPage(new Page<SysBlacklist>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("queryData", queryData);
        return "success";
    }

    /*
    添加/修改大厅
     */
    public String blackAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        SysBlacklist hall = null;
        if(id!=0){
            hall = sysBlacklistService.selectById(id);
        }

        request.setAttribute("entity", hall);
        return "success";
    }

    /*
    添加/修改大厅  提交数据
     */
    public String blackPost(){
        SysBlacklist entity = new SysBlacklist();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            boolean succ = false;
            if(entity.getId() == 0){
                if (sysBlacklistService.selectCount(new EntityWrapper<SysBlacklist>().where("idcard={0}", entity.getIdcard()))>0){
                    request.setAttribute("msg", "黑名单已经存在");
                    return "success";
                }
                entity.setCtime(new Date());
                succ = entity.insert();
            }
            else{
                if (sysBlacklistService.selectCount(new EntityWrapper<SysBlacklist>().where("idcard={0} and id!={1}", entity.getIdcard(), entity.getId()))>0){
                    request.setAttribute("msg", "黑名单已经存在");
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
    public String blackDel(){
        int id = req.getInt("id");
        boolean succ = sysBlacklistService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }


    /*
    大厅查询
     */
    public String proxyQuery() throws Exception {
        String key_idcard = req.getString("idcard");
        String key_name = req.getString("name");
        Wrapper<Map> ew =new EntityWrapper<>();
        if (null!=key_idcard && !"".equals(key_idcard))
            ew = ew.where("idcard={0}", key_idcard);
        if (null!=key_name && !"".equals(key_name))
            ew = ew.like("name", key_name);
        Page<Map> queryData = sysProxyService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy("id desc"));
        request.setAttribute("queryData", queryData);
        return "success";
    }

    /*
    添加/修改大厅
     */
    public String proxyAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        SysProxy hall = null;
        if(id!=0){
            hall = sysProxyService.selectById(id);
        }

        request.setAttribute("entity", hall);
        return "success";
    }

    /*
    添加/修改大厅  提交数据
     */
    public String proxyPost(){
        SysProxy entity = new SysProxy();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            boolean succ = false;
            System.out.println("++++" + JSONObject.fromObject(entity).toString());
            if(entity.getId() == 0){
                if (sysProxyService.selectCount(new EntityWrapper<SysProxy>().where("idcard={0}", entity.getIdcard()))>0){
                    request.setAttribute("msg", "代理人已经存在");
                    return "success";
                }
                entity.setCtime(new Date());
                succ = entity.insert();
            }
            else{
                if (sysProxyService.selectCount(new EntityWrapper<SysProxy>().where("idcard={0} and id!={1}", entity.getIdcard(), entity.getId()))>0){
                    request.setAttribute("msg", "代理人已经存在");
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
        }  catch (ValidException e) {e.getMessage();
            request.setAttribute("msg", e.getMessage());
        } catch (Exception e) { e.printStackTrace();
            request.setAttribute("msg", "提交失败，系统错误");
        }
        return "success";
    }

    /*
    删除
     */
    public String proxyDel(){
        int id = req.getInt("id");
        boolean succ = sysProxyService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }
}
