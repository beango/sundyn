package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysQueuecounter;
import com.sundyn.entity.SysQueuecustomervip;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.service.DeptService;
import com.sundyn.service.ISysQueuecounterService;
import com.sundyn.service.ISysQueuecustomervipService;
import com.sundyn.service.ISysQueuehallService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CustomerVipAction extends MainAction
{
    @Resource
    private ISysQueuecustomervipService customerVipService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;
    /*
    查询
     */
    public String vipQuery() throws Exception {
        String key_vipname = req.getString("vipname"),
        key_cardno = req.getString("cardno");
        Wrapper<SysQueuecustomervip> ew =new EntityWrapper<SysQueuecustomervip>();
        if (null!=key_vipname && !"".equals(key_vipname))
            ew = ew.like("vipname", key_vipname);
        if (null!=key_cardno && !"".equals(key_cardno))
            ew = ew.like("vipcardno", key_cardno);
        Page<SysQueuecustomervip> queryData = customerVipService.selectPage(new Page<SysQueuecustomervip>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("queryData", queryData);
        return "success";
    }

    /*
    添加/修改窗口
     */
    public String vipAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        SysQueuecustomervip entity = null;
        if(id!=0)
            entity = customerVipService.selectById(id);
        if (entity == null) {
            entity = new SysQueuecustomervip();
        }
        request.setAttribute("entity", entity);
        return "success";
    }

    /*
    添加/修改  提交数据
     */
    public String vipPost(){
        SysQueuecustomervip entity = new SysQueuecustomervip();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            boolean succ = false;
            if(entity.getId()==null || entity.getId() == 0){
                if (customerVipService.selectCount(new EntityWrapper<SysQueuecustomervip>().where("vipcardno={0}", entity.getVipcardno()))>0){
                    request.setAttribute("msg", "VIP卡号已经存在");
                    return "success";
                }
                succ = entity.insert();
            }
            else{
                if (customerVipService.selectCount(new EntityWrapper<SysQueuecustomervip>().where("vipcardno={0} and id!={1}", entity.getVipcardno(), entity.getId()))>0){
                    request.setAttribute("msg", "VIP卡号已经存在");
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
    public String vipDel(){
        int id = req.getInt("id");
        boolean succ = customerVipService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }
}
