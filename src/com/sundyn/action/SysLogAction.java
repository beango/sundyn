package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.SysLog;
import com.sundyn.service.ISysDevicesService;
import com.sundyn.service.ISysLogService;

import javax.annotation.Resource;
import java.util.Map;

public class SysLogAction extends MainAction {
    @Resource
    private ISysLogService sysLogService;
    @Resource
    private ISysDevicesService sysDevicesService;
    /*
    字典配置
     */
    public String query() {
        try {
            String managername = req.getString("managername");

            Wrapper<Map> ew =new EntityWrapper<Map>().where("actionname!='' ");

            if (null!=managername && !"".equals(managername))
                ew = ew.where("realname like '%"+managername+"%'");

            Page<Map> queryData = sysLogService.selectListEx(new Page<Map>(pageindex, pageSize), ew.orderBy("actiontime desc"));
            request.setAttribute("queryData", queryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    public String sysdevices() {
        try {
            String devicename = req.getString("devicename");

            Wrapper<Map> ew =new EntityWrapper<Map>();

            if (null!=devicename && !"".equals(devicename))
                ew = ew.where("devicename like '%"+devicename+"%'");

            Page<Map> queryData = sysDevicesService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy("lastonlinetime desc"));
            request.setAttribute("queryData", queryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
