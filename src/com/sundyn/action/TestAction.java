package com.sundyn.action;

import com.sundyn.dao.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

public class TestAction extends SuperDao
{
    public static void main(final String[] args) {
        System.out.println("abcd".indexOf("m"));
    }
    
    public String testData() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String numTemp = request.getParameter("num");
        final String monthTemp = request.getParameter("month");
        final String keyTemp = request.getParameter("key");
        Integer num = 1000;
        if (numTemp != null) {
            num = Integer.valueOf(numTemp);
        }
        final List empList = this.getJdbcTemplate().queryForList("select * from appries_employee");
        final List tempList = new ArrayList();
        for (int i = 0; i < empList.size(); ++i) {
            final Map m = (Map) empList.get(i);
            final String CardNum = m.get("CardNum").toString();
            final String depId = m.get("deptid").toString();
            String remark = "";
            final List l = this.getJdbcTemplate().queryForList("select remark from appries_dept where fatherId=" + depId);
            if (l.size() > 0) {
                final Map mtemp = (Map) l.get(0);
                remark = mtemp.get("remark").toString();
            }
            final Map em = new HashMap();
            em.put("CardNum", CardNum);
            em.put("remark", remark);
            tempList.add(em);
        }
        int i = 0;
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        while (i <= 1000) {
            final int intTemp = (int)(Math.random() * tempList.size());
            int key = (int)(Math.random() * 7.0);
            if (keyTemp != null) {
                key = Integer.valueOf(keyTemp);
            }
            int j = (int)(Math.random() * 12.0) + 1;
            if (monthTemp != null) {
                j = Integer.valueOf(monthTemp);
            }
            final int dd = (int)(Math.random() * 28.0) + 1;
            final int hh = ((Math.random() > 0.5) ? 9 : 15) + (int)(Math.random() * 3.0);
            final int mm = (int)(Math.random() * 60.0);
            final int ss = (int)(Math.random() * 60.0);
            final Calendar cal = Calendar.getInstance();
            cal.set(2011, j, dd, hh, mm, ss);
            final SimpleDateFormat dfweek = new SimpleDateFormat("E");
            final String mydate3 = dfweek.format(cal.getTime());
            System.out.println(cal.get(3));
            if (!mydate3.equals("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd") && !mydate3.equals("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd")) {
                final String sql = "call appriesAdd(?,?,?,?,?,1)";
                final String dt = df.format(cal.getTime());
                Map map = (Map)tempList.get(intTemp);
                final Object[] args = { map.get("remark").toString(), dt, map.get("CardNum").toString(), key, "asdfasdfasdf" };
                try {
                    this.getJdbcTemplate().update(sql, args);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(dt);
                System.out.println(i);
                ++i;
            }
        }
        return "success";
    }
}
