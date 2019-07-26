package com.sundyn.action;

import com.sundyn.service.AppService;
import com.sundyn.service.DeviceService;
import com.sundyn.util.DateHelper;
import com.sundyn.util.FileInfo;
import com.sundyn.util.Pager;
import com.sundyn.utils.JavaXML;
import com.xuan.xutils.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeviceAction extends MainAction
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

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

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public AppService appService;
    private Pager pager;

    public InputStream getEvalbutton() {
        try{
            String layout = req.getString("layout");
            String filename = "evalbuttons.xml";
            if (StringUtils.isNotBlank(layout))
                filename = "evalbuttons-" + layout + ".xml";
            File f = JavaXML.XMLOutFile(filename);
            if (!f.exists())
            {
                filename = "evalbuttons.xml";
            }
            return ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/source/" + filename);
        }
        catch (Exception ex){
            return null;
        }
    }

    public void setEvalbutton(InputStream evalbutton) {
        this.evalbutton = evalbutton;
    }

    private InputStream evalbutton;

    public String batchquery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        //final int rowsCount = this.deviceService.getCount1();
        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] total = new int[1];
        List list = this.deviceService.findDeviceBatch((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), total);
        this.pager = new Pager("currentPage", pageSize, total[0], request, this);
        this.pager.setPageList(list);

        return "success";
    }

    public String androidAddDeviceByMac(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        String prdcode = request.getParameter("prdcode"); // ZXEval-XF
        String androidVer = request.getParameter("androidVer");
        if(mac!=null && !"".equals(mac)) {
            Map deviceMap = deviceService.findByMac(mac);
            deviceService.addDevice(deviceMap==null?"":deviceMap.get("id").toString(), "", mac, "", DateHelper.getInstance().getDataString_1(null), prdcode, androidVer);
            this.deviceService.findAndAddByMac(mac, prdcode, androidVer);

            deviceService.updateDeviceInfo(mac, prdcode, androidVer);
        }
        return "success";
    }

    public String batchToAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        request.setAttribute("vo",this.deviceService.findBatchById(id));
        Calendar now = Calendar.getInstance();
        request.setAttribute("curyear", now.get(Calendar.YEAR));
        request.setAttribute("curmonth", String.format("%02d", now.get(Calendar.MONTH) + 1));
        return "success";
    }

    public String batchAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        //final String batchid = request.getParameter("batchid");
        final String batchid_deviceclass_1 = request.getParameter("batchid_deviceclass_1");
        final String batchid_deviceclass_2 = request.getParameter("batchid_deviceclass_2");
        final String batchid_year = request.getParameter("batchid_year");
        final String batchid_month = request.getParameter("batchid_month");
        final String batchid_no = request.getParameter("batchid_no");

        final String batchname = request.getParameter("batchname");
        final String batchdate = request.getParameter("batchdate");
        String errmsg = "";
        JSONObject jo = new JSONObject();
        if (batchid_deviceclass_1 == null || batchid_deviceclass_1.equals("")) {
            errmsg = "设备类型码不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (batchid_deviceclass_2 == null || batchid_deviceclass_2.equals("")) {
            errmsg = "设备类型码不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (batchid_year == null || batchid_year.equals("")) {
            errmsg = "年不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (batchid_month == null || batchid_month.equals("")) {
            errmsg = "月不能为空!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        int m = Integer.valueOf(batchid_month);
        if (m>12 && m<1){
            errmsg = "不是正确的月份!";
            jo.put("succ", false);
            jo.put("errmsg", errmsg);
            request.setAttribute("msg", jo);
            return "success";
        }
        if (batchid_no == null || batchid_no.equals("")) {
            errmsg = "批次不能为空!";
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
        String batchid = batchid_deviceclass_1+batchid_deviceclass_2 + batchid_year + String.format("%02d", m) + batchid_no;
        if (id == null || id.equals("")){
            List exist = deviceService.findBatchByBatchId(batchid, null);
            if (exist!=null && exist.size()>0){
                errmsg = "批次号已经存在!";
                jo.put("succ", false);
                jo.put("errmsg", errmsg);
                request.setAttribute("msg", jo);
                return "success";
            }

            this.deviceService.addBatch(batchid, batchname, batchdate, batchid_deviceclass_1,batchid_deviceclass_2,batchid_year, String.format("%02d", m),batchid_no);
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
            this.deviceService.batchUpdate(id, batchid, batchname, batchdate, batchid_deviceclass_1,batchid_deviceclass_2,batchid_year, String.format("%02d", m),batchid_no);

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
        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] total = new int[1];
        String startDate = request.getParameter("startDate"),
                endDate = request.getParameter("endDate"),
                batchno = request.getParameter("batchno"),
                name = request.getParameter("name"),
                keymac = request.getParameter("mac");

        List list = this.deviceService.findDevice(name, batchno, keymac, startDate, endDate,(this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), total);
        this.pager = new Pager("currentPage", pageSize, total[0], request, this);
        this.pager.setPageList(list);
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        if (null != this.pager.getPageList()){
            for (int i=0; i<pager.getPageList().size(); i++){
                Map m = (Map)pager.getPageList().get(i);

                String mac = m.get("mac").toString();
                File macfile = new File(spath+"WEB-INF/cer/" + mac + ".cer");
                if (macfile.exists()){
                    m.put("cerfile", spath+"WEB-INF/cer/" + mac + ".cer");
                }
                else
                    m.put("cerfile", "");
            }
        }

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        request.setAttribute("mac", keymac);
        request.setAttribute("batchno", batchno);
        request.setAttribute("name", name);
        return "success";
    }

    public String deviceToAdd(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        if (id != null && !id.equals("")) {
            request.setAttribute("vo",this.deviceService.findDeviceById(id));
        }
        else{
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

            this.deviceService.addDevice("", batchid, mac, "", ctime, null, null);
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
    }

    public String CheckLivingUrl() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final Element root = new Element("check");
        Map newestApp = appService.getNewestApp();
        final Document Doc = new Document(root);
        if (newestApp != null) {
            final Element playlistversion = new Element("name").setText(String.valueOf(newestApp.get("appname")));
            root.addContent((Content)playlistversion);

            final Element applicationversion = new Element("version").setText(String.valueOf(newestApp.get("versionCode")));
            root.addContent((Content)applicationversion);

            final Element menulistversion = new Element("url").setText(String.valueOf(newestApp.get("appurl")));
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

    public String getevalbuttons(){
        return "downloadOk";
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
