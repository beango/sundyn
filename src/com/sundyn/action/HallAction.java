package com.sundyn.action;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.service.DeptService;
import com.sundyn.service.ISysQueuehallService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import com.sundyn.util.impl.util;
import com.sundyn.vo.ManagerVo;
import com.xuan.xutils.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class HallAction extends MainAction
{
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    Map<String,Object> jsonData = new HashMap<String,Object>();
    /*
    大厅查询
     */
    public String hallQuery() throws Exception {
        String key_hallname = request.getParameter("hallname"),
                key_deptId = req.getString("deptId");
        Wrapper<SysQueuehall> ew =new EntityWrapper<SysQueuehall>();
        if (null!=key_hallname && !"".equals(key_hallname))
            ew = ew.like("hallname", key_hallname, SqlLike.DEFAULT);
        String key_deptids = this.deptService.findChildALLStr1234(key_deptId);
        ew = ew.in("deptid", key_deptids.split(","));

        Page<SysQueuehall> queryData = hallService.selectPage(new Page<SysQueuehall>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        request.setAttribute("queryData", queryData);
        request.setAttribute("key_hallname", key_hallname);
        return "success";
    }

    /*
    添加/修改大厅
     */
    public String hallAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");

        SysQueuehall hall = null;
        if(id!=0)
            hall = hallService.selectById(id);
        if(deptid!=0)
            hall = hallService.selectOne(new EntityWrapper<SysQueuehall>().where("deptId={0}", deptid));
        if (hall == null) {//添加时，大厅名称默认为dept表名称，且不允许修改
            hall = new SysQueuehall();
            hall.setHallname(req.getString("deptName"));
        }
        request.setAttribute("hall", hall);
        return "success";
    }

    private String genCheckDigit(SysQueuehall model){
        StringBuilder digitStr = new StringBuilder();

        digitStr.append(model.getHallno());
        digitStr.append("|");

        digitStr.append(model.getHallname());
        digitStr.append("|");

        return (util.md5(digitStr.toString()));
    }

    /*
    添加/修改大厅  提交数据
     */
    public String hallPost(){
        SysQueuehall hall = new SysQueuehall();
        try {
            BeanUtils.populate(hall, request.getParameterMap());
            ValidateUtil.validate(hall);
            hall.setCheckdigit(genCheckDigit(hall));
            boolean succ = false;
            if(hall.getId() == null || hall.getId() == 0){
                if (hallService.selectCount(new EntityWrapper<SysQueuehall>().where("hallno={0}", hall.getHallno()))>0){
                    request.setAttribute("msg", "大厅编码已经存在");
                    return "success";
                }
                succ = hall.insert();
            }
            else{
                if (hallService.selectCount(new EntityWrapper<SysQueuehall>().where("hallno={0} and id!={1}", hall.getHallno(), hall.getId()))>0){
                    request.setAttribute("msg", "大厅编码已经存在");
                    return "success";
                }
                if (hall.getDeptid()==0)
                    hall.setDeptid(null);
                hall.setCheckdigited(0);
                succ = hall.updateById();
            }
            if (!succ){
                request.setAttribute("msg", "提交失败");
                return "success";
            }
        } catch (IllegalAccessException e) {
            request.setAttribute("msg", "提交失败，系统错误");
        } catch (InvocationTargetException e) {
            request.setAttribute("msg", "提交失败，系统错误");
        }  catch (ValidException e) {
            request.setAttribute("msg", e.getMessage());
        } catch (Exception e) {
            request.setAttribute("msg", "提交失败，系统错误");
        }
        return "success";
    }

    /*
    删除
     */
    public String hallDel(){
        int id = req.getInt("id");
        boolean succ = hallService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }


}
