package com.sundyn.action;

import com.opensymphony.xwork2.*;
import org.apache.log4j.*;
import com.sundyn.service.*;
import com.sundyn.entity.*;
import org.apache.struts2.*;
import com.sundyn.vo.*;
import javax.servlet.http.*;
import java.text.*;
import com.sundyn.util.*;
import java.net.*;
import java.util.*;
import java.sql.*;
import com.sundyn.utils.*;

public class AppriesAction extends ActionSupport
{
    private static final Logger logger;
    private static final long serialVersionUID = 1L;
    private static final Integer pageSize;
    private AppriesService appriesService;
    private DeptService deptService;
    private EmployeeService employeeService;
    private ErrorInfoService errorInfoService;
    private KeyTypeService keyTypeService;
    private ManagerService managerService;
    private PowerService powerService;
    private String msg;
    private QueryService queryService;
    private String startDate;
    private String endDate;
    private Integer id;
    private Pager pager;
    private String mobileIp;
    private int mobilePort;
    private int clientPort;
    private String str;
    private City city;
    private Province province;
    private CitysUtils cityutils;
    
    static {
        logger = Logger.getLogger((Class)AppriesAction.class.getClass());
        pageSize = 6;
    }
    
    public int getMobilePort() {
        return this.mobilePort;
    }
    
    public void setMobilePort(final int mobilePort) {
        this.mobilePort = mobilePort;
    }
    
    public int getClientPort() {
        return this.clientPort;
    }
    
    public void setClientPort(final int clientPort) {
        this.clientPort = clientPort;
    }
    
    public String getMobileIp() {
        return this.mobileIp;
    }
    
    public void setMobileIp(final String mobileIp) {
        this.mobileIp = mobileIp;
    }
    
