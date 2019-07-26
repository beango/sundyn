package com.sundyn.action;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.service.ISysSmsdetailService;
import com.xuan.xutils.utils.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangding
 * @since 2019-05-30
 */

public class SysSmsdetailAction extends MainAction {
    @Resource
    private ISysSmsdetailService smsdetailService;

    public String smsQuery() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            String type = req.getString("type");
            String deptId = req.getString("deptId");
            String deptname = req.getString("deptname");
            String sort = req.getString("sort");
            if (StringUtils.isBlank(sort))
                sort = "sys_smsdetail.ctime,desc";
            final Integer employeeId = req.getInt("employeeId");
            String keyCardNum = null;

            sort = sort.replace(",", " ");
            String ypfj = req.getString("ypfj");

            Wrapper<Map> ew =new EntityWrapper<Map>();
            if (StringUtils.isNotBlank(type)){
                ew = ew.eq("type", type);
            }
            if (ypfj.equalsIgnoreCase("1")){
                ew = ew.where("(ypfj is not null and ypfj!=0)");
            }
            if (ypfj.equalsIgnoreCase("0")){
                ew = ew.where("(ypfj is null or ypfj=0)");
            }
            request.setAttribute("ypfj", ypfj);
            Page<Map> queryData = smsdetailService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy(sort));
            request.setAttribute("queryData", queryData);
            request.setAttribute("type", type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}

