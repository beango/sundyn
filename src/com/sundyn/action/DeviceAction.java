package com.sundyn.action;

import com.sundyn.service.DeviceService;
import com.sundyn.util.DateHelper;
import com.sundyn.util.Pager;
import com.sundyn.utils.JavaXML;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
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

    public String androidAddDeviceByMac(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        if(mac!=null && !"".equals(mac))
            this.deviceService.findAndAddByMac(mac);
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
        List list = this.deviceService.findDeviceByBatchId(id);
        if (list!=null && list.size()>0){
            request.setAttribute("msg","该批次下存在未删除的设备!");
            return "error";
        }

        this.deviceService.batchDelete(id);
        request.setAttribute("msg","删除成功!");
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
        String startDate = request.getParameter("startDate"),
                endDate = request.getParameter("endDate"),
                batchno = request.getParameter("batchno"),
                keymac = request.getParameter("mac");

        List list = this.deviceService.findDevice(batchno, keymac, startDate, endDate,(this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), total);
        this.pager = new Pager("currentPage", pageSize, total[0], request);
        this.pager.setPageList(list);
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        for (int i=0; i<pager.getPageList().size(); i++){
            Map m = (Map)pager.getPageList().get(i);

            String mac = m.get("mac").toString();
            File macfile = new File(spath+"cer\\" + mac + ".cer");
            if (macfile.exists()){
                m.put("cerfile", spath+"cer\\" + mac + ".cer");
            }
            else
                m.put("cerfile", "");
        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("mac", keymac);
        request.setAttribute("batchno", batchno);
        return "success";
    }

    public String deviceToAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            request.setAttribute("vo",this.deviceService.findDeviceById(id));
        }
        else{
            System.out.println("????");
            request.setAttribute("ctime", DateHelper.getInstance().getDataString_1(new Date()));
        }
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
        List list = this.deviceService.findDeviceByBatchId(id);
        if (list!=null && list.size()>0){
            request.setAttribute("msg","该批次下存在未删除的设备!");
            return "error";
        }
        request.setAttribute("msg","该批次下存在未删除的设备!");
        return "error";

        /*this.deviceService.deviceDelete(id);
        JSONObject jo = new JSONObject();
        jo.put("succ", true);
        jo.put("errmsg", "删除成功");
        request.setAttribute("msg", jo);
        return "success";*/
    }

    public String CheckLivingUrl() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final Element root = new Element("check");
        final Document Doc = new Document(root);
        List employInfoSet = deviceService.findBatchByBatchId("", "");
        if (employInfoSet != null) {
            final Element playlistversion = new Element("playlistversion").setText("0");
            root.addContent((Content)playlistversion);

            final Element applicationversion = new Element("applicationversion").setText("1");
            root.addContent((Content)applicationversion);

            final Element menulistversion = new Element("menulistversion").setText("0");
            root.addContent((Content)menulistversion);

            final Element installtable = new Element("installtable").setText("");
            root.addContent((Content)installtable);

            final Element welcome = new Element("welcome").setText("");
            root.addContent((Content)welcome);
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + "DeviceLivingData.xml"));

        File f = new File(String.valueOf(file) + "DeviceLivingData.xml");
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(f));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        request.setAttribute("msg", sb.toString());
        return "success";
    }

    /*

     */
    public String ApplicationUrl(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");

        JSONObject jo = new JSONObject();
        jo.put("versionCode", 2);
        jo.put("versionName", "1.1.1");
        jo.put("content", "1.新增抢单功能#2.性能优化");
        jo.put("minSupport", 1);
        jo.put("url", "http://111.230.14.84:8080/update/ZXEval-1.0.apk");

        request.setAttribute("msg", jo.toString());
        return "success";
    }
}
