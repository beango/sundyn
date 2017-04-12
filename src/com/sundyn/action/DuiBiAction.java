package com.sundyn.action;

import com.opensymphony.xwork2.*;
import org.apache.struts2.interceptor.*;
import javax.servlet.http.*;
import java.io.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import com.sundyn.util.*;
import java.util.*;

public class DuiBiAction extends ActionSupport implements ServletRequestAware
{
    private HttpServletRequest request;
    private static final Integer pageSize;
    private Integer deptId;
    private List deptList;
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
    private List list;
    
    static {
        pageSize = 6;
    }
    
    public List getList() {
        return this.list;
    }
    
    public void setList(final List list) {
        this.list = list;
    }
    
    public Integer getDeptId() {
        return this.deptId;
    }
    
    public void setDeptId(final Integer deptId) {
        this.deptId = deptId;
    }
    
    public List getDeptList() {
        return this.deptList;
    }
    
    public void setDeptList(final List deptList) {
        this.deptList = deptList;
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
    
    public String getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }
    
    public InputStream getExcel() {
        return this.excel;
    }
    
    public void setExcel(final InputStream excel) {
        this.excel = excel;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public String getKeyword() {
        return this.keyword;
    }
    
    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public Pager getPager() {
        return this.pager;
    }
    
    public void setPager(final Pager pager) {
        this.pager = pager;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public QueryService getQueryService() {
        return this.queryService;
    }
    
    public void setQueryService(final QueryService queryService) {
        this.queryService = queryService;
    }
    
    public String getSelect() {
        return this.select;
    }
    
    public void setSelect(final String select) {
        this.select = select;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }
    
    public TotalService getTotalService() {
        return this.totalService;
    }
    
    public void setTotalService(final TotalService totalService) {
        this.totalService = totalService;
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
    
    public String getFileName() {
        return this.fileName;
    }
    
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }
    
    public boolean isK7() {
        return this.k7;
    }
    
    public void setK7(final boolean k7) {
        this.k7 = k7;
    }
    
    public List getMls() {
        return this.mls;
    }
    
    public void setMls(final List mls) {
        this.mls = mls;
    }
    
    public List getBmls() {
        return this.bmls;
    }
    
    public void setBmls(final List bmls) {
        this.bmls = bmls;
    }
    
    public static Integer getPagesize() {
        return DuiBiAction.pageSize;
    }
    
    public void setServletRequest(final HttpServletRequest request) {
        this.request = request;
    }
    
    public String gongZuoLiang() throws Exception {
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
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.unitWorkDiagram") + "' xAxisName='" + this.getText("sundyn.system.checkOnlineM7.unitName") + "' yAxisName='" + this.getText("sundyn.inquiry.workload") + "' rotateYAxisName='1' baseFont='Arial' baseFontSize='14' decimalPrecision='0' formatNumberScale='0' >");
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
                strXML1.append("<set name='" + name + "' value='" + sum + "' color='FF9933' />");
                System.out.println("name=" + name + "," + "value=" + sum);
            }
        }
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        return "success";
    }
    
