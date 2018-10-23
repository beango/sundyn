package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.entity.QueueDetail;
import com.sundyn.entity.SysQueuecounter;
import com.sundyn.entity.SysQueuehall;
import com.sundyn.entity.SysQueuehisdetail;
import com.sundyn.service.*;
import com.sundyn.util.ValidException;
import com.sundyn.util.ValidateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class HisDetailAction extends MainAction
{
    @Resource
    private IQueueDetailService detailService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private DeptService deptService;
    /*
    查询
     */
    public String hisDetailQuery() throws Exception {
        String key_hallid = req.getString("hallid");
        String key_countername = req.getString("countername");
        Wrapper<QueueDetail> ew =new EntityWrapper<QueueDetail>();
        if (null!=key_hallid && !"".equals(key_hallid))
            ew = ew.where("hallid={0}", key_hallid);
        if (null!=key_countername && !"".equals(key_countername))
            ew = ew.like("countername", key_countername);
        Page<QueueDetail> queryData = detailService.selectPage(new Page<QueueDetail>(pageindex, pageSize), ew.orderBy("id desc"));
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println(queryData.getRecords().size());
        request.setAttribute("hallid", key_hallid);
        request.setAttribute("queryData", queryData);
        request.setAttribute("hallList", hallService.selectList(null));
        return "success";
    }
}
