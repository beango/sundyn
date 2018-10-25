package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import com.sundyn.vo.*;
import javax.servlet.http.*;
import net.sf.json.*;
import com.sundyn.util.*;
import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import java.util.*;
import java.text.*;

public class BusinessAction extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    private BusinessService businessService;
    AppriesService appriesService;
    DeptService deptService;
    EmployeeService employeeService;
    private List list;
    private File img;
    String msg;
    
    public BusinessAction() {
        this.msg = "";
    }
    
    public BusinessService getBusinessService() {
        return this.businessService;
    }
    
    public void setBusinessService(final BusinessService businessService) {
        this.businessService = businessService;
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
    
    public void setList(final List list) {
        this.list = list;
    }
    
    public List getList() {
        return this.list;
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
    
    public String businessQuery() {
        this.list = this.businessService.findall();
        return "success";
    }
    
    public String businessUpdate() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String businessIds = request.getParameter("businessIds");
        final String businessNames = request.getParameter("businessNames");
        final String businessDescriptions = request.getParameter("businessDescriptions");
        final String businessIsUses = request.getParameter("businessIsUses");
        final String[] businessId = businessIds.split(",");
        final String[] businessName = businessNames.split(",");
        final String[] businessDescription = businessDescriptions.split(",");
        final String[] businessIsUse = businessIsUses.split(",");
        for (int i = 0; i < businessId.length; ++i) {
            final BusinessVo businessVo = new BusinessVo();
            businessVo.setBusinessId(Integer.valueOf(businessId[i]));
            businessVo.setBusinessName(businessName[i]);
            businessVo.setBusinessDescription(businessDescription[i]);
            businessVo.setBusinessIsUse(Boolean.valueOf(businessIsUse[i]));
            this.businessService.update(businessVo);
        }
        request.setAttribute("msg", (Object)"\u4fdd\u5b58\u6210\u529f");
        return "success";
    }
    
    public String businessView() {
        this.list = this.businessService.findByFatherIdAll(-1);
        return "success";
    }
    
    public String businessReg() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String id = request.getParameter("id");
        final Map business = this.businessService.findById(Integer.valueOf(id));
        request.setAttribute("business", (Object)business);
        return "success";
    }
    
    public String businessAddDialog() {
        return "success";
    }
    
    public String businessAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final BusinessVo businessVo = new BusinessVo();
        businessVo.setBusinessName(request.getParameter("businessName"));
        businessVo.setBusinessDescription(request.getParameter("businessDescription"));
        businessVo.setBusinessFatherId(Integer.valueOf(request.getParameter("businessFatherId")));
        this.businessService.add(businessVo);
        final List list = this.businessService.findByFatherIdAll(-1);
        final JSONArray json = JSONArray.fromObject((Object)list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String businessEditDialog() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String businessId = request.getParameter("businessId");
        final Map business = this.businessService.findById(Integer.valueOf(businessId));
        request.setAttribute("business", (Object)business);
        return "success";
    }
    
    public String businessEdit() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String businessId = request.getParameter("businessId");
        final String businessName = request.getParameter("businessName");
        final String businessDescription = request.getParameter("businessDescription");
        final BusinessVo businessVo = new BusinessVo();
        businessVo.setBusinessId(Integer.valueOf(businessId));
        businessVo.setBusinessName(businessName);
        businessVo.setBusinessDescription(businessDescription);
        this.businessService.update(businessVo);
        final List list = this.businessService.findByFatherIdAll(-1);
        final JSONArray json = JSONArray.fromObject((Object)list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String businessDel() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String businessId = request.getParameter("businessId");
        this.businessService.del(Integer.valueOf(businessId));
        final List list = this.businessService.findByFatherIdAll(-1);
        final JSONArray json = JSONArray.fromObject((Object)list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String businessExportDc() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String ids = this.businessService.findByFatherIdAllId(id);
        final Map m = this.businessService.findById(id);
        String title = "";
        if (m != null) {
            title = m.get("businessName").toString();
        }
        final List ls = this.businessService.getAllLeafs(ids);
        final Poi poi = new Poi();
        poi.addTitle("\u95ee\u5377\u8c03\u67e5--" + title, 1, 2);
        poi.addBusinessList(ls);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }
    
    public String businessExportTitle() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String ids = this.businessService.findByFatherIdAllId(id);
        final List ls = this.businessService.getAllLeafs(ids);
        final Poi poi = new Poi();
        poi.addTitle("\u5e38\u5c71\u53bf\u4eba\u6c11\u533b\u9662\u60a3\u8005\u6ee1\u610f\u5ea6\u7ed3\u679c", 1, ls.size() / 2);
        poi.addBusinessListTitle(ls);
        String path = ServletActionContext.getServletContext().getRealPath("/");
        path = String.valueOf(path) + "standard.xls";
        poi.createFile(path);
        return "success";
    }
    
    public String businessImportDialog() throws Exception {
        this.list = this.businessService.findByFatherId(1);
        return "success";
    }
    
    public String businessImport() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String ids = this.businessService.findByFatherIdAllId(id);
        final String imgName = request.getParameter("imgName");
        final List ls = this.businessService.getAllLeafs(ids);
        final List ls_res = new ArrayList();
        if (this.img != null) {
            try {
                final HSSFWorkbook workbook = new HSSFWorkbook((InputStream)new FileInputStream(this.img));
                final HSSFSheet sheet = workbook.getSheetAt(0);
                HSSFRow row = sheet.getRow(7);
                for (int i = 0; i < row.getLastCellNum() - 3; ++i) {
                    final String temp = row.getCell(i).getStringCellValue();
                    Map map = (Map)ls.get(i);
                    final String temp2 = map.get("businessName").toString();
                    if (!temp2.equals(temp)) {
                        this.msg = "\u6570\u636e\u683c\u5f0f\u4e0d\u5bf9,\u8bf7\u4ece\u65b0\u5bfc\u51fa\u8868\u5934";
                        return "success";
                    }
                }
                final int t = sheet.getLastRowNum();
                for (int j = 8; j <= sheet.getLastRowNum(); ++j) {
                    row = sheet.getRow(j);
                    final Object[] temp3 = new Object[row.getLastCellNum()];
                    for (int k = 0; k < row.getLastCellNum(); ++k) {
                        final HSSFCell cell = row.getCell(k);
                        if (cell.getCellType() == CellType.STRING) {
                            temp3[k] = cell.getStringCellValue();
                        }
                        else if (HSSFDateUtil.isCellDateFormatted((Cell)cell)) {
                            temp3[k] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cell.getDateCellValue());
                        }
                        else {
                            if (cell.getCellType() != CellType.NUMERIC) {
                                this.msg = "\u6570\u636e\u683c\u5f0f\u4e0d\u5bf9,\u8bf7\u4ece\u65b0\u8f93\u5165\u6570\u636e";
                                return "success";
                            }
                            temp3[k] = (int)cell.getNumericCellValue();
                        }
                    }
                    ls_res.add(temp3);
                }
                for (int j = 0; j < ls_res.size(); ++j) {
                    final Object[] ls_r = (Object[]) ls_res.get(j);
                    for (int k = 0; k < ls_r.length; ++k) {
                        final String mac = ls_r[ls_r.length - 2].toString();
                        final String cardnum = ls_r[ls_r.length - 3].toString();
                        final Map m = this.deptService.findByMac(mac);
                        if (m == null) {
                            this.msg = String.valueOf(mac) + "\u4e0d\u5b58\u5728";
                            return "success";
                        }
                        final List l = this.employeeService.findByCardnumOrName(cardnum);
                        if (l == null || l.size() == 0) {
                            this.msg = String.valueOf(cardnum) + "\u4e0d\u5b58\u5728";
                            return "success";
                        }
                    }
                }
                final String[] keyType = { "5", "4", "3", "2", "1", "0", "6" };
                for (int i2 = 0; i2 < ls_res.size(); ++i2) {
                    final Object[] ls_r2 = (Object[]) ls_res.get(i2);
                    for (int j2 = 0; j2 < ls_r2.length - 3; ++j2) {
                        final String tt = ls_r2[ls_r2.length - 1].toString();
                        final String mac2 = ls_r2[ls_r2.length - 2].toString();
                        final String cardnum2 = ls_r2[ls_r2.length - 3].toString();
                        final String demo = "";
                        final String pj = keyType[Integer.parseInt(ls_r2[j2].toString())];
                        Map map = (Map)ls.get(j2);
                        final String businessTypeId = map.get("businessId").toString();
                        this.appriesService.addArrires(mac2, tt, cardnum2, pj, demo, businessTypeId);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                this.msg = "\u6587\u4ef6\u683c\u5f0f\u4e0d\u5bf9";
                return "success";
            }
        }
        this.msg = "\u5bfc\u5165\u6210\u529f";
        return "success";
    }
    
    public String businessWj() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String ids = this.businessService.findByFatherIdAllId(id);
        this.list = this.businessService.getAllLeafs(ids);
        request.setAttribute("id", (Object)id);
        return "success";
    }
    
    public String businessWjDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer id = Integer.valueOf(request.getParameter("id"));
        final String ids = this.businessService.findByFatherIdAllId(id);
        final String mac = request.getParameter("mac");
        final String cardNum = request.getParameter("cardNum");
        final String business = request.getParameter("business");
        final String[] business_array = business.split(",");
        final List ls = this.businessService.getAllLeafs(ids);
        final Calendar cal = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String tt = df.format(cal.getTime());
        final String demo = "";
        final String[] keyType = { "5", "4", "3", "2", "1", "0", "6" };
        final Map m = this.deptService.findByMac(mac);
        if (m == null) {
            this.msg = String.valueOf(mac) + "\u4e0d\u5b58\u5728";
            return "success";
        }
        final List l = this.employeeService.findByCardnumOrName(cardNum);
        if (l == null || l.size() == 0) {
            this.msg = String.valueOf(cardNum) + "\u4e0d\u5b58\u5728";
            return "success";
        }
        for (int i = 0; i < business_array.length; ++i) {
        	Map map = (Map)ls.get(i);
            final String businessTypeId = map.get("businessId").toString();
            final String pj = keyType[Integer.parseInt(business_array[i].toString())];
            this.appriesService.addArrires(mac, tt, cardNum, pj, demo, businessTypeId);
        }
        this.msg = "\u5199\u5165\u6210\u529f";
        return "success";
    }
    
    public String businessListDialog() {
        this.list = this.businessService.findByFatherId(1);
        return "success";
    }
    
    public String businessTitleDialog() {
        this.list = this.businessService.findByFatherId(1);
        return "success";
    }
    
    public String businessWjDialog() {
        this.list = this.businessService.findByFatherId(1);
        return "success";
    }
}
