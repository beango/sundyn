package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.opensymphony.xwork2.ActionContext;
import com.sundyn.entity.QueueDetail;
import com.sundyn.service.*;
import com.sundyn.util.*;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class QueryAction extends MainAction
{
    public static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger();

    private Integer deptId;
    private List deptList;

    public List getDeptList2() {
        return deptList2;
    }

    public void setDeptList2(List deptList2) {
        this.deptList2 = deptList2;
    }

    private List deptList2;

    public List getDeptJSON() {
        return deptJSON;
    }

    public void setDeptJSON(List deptJSON) {
        this.deptJSON = deptJSON;
    }

    private List deptJSON;

    public List getEmployeeTreeData() {
        return employeeTreeData;
    }

    public void setEmployeeTreeData(List employeeTreeData) {
        this.employeeTreeData = employeeTreeData;
    }

    private List employeeTreeData;
    private DeptService deptService;
    private EmployeeService employeeService;
    private String endDate;
    private InputStream excel;
    private Integer id;
    private KeyTypeService keyTypeService;
    private String keyword;
    private String msg;
    private Pager pager;
    private PowerService powerService;
    private QueryService queryService;
    private String select;
    private String startDate;
    private TotalService totalService;
    private String deptIds;
    private String keys;
    private String fileName;
    private boolean k7;
    private List mls;
    private List bmls;

    public JSONObject getPagerJSON() {
        return pagerJSON;
    }

    public void setPagerJSON(JSONObject pagerJSON) {
        this.pagerJSON = pagerJSON;
    }

    private JSONObject pagerJSON;

    public List getBmls() {
        return this.bmls;
    }

    public void setBmls(final List bmls) {
        this.bmls = bmls;
    }

    public List getMls() {
        return this.mls;
    }

    public void setMls(final List mls) {
        this.mls = mls;
    }

    public boolean isK7() {
        return this.k7;
    }

    public void setK7(final boolean k7) {
        this.k7 = k7;
    }


    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public String getDeptIds() {
        return this.deptIds;
    }

    public void setDeptIds(final String deptIds) {
        this.deptIds = deptIds;
    }

    public String getKeys() {
        return this.keys;
    }

    public void setKeys(final String keys) {
        this.keys = keys;
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

    public Integer getDeptId() {
        return this.deptId;
    }

    public List getDeptList() {
        return this.deptList;
    }

    public DeptService getDeptService() {
        return this.deptService;
    }

    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public InputStream getExcel() {
        return this.excel;
    }

    public Integer getId() {
        return this.id;
    }

    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public String getMsg() {
        return this.msg;
    }

    public Pager getPager() {
        return this.pager;
    }

    public PowerService getPowerService() {
        return this.powerService;
    }

    public QueryService getQueryService() {
        return this.queryService;
    }

    public String getSelect() {
        return this.select;
    }

    public String getStartDate() {
        return this.startDate;
    }

    private List getTotalList(final Map totalMap) {
        if (totalMap != null) {
            try {
                final List keyList = this.keyTypeService.findByApprieserId(1, 1);
                final List totalList = new ArrayList();
                for (int i = 0; i < keyList.size(); ++i) {
                    final Map keyType = new HashMap();
                    final Map k = (Map) keyList.get(i);
                    final String keyNo = k.get("keyNo").toString();
                    keyType.put("name", k.get("name"));
                    keyType.put("num", totalMap.get("key" + keyNo));
                    totalList.add(keyType);
                }
                return totalList;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public TotalService getTotalService() {
        return this.totalService;
    }

    public String queryDemo() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map appries = this.queryService.queryById(this.id);
        final List info = new ArrayList();
        final List list = new ArrayList();
        if (appries != null && appries.get("remark") != null) {
            final String remark = appries.get("remark").toString();
            final String[] temp = remark.split(",,,");
            for (int i = 0; i < temp.length; ++i) {
                temp[i] = temp[i].trim();
                final String[] t = temp[i].split("=");
                list.add(t[0]);
                if (t.length == 2) {
                    info.add(t[1]);
                }
                else {
                    info.add("");
                }
            }
        }
        request.setAttribute("info", (Object)info);
        request.setAttribute("ls", (Object)list);
        return "success";
    }

    public String queryDept() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.deptList = new ArrayList();
        final Map dept = this.deptService.findDeptById(Integer.valueOf(deptIdGroup));
        this.deptList.add(dept);
        return "success";
    }

    public String queryDeptAjax2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map) request.getSession().getAttribute("manager");
        this.select = "";
        String type = request.getParameter("type");
        request.setAttribute("level", Integer.valueOf(request.getParameter("level"))+1);
        if(this.id!=null){
            Map map = this.deptService.findDeptByFatherid(this.id);

            if (map!=null && map.get("deptType").toString().equals("1") && type.equals("employee")){ //大厅
                this.deptList = this.employeeService.findEmployeeByDeptId(this.id);
                request.setAttribute("dataType", "employee");
                return "success";
            }
        }

        this.deptList = this.deptService.findchild(this.id);
        List d = new ArrayList();
        for (Object m : this.deptList){
            Map _m = (Map)m;
            if (type.equals("dept") && _m.get("deptType").toString().equals("2")){//部门
                d.add(_m);
            }
            if ((type.equals("dating")||type.equals("employee")) && (_m.get("deptType").toString().equals("2") || _m.get("deptType").toString().equals("1"))){//大厅
                d.add(_m);
            }
            if (type.equals("window") && (_m.get("deptType").toString().equals("2") || _m.get("deptType").toString().equals("1") || _m.get("deptType").toString().equals("0"))){//窗口
                d.add(_m);
            }
        }
        request.setAttribute("dataType", "dept");
        this.deptList = d;
        List nl = new ArrayList();
        for (int i=0; i< deptList.size(); i++){
            if ((","+super.DEPTIDS+",").contains(((Map)deptList.get(i)).get("id").toString()))
            {
                nl.add(deptList.get(i));
            }
        }
        this.deptList = nl;
        return "success";
    }

    private List loopchild(int id, String type){
        List _deptList = this.deptService.findchild(id);
        List d = new ArrayList();
        for (Object m : _deptList){
            Map _m = (Map)m;
            if (type.equals("dept") && _m.get("deptType").toString().equals("2")){//部门
                d.add(_m);
            }
            if ((type.equals("dating")||type.equals("employee")) && (_m.get("deptType").toString().equals("2") || _m.get("deptType").toString().equals("1"))){//大厅
                d.add(_m);
            }
            if (type.equals("window") && (_m.get("deptType").toString().equals("2") || _m.get("deptType").toString().equals("1") || _m.get("deptType").toString().equals("0"))){//窗口
                d.add(_m);
            }
            loopchild(Integer.valueOf(_m.get("id").toString()), type);
        }
        return d;
    }
    public String queryDeptAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.select = "";
        if (!this.deptService.isLastestTwo(this.id)) {
            this.deptList = this.deptService.findchild(this.id);
            String temp = "";
            if (this.deptList == null || this.deptList.size() == 0) {
                temp = "";
            }
            else {
                temp = "<select name=deptId ";
                temp = String.valueOf(temp) + " >";
                for (int i = 0; i < this.deptList.size(); ++i) {
                	Map map = (Map)this.deptList.get(i);
                    final String idt = map.get("id").toString();
                    final String name = map.get("name").toString();
                    temp = String.valueOf(temp) + "<option value=" + idt;
                    if (i == 0) {
                        temp = String.valueOf(temp) + " selected='selected' ";
                    }
                    temp = String.valueOf(temp) + ">" + name + "</>";
                }
                temp = String.valueOf(temp) + "</select>";
            }
            this.select = temp;
        }
        boolean flag = true;
        final String[] deptArray = deptIdGroup.split(",");
        for (int j = 0; j < deptArray.length; ++j) {
            if (this.id.toString().equals(deptArray[j])) {
                flag = false;
            }
        }
        while (flag) {
            final Map deptM = this.deptService.findDeptById(this.id);
            final Integer fatherId = Integer.valueOf(deptM.get("fatherId").toString());
            final Integer tempid = Integer.valueOf(deptM.get("id").toString());
            final List currList = this.deptService.findchild(fatherId);
            String currselect = "";
            currselect = "<select name=deptId ";
            if (currList.size() == 1) {
                currselect = String.valueOf(currselect) + " onclick='dept(this.value)'";
            }
            else {
                currselect = String.valueOf(currselect) + " onchange='dept(this.value)'";
            }
            currselect = String.valueOf(currselect) + " >";
            for (int k = 0; k < currList.size(); ++k) {
            	Map map = (Map)currList.get(k);
                final String idcur = map.get("id").toString();
                final String namecur = map.get("name").toString();
                if (idcur.equals(this.id.toString())) {
                    currselect = String.valueOf(currselect) + "<option value=" + idcur + " selected='selected'>" + namecur + "</>";
                }
                else {
                    currselect = String.valueOf(currselect) + "<option value=" + idcur + ">" + namecur + "</>";
                }
            }
            currselect = String.valueOf(currselect) + "</select>";
            this.select = String.valueOf(currselect) + this.select;
            this.id = fatherId;
            for (int k = 0; k < deptArray.length; ++k) {
                if (this.id.toString().equals(deptArray[k])) {
                    flag = false;
                }
            }
        }
        final List glist = this.deptService.findAll(deptIdGroup);
        String gtemp = "<select name=deptId ";
        if (glist.size() == 1) {
            gtemp = String.valueOf(gtemp) + " onclick='dept(this.value)'";
        }
        else {
            gtemp = String.valueOf(gtemp) + " onchange='dept(this.value)'";
        }
        gtemp = String.valueOf(gtemp) + " >";
        for (int l = 0; l < glist.size(); ++l) {
        	Map map = (Map)glist.get(l);
            final String idcur2 = map.get("id").toString();
            final String namecur2 = map.get("name").toString();
            if (idcur2.equals(this.id.toString())) {
                gtemp = String.valueOf(gtemp) + "<option value=" + idcur2 + " selected='selected'>" + namecur2 + "</>";
            }
            else {
                gtemp = String.valueOf(gtemp) + "<option value=" + idcur2 + ">" + namecur2 + "</>";
            }
        }
        gtemp = String.valueOf(gtemp) + "</select>";
        this.select = String.valueOf(gtemp) + this.select;
        return "success";
    }

    public String queryDeptDeal() throws Exception {
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null) {
            startDate = dateHelper.getDataString_1(dateHelper.getWeekFirstDate());
        }
        if(endDate == null) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");

        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        request.setAttribute("rootdept", deptIdGroup2);
        this.deptList2 = new ArrayList();
        final Map dept2 = this.deptService.findDeptById(Integer.valueOf(deptIdGroup2));
        this.deptList2.add(dept2);
        this.deptJSON = this.deptService.findChildALL(deptIdGroup2,2);

        if(request.getParameter("deptId")!=null&&!request.getParameter("deptId").equals(""))
            this.deptId = Integer.valueOf(request.getParameter("deptId"));
        final String deptIds = this.deptService.findChildALLStr1234(request.getParameter("deptId"));
        final String allKeyInUse = this.keyTypeService.findAllKeyInUseWithNoEval(1);
        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        int[] rowcounts = new int[1];
        List list = this.queryService.queryDept2Ex(deptIds,req.getString("bizname"),null, null, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
                this.pager.getPageSize(), allKeyInUse, rowcounts);
        this.pager = new Pager("currentPage", pageSize, rowcounts[0], request, this);
        list = this.handleEmptyFile(list);
        this.pager.setPageList(list);
        JSONObject gson = new JSONObject(pager);
        this.pagerJSON = gson;
        final List chatList = this.queryService.QueryDeptChat(deptIds, this.startDate, this.endDate, allKeyInUse);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFont='\u5b8b\u4f53'  baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        for (int i = 0; i < chatList.size(); ++i) {
            final Map temp = (Map) chatList.get(i);
            final Map m = new HashMap();
            m.put("keytypename", temp.get("name"));
            m.put("num", temp.get("num"));
            strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        String exportExcel = request.getParameter("export");
        if (exportExcel != null && exportExcel.toLowerCase().equals("true")) {
            System.out.println("导出：" + req.getString("deptId"));
            List list2 = this.queryService.queryDept2(deptIds, this.startDate, this.endDate, 0, 0, allKeyInUse, null);

            final List ls = new ArrayList();
            for (int i = 0; i < list2.size(); ++i) {
                final Map m = new LinkedHashMap();
                final Map temp = (Map) list2.get(i);
                m.put("m1", temp.get("CardNum"));
                m.put("m2", temp.get("employeeName"));
                m.put("m3", temp.get("fatherName"));
                m.put("m4", temp.get("keyName"));
                m.put("m5", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.get("JieshouTime")));
                m.put("m6", "");
                m.put("m7",  temp.get("businessMin") + getText("sundyn.inquiry.result.minuteForShort") + temp.get("businessSec") + getText("sundyn.inquiry.result.secondForShort"));
                m.put("m8", temp.get("ext1"));
                m.put("m9", temp.get("ext2"));
                m.put("m10", temp.get("remark"));
                ls.add(m);
            }
            final Poi poi = new Poi();
            final String[] args = { this.getText("sundyn.column.employeeCardNum"), this.getText("sundyn.column.employeeName"),
                    this.getText("sundyn.column.atDating"), this.getText("sundyn.column.appriesResult"),
                    this.getText("sundyn.column.appriesTime"), this.getText("sundyn.inquiry.result.obtainEvidence"),
                    this.getText("sundyn.inquiry.result.businessTime"), "客户姓名", "客户电话", "意见反馈"};

            poi.addTitle(this.getText("sundyn.query.appriesData"), 1, args.length-1);
            poi.addListTitle(args, 1);
            poi.addList(ls);
            poi.createFile(String.valueOf(path) + "standard.xls");
            this.excel = new FileInputStream(String.valueOf(path) + "standard.xls");
            this.fileName = "standard" + Math.round(Math.random() * 10000.0) + ".xls";
            return "excel";
        }
        request.setAttribute("QueueDetailBean", new QueueDetail());
        return "success";
    }

    public String jiGouGongZuoiLiang() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List temp = this.deptService.findchild(Integer.valueOf(deptIdGroup));
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.unitWorkDiagram") + "' xAxisName='" + this.getText("sundyn.system.checkOnlineM7.unitName") + "' yAxisName='" + this.getText("sundyn.inquiry.workload") + "' decimalPrecision='0' formatNumberScale='0'>");
        final List res = new ArrayList();
        for (int i = 0; i < temp.size(); ++i) {
        	Map map = (Map)temp.get(i);
            final String name = map.get("name").toString();
            final String id = map.get("id").toString();
            final String ids = this.deptService.findChildALLStr123(id);
            final Map m = this.totalService.totalDept(ids, this.startDate, this.endDate);
            if (m != null) {
                m.put("DeptId", id);
                m.put("name", name);
                final Integer key0 = Integer.valueOf(m.get("key0").toString());
                final Integer key2 = Integer.valueOf(m.get("key1").toString());
                final Integer key3 = Integer.valueOf(m.get("key2").toString());
                final Integer key4 = Integer.valueOf(m.get("key3").toString());
                final Integer key5 = Integer.valueOf(m.get("key4").toString());
                final Integer key6 = Integer.valueOf(m.get("key5").toString());
                final Integer key7 = Integer.valueOf(m.get("key6").toString());
                final Integer[] key8 = { key0, key2, key3, key4, key5, key6, key7 };
                final double prate = Math.rint((1.0 - key7 * 1.0 / (key0 + key2 + key4 + key5 + key6 + key7)) * 10000.0) / 100.0;
                final double erate = Math.rint((key0 * 1.0 + key2 * 1.0) / (key0 + key2 + key3 + key4 + key5 + key6 + key7) * 10000.0) / 100.0;
                double mrate = 0.0;
                final List km = new ArrayList();
                final List kbm = new ArrayList();
                int msum = 0;
                int bmsum = 0;
                int p = 0;
                int sum = 0;
                if (this.k7) {
                    for (int j = 0; j < this.mls.size(); ++j) {
                        final Map k = (Map) this.mls.get(j);
                        if (!k.get("keyNo").toString().equals("6")) {
                            msum += key8[Integer.parseInt(k.get("keyNo").toString())];
                            km.add(key8[Integer.parseInt(k.get("keyNo").toString())]);
                        }
                        else {
                            this.mls.remove(j);
                        }
                    }
                    for (int j = 0; j < this.bmls.size(); ++j) {
                        final Map k = (Map) this.bmls.get(j);
                        if (!k.get("keyNo").toString().equals("6")) {
                            bmsum += key8[Integer.parseInt(k.get("keyNo").toString())];
                            kbm.add(key8[Integer.parseInt(k.get("keyNo").toString())]);
                        }
                        else {
                            this.bmls.remove(j);
                        }
                    }
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                    for (int j = 0; j < key8.length - 1; ++j) {
                        p += key8[j];
                    }
                    for (int j = 0; j < key8.length; ++j) {
                        sum += key8[j];
                    }
                }
                else {
                    for (int j = 0; j < this.mls.size(); ++j) {
                        final Map k = (Map) this.mls.get(j);
                        msum += key8[Integer.parseInt(k.get("keyNo").toString())];
                        km.add(key8[Integer.parseInt(k.get("keyNo").toString())]);
                    }
                    for (int j = 0; j < this.bmls.size(); ++j) {
                        final Map k = (Map) this.bmls.get(j);
                        bmsum += key8[Integer.parseInt(k.get("keyNo").toString())];
                        kbm.add(key8[Integer.parseInt(k.get("keyNo").toString())]);
                    }
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                    for (int j = 0; j < key8.length; ++j) {
                        p += key8[j];
                    }
                    for (int j = 0; j < key8.length; ++j) {
                        sum += key8[j];
                    }
                }
                m.put("prate", prate);
                m.put("erate", erate);
                m.put("mrate", mrate);
                m.put("km", km);
                m.put("kbm", kbm);
                m.put("msum", msum);
                m.put("bmsum", bmsum);
                m.put("p", p);
                m.put("sum", sum);
                strXML1.append("<set name='" + name + "' value='" + sum + "' color='D64646' />");
            }
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        return "success";
    }

    public String queryIndex() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        String deptIdGroup = power.get("deptIdGroup").toString();
        deptIdGroup = this.deptService.findChildALLStr123(deptIdGroup);
        final Date date = new Date();
        final DateFormat dfstart = new SimpleDateFormat("yyyy-MM");
        final DateFormat dfend = new SimpleDateFormat("yyyy-MM-dd");
        this.startDate = String.valueOf(dfstart.format(date)) + "-01";
        this.endDate = dfend.format(date);
        final Map totalMap = this.totalService.totalDept(deptIdGroup, this.startDate, this.endDate);
        final List totalList = this.getTotalList(totalMap);
        final PieXml pieXml = new PieXml(path);
        pieXml.setData(totalList);
        pieXml.createXml(path);
        boolean k7 = true;
        boolean bind = false;
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet.getM_system().get("k7").toString());
            bind = Boolean.valueOf(sundynSet.getM_system().get("bind").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final List mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        String mk = "";
        String bmk = "";
        if (k7) {
            for (int j = 0; j < mls.size(); ++j) {
                final Map i = (Map) mls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    mk = String.valueOf(mk) + i.get("keyNo").toString() + ",";
                }
            }
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                if (!i.get("keyNo").toString().equals("6")) {
                    bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
                }
            }
        }
        else {
            for (int j = 0; j < mls.size(); ++j) {
                final Map i = (Map) mls.get(j);
                mk = String.valueOf(mk) + i.get("keyNo").toString() + ",";
            }
            for (int j = 0; j < bmls.size(); ++j) {
                final Map i = (Map) bmls.get(j);
                bmk = String.valueOf(bmk) + i.get("keyNo").toString() + ",";
            }
        }
        if (mk.endsWith(",")) {
            mk = mk.substring(0, mk.length() - 1);
        }
        if (bmk.endsWith(",")) {
            bmk = bmk.substring(0, bmk.length() - 1);
        }
        if (mk.startsWith(",")) {
            mk = mk.substring(0, mk.length() - 1);
        }
        if (bmk.startsWith(",")) {
            bmk = bmk.substring(0, bmk.length() - 1);
        }
        if (!mk.equals("")) {
            mk = String.valueOf(mk) + "," + bmk;
        }
        if (mk.endsWith(",")) {
            mk = mk.substring(0, mk.length() - 1);
        }
        List l1 = null;
        List l2 = null;
        if (bind) {
            l1 = this.queryService.queryTypeByDoor(deptIdGroup, mk, 2);
            l2 = this.queryService.queryTypeByDoor(deptIdGroup, bmk, 5);
        }
        else {
            l1 = this.queryService.queryType(deptIdGroup, mk, 5);
            l2 = this.queryService.queryType(deptIdGroup, bmk, 5);
        }
        request.setAttribute("l1", (Object)l1);
        request.setAttribute("l0", (Object)l2);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("totalList", (Object)totalList);
        request.setAttribute("deptIds", (Object)deptIdGroup);
        request.setAttribute("mk", (Object)mk);
        request.setAttribute("bmk", (Object)bmk);
        request.setAttribute("bind", (Object)bind);
        if (this.getCamera().endsWith("true")) {
            return "camera";
        }
        return "success";
    }

    public String queryLeft() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String baseSet = power.get("baseSet").toString();
        final String dataManage = power.get("dataManage").toString();
        int b = 0;
        int d = 0;
        if (baseSet.equals("true") || baseSet.equals("1")) {
            b = 1;
        }
        if (dataManage.equals("true") || dataManage.equals("1")) {
            d = 1;
        }
        final String language = ActionContext.getContext().getLocale().getLanguage();
        final String jsurl = String.valueOf(language) + "/" + "nav" + b + d + ".js";
        request.setAttribute("jsurl", (Object)jsurl);
        request.setAttribute("realname", manager.get("realname"));
        request.setAttribute("name", power.get("name"));
        return "success";
    }

    public String queryPeopley() throws Exception {
        return "success";
    }

    public String queryPeopleyAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        String deptIdGroup = power.get("deptIdGroup").toString();
        final List temp = new ArrayList();
        temp.add(this.deptService.findDeptById(Integer.valueOf(deptIdGroup)));
        deptIdGroup = this.deptService.findChildALLStr123(deptIdGroup);
        if (this.keyword == null) {
            this.keyword = "";
        }
        this.keyword.trim();
        this.keyword = Mysql.mysql(this.keyword);
        this.deptList = this.employeeService.findEmployeeByKeyowrd(this.keyword, deptIdGroup);
        request.setAttribute("temp", (Object)temp);
        return "success";
    }

    public String queryPeopleyAjax2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String deptId = request.getParameter("deptId");
        deptId = this.deptService.findChildALLStr123(deptId);
        if (this.keyword == null) {
            this.keyword = "";
        }
        this.keyword.trim();
        this.keyword = Mysql.mysql(this.keyword);
        this.deptList = this.employeeService.findEmployeeByKeyowrd(this.keyword, deptId);
        return "success";
    }

    public String queryDealPeopleyForDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int rowsCount = this.queryService.countQueryEmployee(this.id, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
        final List querylist = this.queryService.queryEmployee(this.id, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(querylist);
        return "success";
    }
    public void queryPeopleyData() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        String deptIdGroup = power.get("deptIdGroup").toString();
        final List temp = new ArrayList();
        temp.add(this.deptService.findDeptById(Integer.valueOf(deptIdGroup)));
        deptIdGroup = this.deptService.findChildALLStr123(deptIdGroup);
        if (this.keyword == null) {
            this.keyword = "";
        }
        this.keyword.trim();
        this.keyword = Mysql.mysql(this.keyword);
        this.deptList = this.employeeService.findEmployeeByKeyowrd(this.keyword, deptIdGroup);
        request.setAttribute("temp", (Object)temp);
    }

    public void getDeptTree() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        this.deptJSON = this.deptService.findChildALL(deptIdGroup2, 2);
    }

    public void getEmployeeTree() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        this.deptJSON = this.deptService.findChildALL(this.deptService.findChildALLStr1234("0"), 2);

        final List temp = new ArrayList();
        for (int i = 0; i < this.deptJSON.size(); ++i) {
            Map map = (Map)deptJSON.get(i);
            temp.addAll(this.employeeService.findEmployeeByDeptId(Integer.valueOf(map.get("id").toString())));
        }
        employeeTreeData = temp;
    }

    public String queryPeopleyDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null) {
            startDate = dateHelper.getDataString_1(dateHelper.getWeekFirstDate());
        }
        if(endDate == null) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }
        final String deptIds = this.deptService.findChildALLStr1234(request.getParameter("deptId"));
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String allKeyInUse = this.keyTypeService.findAllKeyInUseWithNoEval(1);
        int rowsCount = 0;//this.queryService.countQueryEmployee2(this.id, this.startDate, this.endDate, allKeyInUse);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
        int[] rc = new int[1];
        //List querylist = this.queryService.queryEmployee2(deptIds, req.getInt("employeeid"), this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
        //        this.pager.getPageSize(), allKeyInUse, rc);
        List querylist = this.queryService.queryDept2Ex(deptIds,req.getString("bizname"), req.getInt("employeeid"),null, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
                this.pager.getPageSize(), allKeyInUse, rc);
        rowsCount = rc[0];

        this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
        if (querylist != null) {
            querylist = this.handleEmptyFile(querylist);
        }
        this.pager.setPageList(querylist);
        final List chatList = this.queryService.QueryEmployeeChat2(deptIds, req.getInt("employeeid"), this.startDate, this.endDate, allKeyInUse);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        for (int i = 0; i < chatList.size(); ++i) {
            final Map temp = (Map) chatList.get(i);
            final Map m = new HashMap();
            m.put("name", temp.get("name"));
            m.put("num", temp.get("num"));
            strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        request.setAttribute("employeeinfo", employeeService.findEmployeeById(req.getInt("employeeid")));
        String exportExcel = request.getParameter("export");
        if (exportExcel != null && exportExcel.toLowerCase().equals("true")) {
            List list2 = this.queryService.queryEmployee2(deptIds, req.getInt("employeeid"), this.startDate, this.endDate, 0, 0, allKeyInUse, null);

            final List ls = new ArrayList();
            for (int i = 0; i < list2.size(); ++i) {
                final Map m = new LinkedHashMap();
                final Map temp = (Map) list2.get(i);
                m.put("m1", temp.get("CardNum"));
                m.put("m2", temp.get("employeeName"));
                m.put("m3", temp.get("fatherName"));
                m.put("m4", temp.get("keyName"));
                m.put("m5", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.get("JieshouTime")));
                m.put("m6", "");
                m.put("m7",  temp.get("businessMin") + getText("sundyn.inquiry.result.minuteForShort") + temp.get("businessSec") + getText("sundyn.inquiry.result.secondForShort"));
                m.put("m8", temp.get("ext1"));
                m.put("m9", temp.get("ext2"));
                m.put("m10", temp.get("remark"));
                ls.add(m);
            }
            final Poi poi = new Poi();
            final String[] args = { this.getText("sundyn.column.employeeCardNum"), this.getText("sundyn.column.employeeName"),
                    this.getText("sundyn.column.atDating"), this.getText("sundyn.column.appriesResult"),
                    this.getText("sundyn.column.appriesTime"), this.getText("sundyn.inquiry.result.obtainEvidence"),
                    this.getText("sundyn.inquiry.result.businessTime"), "客户姓名", "客户电话", "意见反馈"};

            poi.addTitle(this.getText("sundyn.query.appriesData"), 1, args.length-1);
            poi.addListTitle(args, 1);
            poi.addList(ls);
            poi.createFile(String.valueOf(path) + "standard.xls");
            this.excel = new FileInputStream(String.valueOf(path) + "standard.xls");
            this.fileName = "standard" + Math.round(Math.random() * 10000.0) + ".xls";
            return "excel";
        }
        request.setAttribute("QueueDetailBean", new QueueDetail());
        return "success";
    }

    public String queryJobNumDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String cardNum = request.getParameter("cardNum");
        final Map m3 = this.employeeService.employeeFindByCardnum(cardNum);
        if (m3 != null && m3.size() >= 1) {
            this.id = Integer.valueOf(this.employeeService.employeeFindByCardnum(cardNum).get("id").toString());
            final int rowsCount = this.queryService.countQueryEmployee(this.id, this.startDate, this.endDate);
            this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
            final List querylist = this.queryService.queryEmployee(this.id, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(querylist);
            final List chatList = this.queryService.QueryEmployeeChat(this.id, this.startDate, this.endDate);
            final PieXml pieXml = new PieXml(path);
            pieXml.setData(chatList);
            pieXml.createXml(path);
        }
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }

    public String queryEmployee() {
        return "success";
    }

    public String queryEmployeeDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map emp = (Map)request.getSession().getAttribute("emp");
        if (emp == null) {
            return "error";
        }
        final Integer employeeId = Integer.valueOf(emp.get("Id").toString());
        final int rowsCount = this.queryService.countQueryEmployee(employeeId, this.startDate, this.endDate);
        final Pager pager = new Pager("currentPage", pageSize, rowsCount, request, this);
        this.pager = pager;
        this.pager = pager;
        final List querylist = this.queryService.queryEmployee(employeeId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(querylist);
        return "success";
    }

    public String queryResult() throws Exception {
        this.deptList = this.keyTypeService.findByApprieserId(1, 1);
        return "success";
    }

    public String queryResultDeal() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null) {
            startDate = dateHelper.getDataString_1(dateHelper.getWeekFirstDate());
        }
        if(endDate == null) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }

        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr1234(deptIdGroup);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        Integer keys = null;
        if(request.getParameter("keys")!=null && !request.getParameter("keys").equals(""))
            keys =Integer.parseInt(request.getParameter("keys"));
        final int[] rowsCount = new int[]{0};
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        final String allKeyInUse = this.keyTypeService.findAllKeyInUseWithNoEval(1);
        //List querylist = this.queryService.queryResult(deptIds, keys, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
        //        this.pager.getPageSize(), allKeyInUse, rowsCount);
        List querylist = this.queryService.queryDept2Ex(deptIds, req.getString("bizname"), null, keys, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
                this.pager.getPageSize(), allKeyInUse, rowsCount);
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        querylist = this.handleEmptyFile(querylist);
        this.pager.setPageList(querylist);
        this.keys = new StringBuilder(String.valueOf(keys)).toString();
        final List chatList = this.queryService.QueryResultChat(deptIds, keys, this.startDate, this.endDate, allKeyInUse);
        if (chatList!=null){
            for (int i = 0; i < chatList.size(); ++i) {
                final Map temp = (Map) chatList.get(i);
                final Map m = new HashMap();
                m.put("name", temp.get("name"));
                m.put("num", temp.get("num"));
                strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
            }
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        this.deptList = this.keyTypeService.findByApprieserId(1, 1);


        String exportExcel = request.getParameter("export");
        if (exportExcel != null && exportExcel.toLowerCase().equals("true")) {
            List list2 = this.queryService.queryResult(deptIds, keys, this.startDate, this.endDate, 0, 0, allKeyInUse, null);
            querylist = this.handleEmptyFile(querylist);

            final List ls = new ArrayList();
            for (int i = 0; i < list2.size(); ++i) {
                final Map m = new LinkedHashMap();
                final Map temp = (Map) list2.get(i);
                m.put("m0", i+1);
                m.put("m1", temp.get("CardNum"));
                m.put("m2", temp.get("employeeName"));
                m.put("m3", temp.get("fatherName"));
                m.put("m4", temp.get("keyName"));
                m.put("m5", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.get("JieshouTime")));
                m.put("m6", "");
                m.put("m7",  temp.get("businessMin") + getText("sundyn.inquiry.result.minuteForShort") + temp.get("businessSec") + getText("sundyn.inquiry.result.secondForShort"));
                m.put("m8", temp.get("ext1"));
                m.put("m9", temp.get("ext2"));
                m.put("m10", temp.get("remark"));
                ls.add(m);
            }
            final Poi poi = new Poi();
            final String[] args = { this.getText("sundyn.column.employeeXuHao"), this.getText("sundyn.column.employeeCardNum"), this.getText("sundyn.column.employeeName"),
                    this.getText("sundyn.column.atDating"), this.getText("sundyn.column.appriesResult"),
                    this.getText("sundyn.column.appriesTime"), this.getText("sundyn.inquiry.result.obtainEvidence"),
                    this.getText("sundyn.inquiry.result.businessTime"), "客户姓名", "客户电话", "意见反馈"};

            poi.addTitle(this.getText("sundyn.query.appriesData"), 1, args.length-1);
            poi.addListTitle(args, 1);
            poi.addList(ls);
            poi.createFile(String.valueOf(path) + "standard.xls");
            this.excel = new FileInputStream(String.valueOf(path) + "standard.xls");
            this.fileName = "standard" + Math.round(Math.random() * 10000.0) + ".xls";
            return "excel";
        }

        if (this.getCamera().equals("true")) {
            return "camera";
        }
        request.setAttribute("QueueDetailBean", new QueueDetail());
        return "success";
    }

    public String queryResultDealTel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        final String tel = request.getParameter("tel");
        final int rowsCount = this.queryService.countQueryResultTel(deptIds, tel, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", pageSize, rowsCount, request, this);
        List querylist = this.queryService.queryResultTel(deptIds, tel, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        querylist = this.handleEmptyFile(querylist);
        this.pager.setPageList(querylist);
        this.keys = new StringBuilder(String.valueOf(this.keys)).toString();
        final List chatList = this.queryService.QueryResultChatTel(tel, this.startDate, this.endDate);
        if (chatList != null) {
            for (int i = 0; i < chatList.size(); ++i) {
                final Map temp = (Map) chatList.get(i);
                final Map m = new HashMap();
                m.put("name", temp.get("name"));
                m.put("num", temp.get("num"));
                strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
            }
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }

    public String queryResultDealIdCard() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        final String tel = request.getParameter("tel");
        final int[] rowsCount = new int[]{0};
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        final String allKeyInUse = this.keyTypeService.findAllKeyInUse(1);
        List querylist = this.queryService.queryResultIdCard(deptIds, tel, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
                this.pager.getPageSize(), allKeyInUse, rowsCount);
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        querylist = this.handleEmptyFile(querylist);
        this.pager.setPageList(querylist);
        this.keys = new StringBuilder(String.valueOf(this.keys)).toString();
        final List chatList = this.queryService.QueryResultChatIdCard(tel, this.startDate, this.endDate);
        if (chatList != null) {
            for (int i = 0; i < chatList.size(); ++i) {
                final Map temp = (Map) chatList.get(i);
                final Map m = new HashMap();
                m.put("name", temp.get("name"));
                m.put("num", temp.get("num"));
                strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
            }
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }

    public String querySaveAsExcel() {
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\u4e66\u540d").append("\t").append("\u65e5\u671f").append("\t").append("\u4f5c\u8005").append("\n");
        excelBuf.append("Thinking in Java").append("\t").append("2001").append("\t").append("Eckel").append("\n");
        excelBuf.append("Spring in action").append("\t").append("2005").append("\t").append("Rod").append("\n");
        final String excelString = excelBuf.toString();
        this.excel = new ByteArrayInputStream(excelString.getBytes(), 0, excelString.length());
        return "success";
    }

    public String queryZh() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.deptList = this.deptService.findChildALL(deptIdGroup);
        if (this.deptList != null && this.deptList.size() > 0) {
            final Map dept = (Map) this.deptList.get(0);
            dept.put("fatherId", -1);
        }
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList);
        return "success";
    }

    public String queryZhDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");

        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.deptList2 = this.deptService.findChildALL(deptIdGroup);
        if (this.deptList2 != null && this.deptList2.size() > 0) {
            final Map dept = (Map) this.deptList2.get(0);
            dept.put("fatherId", -1);
        }
        final List keyList2 = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList2);

        String deptIds = request.getParameter("deptIds");
        String keys = request.getParameter("keys");
        String bizname = req.getString("bizname");
        if (this.id == null)
            this.id = 0;
        if (keys == null || keys.equals("")) {
            keys = this.keyTypeService.findAllKeyInUse(1);
        }
        if (deptIds!=null && deptIds.equals("")) {
            deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        }
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.endDate = "";
        }
        if(deptIds==null)
            deptIds = "";
        if (deptIds!=null && deptIds.endsWith(",")) {
            deptIds = deptIds.substring(0, deptIds.length() - 1);
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int rowsCount[] = new int[1];//this.queryService.countQueryZh2(this.id, keys, deptIds, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", pageSize, 0, request, this);
        List tempList = this.queryService.queryZh2(this.id, keys, deptIds, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowsCount);
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        if (tempList != null) {
            tempList = this.handleEmptyFile(tempList);
        }
        this.pager.setPageList(tempList);
        final List chatList = this.queryService.QueryZhChat2(this.id, keys, deptIds, this.startDate, this.endDate);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        for (int i = 0; i < chatList.size(); ++i) {
            final Map temp = (Map) chatList.get(i);
            final Map m = new HashMap();
            m.put("keytypename", temp.get("name"));
            m.put("num", temp.get("num"));
            strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        request.setAttribute("chatList", (Object)chatList);
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }

    public String queryZhDealAjax() throws Exception {
        DateHelper dateHelper = DateHelper.getInstance();
        if(startDate == null) {
            startDate = dateHelper.getDataString_1(dateHelper.getWeekFirstDate());
        }
        if(endDate == null) {
            endDate = dateHelper.getDataString_1(dateHelper.getTodayLastSecond());
        }

        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");

        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        System.out.println("deptidgroup:2>" + deptIdGroup);
        String deptIds = request.getParameter("deptIds");
        String keys = request.getParameter("keys");
        if (keys == null || keys.equals("")) {
            keys = this.keyTypeService.findAllKeyInUseWithNoEval(1);
        }
        if (null==deptIds || deptIds.equals("")) {
            deptIds = this.deptService.findChildALLStr1234(deptIdGroup);
        }
        else{
            deptIds = this.deptService.findChildALLStr1234(deptIds);
        }
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (keys.endsWith(",")) {
            keys = keys.substring(0, keys.length() - 1);
        }
        final int[] rowsCount = new int[]{0};
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        final String allKeyInUse = this.keyTypeService.findAllKeyInUseWithNoEval(1);
        //List tempList = this.queryService.queryZh2(this.id, keys, deptIds, this.startDate, this.endDate,
        //        (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize(), rowsCount);
        List tempList = this.queryService.queryDept2Ex(deptIds,req.getString("bizname"), id, req.getInt("keys",-1), this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(),
                this.pager.getPageSize(), allKeyInUse, rowsCount);
        this.pager = new Pager("currentPage", pageSize, rowsCount[0], request, this);
        if (tempList != null) {
            tempList = this.handleEmptyFile(tempList);
        }
        this.pager.setPageList(tempList);

        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList);

        final List chatList = this.queryService.QueryZhChat2(this.id, keys, deptIds, this.startDate, this.endDate);
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.appriesDataDiagram") + "' xAxisName='\u540d\u79f0' yAxisName='AAAA\u91cf' baseFontSize='14' rotateYAxisName='1' decimalPrecision='0' formatNumberScale='0'>");
        for (int i = 0; i < chatList.size(); ++i) {
            final Map temp = (Map) chatList.get(i);
            final Map m = new HashMap();
            m.put("keytypename", temp.get("name"));
            m.put("num", temp.get("num"));
            strXML1.append("<set name='" + temp.get("name") + "' value='" + temp.get("num") + "' color='" + ColorHelper.getColor() + "' />");
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        request.setAttribute("chatList", (Object)chatList);
        request.setAttribute("QueueDetailBean", new QueueDetail());
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }

    public String queryExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        if (this.startDate == null) {
            this.startDate = "";
        }
        if (this.endDate == null) {
            this.startDate = "";
        }
        if (this.keys == null) {
            this.keys = "";
        }
        if (this.deptIds == null) {
            this.deptIds = "";
        }
        if (this.id == null) {
            this.id = 0;
        }
        if (this.deptIds.endsWith(",")) {
            this.deptIds = this.deptIds.substring(0, this.deptIds.length() - 1);
        }
        if (this.keys.endsWith(",")) {
            this.keys = this.keys.substring(0, this.keys.length() - 1);
        }
        final List tempList = this.queryService.queryZh(this.id, this.keys, this.deptIds, this.startDate, this.endDate, null, null);
        if (tempList != null) {
            final List ls = new ArrayList();
            for (int i = 0; i < tempList.size(); ++i) {
                final Map m = new LinkedHashMap();
                final Map temp = (Map) tempList.get(i);
                m.put("m1", temp.get("CardNum"));
                m.put("m2", temp.get("employeeName"));
                m.put("m3", temp.get("fatherName"));
                m.put("m4", temp.get("keyName"));
                m.put("m5", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(temp.get("JieshouTime")));
                ls.add(m);
            }
            final Poi poi = new Poi();
            poi.addTitle(this.getText("sundyn.query.appriesData"), 1, 3);
            final String[] args = { this.getText("sundyn.column.employeeCardNum"), this.getText("sundyn.column.employeeName"), this.getText("sundyn.column.atDating"), this.getText("sundyn.column.appriesResult"), this.getText("sundyn.column.appriesTime") };
            poi.addListTitle(args, 1);
            poi.addList(ls);
            poi.createFile(String.valueOf(path) + "standard.xls");
            this.excel = new FileInputStream(String.valueOf(path) + "standard.xls");
            this.fileName = "standard" + Math.round(Math.random() * 10000.0) + ".xls";
        }
        return "success";
    }

    public void setDeptId(final Integer deptId) {
        this.deptId = deptId;
    }

    public void setDeptList(final List deptList) {
        this.deptList = deptList;
    }

    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }

    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    public void setExcel(final InputStream excel) {
        this.excel = excel;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setPager(final Pager pager) {
        this.pager = pager;
    }

    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }

    public void setQueryService(final QueryService queryService) {
        this.queryService = queryService;
    }

    public void setSelect(final String select) {
        this.select = select;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public void setTotalService(final TotalService totalService) {
        this.totalService = totalService;
    }

    public String deleteVideoFile() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.deptList = new ArrayList();
        final Map dept = this.deptService.findDeptById(Integer.valueOf(deptIdGroup));
        this.deptList.add(dept);
        request.setAttribute("keyList", (Object)keyList);
        return "success";
    }

    public String deleteVideoFileDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String keyNo = request.getParameter("keyNo");
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        System.out.println("keyNo=" + keyNo);
        spath = String.valueOf(spath) + "download";
        final HttpSession session = request.getSession();
        final String deptIds = this.deptService.findChildALLStr123(this.deptId==null?"":this.deptId.toString());
        final List l = this.queryService.queryAppriesForDel(deptIds, keyNo, this.startDate, this.endDate);
        boolean result = false;
        int num = 0;
        String path = "";
        final Delete del = new Delete();
        logger.info("需要删除数据条数:" + l.size());
        for (final Object o : l) {
            final Map m = (Map)o;
            if (m.get("videofile") != null) {
                path = String.valueOf(spath) + File.separator + m.get("videofile").toString();
                logger.info("开始删除文件:" + path);
                result = del.DeleteFolder(path);
                final String id = m.get("id").toString();
                this.queryService.updateAppriesById(id);
                if (result) {
                    ++num;
                }
            }
        }
        request.setAttribute("msg", (Object)("操作完成,共删除文件" + num + "个文件"));
        return "success";
    }

    public String deleteVideoFileDeal2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        spath = String.valueOf(spath) + "download";
        final HttpSession session = request.getSession();
        final String deptIds = this.deptService.findChildALLStr123(new StringBuilder().append(this.deptId).toString());
        final List l = this.queryService.queryAppriesForDel(deptIds, this.startDate, this.endDate);
        boolean result = false;
        int num = 0;
        String path = "";
        final Delete del = new Delete();
        for (final Object o : l) {
            final Map m = (Map)o;
            if (m.get("videofile") != null) {
                path = String.valueOf(spath) + File.separator + m.get("videofile").toString();
                result = del.DeleteFolder(path);
                final String id = m.get("id").toString();
                this.queryService.updateAppriesById(id);
                if (result) {
                    ++num;
                }
            }
        }
        request.setAttribute("msg", (Object)("共删除文件" + num + "个"));
        return "success";
    }

    public List handleEmptyFile(final List temp) {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String spath = ServletActionContext.getServletContext().getRealPath("/");
        spath = String.valueOf(spath) + "download\\recorder\\";
        String fileName = "";
        final List temp2 = new ArrayList();
        Map m = new HashMap();
        if (temp != null) {
            for (final Object o : temp) {
                m = (Map)o;
                if (m.get("imgfile") != null) {
                    fileName = m.get("imgfile").toString();
                }
                final File file = new File(String.valueOf(spath) + fileName);
                if (!file.exists()) {
                    m.put("imgfile", "");
                }
                else if (file.length() == 0L) {
                    m.put("imgfile", "");
                }
                temp2.add(m);
            }
            return temp2;
        }
        return null;
    }

    @Resource
    private IWarnOntimedetailService warnDetailService;

    public String warnQuery(){
        try {
            String orgcode = req.getString("orgcode");
            String orgname = req.getString("orgname");
            String type = req.getString("type");
            String nofityname = req.getString("nofityname", "");
            Wrapper<Map> ew =new EntityWrapper<>();

            if (null!=orgname && !"".equals(orgname))
                ew = ew.where("orgname like '%"+orgname+"%'");
            if (null!=orgcode && !"".equals(orgcode))
                ew = ew.where("orgcode like '%"+orgcode+"%'");
            if (null!=nofityname && !"".equals(nofityname))
                ew = ew.where("appries_employee.name like '%"+nofityname+"%'");
            if (null!=type && !"".equals(type))
                ew = ew.where("type='"+type+"'");

            Page<Map> queryData = warnDetailService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy("ctime desc"));
            request.setAttribute("nofityname", nofityname);
            request.setAttribute("queryData", queryData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
