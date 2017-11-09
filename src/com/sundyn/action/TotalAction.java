package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.*;
import com.sundyn.util.JfreeChart;
import com.sundyn.util.Pager;
import com.sundyn.util.Poi;
import com.sundyn.util.SundynSet;
import com.sundyn.vo.WeburlVo;
import org.apache.struts2.ServletActionContext;
import org.jdom.JDOMException;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalAction extends ActionSupport
{
    private List bmls;
    private DeptService deptService;
    private String endDate;
    private InputStream excel;
    private String ids;
    private boolean k7;
    private KeyTypeService keyTypeService;
    private List list;

    public List getList2() {
        return list2;
    }

    public void setList2(List list2) {
        this.list2 = list2;
    }

    private List list2;
    private List mls;
    private Pager pager;
    private PowerService powerService;
    private String startDate;
    private TotalService totalService;
    private BusinessService businessService;
    private String str;

    public TotalAction() {
        this.str = "";
    }

    public List getBmls() {
        return this.bmls;
    }

    private Map getDemo() throws Exception {
        final List ls = this.keyTypeService.findByApprieserId(1, 1);
        final Map m = new HashMap();
        for (int i = 0; i < ls.size(); ++i) {
            final Map temp = (Map) ls.get(i);
            m.put("key" + temp.get("keyNo").toString(), temp.get("name"));
        }
        return m;
    }

    public DeptService getDeptService() {
        return this.deptService;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public InputStream getExcel() {
        return this.excel;
    }

    public String getIds() {
        return this.ids;
    }

    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }

    public List getList() {
        return this.list;
    }

    public List getMls() {
        return this.mls;
    }

    public Pager getPager() {
        return this.pager;
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

    public PowerService getPowerService() {
        return this.powerService;
    }

    public Map getRate(final Map totalMap) throws Exception {
        if (totalMap != null) {
            this.k7 = this.isK7();
            this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
            this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
            final Integer k0 = Integer.valueOf(totalMap.get("key0").toString());
            final Integer k2 = Integer.valueOf(totalMap.get("key1").toString());
            final Integer k3 = Integer.valueOf(totalMap.get("key2").toString());
            final Integer k4 = Integer.valueOf(totalMap.get("key3").toString());
            final Integer k5 = Integer.valueOf(totalMap.get("key4").toString());
            final Integer k6 = Integer.valueOf(totalMap.get("key5").toString());
            final Integer k7 = Integer.valueOf(totalMap.get("key6").toString());
            final Integer[] key = { k0, k2, k3, k4, k5, k6, k7 };
            final Integer ksum = k0 + k2 + k3 + k4 + k5 + k6 + k7;
            final double kr0 = Math.rint(k0 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr2 = Math.rint(k2 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr3 = Math.rint(k3 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr4 = Math.rint(k4 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr5 = Math.rint(k5 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr6 = Math.rint(k6 * 1.0 / ksum * 1000.0) / 10.0;
            final double kr7 = Math.rint(k7 * 1.0 / ksum * 1000.0) / 10.0;
            final double[] keyr = { kr0, kr2, kr3, kr4, kr5, kr6, kr7 };
            double prate = 0.0;
            double mrate = 0.0;
            double bmrate = 0.0;
            final List km = new ArrayList();
            final List kbm = new ArrayList();
            final List krm = new ArrayList();
            final List krbm = new ArrayList();
            int msum = 0;
            int bmsum = 0;
            double krsum = 0.0;
            double krbsum = 0.0;
            int p = 0;
            int sum = 0;
            String k7Name = "";
            if (this.k7) {
                for (int j = 0; j < this.mls.size(); ++j) {
                    final Map i = (Map) this.mls.get(j);
                    if (!i.get("keyNo").toString().equals("6")) {
                        msum += key[Integer.parseInt(i.get("keyNo").toString())];
                        krsum += keyr[Integer.parseInt(i.get("keyNo").toString())];
                        km.add(key[Integer.parseInt(i.get("keyNo").toString())]);
                        krm.add(keyr[Integer.parseInt(i.get("keyNo").toString())]);
                    }
                    else {
                        k7Name = i.get("name").toString();
                        this.mls.remove(j);
                    }
                }
                for (int j = 0; j < this.bmls.size(); ++j) {
                    final Map i = (Map) this.bmls.get(j);
                    if (!i.get("keyNo").toString().equals("6")) {
                        bmsum += key[Integer.parseInt(i.get("keyNo").toString())];
                        krbsum += keyr[Integer.parseInt(i.get("keyNo").toString())];
                        kbm.add(key[Integer.parseInt(i.get("keyNo").toString())]);
                        krbm.add(keyr[Integer.parseInt(i.get("keyNo").toString())]);
                    }
                    else {
                        k7Name = i.get("name").toString();
                        this.bmls.remove(j);
                    }
                }
                mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0 + k7 * 1.0) * 10000.0) * 1.0 / 100.0;
                bmrate = Math.rint(bmsum * 1.0 / (msum * 1.0 + bmsum * 1.0 + k7 * 1.0) * 10000.0) * 1.0 / 100.0;
                for (int j = 0; j < key.length - 1; ++j) {
                    p += key[j];
                }
                for (int j = 0; j < key.length; ++j) {
                    sum += key[j];
                }
                prate = Math.rint(p * 1.0 / (sum * 1.0) * 10000.0 / 100.0);
            }
            else {
                for (int j = 0; j < this.mls.size(); ++j) {
                    final Map i = (Map) this.mls.get(j);
                    msum += key[Integer.parseInt(i.get("keyNo").toString())];
                    krsum += keyr[Integer.parseInt(i.get("keyNo").toString())];
                    km.add(key[Integer.parseInt(i.get("keyNo").toString())]);
                    krm.add(keyr[Integer.parseInt(i.get("keyNo").toString())]);
                }
                for (int j = 0; j < this.bmls.size(); ++j) {
                    final Map i = (Map) this.bmls.get(j);
                    bmsum += key[Integer.parseInt(i.get("keyNo").toString())];
                    krbsum += keyr[Integer.parseInt(i.get("keyNo").toString())];
                    kbm.add(key[Integer.parseInt(i.get("keyNo").toString())]);
                    krbm.add(keyr[Integer.parseInt(i.get("keyNo").toString())]);
                }
                mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                bmrate = Math.rint(bmsum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                for (int j = 0; j < key.length; ++j) {
                    p += key[j];
                }
                for (int j = 0; j < key.length; ++j) {
                    sum += key[j];
                }
                prate = Math.rint(p * 1.0 / (sum * 1.0) * 10000.0 / 100.0);
            }
            totalMap.put("kr0", kr0);
            totalMap.put("kr1", kr2);
            totalMap.put("kr2", kr3);
            totalMap.put("kr3", kr4);
            totalMap.put("kr4", kr5);
            totalMap.put("kr5", kr6);
            totalMap.put("kr6", kr7);
            totalMap.put("prate", prate);
            totalMap.put("mrate", mrate);
            totalMap.put("bmrate", bmrate);
            totalMap.put("km", km);
            totalMap.put("kbm", kbm);
            totalMap.put("krm", krm);
            totalMap.put("krbm", krbm);
            totalMap.put("msum", msum);
            totalMap.put("bmsum", bmsum);
            totalMap.put("krsum", krsum);
            totalMap.put("krbsum", krbsum);
            totalMap.put("krm", krm);
            totalMap.put("krbm", krbm);
            totalMap.put("key", key);
            totalMap.put("keyr", keyr);
            totalMap.put("k7Name", k7Name);
            return totalMap;
        }
        return null;
    }

    private List getDOld(final List ls) {
        if (this.list != null) {
            final List temp = new ArrayList();
            for (int i = 0; i < this.list.size(); ++i) {
                final Map m = (Map) this.list.get(i);
                System.out.println("getD-m=" + m);
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
                    m.put("num", (key0 * 5 + key2 * 4 + key3 * 3) / (key0 + key2 + key3 + key4 + key5 + key6) * 20);
                }
                temp.add(m);
            }
            return temp;
        }
        return null;
    }

    private List getD(final List ls) throws Exception {
        final int[] keyWeight = this.getDegreeWeight();
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
                final Integer key7 = Integer.valueOf(m.get("key6").toString());
                if (key0 + key2 + key3 + key4 + key5 + key6 == 0) {
                    m.put("num", 100);
                }
                else {
                    m.put("num", (key0 * keyWeight[0] + key2 * keyWeight[1] + key3 * keyWeight[2] + key4 * keyWeight[3] + key5 * keyWeight[4] + key6 * keyWeight[5] + key7 * keyWeight[6]) / (key0 + key2 + key3 + key4 + key5 + key6 + key7) * 20);
                }
                temp.add(m);
            }
            return temp;
        }
        return null;
    }

    private int[] getDegreeWeight() throws Exception {
        final int[] keyWeight = { 5, 4, 3, 1, 1, 1, 1 };
        final Map[] keyMap = new Map[7];
        final List allKeyWeight = this.keyTypeService.findall();
        for (int i = 0; i < allKeyWeight.size(); ++i) {
            keyMap[i] = (Map) allKeyWeight.get(i);
            if (keyMap[i].get("ext1") != null && !keyMap[i].get("ext1").toString().equals("")) {
                keyWeight[i] = Integer.valueOf(keyMap[i].get("ext1").toString());
            }
        }
        return keyWeight;
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

    public String getStartDate() {
        return this.startDate;
    }

    public TotalService getTotalService() {
        return this.totalService;
    }

    public boolean isK7() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.k7 = Boolean.valueOf(sundynSet.getM_system().get("k7").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.k7;
    }

    public void setBmls(final List bmls) {
        this.bmls = bmls;
    }

    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }

    public void setEndDate(final String endDate) {
        this.endDate = endDate;
    }

    public void setExcel(final InputStream excel) {
        this.excel = excel;
    }

    public void setIds(final String ids) {
        this.ids = ids;
    }

    public void setK7(final boolean k7) {
        this.k7 = k7;
    }

    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }

    public void setList(final List list) {
        this.list = list;
    }

    public void setMls(final List mls) {
        this.mls = mls;
    }

    public void setPager(final Pager pager) {
        this.pager = pager;
    }

    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }

    public void setStartDate(final String startDate) {
        this.startDate = startDate;
    }

    public void setTotalService(final TotalService totalService) {
        this.totalService = totalService;
    }

    public BusinessService getBusinessService() {
        return this.businessService;
    }

    public void setBusinessService(final BusinessService businessService) {
        this.businessService = businessService;
    }

    public String totalBusiness() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List deptList = this.deptService.findChildALL(deptIdGroup);
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }

    public String totalBusinessDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptId = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.totalService.countTotalBusiness(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalBusiness(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        this.pager.setPageList(this.list);
        Map totalMap = this.totalService.totalDept(deptId, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalBusinessExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final String ids = this.deptService.findChildALLStr123(deptId);
        this.list = this.totalService.totalBusiness(ids, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t\u4e1a\u52a1\u6c47\u603b\n");
        excelBuf.append("\u4ee5\u4e0b\u662f\u6c47\u603b\u4ece'" + this.startDate + "'\u5230'" + this.endDate + "'\u7684\u90e8\u95e8\u4fe1\u606f\n");
        excelBuf.append("\t\t\t\u6ee1\u610f\t\t\t\t\t\u4e0d\u6ee1\u610f\n");
        excelBuf.append("\t\t");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\u6ee1\u610f");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\u4e0d\u6ee1\u610f");
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append("\u4e1a\u52a1\u540d\u79f0\t\u5206\u6570\t");
        for (int i = 0; i < this.mls.size(); ++i) {
        	Map map = (Map)mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append("\u6ee1\u610f\u5408\u8ba1\t");
        for (int i = 0; i < this.bmls.size(); ++i) {
        	Map map = (Map)bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append("\u4e0d\u6ee1\u610f\u5408\u8ba1\t");
        if (this.k7) {
            excelBuf.append("\u8bc4\u4ef7\u6570\t\u672a\u8bc4\u4ef7\u6570\t");
        }
        excelBuf.append("\u5408\u8ba1\t");
        if (this.k7) {
            excelBuf.append("\u8bc4\u4ef7\u7387\t");
        }
        excelBuf.append("\u6ee1\u610f\u7387\n");
        for (int i = 0; i < this.list.size(); ++i) {
            final Map m = (Map) this.list.get(i);
            excelBuf.append(m.get("businessName") + "\t");
            excelBuf.append(m.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m.get("km");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("msum") + "\t");
            tem = (ArrayList) m.get("kbm");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("p") + "\t");
                excelBuf.append(m.get("key6") + "\t");
            }
            excelBuf.append(m.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("prate") + "%\t");
            }
            excelBuf.append(m.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t\u7edf\u8ba1\u4fe1\u606f\uff08\u5458\u5de5\u6c47\u603b\u67e5\u8be2\uff09\n");
        for (int i = 0; i < this.mls.size(); ++i) {
            if (i == 0) {
                excelBuf.append("\u6ee1\u610f\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int i = 0; i < this.bmls.size(); ++i) {
            if (i == 0) {
                excelBuf.append("\u4e0d\u6ee1\u610f\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append("\u672a\u8bc4\u4ef7\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\u7edf\u8ba1\u65f6\u95f4\uff1a" + this.startDate + "\u5230" + this.endDate);
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie("\u4e1a\u52a1\u7edf\u8ba1", totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String totalBusinessPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String deptId = request.getParameter("deptId");
        deptId = this.deptService.findChildALLStr123(deptId);
        final int rowsCount = this.totalService.countTotalBusiness(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalBusiness(deptId, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.pager.setPageList(this.list);
        Map totalMap = this.totalService.totalDept(deptId, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalDating() throws Exception {
        return "success";
    }

    public String totalDatingDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.totalService.counttotalDating(ids, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalDating(ids, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("datingname"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        this.pager.setPageList(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        return "success";
    }

    public String totalDatingExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        this.list = this.totalService.totalDating(ids, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t" + this.getText("sundyn.total.excelTitle2") + "\n");
        final String[] args = { this.startDate, this.endDate };
        excelBuf.append(this.getText("sundyn.total.excel.totalTime", args) + "\n");
        excelBuf.append("\t\t");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.content"));
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontent"));
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.column.datingName") + "\t" + this.getText("sundyn.column.mark") + "\t");
        for (int i = 0; i < this.mls.size(); ++i) {
        	Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentTotal") + "\t");
        for (int i = 0; i < this.bmls.size(); ++i) {
        	Map map = (Map)this.bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontentTotal") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesNum") + "\t" + this.getText("sundyn.column.noappries") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.sum") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesRate") + "\t");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.workEffective") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentRate") + "\n");
        for (int i = 0; i < this.list.size(); ++i) {
            final Map m = (Map) this.list.get(i);
            excelBuf.append(m.get("datingname") + "\t");
            excelBuf.append(m.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m.get("km");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("msum") + "\t");
            tem = (ArrayList) m.get("kbm");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("p") + "\t");
                excelBuf.append(m.get("key6") + "\t");
            }
            excelBuf.append(m.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("prate") + "%\t");
            }
            if (this.k7) {
                excelBuf.append(m.get("erate") + "%\t");
            }
            excelBuf.append(m.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t" + this.getText("sundyn.total.excel.hall") + "\n");
        for (int i = 0; i < this.mls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.content") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int i = 0; i < this.bmls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.nocontent") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.noappries") + "\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.total.excel.totalTime", args));
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie(this.getText("sundyn.total.excelTitle2"), totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String totalDatingPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        this.list = this.totalService.totalDating(ids, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        return "success";
    }

    public String totalDept() throws Exception {
        return "success";
    }

    public String totalDeptDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        int n = 0;
        for (int i = 0; i < this.mls.size(); ++i) {
            final Map m = (Map) this.mls.get(i);
            final String s = m.get("ext2") + "=" + m.get("name");
            if (n % 3 == 0) {
                this.str = String.valueOf(this.str) + s + " ";
            }
            else if (n % 3 == 2) {
                this.str = String.valueOf(this.str) + " , " + s + "</br></br>";
            }
            else {
                this.str = String.valueOf(this.str) + "," + s;
            }
            ++n;
        }
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        for (int i = 0; i < this.bmls.size(); ++i) {
            final Map m = (Map) this.bmls.get(i);
            final String s = m.get("ext2") + "=" + m.get("name");
            if (n % 3 == 0) {
                this.str = String.valueOf(this.str) + s + " ";
            }
            else if (n % 3 == 2) {
                this.str = String.valueOf(this.str) + " , " + s + "</br></br>";
            }
            else {
                this.str = String.valueOf(this.str) + ", " + s;
            }
            ++n;
        }
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List temp = this.deptService.findchild(Integer.valueOf(deptIdGroup));
        final List res = new ArrayList();
        for (int j = 0; j < temp.size(); ++j) {
        	Map map = (Map)temp.get(j);
            final String name = map.get("name").toString();
            final String id = map.get("id").toString();
            final String ids = this.deptService.findChildALLStr123(id);
            final Map k = this.totalService.totalDept(ids, this.startDate, this.endDate);
            if (k != null) {
                k.put("DeptId", id);
                k.put("name", name);
                final Integer key0 = Integer.valueOf(k.get("key0").toString());
                final Integer key2 = Integer.valueOf(k.get("key1").toString());
                final Integer key3 = Integer.valueOf(k.get("key2").toString());
                final Integer key4 = Integer.valueOf(k.get("key3").toString());
                final Integer key5 = Integer.valueOf(k.get("key4").toString());
                final Integer key6 = Integer.valueOf(k.get("key5").toString());
                final Integer key7 = Integer.valueOf(k.get("key6").toString());
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
                    for (int l = 0; l < this.mls.size(); ++l) {
                        final Map k2 = (Map) this.mls.get(l);
                        if (!k2.get("keyNo").toString().equals("6")) {
                            msum += key8[Integer.parseInt(k2.get("keyNo").toString())];
                            km.add(key8[Integer.parseInt(k2.get("keyNo").toString())]);
                        }
                        else {
                            this.mls.remove(l);
                        }
                    }
                    System.out.println("k7=true\uff0cmsum=" + msum);
                    for (int l = 0; l < this.bmls.size(); ++l) {
                        final Map k2 = (Map) this.bmls.get(l);
                        if (!k2.get("keyNo").toString().equals("6")) {
                            bmsum += key8[Integer.parseInt(k2.get("keyNo").toString())];
                            kbm.add(key8[Integer.parseInt(k2.get("keyNo").toString())]);
                        }
                        else {
                            this.bmls.remove(l);
                        }
                    }
                    System.out.println("k7=true\uff0cbmsum=" + bmsum);
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                    for (int l = 0; l < key8.length - 1; ++l) {
                        p += key8[l];
                    }
                    for (int l = 0; l < key8.length; ++l) {
                        sum += key8[l];
                    }
                }
                else {
                    for (int l = 0; l < this.mls.size(); ++l) {
                        final Map k2 = (Map) this.mls.get(l);
                        msum += key8[Integer.parseInt(k2.get("keyNo").toString())];
                        km.add(key8[Integer.parseInt(k2.get("keyNo").toString())]);
                    }
                    System.out.println("k7=false\uff0cmsum=" + msum);
                    for (int l = 0; l < this.bmls.size(); ++l) {
                        final Map k2 = (Map) this.bmls.get(l);
                        bmsum += key8[Integer.parseInt(k2.get("keyNo").toString())];
                        kbm.add(key8[Integer.parseInt(k2.get("keyNo").toString())]);
                    }
                    System.out.println("k7=false\uff0cbmsum=" + bmsum);
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 10000.0) * 1.0 / 100.0;
                    for (int l = 0; l < key8.length; ++l) {
                        p += key8[l];
                    }
                    for (int l = 0; l < key8.length; ++l) {
                        sum += key8[l];
                    }
                }
                k.put("prate", prate);
                k.put("erate", erate);
                k.put("mrate", mrate);
                k.put("km", km);
                k.put("kbm", kbm);
                k.put("msum", msum);
                k.put("bmsum", bmsum);
                k.put("p", p);
                k.put("sum", sum);
                res.add(k);
            }
        }
        this.list = res;
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m2 = new HashMap();
        m2.put("num", standard);
        m2.put("category", "\u611f\u77e5");
        m2.put("item", "\u6807\u51c6");
        ls.add(m2);
        for (int i2 = 0; i2 < this.list.size(); ++i2) {
            final Map t = (Map) this.list.get(i2);
            final Map m3 = new HashMap();
            m3.put("num", t.get("num"));
            m3.put("category", "\u611f\u77e5");
            m3.put("item", t.get("name"));
            ls.add(m3);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        final String ids2 = this.deptService.findChildALLStr123(deptIdGroup);
        Map totalMap = this.totalService.totalDept(ids2, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("startDate", (Object)startDate);
        request.setAttribute("endDate", (Object)endDate);
        return "success";
    }

    public String totalDeptExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.k7 = this.isK7();
        this.mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        this.bmls = this.keyTypeService.findByApprieserId(1, 1, "");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List temp = this.deptService.findchild(Integer.valueOf(deptIdGroup));
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
                final double prate = Math.rint((1.0 - key7 * 1.0 / (key0 + key2 + key3 + key4 + key5 + key6 + key7)) * 10000.0) / 100.0;
                double mrate = 0.0;
                double erate = 0.0;
                erate = Math.rint((key0 * 1.0 + key2 * 1.0) / (key0 + key2 + key3 + key4 + key5 + key6 + key7) * 10000.0) / 100.0;
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
                m.put("mrate", mrate);
                m.put("erate", erate);
                m.put("km", km);
                m.put("kbm", kbm);
                m.put("msum", msum);
                m.put("bmsum", bmsum);
                m.put("p", p);
                m.put("sum", sum);
                res.add(m);
            }
        }
        this.list = res;
        final String ids2 = this.deptService.findChildALLStr123(deptIdGroup);
        Map totalMap = this.totalService.totalDept(ids2, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t" + this.getText("sundyn.total.excelTitle1") + "\n");
        final String[] args = { this.startDate, this.endDate };
        excelBuf.append(String.valueOf(this.getText("sundyn.total.info", args)) + "\n");
        excelBuf.append("\t\t");
        for (int l = 0; l < (this.mls.size() + 1) / 2; ++l) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.content"));
        for (int l = 0; l < (this.mls.size() + 1) / 2; ++l) {
            excelBuf.append("\t");
        }
        for (int l = 0; l < (this.bmls.size() + 1) / 2; ++l) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontent"));
        for (int l = 0; l < (this.bmls.size() + 1) / 2; ++l) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.column.sectionName") + "\t" + this.getText("sundyn.column.mark") + "\t");
        for (int l = 0; l < this.mls.size(); ++l) {
        	Map map = (Map)mls.get(l);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentTotal") + "\t");
        for (int l = 0; l < this.bmls.size(); ++l) {
        	Map map = (Map)bmls.get(l);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontentTotal") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesNum") + "\t" + this.getText("sundyn.column.noappries") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.sum") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesRate") + "\t");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.workEffective") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentRate") + "\n");
        for (int l = 0; l < this.list.size(); ++l) {
            final Map m2 = (Map) this.list.get(l);
            excelBuf.append(m2.get("name") + "\t");
            excelBuf.append(m2.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m2.get("km");
            for (int j2 = 0; j2 < tem.size(); ++j2) {
                excelBuf.append(String.valueOf(tem.get(j2).toString()) + "\t");
            }
            excelBuf.append(m2.get("msum") + "\t");
            tem = (ArrayList) m2.get("kbm");
            for (int j2 = 0; j2 < tem.size(); ++j2) {
                excelBuf.append(String.valueOf(tem.get(j2).toString()) + "\t");
            }
            excelBuf.append(m2.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m2.get("p") + "\t");
                excelBuf.append(m2.get("key6") + "\t");
            }
            excelBuf.append(m2.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m2.get("prate") + "%\t");
            }
            if (this.k7) {
                excelBuf.append(m2.get("erate") + "%\t");
            }
            excelBuf.append(m2.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t" + this.getText("sundyn.total.excel.dept") + "\n");
        for (int l = 0; l < this.mls.size(); ++l) {
            if (l == 0) {
                excelBuf.append(this.getText("sundyn.column.content") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)mls.get(l);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (l == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (l == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int l = 0; l < this.bmls.size(); ++l) {
            if (l == 0) {
                excelBuf.append(this.getText("sundyn.column.nocontent") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)bmls.get(l);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (l == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (l == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.noappries") + "\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.total.excel.totalTime", args));
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie(this.getText("sundyn.total.excelTitle1"), totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String deptDuiBi() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final StringBuilder strXML1 = new StringBuilder("");
        strXML1.append("<?xml version='1.0' encoding='GBK'?>");
        strXML1.append("<graph xaxisname='Continent' yaxisname='Export' hovercapbg='DEDEBE' hovercapborder='889E6D' rotateNames='0' yAxisMaxValue='100' numdivlines='9' divLineColor='CCCCCC' divLineAlpha='80' decimalPrecision='0' showAlternateHGridColor='1' AlternateHGridAlpha='30' AlternateHGridColor='CCCCCC' caption='Global Export' subcaption='In Millions Tonnes per annum pr Hectare'><categories font='Arial' fontSize='11' fontColor='000000'>");
        strXML1.append("<categories font='Arial' fontSize='12' fontColor='000000'>");
        strXML1.append("<category name='N. America' hoverText='North America'/>");
        strXML1.append("<category name='Asia'/>");
        strXML1.append("<category name='Europe'/>");
        strXML1.append("<category name='Australia'/>");
        strXML1.append("<category name='Africa'/>");
        strXML1.append("</categories>");
        strXML1.append("<dataset seriesname='Rice' color='FDC12E'>");
        strXML1.append("<set value='30'/>");
        strXML1.append("<set value='26'/>");
        strXML1.append("<set value='29'/>");
        strXML1.append("<set value='31'/>");
        strXML1.append("<set value='34'/>");
        strXML1.append("</dataset>");
        strXML1.append("<dataset seriesname='Wheat' color='56B9F9'>");
        strXML1.append("<set value='67'/>");
        strXML1.append("<set value='98'/>");
        strXML1.append("<set value='79'/>");
        strXML1.append("<set value='73'/>");
        strXML1.append("<set value='80'/>");
        strXML1.append("</dataset>");
        strXML1.append("</graph>");
        request.setAttribute("strXML1", (Object)strXML1.toString());
        return "duibitongjibiao";
    }

    public String totalPerson() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List deptList = new ArrayList();
        final Map dept = this.deptService.findDeptById(Integer.valueOf(deptIdGroup));
        deptList.add(dept);
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }

    public String totalPersonAjax() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        this.list = this.deptService.findchild(Integer.valueOf(id));
        return "success";
    }

    public String totalPersonDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");

        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        final List deptList2 = new ArrayList();
        final Map dept2 = this.deptService.findDeptById(Integer.valueOf(deptIdGroup2));
        deptList2.add(dept2);
        request.setAttribute("deptList", (Object)deptList2);

        String deptId = request.getParameter("deptId");
        deptId = this.deptService.findChildALLStr123(deptId);
        final int rowsCount = this.totalService.countTotalPerson(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalPerson(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("employeeName"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u4eba\u5458\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        this.pager.setPageList(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(deptId, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalPersonDeal2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String deptId = request.getParameter("deptId");
        deptId = this.deptService.findChildALLStr123(deptId);
        final int rowsCount = this.totalService.countEmployeeAll();
        System.out.println("totalPersonDeal2-rowsCount=" + rowsCount);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalPerson3(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("employeeName"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u4eba\u5458\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        this.pager.setPageList(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(deptId, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalPersonExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        this.getPandM(this.list = this.totalService.totalPerson(deptId, this.startDate, this.endDate, null, null));
        this.list = this.getPandM(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t" + this.getText("sundyn.total.excelTitle4") + "\n");
        final String[] args = { this.startDate, this.endDate };
        excelBuf.append(this.getText("sundyn.total.excel.totalTime", args) + "\n");
        excelBuf.append("\t\t\t" + this.getText("sundyn.column.content") + "\t\t\t\t\t" + this.getText("sundyn.column.nocontent") + "\n");
        excelBuf.append("\t\t");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.content"));
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontent"));
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.column.employeeName") + "\t" + this.getText("sundyn.column.mark") + "\t");
        for (int i = 0; i < this.mls.size(); ++i) {
        	Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentTotal") + "\t");
        for (int i = 0; i < this.bmls.size(); ++i) {
        	Map map = (Map)this.bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontentTotal") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesNum") + "\t" + this.getText("sundyn.column.noappries") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.sum") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesRate") + "\t");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.workEffective") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentRate") + "\n");
        for (int i = 0; i < this.list.size(); ++i) {
            final Map m = (Map) this.list.get(i);
            excelBuf.append(m.get("employeeName") + "\t");
            excelBuf.append(m.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m.get("km");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("msum") + "\t");
            tem = (ArrayList) m.get("kbm");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("p") + "\t");
                excelBuf.append(m.get("key6") + "\t");
            }
            excelBuf.append(m.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("prate") + "%\t");
            }
            if (this.k7) {
                excelBuf.append(m.get("erate") + "%\t");
            }
            excelBuf.append(m.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t" + this.getText("sundyn.total.excel.person") + "\n");
        for (int i = 0; i < this.mls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.content") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int i = 0; i < this.bmls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.nocontent") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            Map map = (Map)this.bmls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(map.get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(map.get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.noappries") + "\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.total.excel.totalTime", args));
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie(this.getText("sundyn.total.excelTitle4"), totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String totalPersonPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        final int rowsCount = this.totalService.countTotalPerson(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalPerson(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.pager.setPageList(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalWindow() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        this.list = this.deptService.findDeptByType(ids, 1);
        return "success";
    }

    public String totalWindowDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String deptId = request.getParameter("deptId");
        System.out.println(deptId);
        final Map manager2 = (Map)request.getSession().getAttribute("manager");
        final Integer groupid2 = Integer.valueOf(manager2.get("userGroupId").toString());
        final Map power2 = this.powerService.getUserGroup(groupid2);
        final String deptIdGroup2 = power2.get("deptIdGroup").toString();
        final String ids22 = this.deptService.findChildALLStr123(deptIdGroup2);
        this.list2 = this.deptService.findDeptByType(ids22, 1);

        final int rowsCount = this.totalService.counttotalWindow(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalWindow(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("windowname"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        this.pager.setPageList(this.list);
        final String ids2 = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(ids2, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalJobNum() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final List deptList = new ArrayList();
        final Map dept = this.deptService.findDeptById(Integer.valueOf(deptIdGroup));
        deptList.add(dept);
        request.setAttribute("deptList", (Object)deptList);
        return "success";
    }

    public String totalWindowExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        this.getPandM(this.list = this.totalService.totalWindow(deptId, this.startDate, this.endDate, null, null));
        this.list = this.getPandM(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t" + this.getText("sundyn.total.excelTitle3") + "\n");
        final String[] args = { this.startDate, this.endDate };
        excelBuf.append(String.valueOf(this.getText("sundyn.total.excel.totalTime", args)) + "\n");
        excelBuf.append("\t\t");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.content"));
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontent"));
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append(this.getText("sundyn.column.windowName") + "\t" + this.getText("sundyn.column.mark") + "\t");
        for (int i = 0; i < this.mls.size(); ++i) {
        	Map map = (Map)this.mls.get(i);
            excelBuf.append(String.valueOf(map.get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentTotal") + "\t");
        for (int i = 0; i < this.bmls.size(); ++i) {
            excelBuf.append(String.valueOf(((Map) this.bmls.get(i)).get("name").toString()) + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.nocontentTotal") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesNum") + "\t" + this.getText("sundyn.column.noappries") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.sum") + "\t");
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.appriesRate") + "\t");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.workEffective") + "\t");
        }
        excelBuf.append(this.getText("sundyn.column.contentRate") + "\n");
        for (int i = 0; i < this.list.size(); ++i) {
            final Map m = (Map) this.list.get(i);
            excelBuf.append(m.get("windowname") + "\t");
            excelBuf.append(m.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m.get("km");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("msum") + "\t");
            tem = (ArrayList) m.get("kbm");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("p") + "\t");
                excelBuf.append(m.get("key6") + "\t");
            }
            excelBuf.append(m.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("prate") + "%\t");
            }
            if (this.k7) {
                excelBuf.append(m.get("erate") + "%\t");
            }
            excelBuf.append(m.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t" + this.getText("sundyn.total.excel.window") + "\n");
        for (int i = 0; i < this.mls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.content") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(((Map) this.mls.get(i)).get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(((Map) this.mls.get(i)).get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(((Map) this.mls.get(i)).get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int i = 0; i < this.bmls.size(); ++i) {
            if (i == 0) {
                excelBuf.append(this.getText("sundyn.column.nocontent") + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(((Map) this.bmls.get(i)).get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(((Map) this.bmls.get(i)).get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(((Map) this.bmls.get(i)).get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append(this.getText("sundyn.column.noappries") + "\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append(String.valueOf(this.getText("sundyn.total.excel.totalTime", args)) + "\n");
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie(this.getText("sundyn.total.excelTitle3"), totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String totalWindowPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String deptId = request.getParameter("deptId");
        this.list = this.totalService.totalWindow(deptId, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        final String ids = this.deptService.findChildALLStr123(deptId);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        request.setAttribute("deptId", (Object)deptId);
        return "success";
    }

    public String totalD() {
        this.list = this.businessService.findByFatherId(1);
        return "success";
    }

    public String totalDDeal() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String businessId = request.getParameter("businessId");
        this.ids = this.businessService.findByFatherIdAllId(Integer.valueOf(businessId));
        this.list = this.totalService.totalD(this.startDate, this.endDate, this.ids);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("d"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("businessName"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        return "success";
    }

    public String totalDExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        this.list = this.totalService.totalD(this.startDate, this.endDate, this.ids);
        final List ls = new ArrayList();
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map m = new HashMap();
            m.put("num", temp.get("d"));
            m.put("category", "\u611f\u77e5");
            m.put("item", temp.get("businessName"));
            ls.add(m);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        final Poi poi = new Poi();
        poi.addTitle("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", 0, 2);
        final String[] args = { "\u6ee1\u610f\u5ea6", "\u56db\u7ea7\u6307\u6807", "\u4e09\u7ea7\u6307\u6807", "\u4e8c\u7ea7\u6307\u6807", "\u4e00\u7ea7\u6307\u6807" };
        poi.addListTitle(args, 1);
        poi.addList(this.list);
        poi.addPic(new File(String.valueOf(path) + "pubpic.jpg"), 1, 2);
        poi.createFile(String.valueOf(path) + "standard.xls");
        return "success";
    }

    @Test
    public void test() throws Exception {
        final Poi poi = new Poi();
        poi.addTitle("\u4f1a\u5458\u8868", 0, 2);
        final String[] args = { "\u59d3\u540d", "\u6027\u522b", "\u751f\u65e5", "\u624b\u673a" };
        this.list = new ArrayList();
        final WeburlVo v1 = new WeburlVo();
        final Map m = new HashMap();
        m.put("\u59d3\u540d", "\u5f20\u4e09\u554a");
        m.put("\u624b\u673a", "189");
        final Map m2 = new HashMap();
        m2.put("\u59d3\u540d", "\u5f20\u4e091");
        m2.put("\u624b\u673a", "1892");
        this.list.add(m);
        this.list.add(m2);
        poi.addListTitle(args, 1);
        poi.addList(this.list);
        poi.createFile("c:/member.xls");
    }

    public String totalDPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        this.list = this.totalService.totalD(this.startDate, this.endDate, this.ids);
        return "success";
    }

    public String totalShop() throws Exception {
        return "success";
    }

    public String totalSection() {
        return "success";
    }

    public String totalSectionDeal() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        final int rowsCount = this.totalService.countTotalSection(ids, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        this.list = this.totalService.totalSection(ids, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        this.list = this.getD(this.list);
        final List ls = new ArrayList();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String standard = sundynSet.getM_content().get("standard").toString();
        final Map m1 = new HashMap();
        m1.put("num", standard);
        m1.put("category", "\u611f\u77e5");
        m1.put("item", "\u6807\u51c6");
        ls.add(m1);
        for (int i = 0; i < this.list.size(); ++i) {
            final Map temp = (Map) this.list.get(i);
            final Map j = new HashMap();
            j.put("num", temp.get("num"));
            j.put("category", "\u611f\u77e5");
            j.put("item", temp.get("windowname"));
            ls.add(j);
        }
        final JfreeChart jfreeChart = new JfreeChart();
        jfreeChart.createBar("\u603b\u7684\u6ee1\u610f\u5ea6\u6307\u6570", "\u611f\u77e5\u9879", "\u6ee1\u610f\u5ea6", ls, String.valueOf(path) + "pubpic.jpg");
        this.pager.setPageList(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        return "success";
    }

    public String totalSectionExcel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        this.list = this.totalService.totalSection(ids, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        final StringBuffer excelBuf = new StringBuffer();
        excelBuf.append("\t\t\t\t\t\t\t\t\u5927\u5385\u6c47\u603b\n");
        excelBuf.append("\u4ee5\u4e0b\u662f\u6c47\u603b\u4ece'" + this.startDate + "'\u5230'" + this.endDate + "'\u7684\u90e8\u95e8\u4fe1\u606f\n");
        excelBuf.append("\t\t");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\u5a4a\u2103\ufffd");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\u6ee1\u610f");
        for (int i = 0; i < (this.mls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\u4e0d\u6ee1\u610f");
        for (int i = 0; i < (this.bmls.size() + 1) / 2; ++i) {
            excelBuf.append("\t");
        }
        excelBuf.append("\n");
        excelBuf.append("\u90e8\u95e8\u540d\u79f0\t\u5206\u6570\t");
        for (int i = 0; i < this.mls.size(); ++i) {
            excelBuf.append(String.valueOf(((Map) this.mls.get(i)).get("name").toString()) + "\t");
        }
        excelBuf.append("\u6ee1\u610f\u5408\u8ba1\t");
        for (int i = 0; i < this.bmls.size(); ++i) {
            excelBuf.append(String.valueOf(((Map) this.bmls.get(i)).get("name").toString()) + "\t");
        }
        excelBuf.append("\u4e0d\u6ee1\u610f\u5408\u8ba1\t");
        if (this.k7) {
            excelBuf.append("\u8bc4\u4ef7\u6570\t\u672a\u8bc4\u4ef7\u6570\t");
        }
        excelBuf.append("\u5408\u8ba1\t");
        if (this.k7) {
            excelBuf.append("\u8bc4\u4ef7\u7387\t");
        }
        excelBuf.append("\u6ee1\u610f\u7387\n");
        for (int i = 0; i < this.list.size(); ++i) {
            final Map m = (Map) this.list.get(i);
            excelBuf.append(m.get("windowname") + "\t");
            excelBuf.append(m.get("mrate") + "\t");
            ArrayList tem = (ArrayList) m.get("km");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("msum") + "\t");
            tem = (ArrayList) m.get("kbm");
            for (int j = 0; j < tem.size(); ++j) {
                excelBuf.append(String.valueOf(tem.get(j).toString()) + "\t");
            }
            excelBuf.append(m.get("bmsum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("p") + "\t");
                excelBuf.append(m.get("key6") + "\t");
            }
            excelBuf.append(m.get("sum") + "\t");
            if (this.k7) {
                excelBuf.append(m.get("prate") + "%\t");
            }
            excelBuf.append(m.get("mrate") + "%\t\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\t\t\t\u7edf\u8ba1\u4fe1\u606f\uff08\u5927\u5385\u6c47\u603b\u67e5\u8be2\uff09\n");
        for (int i = 0; i < this.mls.size(); ++i) {
            if (i == 0) {
                excelBuf.append("\u6ee1\u610f\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(((Map) this.mls.get(i)).get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(((Map) this.mls.get(i)).get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("msum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(((Map) this.mls.get(i)).get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("mrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        for (int i = 0; i < this.bmls.size(); ++i) {
            if (i == 0) {
                excelBuf.append("\u4e0d\u6ee1\u610f\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(((Map) this.bmls.get(i)).get("name").toString()) + "\t");
            final double[] tr = (double[]) totalMap.get("keyr");
            final Integer[] tk = (Integer[]) totalMap.get("key");
            excelBuf.append(tk[Integer.parseInt(((Map) this.bmls.get(i)).get("keyNo").toString())] + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmsum").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append(String.valueOf(tr[Integer.parseInt(((Map) this.bmls.get(i)).get("keyNo").toString())]) + "\t");
            if (i == 0) {
                excelBuf.append(String.valueOf(totalMap.get("bmrate").toString()) + "\t");
            }
            else {
                excelBuf.append("\t");
            }
            excelBuf.append("\n");
        }
        if (this.k7) {
            excelBuf.append("\u672a\u8bc4\u4ef7\t");
            excelBuf.append(String.valueOf(totalMap.get("k7Name").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("key6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append(String.valueOf(totalMap.get("kr6").toString()) + "\t");
            excelBuf.append("\n");
        }
        excelBuf.append("\n");
        excelBuf.append("\n");
        excelBuf.append("\u7edf\u8ba1\u65f6\u95f4\uff1a" + this.startDate + "\u5230" + this.endDate);
        final String excelString = excelBuf.toString();
        final Poi poi = new Poi();
        poi.addText(excelString);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        JfreeChart.createPie("\u90e8\u95e8\u7edf\u8ba1", totalMap, this.getDemo(), String.valueOf(path) + "demo.png");
        poi.addPic(new File(String.valueOf(path) + "demo.png"), 2, 4);
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }

    public String totalSectionPrint() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String ids = this.deptService.findChildALLStr123(deptIdGroup);
        this.list = this.totalService.totalSection(ids, this.startDate, this.endDate, null, null);
        this.list = this.getPandM(this.list);
        this.list = this.getStar(this.list);
        Map totalMap = this.totalService.totalDept(ids, this.startDate, this.endDate);
        totalMap = this.getRate(totalMap);
        request.setAttribute("totalMap", (Object)totalMap);
        return "success";
    }

    public String videoList() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String deptId = request.getParameter("deptId");
        final int rowsCount = this.totalService.counttotalWindow(deptId, this.startDate, this.endDate);
        this.pager = new Pager("currentPage", 10, rowsCount, request);
        final List videolist = this.totalService.totalWindowVideo(deptId, this.startDate, this.endDate, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        ServletActionContext.getRequest().setAttribute("videolist", (Object)videolist);
        return "success";
    }

    public String getStr() {
        return this.str;
    }

    public void setStr(final String str) {
        this.str = str;
    }
}
