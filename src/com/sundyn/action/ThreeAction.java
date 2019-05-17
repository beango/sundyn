package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.*;
import com.sundyn.util.Pager;
import com.sundyn.util.Poi;
import com.sundyn.util.Three;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ThreeAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    private static final Integer pageSize;
    private ThreeService threeService;
    private PowerService powerService;
    private DeptService deptService;
    private KeyTypeService keyTypeService;
    private QueryService queryService;
    private List deptList;
    private File img;
    private String msg;
    private String deptIds;
    private String keys;
    private String startDate;
    private String endDate;
    private Integer id;
    private Pager pager;
    
    static {
        pageSize = 12;
    }
    
    public QueryService getQueryService() {
        return this.queryService;
    }
    
    public void setQueryService(final QueryService queryService) {
        this.queryService = queryService;
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
    
    public ThreeService getThreeService() {
        return this.threeService;
    }
    
    public void setThreeService(final ThreeService threeService) {
        this.threeService = threeService;
    }
    
    public File getImg() {
        return this.img;
    }
    
    public void setImg(final File img) {
        this.img = img;
    }
    
    public String getMsg() {
        return this.msg;
    }
    
    public void setMsg(final String msg) {
        this.msg = msg;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
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
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public String threeView() {
        return "success";
    }
    
    public String threeLoad() throws Exception {
        this.threeService.del();
        int sum = 0;
        String error = "";
        if (this.img != null) {
            List<?> ls = new ArrayList<Object>();
            try {
                final Three three = new Three(this.img);
                ls = three.getTableData();
            }
            catch (Exception e) {
                e.printStackTrace();
                this.msg = "\ufffd\u013c\ufffd\ufffd\ufffd\u02bd\ufffd\ufffd\ufffd\ufffd\ufffd\u28ec\ufffd\ufffd\ufffd\ufffd";
                return "success";
            }
            if (ls != null && ls.size() > 0) {
                for (int i = 0; i < ls.size(); ++i) {
                    final String[] args = (String[])ls.get(i);
                    if (this.threeService.add(args)) {
                        ++sum;
                    }
                    else {
                        error = String.valueOf(error) + args[0];
                    }
                }
                this.msg = "\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd" + sum + "\ufffd\ufffd\ufffd\ufffd\u0763\ufffd";
                if (!error.equals("")) {
                    this.msg = String.valueOf(this.msg) + error + "\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd";
                }
            }
            else {
                this.msg = "\ufffd\u013c\ufffd\ufffd\ufffd\u00fb\ufffd\ufffd\ufffd\ufffd\u0763\ufffd\ufffd\ufffd\ufffd\ufffd";
            }
        }
        else {
            this.msg = "\u00fb\ufffd\ufffd\ufffd\u013c\ufffd";
        }
        return "success";
    }
    
    public String threeSuit() throws ParseException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String big = request.getParameter("big");
        final String small = request.getParameter("small");
        final String step = request.getParameter("step");
        Integer i_big = 300;
        Integer i_small = 0;
        Integer i_step = 10;
        try {
            i_big = Integer.valueOf(big);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            i_small = Integer.valueOf(small);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            i_step = Integer.valueOf(step);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        String startDate = this.threeService.getMinDate();
        String endDate = this.threeService.getMaxDate();
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        cal.setTime(df.parse(startDate));
        cal.add(13, 0 - i_big);
        Date d = cal.getTime();
        startDate = df.format(d);
        cal.setTime(df.parse(endDate));
        cal.add(13, i_big);
        d = cal.getTime();
        endDate = df.format(d);
        final List appries_ls = this.threeService.queryAppries(startDate, endDate);
        final List ls = this.threeService.query(false);
        int sum = 0;
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); ++i) {
                final Map business = (Map) ls.get(i);
                final String b_name = business.get("realName").toString();
                final String b_userName = business.get("userName").toString();
                final String b_date = business.get("ext9").toString();
                final String b_id = business.get("ext1").toString();
                for (int k = i_small; k <= i_big; k += i_step) {
                    cal.setTime(df.parse(b_date));
                    cal.add(13, k);
                    d = cal.getTime();
                    final String b_s_date = df.format(d);
                    cal.add(13, -2 * k);
                    d = cal.getTime();
                    final String b_e_date = df.format(d);
                    final int temp = appries_ls.size();
                    for (int j = 0; j < appries_ls.size(); ++j) {
                        final Map appries = (Map) appries_ls.get(j);
                        final String name = appries.get("Name").toString();
                        String date = appries.get("JieshouTime").toString();
                        final String userName = appries.get("ext2").toString();
                        if (date.endsWith(".0")) {
                            date = date.substring(0, date.length() - 2);
                        }
                        final String id = appries.get("id").toString();
                        if (b_name.equals(name) && b_userName.equals(userName) && date.compareTo(b_s_date) >= 0 && date.compareTo(b_e_date) >= 0) {
                            this.threeService.match(b_id, id);
                            appries_ls.remove(j);
                            ++sum;
                            break;
                        }
                    }
                    if (temp != appries_ls.size()) {
                        break;
                    }
                }
            }
        }
        this.msg = "\ufffd\ufffd\ufffd\ufffd" + (ls.size() - sum) + "\ufffd\ufffd\ufffd\ufffd\ufffd\u03b4\u01a5\ufffd\ufffd\u0279\ufffd";
        return "success";
    }
    
    public String threeQuery() throws SQLException {
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
    
    public String threeQueryDeal() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String deptIds = request.getParameter("deptIds");
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
        final int rowsCount = this.threeService.countQuery(this.id, keys, deptIds, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", ThreeAction.pageSize, rowsCount, request, this);
        final List tempList = this.threeService.query(this.id, keys, deptIds, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(tempList);
        this.keys = keys;
        this.deptIds = deptIds;
        return "success";
    }
    
    public String threeExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String deptIds = request.getParameter("deptIds");
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
        final List tempList = this.threeService.query(this.id, keys, deptIds, this.startDate, this.endDate, null, null);
        final List ls = new ArrayList();
        if (tempList != null) {
            for (int i = 0; i < tempList.size(); ++i) {
                final Map m = (Map) tempList.get(i);
                final Map l_m = new LinkedHashMap();
                l_m.put("ext1", m.get("ext1"));
                l_m.put("userName", m.get("userName"));
                l_m.put("employeeName", m.get("employeeName"));
                l_m.put("ext9", m.get("ext9"));
                l_m.put("ext5", m.get("ext5"));
                l_m.put("deptName", m.get("deptName"));
                l_m.put("keyName", m.get("keyName"));
                ls.add(l_m);
            }
        }
        final Poi poi = new Poi();
        final String[] args = { "\u04b5\ufffd\ufffd\ufffd\ufffd\u02ee\ufffd\ufffd", "\ufffd\ufffd\ufffd\ufffd\u0531\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd", "\u0531\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd", "\ufffd\ufffd\ufffd\ufffd\u02b1\ufffd\ufffd", "\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd", "\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd", "\ufffd\ufffd\ufffd\u06fd\ufffd\ufffd" };
        poi.addListTitle(args, 1);
        poi.addList(ls);
        poi.createFile(String.valueOf(path) + "standard.xls");
        return "success";
    }
}