    public String daTingGongZuoLaing() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map manager = (Map)this.request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.totalService.counttotalDating(ids, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, this.request);
        this.list = this.totalService.totalDating(ids, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.hallSatisfactionDiagram") + "' xAxisName='" + this.getText("sundyn.column.datingName") + "' yAxisName='" + this.getText("sundyn.column.contentDegree") + "' rotateYAxisName='1' baseFont='Arial' baseFontSize='14' decimalPrecision='0' formatNumberScale='0'>");
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\ufffd\ufffd\u05aa");
        m1.put("item", "\ufffd\ufffd\u05fc");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\ufffd\ufffd\u05aa");
            j.put("item", temp.get("datingname"));
            strXML1.append("<set name='" + temp.get("datingname") + "' value='" + temp.get("num") + "' color='FF9933' />");
            System.out.println("name=" + temp.get("datingname") + "," + "value=" + temp.get("num"));
        }
        strXML1.append("</graph>");
        this.request.setAttribute("strXML1", (Object)strXML1.toString());
        return "success";
    }
    
    public String windowDuiBi() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String deptId = this.request.getParameter("deptId");
        final int rowsCount = this.totalService.counttotalWindow(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, this.request);
        this.list = this.totalService.totalWindow(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.windowSatisfactionDiagram") + "' xAxisName='" + this.getText("sundyn.column.windowName") + "' yAxisName='" + this.getText("sundyn.column.contentDegree") + "' rotateYAxisName='1' baseFont='Arial' baseFontSize='14' decimalPrecision='0' formatNumberScale='0'>");
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\ufffd\ufffd\u05aa");
        m1.put("item", "\ufffd\ufffd\u05fc");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            System.out.println("\ufffd\ufffd\ufffd\ufffd\ufffd=" + temp.get("num"));
            j.put("category", "\ufffd\ufffd\u05aa");
            j.put("item", temp.get("windowname"));
            System.out.println("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd=" + temp.get("windowname"));
            strXML1.append("<set name='" + temp.get("windowname") + "' value='" + temp.get("num") + "' color='FF9933' />");
            System.out.println("name=" + temp.get("windowname") + "," + "value=" + temp.get("num"));
        }
        strXML1.append("</graph>");
        this.request.setAttribute("strXML1", (Object)strXML1.toString());
        return "success";
    }
    
    public String personDuiBi() throws Exception {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String deptId = this.request.getParameter("deptId");
        deptId = this.deptService.findChildALLStr123(deptId);
        final int rowsCount = this.totalService.countTotalPerson(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, this.request);
        this.list = this.totalService.totalPerson(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='UTF-8'?>");
        strXML1.append("<graph caption='" + this.getText("sundyn.inquiry.employeeSatisfactionDiagram") + "' xAxisName='" + this.getText("sundyn.column.name") + "' yAxisName='" + this.getText("sundyn.column.contentDegree") + "' rotateYAxisName='1' baseFont='Arial' baseFontSize='14' decimalPrecision='0' formatNumberScale='0'>");
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\ufffd\ufffd\u05aa");
        m1.put("item", "\ufffd\ufffd\u05fc");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\ufffd\ufffd\u05aa");
            j.put("item", temp.get("employeeName"));
            strXML1.append("<set name='" + temp.get("employeeName") + "' value='" + temp.get("num") + "' color='FF9933' />");
            System.out.println("name=" + temp.get("employeeName") + "," + "value=" + temp.get("num"));
        }
        strXML1.append("</graph>");
        this.request.setAttribute("strXML1", (Object)strXML1.toString());
        return "success";
    }
    
    public List getPandM(final List list) throws Exception {
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        if (list != null) {
            final List temp = new ArrayList();
            for (int i = 0; i < list.size(); ++i) {
                final Map m = (Map) list.get(i);
                final Integer key0 = Integer.valueOf(m.get("key0").toString());
                final Integer key2 = Integer.valueOf(m.get("key1").toString());
                final Integer key3 = Integer.valueOf(m.get("key2").toString());
                final Integer key4 = Integer.valueOf(m.get("key3").toString());
                final Integer key5 = Integer.valueOf(m.get("key4").toString());
                final Integer key6 = Integer.valueOf(m.get("key5").toString());
                final Integer key7 = Integer.valueOf(m.get("key6").toString());
                final Integer[] key8 = { key0, key2, key3, key4, key5, key6, key7 };
                final double prate = Math.rint((1.0 - key7 * 1.0 / (key0 + key2 + key3 + key4 + key5 + key6 + key7)) * 10000.0) / 100.0;
                final double erate = Math.rint((key0 * 1.0 + key2 * 1.0) / (key0 + key2 + key4 + key5 + key6 + key7) * 10000.0) / 100.0;
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
                    if (Double.valueOf(mrate).equals(Double.NaN)) {
                        mrate = 0.0;
                    }
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
                    if (Double.valueOf(mrate).equals(Double.NaN)) {
                        mrate = 0.0;
                    }
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
                temp.add(m);
            }
            return temp;
        }
        return null;
    }
    
    private List getStar(final List ls) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        if (ls != null) {
            try {
                final List temp = new ArrayList();
                final SundynSet sundynSet = SundynSet.getInstance(path);
                final List l_star = sundynSet.getL_star();
                for (int i = 0; i < ls.size(); ++i) {
                    final Map t = (Map) ls.get(i);
                    final Double mrate = Double.valueOf(t.get("mrate").toString());
                    int j;
                    for (j = 0; j < l_star.size(); ++j) {
                        final Map star_level = (Map) l_star.get(j);
                        final Double star100 = Double.valueOf(star_level.get("star100").toString());
                        if (mrate >= star100) {
                            t.put("star", star_level.get("star"));
                            temp.add(t);
                            break;
                        }
                    }
                    if (j == l_star.size()) {
                        t.put("star", "&nbsp");
                        temp.add(t);
                    }
                }
                return temp;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
    
    private List getD(final List ls) {
        if (this.list != null) {
            final List temp = new ArrayList();
            for (int i = 0; i < this.list.size(); ++i) {
                final Map m = (Map) this.list.get(i);
                final Integer key0 = Integer.valueOf(m.get("key0").toString());
                final Integer key2 = Integer.valueOf(m.get("key1").toString());
                final Integer key3 = Integer.valueOf(m.get("key2").toString());
                final Integer key4 = Integer.valueOf(m.get("key3").toString());
                final Integer key5 = Integer.valueOf(m.get("key4").toString());
                final Integer key6 = Integer.valueOf(m.get("key5").toString());
                if (key0 + key2 + key3 + key4 + key5 + key6 == 0) {
                    m.put("num", 100);
                }
                else {
                    m.put("num", (key0 * 5 + key2 * 4 + key3 * 3) / (key0 + key2 + key4 + key5 + key6) * 20);
                }
                temp.add(m);
            }
            return temp;
        }
        return null;
    }
}
