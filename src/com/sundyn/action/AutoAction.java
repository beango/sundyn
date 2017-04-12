package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.sql.*;
import com.sundyn.util.*;
import java.text.*;
import java.util.*;
import java.util.Date;

public class AutoAction extends ActionSupport
{
    private static final long serialVersionUID = 2142406288491773772L;
    private AppriesService appriesService;
    private PowerService powerService;
    private DeptService deptService;
    private KeyTypeService keyTypeService;
    private EmployeeService employeeService;
    private List deptList;
    private String msg;
    
    public AppriesService getAppriesService() {
        return this.appriesService;
    }
    
    public void setAppriesService(final AppriesService appriesService) {
        this.appriesService = appriesService;
    }
    
    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }
    
    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
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
    
    public List getDeptList() {
        return this.deptList;
    }
    
    public void setDeptList(final List deptList) {
        this.deptList = deptList;
    }
    
    public String autoView() throws SQLException {
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
    
    public String autoDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptIds = request.getParameter("deptIds");
        final String employeeId = request.getParameter("employeeId");
        final String startDate = request.getParameter("startDate");
        final String endDate = request.getParameter("endDate");
        final String amUp = request.getParameter("amUp");
        final String amDown = request.getParameter("amDown");
        final String pmUp = request.getParameter("pmUp");
        final String pmDown = request.getParameter("pmDown");
        final String key = request.getParameter("key");
        final String num = request.getParameter("num");
        if (deptIds.equals("") && employeeId.equals("")) {
            this.msg = "\u90e8\u95e8\u6216\u4eba\u5458\u5fc5\u987b\u4efb\u9009\u4e00\uff0c\u90e8\u95e8\u4f18\u5148";
            return "success";
        }
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat tf = new SimpleDateFormat("HH:mm");
        Date d_startDate;
        try {
            d_startDate = df.parse(startDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u5f00\u59cb\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        Date d_endDate;
        try {
            d_endDate = df.parse(endDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u7ed3\u675f\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_amUp = tf.parse(amUp);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0a\u5348\u4e0a\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_amDown = tf.parse(amDown);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0a\u5348\u4e0b\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_pmUp = tf.parse(pmUp);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0b\u5348\u4e0a\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_pmDown = tf.parse(pmDown);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0b\u5348\u4e0b\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        Integer i_num;
        try {
            i_num = Integer.valueOf(num);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u6570\u91cf\u683c\u5f0f\u4e0d\u5bf9";
            return "success";
        }
        if (deptIds.equals("")) {
            try {
                final Map m = this.employeeService.findEmployeeById(Integer.valueOf(employeeId));
                final Integer deptId = Integer.valueOf(m.get("deptid").toString());
                this.deptList = this.deptService.findchild(deptId);
                final String[] remark = new String[0];
                if (this.deptList == null || this.deptList.size() == 0) {
                    this.msg = "\u6ca1\u6709\u6dfb\u52a0\u7a97\u53e3";
                    return "success";
                }
                for (int i = 0; i < this.deptList.size(); ++i) {
                	Map map = (Map)this.deptList.get(i);
                    remark[i] = map.get("remark").toString();
                }
                final Calendar cal_start = Calendar.getInstance();
                final Calendar cal_end = Calendar.getInstance();
                cal_start.setTime(d_startDate);
                cal_end.setTime(d_endDate);
                final long n = cal_end.getTimeInMillis() - cal_start.getTimeInMillis();
                int j = 0;
                final List l_pj = new ArrayList();
                while (j < i_num) {
                    final long rand = (long)Math.floor(n * Math.random());
                    final long t = cal_start.getTimeInMillis() + rand;
                    final Date date = new Date(t);
                    final Calendar curent = Calendar.getInstance();
                    curent.setTime(date);
                    if (curent.get(7) != 7) {
                        if (curent.get(7) == 1) {
                            continue;
                        }
                        final String s_time = tf.format(curent.getTime());
                        if (s_time.compareTo(amUp) > -1 && s_time.compareTo(amDown) == -1) {
                            continue;
                        }
                        if (s_time.compareTo(pmUp) > -1 && s_time.compareTo(pmDown) == -1) {
                            continue;
                        }
                        final String s_dt = df.format(curent.getTime());
                        final Map m_date = new HashMap();
                        m_date.put("date", s_dt);
                        l_pj.add(m_date);
                        ++j;
                    }
                }
                Collections.sort((List<Object>)l_pj, new DateComparator());
            }
            catch (NumberFormatException e2) {
                e2.printStackTrace();
                this.msg = "\u8bf7\u9009\u62e9\u4eba\u5458";
                return "success";
            }
            catch (SQLException e3) {
                e3.printStackTrace();
                this.msg = "\u8fd9\u4e2a\u4eba\u4e0d\u5b58\u5728";
                return "success";
            }
        }
        return "success";
    }
    
    public String autoView2() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        System.out.println("groupid=" + groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.deptList = this.deptService.findChildALL(deptIdGroup);
        System.out.println("WindowNum=" + this.deptList.size());
        for (final Object o : this.deptList) {
            System.out.println(o);
        }
        Map m = null;
        int i;
        int j;
        for (i = 0, j = this.deptList.size(), i = 0; i < j; ++i) {
            m = (Map) this.deptList.get(i);
            if (m.get("deptType") != null && !m.get("deptType").toString().equals("0")) {
                this.deptList.remove(i);
                --j;
                --i;
            }
        }
        if (this.deptList != null && this.deptList.size() > 0) {
            final Map dept = (Map) this.deptList.get(0);
            dept.put("fatherId", -1);
        }
        System.out.println("deptList.size=" + this.deptList.size());
        final List keyList = this.keyTypeService.findByApprieserId(1, 1);
        request.setAttribute("keyList", (Object)keyList);
        for (final Object o2 : this.deptList) {
            System.out.println(o2);
        }
        request.setAttribute("deptList", (Object)this.deptList);
        return "success";
    }
    
    public String autoDeal2() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptIds = request.getParameter("deptIds");
        final String deptId2 = request.getParameter("deptId");
        final String employeeId = request.getParameter("employeeId");
        final String startDate = request.getParameter("startDate");
        final String endDate = request.getParameter("endDate");
        final String amUp = request.getParameter("amUp");
        final String amDown = request.getParameter("amDown");
        final String pmUp = request.getParameter("pmUp");
        final String pmDown = request.getParameter("pmDown");
        final String key = request.getParameter("key");
        final String num = request.getParameter("num").toString();
        final String weekend = request.getParameter("weekend");
        System.out.println("weekend=" + weekend);
        if (deptIds.equals("") && employeeId.equals("")) {
            this.msg = "\u90e8\u95e8\u6216\u4eba\u5458\u5fc5\u987b\u4efb\u9009\u4e00\uff0c\u90e8\u95e8\u4f18\u5148";
            return "success";
        }
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final DateFormat tf = new SimpleDateFormat("HH:mm");
        Date d_startDate;
        try {
            d_startDate = df.parse(startDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u5f00\u59cb\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        Date d_endDate;
        try {
            d_endDate = df.parse(endDate);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u7ed3\u675f\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_amUp = tf.parse(amUp);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0a\u5348\u4e0a\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_amDown = tf.parse(amDown);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0a\u5348\u4e0b\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_pmUp = tf.parse(pmUp);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0b\u5348\u4e0a\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        try {
            final Date d_pmDown = tf.parse(pmDown);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u4e0b\u5348\u4e0b\u73ed\u65f6\u95f4\u7684\u683c\u5f0f\u9519\u8bef \u8bf7\u6309\u7167";
            return "success";
        }
        Integer i_num;
        try {
            i_num = Integer.valueOf(num);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.msg = "\u6570\u91cf\u683c\u5f0f\u4e0d\u5bf9";
            return "success";
        }
        if (deptIds.equals("")) {
            try {
                final Map m = this.employeeService.findEmployeeById(Integer.valueOf(employeeId));
                final Integer deptId3 = Integer.valueOf(m.get("deptid").toString());
                this.deptList = this.deptService.findchild(deptId3);
                final String[] remark = new String[(int)Integer.valueOf(num)];
                if (this.deptList == null || this.deptList.size() == 0) {
                    this.msg = "\u6ca1\u6709\u6dfb\u52a0\u7a97\u53e3";
                    return "success";
                }
                System.out.println("deptList.size()=" + this.deptList.size());
                for (int i = 0; i < this.deptList.size(); ++i) {
                    System.out.println("deptList.get(" + i + ")=" + this.deptList.get(i));
                    final Map m2 = (Map) this.deptList.get(i);
                    if (m2.get("remark") != null) {
                        System.out.println(m2.get("remark").toString());
                        remark[i] = m2.get("remark").toString();
                    }
                }
                final Calendar cal_start = Calendar.getInstance();
                final Calendar cal_end = Calendar.getInstance();
                cal_start.setTime(d_startDate);
                cal_end.setTime(d_endDate);
                final long n = cal_end.getTimeInMillis() - cal_start.getTimeInMillis();
                System.out.println("cal_start=" + cal_start);
                System.out.println("cal_end=" + cal_end);
                System.out.println("n=" + n);
                int j = 0;
                final List l_pj = new ArrayList();
                while (j < i_num) {
                    final long rand = (long)Math.floor(n * Math.random());
                    final long t = cal_start.getTimeInMillis() + rand;
                    final Date date = new Date(t);
                    final Calendar curent = Calendar.getInstance();
                    curent.setTime(date);
                    if (weekend != null && weekend.equals("3")) {
                        if (curent.get(7) == 7) {
                            continue;
                        }
                        if (curent.get(7) == 1) {
                            continue;
                        }
                    }
                    else if (weekend != null && weekend.equals("2")) {
                        if (curent.get(7) == 1) {
                            continue;
                        }
                    }
                    else if (weekend != null && weekend.equals("1")) {
                        if (curent.get(7) == 7) {
                            continue;
                        }
                    }
                    else if (weekend != null) {
                        weekend.equals("0");
                    }
                    final String s_time = tf.format(curent.getTime());
                    if (s_time.compareTo(amUp) <= -1 || s_time.compareTo(amDown) != -1) {
                        if (s_time.compareTo(pmUp) > -1 && s_time.compareTo(pmDown) == -1) {
                            continue;
                        }
                        final String s_dt = df.format(curent.getTime());
                        final Map m_date = new HashMap();
                        m_date.put("date", s_dt);
                        l_pj.add(m_date);
                        ++j;
                    }
                }
                Collections.sort((List<Object>)l_pj, new DateComparator());
                final String mac = this.deptService.findDeptById(Integer.valueOf(deptId2)).get("remark").toString();
                System.out.println("deptId2=" + deptId2);
                String tt = "";
                final String cardnum = m.get("cardnum").toString();
                final String pj = key;
                final String demo = "";
                for (int k = 0; k < l_pj.size(); ++k) {
                	Map map = (Map)l_pj.get(k);
                    tt = map.get("date").toString();
                    tt = ConvertToWorkTime(tt, amUp, amDown, pmUp, pmDown);
                    this.appriesService.addArriresAuto(mac, tt, cardnum, pj, demo);
                }
            }
            catch (NumberFormatException e2) {
                e2.printStackTrace();
                this.msg = "\u8bf7\u9009\u62e9\u4eba\u5458";
                return "success";
            }
            catch (SQLException e3) {
                e3.printStackTrace();
                this.msg = "\u8fd9\u4e2a\u4eba\u4e0d\u5b58\u5728";
                return "success";
            }
        }
        System.out.println("7");
        this.msg = "\u751f\u6210" + num + "\u6570\u636e\u6210\u529f";
        return "success";
    }
    
    public static String ConvertToWorkTime(String tt, final String amUp, final String amDown, final String pmUp, final String pmDown) {
        final int amMin = Integer.valueOf(amUp.split(":", 2)[0]);
        final int amMax = Integer.valueOf(amDown.split(":", 2)[0]);
        final int pmMin = Integer.valueOf(pmUp.split(":", 2)[0]);
        final int pmMax = Integer.valueOf(pmDown.split(":", 2)[0]);
        final String[] tt2 = tt.split("\\s");
        final String[] tt3 = tt2[1].split(":", 2);
        final Random rand = new Random();
        String hour = tt3[0];
        if ((tt2[1].compareTo(amUp) <= 0 || tt2[1].compareTo(amDown) >= 0) && (tt2[1].compareTo(pmUp) <= 0 || tt2[1].compareTo(pmDown) >= 0)) {
            if (Integer.valueOf(hour) < 12) {
                hour = String.valueOf(rand.nextInt(amMax - amMin + 1) + amMin);
                if (Integer.valueOf(hour) < 10) {
                    hour = "0" + hour;
                }
            }
            else {
                hour = String.valueOf(rand.nextInt(pmMax - pmMin + 1) + pmMin);
                if (Integer.valueOf(hour) < 10) {
                    hour = "0" + hour;
                }
            }
            tt = String.valueOf(tt2[0]) + " " + hour + ":" + tt3[1];
        }
        return tt;
    }
}