    private String getCamera() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String camera = "true";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            camera = sundynSet.getM_system().get("camera").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return camera;
    }
    
    public AppriesService getAppriesService() {
        return this.appriesService;
    }
    
    public void setAppriesService(final AppriesService appriesService) {
        this.appriesService = appriesService;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }
    
    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    public ErrorInfoService getErrorInfoService() {
        return this.errorInfoService;
    }
    
    public void setErrorInfoService(final ErrorInfoService errorInfoService) {
        this.errorInfoService = errorInfoService;
    }
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public ManagerService getManagerService() {
        return this.managerService;
    }
    
    public void setManagerService(final ManagerService managerService) {
        this.managerService = managerService;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public static long getSerialVersionUID() {
        return 1L;
    }
    
    public QueryService getQueryService() {
        return this.queryService;
    }
    
    public void setQueryService(final QueryService queryService) {
        this.queryService = queryService;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public static Integer getPageSize() {
        return AppriesAction.pageSize;
    }
    
    public String appriesAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String serviceDate = request.getParameter("serviceDate");
        final String serviceCycle = request.getParameter("serviceCycle");
        final String CustorTime = request.getParameter("CustorTime");
        final String DeptId = request.getParameter("DeptId");
        final String EmployeeId = request.getParameter("EmployeeId");
        final String serviceType = request.getParameter("serviceType");
        final String appriesTime = request.getParameter("appriesTime");
        final String updateTime = request.getParameter("updateTime");
        final String JieshouTime = request.getParameter("JieshouTime");
        final String saveTime = request.getParameter("saveTime");
        final String remark = request.getParameter("remark");
        final String deptarl = request.getParameter("deptarl");
        final String ext1 = request.getParameter("ext1");
        final String ext2 = request.getParameter("ext2");
        final String ext3 = request.getParameter("ext3");
        final String ext4 = request.getParameter("ext4");
        final String keyno = request.getParameter("keyno");
        final String mechinetype = request.getParameter("mechinetype");
        final AppriesVo appriesVo = new AppriesVo();
        appriesVo.setServiceDate(serviceDate);
        appriesVo.setServiceCycle(serviceCycle);
        appriesVo.setCustorTime(CustorTime);
        appriesVo.setDeptId(DeptId);
        appriesVo.setEmployeeId(EmployeeId);
        appriesVo.setServiceType(serviceType);
        appriesVo.setAppriesTime(appriesTime);
        appriesVo.setUpdateTime(updateTime);
        appriesVo.setJieshouTime(JieshouTime);
        appriesVo.setSaveTime(saveTime);
        appriesVo.setRemark(remark);
        appriesVo.setDeptarl(deptarl);
        appriesVo.setExt1(ext1);
        appriesVo.setExt2(ext2);
        appriesVo.setExt3(ext3);
        appriesVo.setExt4(ext4);
        appriesVo.setKeyno(keyno);
        appriesVo.setMechinetype(mechinetype);
        if (this.appriesService.addAppries(appriesVo)) {
            this.msg = "success";
        }
        else {
            this.msg = "error";
        }
        return "success";
    }
    
    public String appriesAddSpByPantryn() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String sam = "";
        String eam = "";
        String spm = "";
        String epm = "";
        String tipLanguage = "";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            sam = sundynSet.getM_work2().get("sam").toString();
            eam = sundynSet.getM_work2().get("eam").toString();
            spm = sundynSet.getM_work2().get("spm").toString();
            epm = sundynSet.getM_work2().get("epm").toString();
            tipLanguage = sundynSet.getM_system().get("tipLanguage");
            AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4:sam:" + sam + "eam:" + eam + "spm:" + spm + "epm:" + epm));
        }
        catch (Exception e) {
            AppriesAction.logger.debug((Object)"\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4\u51fa\u9519,\u8bf7\u68c0\u67e5update/set.xml  \u662f\u5426\u6b63\u786e");
            e.printStackTrace();
        }
        final String time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final String tt = request.getParameter("tt");
        final String cardnum = request.getParameter("cardnum");
        final String pj = request.getParameter("pj");
        final String tel = request.getParameter("tel");
        final String idCard = request.getParameter("idCard");
        final String name = request.getParameter("name");
        final String phone = request.getParameter("phone");
        final String demo = request.getParameter("content");
        String businessType = request.getParameter("businessType");
        final String videofile = request.getParameter("videofile");
        String businessTime = request.getParameter("businessTime");
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String cf = request.getParameter("cf");
        if (businessType == null) {
            businessType = "1";
        }
        boolean k7 = true;
        try {
            final SundynSet sundynSet2 = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet2.getM_system().get("k7").toString());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String bmk = "";
        AppriesAction.logger.info((Object)"\u5224\u65ad\u662f\u5426\u4f7f\u7528\u672a\u8bc4\u4ef7\u6570\u636e");
        if (k7) {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                    AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
                }
            }
        }
        else {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
            }
        }
        if (bmk.indexOf(pj) > -1) {
            try {
                final Map appries = this.appriesService.getAppriesInfo(cardnum, mac, pj);
                final Map temp = new HashMap();
                final List host = this.managerService.getuserHost();
                final socketUdp udp = new socketUdp();
                if (tipLanguage.equals("") || tipLanguage.equals("zh")) {
                    this.msg = tt + '\r' + '\n' + "\u5927\u5385:" + appries.get("dating") + '\r' + '\n' + "\u7a97\u53e3:" + appries.get("window") + '\r' + '\n' + "\u804c\u5458:" + appries.get("Name") + '\r' + '\n' + appries.get("keyname") + '\r' + '\n';
                }
                else {
                    this.msg = "Evaluation time:" + tt + '\r' + '\n' + "evaluated hall:" + appries.get("dating") + '\r' + '\n' + "evaluated window:" + appries.get("window") + '\r' + '\n' + "evaluated employee:" + appries.get("Name") + '\r' + '\n' + " unsatisfied description " + appries.get("keyname") + '\r' + '\n';
                }
                if (host != null) {
                    for (int l = 0; l < host.size(); ++l) {
                    	Map map = (Map)host.get(l);
                    	final String ip = map.get("ext2").toString();
                        //final String ip = host.get(l).get("ext2").toString();
                        final String[] sip = ip.split(",");
                        for (int m = 0; m < sip.length; ++m) {
                            temp.put(sip[m], sip[m]);
                            AppriesAction.logger.debug((Object)("\u7ba1\u7406Ip:" + sip[m]));
                        }
                    }
                }
                for (final Object key : temp.keySet()) {
                    final String mb = temp.get(key).toString();
                    if (!mb.equals("")) {
                        udp.send(mb, this.clientPort, this.msg);
                        AppriesAction.logger.debug((Object)("\u53d1\u9001" + temp.get(key).toString() + "\u6210\u529f"));
                    }
                }
                final List mobile = this.managerService.getuserMobile();
                final List l_m = new ArrayList();
                if (mobile != null) {
                    for (int i2 = 0; i2 < mobile.size(); ++i2) {
                    	Map map = (Map)mobile.get(i2);
                        //final String mobiles = mobile.get(i2).get("ext1").toString();
                    	final String mobiles = map.get("ext1").toString();
                        final String[] smobile = mobiles.split(",");
                        for (int j2 = 0; j2 < smobile.length; ++j2) {
                            l_m.add(smobile[j2]);
                            AppriesAction.logger.debug((Object)("\u53d1\u9001" + smobile[j2] + "\u6210\u529f"));
                        }
                    }
                }
                String s_m = "";
                for (int i3 = 0; i3 < l_m.size(); ++i3) {
                    s_m = String.valueOf(s_m) + l_m.get(i3).toString() + ",";
                }
                if (s_m.endsWith(",")) {
                    s_m = s_m.substring(0, s_m.length() - 1);
                }
                if (this.mobileIp.equals("")) {
                    final InetAddress addr = InetAddress.getLocalHost();
                    this.mobileIp = addr.getHostAddress();
                }
                final String t = String.valueOf(this.msg) + "||" + s_m;
                udp.send(this.mobileIp, this.mobilePort, String.valueOf(this.msg) + "||" + s_m);
                udp.close();
            }
            catch (Exception e3) {
                e3.printStackTrace();
                AppriesAction.logger.debug((Object)"\u83b7\u53d6\u9519\u8bef\u8bc4\u4ef7\u4fe1\u606f\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\u5458\u5de5\u5361\u53f7\u662f\u5426\u5b58\u5728\u91cd\u590d\u3002\u90e8\u95e8\u4fe1\u606f\u662f\u5426\u6b63\u786e\uff0cmac\u8bbe\u7f6e\u662f\u5426\u771f\u786e");
            }
        }
        String msg2 = "1";
        AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u8bc4\u4ef7\u6570\u636e");
        if (!sam.equals("") && !eam.equals("") && !spm.equals("") && !epm.equals("")) {
            AppriesAction.logger.debug((Object)"\u5224\u65ad\u4e0a\u4e0b\u73ed\u65f6\u95f4\u4e0d\u4e3a\u7a7a\uff0ctrue");
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u670d\u52a1\u5668\u65f6\u95f4\uff1a" + time));
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4:" + ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0))));
            if ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0)) {
                AppriesAction.logger.debug((Object)"\u5224\u65ad\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4\uff0ctrue");
                AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo));
                if (mac == null || mac.isEmpty()) {
                    this.msg = "error:mac\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noMac");
                }
                else if (tt == null || tt.isEmpty()) {
                    this.msg = "error:tt\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noTime");
                }
                else if (cardnum == null || cardnum.isEmpty()) {
                    this.msg = "error:cardnum\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noCarNum");
                }
                else if (pj == null || pj.isEmpty()) {
                    this.msg = "error:pj\u4e3a\u7a7a";
                    msg2 = this.getText("sundyn.query.error.noPJ");
                }
                else if (this.deptService.findByMac(mac) == null) {
                    this.msg = "\u6ca1\u6709\u7a97\u53e3\u4f7f\u7528\u6b64mac";
                    msg2 = this.getText("sundyn.query.error.wrongMac");
                }
                else if (this.employeeService.findByCardnum(cardnum) == null) {
                    this.msg = "\u6ca1\u6709\u5458\u5de5\u4f7f\u7528\u6b64\u5361\u53f7";
                    msg2 = this.getText("sundyn.query.error.wrongCarNum");
                }
                else {
                    this.msg = "error:mac=" + mac + "--tt=" + tt + "--cardnum=" + cardnum + "--pj=" + pj;
                }
                if (msg2 == "1") {
                    final List list = this.appriesService.checkAppries(tt, pj, mac);
                    if (list != null) {
                        if (list.size() > 0) {
                            this.msg = "error:mac=" + mac + "--tt=" + tt + "--cardnum=" + cardnum + "--pj=" + pj + " has been saved";
                        }
                        else if (list.size() == 0) {
                            if (this.appriesService.addArriresXiangYang(mac, tt, cardnum, pj, demo, videofile, businessTime, min, sec, tel, idCard, name, phone)) {
                                AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u6210\u529f"));
                                this.msg = "success";
                            }
                            else {
                                AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u5931\u8d25"));
                                AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e，，，，");
                                msg2 = this.getText("sundyn.query.error.wrongDB");
                                final boolean flag = this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
                                AppriesAction.logger.debug((Object)("\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e\u7ed3\u679c\u4e3a:" + flag));
                            }
                        }
                    }
                }
                else {
                    this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
                }
            }
            else {
                AppriesAction.logger.debug((Object)"\u5f53\u524d\u4e3a\u4e0b\u73ed\u65f6\u95f4\u65e0\u6cd5\u5199\u5165\u6570\u636e");
                this.msg = "\u5f53\u524d\u4e3a\u4e0b\u73ed\u65f6\u95f4";
                msg2 = this.getText("sundyn.query.error.wrongWorkTime");
                this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
            }
        }
        else {
            AppriesAction.logger.debug((Object)"\u4e0a\u4e0b\u73ed\u65f6\u95f4\u8bbe\u7f6e\u6709\u95ee\u9898\uff0c\u8bf7\u8bbe\u7f6e\u4e0b");
            this.msg = "\u4e0a\u4e0b\u73ed\u65f6\u95f4\u4e3a\u7a7a";
            msg2 = this.getText("sundyn.query.error.noWorkTime");
            this.errorInfoService.addDetail(mac, cardnum, pj, msg2);
        }
        return "success";
    }
    
    public String appriesAddSp2() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String sam = "";
        String eam = "";
        String spm = "";
        String epm = "";
        String tipLanguage = "";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            sam = sundynSet.getM_work2().get("sam").toString();
            eam = sundynSet.getM_work2().get("eam").toString();
            spm = sundynSet.getM_work2().get("spm").toString();
            epm = sundynSet.getM_work2().get("epm").toString();
            tipLanguage = sundynSet.getM_system().get("tipLanguage");
            AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4:sam:" + sam + "eam:" + eam + "spm:" + spm + "epm:" + epm));
        }
        catch (Exception e) {
            AppriesAction.logger.debug((Object)"\u5f97\u5230\u4e0a\u73ed\u65f6\u95f4\u51fa\u9519,\u8bf7\u68c0\u67e5update/set.xml  \u662f\u5426\u6b63\u786e");
            e.printStackTrace();
        }
        final String time = new SimpleDateFormat("HH:mm").format(new java.util.Date());
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final String tt = request.getParameter("tt");
        final String cardnum = request.getParameter("cardnum");
        final String pj = request.getParameter("pj");
        final String demo = request.getParameter("demo");
        String businessType = request.getParameter("businessType");
        final String videofile = request.getParameter("videofile");
        String businessTime = request.getParameter("businessTime");
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String cf = request.getParameter("cf");
        if (businessType == null) {
            businessType = "1";
        }
        boolean k7 = true;
        try {
            final SundynSet sundynSet2 = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet2.getM_system().get("k7").toString());
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String bmk = "";
        AppriesAction.logger.info((Object)"\u5224\u65ad\u662f\u5426\u4f7f\u7528\u672a\u8bc4\u4ef7\u6570\u636e");
        if (k7) {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                    AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
                }
            }
        }
        else {
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                AppriesAction.logger.debug((Object)("\u5f97\u5230\u4e0d\u6ee1\u610fids" + bmk));
            }
        }
        if (bmk.indexOf(pj) > -1) {
            try {
                final Map appries = this.appriesService.getAppriesInfo(cardnum, mac, pj);
                final Map temp = new HashMap();
                final List host = this.managerService.getuserHost();
                final socketUdp udp = new socketUdp();
                if (tipLanguage.equals("") || tipLanguage.equals("zh")) {
                    this.msg = tt + '\r' + '\n' + "\u5927\u5385:" + appries.get("dating") + '\r' + '\n' + "\u7a97\u53e3:" + appries.get("window") + '\r' + '\n' + "\u804c\u5458:" + appries.get("Name") + '\r' + '\n' + appries.get("keyname") + '\r' + '\n';
                }
                else {
                    this.msg = "Evaluation time:" + tt + '\r' + '\n' + "evaluated hall:" + appries.get("dating") + '\r' + '\n' + "evaluated window:" + appries.get("window") + '\r' + '\n' + "evaluated employee:" + appries.get("Name") + '\r' + '\n' + " unsatisfied description " + appries.get("keyname") + '\r' + '\n';
                }
                if (host != null) {
                    for (int l = 0; l < host.size(); ++l) {
                    	Map map = (Map)host.get(l);
                    	final String ip = map.get("ext2").toString();
                        //final String ip = host.get(l).get("ext2").toString();
                        final String[] sip = ip.split(",");
                        for (int m = 0; m < sip.length; ++m) {
                            temp.put(sip[m], sip[m]);
                            AppriesAction.logger.debug((Object)("\u7ba1\u7406Ip:" + sip[m]));
                        }
                    }
                }
                for (final Object key : temp.keySet()) {
                    final String mb = temp.get(key).toString();
                    if (!mb.equals("")) {
                        udp.send(mb, this.clientPort, this.msg);
                        AppriesAction.logger.debug((Object)("\u53d1\u9001" + temp.get(key).toString() + "\u6210\u529f"));
                    }
                }
                final List mobile = this.managerService.getuserMobile();
                final List l_m = new ArrayList();
                if (mobile != null) {
                    for (int i2 = 0; i2 < mobile.size(); ++i2) {
                    	Map map = (Map)mobile.get(i2);
                    	final String mobiles = map.get("ext1").toString();
                        //final String mobiles = mobile.get(i2).get("ext1").toString();
                        final String[] smobile = mobiles.split(",");
                        for (int j2 = 0; j2 < smobile.length; ++j2) {
                            l_m.add(smobile[j2]);
                            AppriesAction.logger.debug((Object)("\u53d1\u9001" + smobile[j2] + "\u6210\u529f"));
                        }
                    }
                }
                String s_m = "";
                for (int i3 = 0; i3 < l_m.size(); ++i3) {
                    s_m = String.valueOf(s_m) + l_m.get(i3).toString() + ",";
                }
                if (s_m.endsWith(",")) {
                    s_m = s_m.substring(0, s_m.length() - 1);
                }
                if (this.mobileIp.equals("")) {
                    final InetAddress addr = InetAddress.getLocalHost();
                    this.mobileIp = addr.getHostAddress();
                }
                final String t = String.valueOf(this.msg) + "||" + s_m;
                udp.send(this.mobileIp, this.mobilePort, String.valueOf(this.msg) + "||" + s_m);
                udp.close();
            }
            catch (Exception e3) {
                e3.printStackTrace();
                AppriesAction.logger.debug((Object)"\u83b7\u53d6\u9519\u8bef\u8bc4\u4ef7\u4fe1\u606f\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\u5458\u5de5\u5361\u53f7\u662f\u5426\u5b58\u5728\u91cd\u590d\u3002\u90e8\u95e8\u4fe1\u606f\u662f\u5426\u6b63\u786e\uff0cmac\u8bbe\u7f6e\u662f\u5426\u771f\u786e");
            }
        }
        AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u8bc4\u4ef7\u6570\u636e");
        if (!sam.equals("") && !eam.equals("") && !spm.equals("") && !epm.equals("")) {
            AppriesAction.logger.debug((Object)"\u5224\u65ad\u4e0a\u4e0b\u73ed\u65f6\u95f4\u4e0d\u4e3a\u7a7a\uff0ctrue");
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u670d\u52a1\u5668\u65f6\u95f4\uff1a" + time));
            AppriesAction.logger.debug((Object)("\u5f53\u524d\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4:" + ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0))));
            if ((time.compareTo(sam) >= 0 && time.compareTo(eam) <= 0) || (time.compareTo(spm) >= 0 && time.compareTo(epm) <= 0)) {
                AppriesAction.logger.debug((Object)"\u5224\u65ad\u662f\u5426\u4e3a\u4e0a\u73ed\u65f6\u95f4\uff0ctrue");
                AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo));
                if (this.appriesService.addArrires3(mac, tt, cardnum, pj, demo, videofile, businessTime, min, sec)) {
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u6210\u529f"));
                    this.msg = "success";
                }
                else {
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u8bc4\u4ef7\u6570\u636e\uff1amac:" + mac + "tt:" + tt + "cardnum:" + cardnum + "pj:" + pj + "cf:" + cf + "demo:" + demo + "\u5931\u8d25"));
                    AppriesAction.logger.debug((Object)"\u5f00\u59cb\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e，，，，");
                    final boolean flag = this.errorInfoService.add(mac, cardnum, pj);
                    AppriesAction.logger.debug((Object)("\u5199\u5165\u9519\u8bef\u7684\u8bc4\u4ef7\u6570\u636e\u7ed3\u679c\u4e3a:" + flag));
                    this.msg = "error";
                }
            }
            else {
                AppriesAction.logger.debug((Object)"\u5f53\u524d\u4e3a\u4e0b\u73ed\u65f6\u95f4\u65e0\u6cd5\u5199\u5165\u6570\u636e");
                this.msg = "error";
            }
        }
        else {
            AppriesAction.logger.debug((Object)"\u4e0a\u4e0b\u73ed\u65f6\u95f4\u8bbe\u7f6e\u6709\u95ee\u9898\uff0c\u8bf7\u8bbe\u7f6e\u4e0b");
            this.msg = "error";
        }
        return "success";
    }
    
    public String appriesDel() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List deptList = this.deptService.findChildALL(deptIdGroup);
        if (deptList != null && deptList.size() > 0) {
            final Map dept = (Map) deptList.get(0);
            dept.put("fatherId", -1);
        }
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList);
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }
    
    public String appriesDelDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String url = request.getQueryString();
        String deptIds = request.getParameter("deptIds");
        if (deptIds == null || deptIds.equals("")) {
            final Map manager = (Map)request.getSession().getAttribute("manager");
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        }
        String keys = request.getParameter("keys");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (deptIds.endsWith(",")) {
            deptIds = deptIds.substring(0, deptIds.length() - 1);
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int rowsCount = this.queryService.countQueryZh(this.id, keys, deptIds, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", AppriesAction.pageSize, rowsCount, request);
        final List tempList = this.queryService.queryZh(this.id, keys, deptIds, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(tempList);
        final List chatList = this.queryService.QueryZhChat(this.id, keys, deptIds, this.startDate, this.endDate);
        request.setAttribute("chatList", (Object)chatList);
        request.setAttribute("url", (Object)url);
        return "success";
    }
    
    public String appriesDelDealDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String tempIds = request.getParameter("ids");
        int delnum = 0;
        if (!tempIds.equals("")) {
            delnum = this.appriesService.del(tempIds);
        }
        request.setAttribute("msg", (Object)this.getText("sundyn.deleteSuccess"));
        return "success";
    }
    
    public String appriesDelDealDelAll() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String url = request.getQueryString();
        String deptIds = request.getParameter("deptIds");
        if (deptIds == null || deptIds.equals("")) {
            final Map manager = (Map)request.getSession().getAttribute("manager");
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        }
        String keys = request.getParameter("keys");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (deptIds.endsWith(",")) {
            deptIds = deptIds.substring(0, deptIds.length() - 1);
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int delnum = this.appriesService.del(this.id, keys, deptIds, this.startDate, this.endDate);
        request.setAttribute("msg", (Object)this.getText("sundyn.deleteSuccess"));
        return "success";
    }
    
    public String appriesDebug() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        final List employeeList = this.employeeService.findAllEmployee();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String date = df.format(new java.util.Date());
        final List allMac = this.appriesService.getAllMac();
        request.setAttribute("employeeList", (Object)employeeList);
        request.setAttribute("date", (Object)date);
        request.setAttribute("allMac", (Object)allMac);
        request.setAttribute("keyList", (Object)keyList);
        request.setAttribute("bussinessTime", (Object)"66");
        request.setAttribute("tel", (Object)"1234567");
        request.setAttribute("idCard", (Object)"7654321");
        return "success";
    }
    
    public String appriesAddDebug() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String tt = request.getParameter("appriesDate");
        final String mac = request.getParameter("mac");
        final String cardNum = request.getParameter("cardNum");
        final String appriesButton = request.getParameter("appriesButton");
        String businessTime = request.getParameter("bussinessTime");
        final String tel = request.getParameter("tel");
        final String idCard = request.getParameter("idCard");
        int min = 0;
        int sec = 0;
        if (businessTime != null) {
            min = Integer.valueOf(businessTime) / 60;
            sec = Integer.valueOf(businessTime) % 60;
            businessTime = String.valueOf(String.valueOf(min)) + "\u5206" + String.valueOf(sec) + "\u79d2";
        }
        final String msg = "http://localhost/appriesAddSp.action?mac=" + mac + "&&tt=" + tt + "&&cardnum=" + cardNum + "&&pj=" + appriesButton + "&&tel=" + tel + "&&idCard=" + idCard + "&&businessType=1";
        System.out.println("debug\u6dfb\u52a0\u8bc4\u4ef7\u6570\u636e\u8bf7\u6c42=" + msg);
        request.setAttribute("msg", (Object)msg);
        this.appriesService.addArriresXiangYang(mac, tt, cardNum, appriesButton, "13333", "", businessTime, 0, 0, tel, idCard, null, null);
        return "success";
    }
    
    public String getWeather() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String macid = request.getParameter("mac");
        if (macid == null || macid.equals("")) {
            this.msg = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            this.str = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            return "success";
        }
        int cityid = 0;
        final Map m = this.deptService.findByMac(macid);
        try {
            final boolean b = m.isEmpty();
        }
        catch (Exception e) {
            this.msg = "mac\uff1a" + macid + "\u6ca1\u6709\u7ed1\u5b9a\u8bbe\u5907.";
            this.str = "mac\uff1a" + macid + "\u6ca1\u6709\u7ed1\u5b9a\u8bbe\u5907.";
            return "success";
        }
        cityid = (int) m.get("cityid");
        cityid = ((cityid == 0) ? 1 : cityid);
        this.city = this.cityutils.getCityById(cityid);
        final String st = "http://m.weather.com.cn/data/" + this.city.getCitynum() + ".html";
        this.str = GetWeatherString.getPageContent(st, "", 2000);
        for (int i = 0; i < 2 && (this.str == null || this.str.equals("")); ++i) {
            this.str = GetWeatherString.getPageContent("http://m.weather.com.cn/data/" + this.city.getCitynum() + ".html", "", 2000);
        }
        return "success";
    }
    
    public String macBinding() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String macid = request.getParameter("mac");
        if (macid == null || macid.equals("")) {
            this.msg = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            this.str = "mac\u503c\u4e0d\u80fd\u4e3a\u7a7a";
            return "success";
        }
        final Map m = this.deptService.findByMac(macid);
        if (m == null) {
            this.msg = "mac\uff1a" + macid + "\u53ef\u4ee5\u4f7f\u7528";
            this.str = "mac\uff1a" + macid + "\u53ef\u4ee5\u4f7f\u7528";
        }
        else {
            this.msg = "mac \u5df2\u7ecf\u88ab\u5360\u7528";
            this.str = "mac \u5df2\u7ecf\u88ab\u5360\u7528";
        }
        request.setAttribute("json", (Object)this.msg);
        return "success";
    }
    
    public static Logger getLogger() {
        return AppriesAction.logger;
    }
    
    public static long getSerialversionuid() {
        return 1L;
    }
    
    public static Integer getPagesize() {
        return AppriesAction.pageSize;
    }
    
    public String getStr() {
        return this.str;
    }
    
    public void setStr(final String str) {
        this.str = str;
    }
    
    public City getCity() {
        return this.city;
    }
    
    public void setCity(final City city) {
        this.city = city;
    }
    
    public Province getProvince() {
        return this.province;
    }
    
    public void setProvince(final Province province) {
        this.province = province;
    }
    
    public CitysUtils getCityutils() {
        return this.cityutils;
    }
    
    public void setCityutils(final CitysUtils cityutils) {
        this.cityutils = cityutils;
    }
}
