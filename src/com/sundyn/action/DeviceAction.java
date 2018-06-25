package com.sundyn.action;

import com.sundyn.service.AdviceService;
import com.sundyn.service.DeviceService;
import com.sundyn.statistics.AdviceStatistics;
import com.sundyn.util.Pager;
import com.sundyn.utils.*;
import com.sundyn.vo.AdviceVo;
import com.sundyn.vo.AnswerVo;
import com.sundyn.vo.CheckVo;
import com.sundyn.vo.QuestionVo;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DeviceAction extends MainAction
{
    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public DeviceService deviceService;
    private Pager pager;
    
    public String batchquery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        //final int rowsCount = this.deviceService.getCount1();
        this.pager = new Pager("currentPage", pageSize, 0, request);
        int[] total = new int[1];
        List list = this.deviceService.findDeviceBatch((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), total);
        this.pager = new Pager("currentPage", pageSize, total[0], request);
        this.pager.setPageList(list);

        return "success";
    }

    public String batchToAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        request.setAttribute("vo",this.deviceService.findBatchById(id));
        return "success";
    }

    public String batchAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final String batchid = request.getParameter("batchid");
        final String batchname = request.getParameter("batchname");
        final String batchdate = request.getParameter("batchdate");
        String errmsg = "";
        JSONObject jo = new JSONObject();
        if (batchid == null || batchid.equals("")) {
            errmsg = "批次号不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }

        if (batchname == null || batchname.equals("")) {
            errmsg = "批次名不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (batchdate == null || batchdate.equals("")) {
            errmsg = "日期不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (id == null || id.equals("")){
            List exist = deviceService.findBatchByBatchId(batchid, null);
            if (exist!=null && exist.size()>0){
                errmsg = "批次号已经存在!";
                jo.put("succ", false);
                jo.put("errmsg", errmsg);
                request.setAttribute("msg", jo);
                return "success";
            }

            this.deviceService.addBatch(batchid, batchname, batchdate);
            jo.put("succ", true);
            jo.put("errmsg", "添加成功");
            request.setAttribute("msg", jo);
        }
        else{
            List exist = deviceService.findBatchByBatchId(batchid,id);
            if (exist!=null && exist.size()>0){
                errmsg = "批次号已经存在!";
                jo.put("succ", false);
                jo.put("errmsg", errmsg);
                request.setAttribute("msg", jo);
                return "success";
            }
            this.deviceService.batchUpdate(id, batchid, batchname, batchdate);

            jo.put("succ", true);
            jo.put("errmsg", "修改成功");
            request.setAttribute("msg", jo);
        }
        return "success";
    }

    public String batchDelete() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        this.deviceService.batchDelete(id);
        JSONObject jo = new JSONObject();
        jo.put("succ", true);
        jo.put("errmsg", "删除成功");
        request.setAttribute("msg", jo);
        return "success";
    }

    public String batchToUpate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        request.setAttribute("vo",this.deviceService.findBatchById(id));
        return "success";
    }

    public String deviceList() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        //final int rowsCount = this.deviceService.getCount1();
        this.pager = new Pager("currentPage", pageSize, 0, request);
        int[] total = new int[1];
        List list = this.deviceService.findDevice((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), total);
        this.pager = new Pager("currentPage", pageSize, total[0], request);
        this.pager.setPageList(list);
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        for (int i=0; i<pager.getPageList().size(); i++){
            Map m = (Map)pager.getPageList().get(i);

            String mac = m.get("mac").toString();
            File macfile = new File(spath+"cer\\" + mac + ".cer");
            System.out.println("权限文件地址:" + spath+"cer\\" + mac + ".cer");
            if (macfile.exists()){
                m.put("cerfile", spath+"cer\\" + mac + ".cer");
            }
            else
                m.put("cerfile", "");
        }
        return "success";
    }

    public String deviceToAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        request.setAttribute("vo",this.deviceService.findDeviceById(id));
        request.setAttribute("batchList",this.deviceService.findBatch());
        return "success";
    }

    public String deviceAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final String batchid = request.getParameter("batchid");
        final String mac = request.getParameter("mac");
        final String ctime = request.getParameter("ctime");
        String errmsg = "";
        JSONObject jo = new JSONObject();

        if (mac == null || mac.equals("")) {
            errmsg = "MAC地址不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (id == null || id.equals("")){
            List exist = deviceService.existDeviceByMAC(mac, null);
            if (exist!=null && exist.size()>0){
                errmsg = "MAC地址已经存在!";
                jo.put("succ", false);
                jo.put("errmsg", errmsg);
                request.setAttribute("msg", jo);
                return "success";
            }

            this.deviceService.addDevice(batchid, mac, false,"", ctime);
            jo.put("succ", true);
            jo.put("errmsg", "添加成功");
            request.setAttribute("msg", jo);
        }
        else{
            List exist = deviceService.existDeviceByMAC(mac, id);
            if (exist!=null && exist.size()>0){
                errmsg = "MAC地址已经存在!";
                jo.put("succ", false);
                jo.put("errmsg", errmsg);
                request.setAttribute("msg", jo);
                return "success";
            }
            this.deviceService.deviceUpdate(id, batchid, mac, false,"", ctime);

            jo.put("succ", true);
            jo.put("errmsg", "修改成功");
            request.setAttribute("msg", jo);
        }
        return "success";
    }

    public String deviceDelete() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        this.deviceService.deviceDelete(id);
        JSONObject jo = new JSONObject();
        jo.put("succ", true);
        jo.put("errmsg", "删除成功");
        request.setAttribute("msg", jo);
        return "success";
    }
}
