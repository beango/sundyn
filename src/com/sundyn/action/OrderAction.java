package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.OrderProductdetail;
import com.sundyn.service.DeptService;
import com.sundyn.service.IOrderProductService;
import com.sundyn.service.IOrderProductdetailService;
import com.sundyn.service.ManagerService;
import com.sundyn.util.Pager;
import com.xuan.xutils.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAction extends MainAction {
    @Autowired
    private IOrderProductService productService;
    @Autowired
    private IOrderProductdetailService orderProductdetailService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private DeptService deptService;
    @Getter @Setter
    Map<String,Object> jsonData = new HashMap<String,Object>();
    @Getter @Setter
    private Pager pager;

    public String productdel(){
        int prdid = req.getInt("id");
        if (orderProductdetailService.selectCount(new EntityWrapper<OrderProductdetail>().where("productid={0}", prdid))>0){
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "删除失败，已经存在该产品的订单！");
            return SUCCESS;
        }
        boolean b = productService.deleteById(prdid);
        if (b){
            jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", "删除成功");
        }
        else{
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "删除失败");
        }
        return SUCCESS;
    }


    public String orderlicense(){
        String key = req.getString("key");
        request.setAttribute("key", key);

        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] totalrecords = new int[]{0};
        List list = this.managerService.orderlicense(null, key, totalrecords, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager = new Pager("currentPage", pageSize, totalrecords[0], request, this);
        this.pager.setPageList(list);
        request.setAttribute("list", this.pager);

        List orderidAy = new ArrayList();
        for (Object detail : pager.getPageList())
            orderidAy.add(((Map)detail).get("id"));

        List details = this.managerService.orderlicensedetail(orderidAy);
        request.setAttribute("details", details);

        return SUCCESS;
    }

    public String orderLincese(){
        String mac = req.getString("mac");
        Integer productid = req.getInt("productid", 0);
        if (StringUtils.isBlank(mac)){
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "没有填写MAC！");
            return SUCCESS;
        }
        if (mac.length()>2000){
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "MAC地址过多，请分多次提交！");
            return SUCCESS;
        }
        mac = mac.replace("，", ",");
        ArrayList<String> macArr = new ArrayList<>();
        System.out.println("++++++++++++" +  mac.split(",").length);
        for (String m : mac.split(",")){
            if (StringUtils.isNotBlank(m) && m.length()!=12)
            {
                this.jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", "MAC地址长度必须为12位！");
                return SUCCESS;
            }
            if (StringUtils.isNotBlank(m) && macArr.contains(m))
            {
                this.jsonData.clear();
                jsonData.put("succ", false);
                jsonData.put("msg", "存在重复的MAC地址！");
                return SUCCESS;
            }
            if (StringUtils.isNotBlank(m))
                macArr.add(m);
        }
        if (productid == null || productid ==0){
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", "请选择需要授权的产品类型！");
            return SUCCESS;
        }
        Object[] rst = managerService.order_license(super.UserID, String.join(",", macArr), productid);
        if ((boolean)rst[0]){
            for (String _mac : macArr){
                deptService.DeviceCer(_mac, "");
            }
            //List list = managerService.getOrderlicense_devicelist(super.UserID, macArr, productid);
            this.jsonData.clear();
            jsonData.put("succ", true);
            jsonData.put("msg", rst[1]);
            return SUCCESS;
        }
        else{
            this.jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", rst[1]);
            return SUCCESS;
        }
    }

}
