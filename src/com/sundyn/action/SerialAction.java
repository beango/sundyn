package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.opensymphony.xwork2.Action;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.entity.SysQueueserial;
import com.sundyn.service.DeptService;
import com.sundyn.service.ISysQueuehallService;
import com.sundyn.service.ISysQueueserialService;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerialAction extends MainAction
{
    @Resource
    private ISysQueueserialService serialService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;
    @Getter @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();
    /*
    大厅查询
     */
    public String serialQuery() throws Exception {
        String key_hallid = req.getString("hallid");
        String key_bizname = req.getString("bizname");
        final String deptIds = this.deptService.findChildALLStr1234(null);
        Wrapper<SysQueueserial> ew =new EntityWrapper<SysQueueserial>();
        if (null!=deptIds && !"".equals(deptIds))
            ew = ew.in("deptid", deptIds.split(","));
        if (null!=key_hallid && !"".equals(key_hallid))
            ew = ew.where("hallid={0}", key_hallid);
        if (null!=key_bizname && !"".equals(key_bizname))
            ew = ew.like("bizname", key_bizname);
        Page<SysQueueserial> queryData = serialService.selectListEx(new Page<SysQueueserial>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");

        request.setAttribute("hallid", key_hallid);
        request.setAttribute("bizname", key_bizname);
        request.setAttribute("queryData", queryData);
        request.setAttribute("hallList", hallService.selectList(null));
        return "success";
    }

    /*
    添加/修改大厅
     */
    public String serialAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        int deptid = req.getInt("deptid");
        int hallid = req.getInt("hallid");
        String bizid = req.getString("bizid");

        SysQueueserial hall = null;
        if(id!=0)
            hall = serialService.selectById(id);
        if(hallid != 0 && bizid != null)
            hall = serialService.selectOne(new EntityWrapper<SysQueueserial>().where("hallid={0} and bizid={1}", hallid, bizid));
        if(deptid != 0)
            hall = serialService.selectOne(new EntityWrapper<SysQueueserial>().where("deptId={0}", deptid));
        if (hall == null) {//添加时，大厅名称默认为dept表名称，且不允许修改
            hall = new SysQueueserial();
        }

        request.setAttribute("entity", hall);
        request.setAttribute("hallList", hallService.selectList(null));
        return "success";
    }

    /*
    添加/修改大厅  提交数据
     */
    public String serialPost(){
        SysQueueserial entity = new SysQueueserial();
        try {
            BeanUtils.populate(entity, request.getParameterMap());
            ValidateUtil.validate(entity);
            SysQueuehall hallentity = hallService.selectById(entity.getHallid());
            Map deptentity = deptService.findDeptById(hallentity.getDeptid());
            entity.setDeptid(Integer.valueOf(deptentity.get("id").toString()));
            entity.setDeptname(deptentity.get("name").toString());
            boolean succ = false;
            if(entity.getId() == 0){
                if (serialService.selectCount(new EntityWrapper<SysQueueserial>().where("hallid={0} and bizid={1}", entity.getHallid(), entity.getBizid()))>0){
                    request.setAttribute("msg", "业务事项id已经存在");
                    return "success";
                }
                succ = entity.insert();
            }
            else{
                if (serialService.selectCount(new EntityWrapper<SysQueueserial>().where("hallid={0} and bizid={1} and id!={2}", entity.getHallid(), entity.getBizid(), entity.getId()))>0){
                    request.setAttribute("msg", "业务事项id已经存在");
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
    public String serialDel(){
        int id = req.getInt("id");
        boolean succ = serialService.deleteById(id);
        if (!succ){
            request.setAttribute("msg", "删除失败");
        }
        return "success";
    }

    public String getHallSerial(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int hallid = req.getInt("hallid");
        List<SysQueueserial> list = serialService.selectList(new EntityWrapper<SysQueueserial>().where("hallid={0}", hallid));
        JSONArray ja = new JSONArray();
        for (SysQueueserial serial : list){
            JSONObject jo = new JSONObject();
            jo.put("id", serial.getBizid());
            jo.put("name", serial.getBizname());
            ja.put(jo);
        }
        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("data", ja.toString());
        return Action.SUCCESS;
    }
}
