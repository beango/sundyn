package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sundyn.service.DeptService;
import com.sundyn.service.EmployeeService;
import com.sundyn.service.IJxEmployeeService;
import com.sundyn.util.Poi;
import com.xuan.xutils.utils.DateUtils;
import com.xuan.xutils.utils.MapUtils;
import com.xuan.xutils.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class JxEmployeeAction extends MainAction {
    @Resource
    private IJxEmployeeService jxEmployeeService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private DeptService deptService;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String fileName;
    @Getter @Setter
    private InputStream excel;

    /*
    绩效数据配置
     */
    public String jxEmployeeQuery() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            String exportExcel = request.getParameter("export");
            String servicedate = req.getString("servicedate");
            if (StringUtils.isBlank(servicedate))
                servicedate = DateUtils.date2String(DateUtils.getPreMonth(), "yyyy-MM");
            String deptId = req.getString("deptId");
            String deptname = req.getString("deptname");
            final String ids = this.deptService.findChildALLStr1234(deptId);
            String sort = req.getString("sort");
            if (StringUtils.isBlank(sort))
                sort = "ctime,desc";
            final Integer employeeId = req.getInt("employeeId");
            Map e = this.employeeService.findEmployeeById(employeeId);
            String keyCardNum = null;
            if (e!=null)
            {
                keyCardNum = e.get("cardnum").toString();
            }

            sort = sort.replace(",", " ");
            String ypfj = req.getString("ypfj");

            Wrapper<Map> ew =new EntityWrapper<Map>();

            if (StringUtils.isNotBlank(ids))
                ew = ew.where("deptid in("+ids+")");
            if (StringUtils.isNotBlank(servicedate)){
                ew = ew.where("servicedate='" + servicedate + "'");
            }
            if (ypfj.equalsIgnoreCase("1")){
                ew = ew.where("(ypfj is not null and ypfj!=0)");
            }
            if (ypfj.equalsIgnoreCase("0")){
                ew = ew.where("(ypfj is null or ypfj=0)");
            }
            request.setAttribute("ypfj", ypfj);
            if (exportExcel != null && exportExcel.toLowerCase().equals("true"))
                pageSize = 999999;
            Page<Map> queryData = jxEmployeeService.querypagemap(new Page<Map>(pageindex, pageSize), ew.orderBy(sort));
            request.setAttribute("queryData", queryData);
            request.setAttribute("servicedate", servicedate);

            if (exportExcel != null && exportExcel.toLowerCase().equals("true")) {
                List ls2 = new ArrayList();
                List mls = new ArrayList();
                List bmls = new ArrayList();
                java.text.DecimalFormat df =new java.text.DecimalFormat("0.00");
                for (int i = 0; i < queryData.getRecords().size(); ++i) {
                    final Map m = new LinkedHashMap();
                    final Map temp = (Map) queryData.getRecords().get(i);
                    MapUtils mapUtils = new MapUtils(temp);
                    int j = 0;
                    m.put("m" + (j++), i+1);
                    m.put("m" + (j++), mapUtils.getString("ename"));
                    m.put("m" + (j++), mapUtils.getString("deptname"));
                    m.put("m" + (j++), mapUtils.getString("servicedate"));
                    m.put("m" + (j++), mapUtils.getString("servicetimename"));
                    m.put("m" + (j++), mapUtils.getInteger("servicecount"));
                    m.put("m" + (j++), mapUtils.getString("deptservicetimeavgname"));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("fwzl")));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("employeefwxn")));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("deptfwxn")));
                    m.put("m" + (j++), df.format(100.0 * mapUtils.getDouble("fwpjl")) + "%");
                    m.put("m" + (j++), df.format(mapUtils.getDouble("ykq")));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("qzby")));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("rcxc")));
                    m.put("m" + (j++), mapUtils.getString("fjdesc"));
                    m.put("m" + (j++), df.format(mapUtils.getDouble("totalscore")));
                    ls2.add(m);
                }
                final Poi poi = new Poi();
                /*****************************************
                 * 开始添加第一个标题行
                 *****************************************/
                List<String> args = new ArrayList<String>();
                args.add("排名");
                args.add("姓名");
                args.add("所属部门");
                args.add("月份");
                args.add("个人本月有效服务时间");
                args.add("个人本月有效服务人次");
                args.add("大厅本月平均服务时间");
                args.add("服务质量");
                args.add("个人服务效能");
                args.add("相对本单位服务效能");
                args.add("评价率");
                args.add("月度考勤");
                args.add("群众表扬");
                args.add("日常巡查");
                args.add("一票否决");
                args.add("总得分");

                String deptNamT = "";
                if (StringUtils.isNotBlank(deptname))
                    deptNamT = "（" + deptname + "）";
                poi.addTitle("员工绩效统计" + deptNamT,  servicedate, 1, args.size()-1); //添加报表标题，合并
                poi.addListTitle(args.toArray(), 1);
                int mergStepN = 0;//需要合并行（不是列）的n列，如机构名称，业务量，弃号量等
                //poi.addMerge(2,mergStepN,2,mergStepN + 0);
                //poi.addMerge(2,mergStepN+0+1,2,mergStepN + 0+1 + 0);

                /*****************************************
                 * 开始添加第二个标题行
                 *****************************************//*
                List<String> args2 = new ArrayList<String>();
                for (int i = 0; i < mergStepN; i++) {//补第二行 mergStepN列的空数据，会合并掉，所以补空白即可
                    args2.add("");
                }
                args2.add(this.getText("sundyn.column.contentTotal"));
                args2.add(this.getText("sundyn.column.nocontentTotal"));
                args2.add("");//补满意，不满意后面4列的空白数据，会合并掉，所以补空白即可
                args2.add("");
                args2.add("");
                args2.add("");
                poi.addListTitle(args2.toArray(), 1);

                for (int i = 0; i < mergStepN; i++) {//合并标题行-机构名称、业务量、弃号量等
                    poi.addMerge(2, i,3, i);
                }

                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1,3,mergStepN+mls.size() +1+ bmls.size()+1);//合并标题行-满意
                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1+1,3,mergStepN+mls.size() +1+ bmls.size()+1+1);//合并标题行-不满意
                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1+1+1,3,mergStepN+mls.size() +1+ bmls.size()+1+1+1);//未评价
                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1,3,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1);//评价合计
                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1+1,3,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1+1);//满意率
                poi.addMerge(2,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1+1+1,3,mergStepN+mls.size() +1+ bmls.size()+1+1+1+1+1+1);//满意度
                */
                poi.addList(ls2, false);

                poi.createFile(String.valueOf(path) + "standard.xls");
                this.excel = new FileInputStream(String.valueOf(path) + "standard.xls");
                this.fileName = new String("按照员工统计报表.xls".getBytes("gb2312"), "iso8859-1");;
                return "excel";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
}
