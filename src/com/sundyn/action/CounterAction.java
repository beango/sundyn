package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.AppriesDept;
import com.sundyn.entity.SysQueuecounter;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.entity.SysQueueserial;
import com.sundyn.service.DeptService;
import com.sundyn.service.IAppriesDeptService;
import com.sundyn.service.ISysQueuecounterService;
import com.sundyn.service.ISysQueuehallService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import com.sundyn.vo.DeptVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class CounterAction extends MainAction
{
    @Resource
    private ISysQueuecounterService counterService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;
    @Resource
    private IAppriesDeptService deptServiceEx;
    /*
    查询
     */
    public String counterQuery() throws Exception {
        String key_hallid = req.getString("hallid");
        String key_countername = req.getString("countername");
        Wrapper<SysQueuecounter> ew =new EntityWrapper<SysQueuecounter>();
        if (null!=key_hallid && !"".equals(key_hallid))
            ew = ew.where("hallid={0}", key_hallid);
        if (null!=key_countername && !"".equals(key_countername))
            ew = ew.like("countername", key_countername);
        final String deptIds = this.deptService.findChildALLStr1234(null);
        if (null!=deptIds && !"".equals(deptIds))
            ew = ew.in("deptid", deptIds.split(","));
        Page<SysQueuecounter> queryData = counterService.selectListEx(new Page<SysQueuecounter>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("hallid", key_hallid);
        request.setAttribute("queryData", queryData);
        request.setAttribute("hallList", hallService.selectList(null));
        request.setAttribute("hallList", hallService.selectList(new EntityWrapper<SysQueuehall>().in("deptid", deptIds.split(","))));
        return "success";
    }

    /*
    添加/修改窗口
     */
    public String counterAdd(){
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");

        SysQueuecounter entity = null;
        if(id!=0)
            entity = counterService.selectById(id);
        if(deptid != 0)
            entity = counterService.selectOne(new EntityWrapper<SysQueuecounter>().where("deptId={0}", deptid));
        if (entity == null) {//添加时，大厅名称默认为dept表名称，且不允许修改
            entity = new SysQueuecounter();
            int fatherId = req.getInt("fatherId");
            if (fatherId!=0){
                SysQueuehall h = this.hallService.selectOne(new EntityWrapper<SysQueuehall>().where("deptid={0}", fatherId));
                if(null!=h)
                    entity.setHallid(h.getId());
            }
            entity.setCountername(req.getString("deptName"));
        }
        request.setAttribute("deptid", deptid);
        request.setAttribute("entity", entity);
        String deptIds = this.deptService.findChildALLStr1234(null);
        request.setAttribute("hallList", hallService.selectList(new EntityWrapper<SysQueuehall>().in("deptid", deptIds.split(","))));
        request.setAttribute("halldef", req.getString("halldef"));
        return "success";
    }

    /*
    添加/修改  提交数据
     */
    public String counterPost(){
        SysQueuecounter entity = new SysQueuecounter();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            SysQueuehall hallentity = hallService.selectById(entity.getHallid());
            if (hallentity!=null)
                entity.setHallno(hallentity.getHallno());
            if(null!=entity.getDeptid()&& entity.getDeptid()!=0){ //如果有depid,该参数从机构管理入口点进来配置，存储的是评价器id字段(appries_dept表)
                Map deptentity = deptService.findDeptById(entity.getDeptid());
                if (deptentity!=null)
                    entity.setDeptname(deptentity.get("name").toString());
            }
            else{//没有deptid参数，从“窗口管理”-“添加”进来的，自动添加一个默认的记录（appries_dept表）
                AppriesDept dept = new AppriesDept();
                dept.setFatherId(hallentity.getDeptid());
                dept.setName(entity.getCountername());
                dept.setRemark(entity.getCounterno());
                dept.setProvinceid(27);
                dept.setCityid(2194);
                dept.setLenvel(3);
                dept.setDeptType(0);
                if (dept.insert()){
                    entity.setDeptid(dept.getId());
                    entity.setDeptname(entity.getCountername());
                }
            }

            boolean succ = false;
            if(entity.getId()==null || entity.getId() == 0){
                if (counterService.selectCount(new EntityWrapper<SysQueuecounter>().where("hallid={0} and counterno={1}", entity.getHallid(), entity.getCounterno()))>0){
                    request.setAttribute("msg", "新增窗口号失败，窗口号已经存在");
                    return "success";
                }
                succ = entity.insert();
            }
            else{
                if (counterService.selectCount(new EntityWrapper<SysQueuecounter>().where("hallid={0} and counterno={1} and id!={2}", entity.getHallid(), entity.getCounterno(), entity.getId()))>0){
                    request.setAttribute("msg", "修改窗口号失败，窗口号已经存在");
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
        ClearCache_DEPT();
        return "success";
    }

    /*
    删除
     */
    public String counterDel(){
        int id = req.getInt("id");
        boolean succ = counterService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        ClearCache_DEPT();
        return "success";
    }
}
