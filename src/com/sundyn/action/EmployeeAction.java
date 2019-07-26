package com.sundyn.action;

import com.opensymphony.xwork2.Action;
import com.sundyn.service.*;
import com.sundyn.util.*;
import com.sundyn.vo.AttendanceVo;
import com.sundyn.vo.EmployeeVo;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.apache.struts2.ServletActionContext.getServletContext;

public class EmployeeAction extends MainAction
{
    private static final long serialVersionUID = 1L;
    private static final int BUFFER_SIZE = 16384;
    private static final Logger logger;
    private AttendanceService attendanceService;
    private Integer deptId;
    private DeptService deptService;

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    Map<String,Object> jsonData = new HashMap<String,Object>();

    public DeviceService getDeviceService() {
        return deviceService;
    }

    public void setDeviceService(DeviceService deviceServie) {
        this.deviceService = deviceServie;
    }

    private DeviceService deviceService;
    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    private ClientService clientService;
    private EmployeeService employeeService;
    private PlayListService playListService;
    private File img;
    private File img2;
    private KeyTypeService keyTypeService;
    private List list;
    private Map m;
    private String msg;
    private Pager pager;
    private PowerService powerService;
    private String star;
    private String employeeJobNum;
    private String path;
    private InputStream excel;
    private String filename;
    private String url;
    private Integer id;
    private TotalService totalService;
    private Des des;
    private CompressPicDemo cPic;

    static {
        logger = Logger.getLogger((Class)EmployeeAction.class.getClass());
    }

    public CompressPicDemo getcPic() {
        return this.cPic;
    }

    public void setcPic(final CompressPicDemo cPic) {
        this.cPic = cPic;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Des getDes() {
        return this.des;
    }

    public PlayListService getPlayListService() {
        return this.playListService;
    }

    public void setPlayListService(final PlayListService playListService) {
        this.playListService = playListService;
    }

    public void setDes(final Des des) {
        this.des = des;
    }

    private String code(String str) throws Exception {
        str = URLDecoder.decode(str, "GBK");
        str = new String(str.getBytes("UTF-8"), "GB2312");
        return str;
    }

    public String employeeAdd() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("Name");
        final String sex = request.getParameter("Sex");
        String cardNum = request.getParameter("CardNum");
        final String phone = request.getParameter("Phone");
        final String imgName = request.getParameter("imgName");
        final String deptId = request.getParameter("dept");
        final String job_desc = request.getParameter("job_desc");
        String ext2 = request.getParameter("ext2");
        final String remark = request.getParameter("remark");
        final String showDeptName = request.getParameter("showDeptName");
        final String showWindowName = request.getParameter("showWindowName");
        final String unitName = request.getParameter("unitName");
       /* if (ext2.equals("")){
            this.msg = "员工工号不能为空！";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }*/
        if (name.equals("")){
            this.msg = this.getText("employee.valid.name.notnull");
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }

        if (cardNum.equals("")){
            this.msg = this.getText("employee.valid.cardnum.notnull");
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }
        if (this.employeeService.employeeCardNumExsits(cardNum)) {
            this.msg = this.getText("employee.valid.cardnum.exists");
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }
        ext2 = cardNum;
        final EmployeeVo employeeVo = new EmployeeVo();
        employeeVo.setPicture(imgName);
        employeeVo.setName(name);
        employeeVo.setJob_desc(job_desc);
        employeeVo.setSex(sex);
        employeeVo.setCardnum(cardNum);
        employeeVo.setPhone(phone);
        employeeVo.setDeptid(Integer.valueOf(deptId));
        employeeVo.setExt1(null);
        employeeVo.setExt2(ext2);
        employeeVo.setShowDeptName(showDeptName);
        employeeVo.setShowWindowName(showWindowName);
        employeeVo.setCompanyName(unitName);
        employeeVo.setPassWord("49BA59ABBE56E057");
        employeeVo.setPassWord2("123456");
        employeeVo.setRemark(remark);
        if (imgName != null && !imgName.equals("")) {
            final MD5toPic md5 = new MD5toPic();
            final String md5Str = md5.MD5(String.valueOf(getServletContext().getRealPath("/")) + imgName);
            employeeVo.setExt4(md5Str);
        }
        this.employeeService.addEmployee(employeeVo);
        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("msg", this.getText("main.add.succ"));
        return "success";
    }

    public String employeeAddDialog() throws Exception {
        return "success";
    }

    public String employeeChangePsw() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String cardnum = request.getParameter("cardnum");
        final String oldpsw = request.getParameter("oldpsw");
        final String newpsw = request.getParameter("newpsw");
        final Map employee = this.employeeService.employeeLogin(cardnum, oldpsw);
        if (employee == null) {
            this.msg = "errorPassword";
            return "success";
        }
        if (this.employeeService.employeeChangePsw(cardnum, newpsw)) {
            this.msg = "changeSuccess";
        }
        else {
            this.msg = "changeError";
        }
        return "success";
    }

    public String employeeDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        this.employeeService.delEmployee(Integer.valueOf(employeeId));
        return "success";
    }

    public String employeeEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        final String name = request.getParameter("Name");
        final String sex = request.getParameter("Sex");
        final String cardNum = request.getParameter("CardNum");
        final String phone = request.getParameter("Phone");
        final String imgName = request.getParameter("imgName");
        final String job_desc = request.getParameter("job_desc");
        String ext2 = request.getParameter("ext2");

        String remark = request.getParameter("remark");
        final String showDeptName = request.getParameter("showDeptName");
        final String showWindowName = request.getParameter("showWindowName");
        final String unitName = request.getParameter("unitName");
        if (name.equals("")){
            this.msg = "员工姓名不能为空！";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }

        if (cardNum.equals("")){
            this.msg = "员工工号不能为空！";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }
        ext2 = cardNum;
        remark = Mysql.mysql(remark);
        Map exists = employeeService.findByCardnum(cardNum);
        if (exists!=null && !exists.get("id").toString().equals(employeeId)){
            this.msg = "员工工号已经存在！";
            jsonData.clear();
            jsonData.put("succ", false);
            jsonData.put("msg", msg);
            return Action.SUCCESS;
        }
        final EmployeeVo employeeVo = new EmployeeVo();
        employeeVo.setName(name);
        employeeVo.setJob_desc(job_desc);
        employeeVo.setSex(sex);
        employeeVo.setCardnum(cardNum);
        employeeVo.setPhone(phone);
        employeeVo.setId(Integer.valueOf(employeeId));
        employeeVo.setPicture(imgName);
        employeeVo.setExt2(ext2);
        employeeVo.setCompanyName(unitName);
        employeeVo.setRemark(remark);
        employeeVo.setShowDeptName(showDeptName);
        employeeVo.setShowWindowName(showWindowName);

        String filename = String.valueOf(getServletContext().getRealPath("/")) + imgName;
        if (imgName != null && !imgName.equals("") && new File(filename).exists()) {
            final MD5toPic md5 = new MD5toPic();
            final String md5Str = md5.MD5(filename);
            employeeVo.setExt4(md5Str);
        }
        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("msg", "添加成功");
        this.employeeService.UpdateEmployee(employeeVo);
        return "success";
    }

    public String employeeEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        this.m = this.employeeService.findEmployeeById(Integer.valueOf(employeeId));
        return "success";
    }

    public String findEmployee() throws Exception {
        final EmployeeVo employeeVo = this.employeeService.findEmployee(this.id);
        ServletActionContext.getRequest().setAttribute("employeeVo", (Object)employeeVo);
        return "employeeVoInfo";
    }

    public String employeeExsits() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ext2 = request.getParameter("ext2");
        if (this.employeeService.employeeExists(ext2)) {
            this.msg = this.getText("sundyn.exist", new String[] { "" });
        }
        else {
            this.msg = "";
        }
        return "success";
    }

    public String employeeCardNumExsits() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String CardNum = request.getParameter("CardNum");
        if (this.employeeService.employeeCardNumExsits(CardNum)) {
            this.msg = "已经存在";
        }
        else {
            this.msg = "";
        }
        return "success";
    }

    public String employeeFindByCardnumOrName() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        this.list = this.employeeService.findByCardnumOrName(keyword);
        if (this.list != null) {
            for (int i = 0; i < this.list.size(); ++i) {

            }
        }
        return "success";
    }

    /*
    JSON范例
     */
    public String getemployeeentity(){
        String key = req.getString("key");
        List employeeEntity = employeeService.findByKey(key);
        jsonData.put("employee", employeeEntity);
        return Action.SUCCESS;
    }

    private String getStar(final Double mrate) {
        final String path = getServletContext().getRealPath("/");
        String star = "";
        if (mrate != null) {
            try {
                final SundynSet sundynSet = SundynSet.getInstance(path);
                final List l_star = sundynSet.getL_star();
                for (int j = 0; j < l_star.size(); ++j) {
                    final Map star_level = (Map) l_star.get(j);
                    final Double star2 = Double.valueOf(star_level.get("star100").toString());
                    if (mrate >= star2) {
                        star = star_level.get("star").toString();
                        star = String.valueOf(star.length());
                        break;
                    }
                }
                return star;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public String employeeGetInfo() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String cardnum = request.getParameter("cardnum");
        cardnum = Mysql.mysql(cardnum);
        HttpSession session = request.getSession();
        final String path = getServletContext().getRealPath("/");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        String star = sundynSet.getM_system().get("star").toString();
        this.m = this.employeeService.findEmployeeByExt2(cardnum);
        if (this.m == null) {
            this.msg = "getInfoError";
        }
        else {
            if (session.getAttribute("employee") != null) {
            }
            else {
                session = request.getSession();
                session.setAttribute("employee", (Object)this.m);
            }
            final JSONObject json = JSONObject.fromObject((Object)this.m);
            if (star.equals("true")) {
                final String employeeId = this.m.get("Id").toString();
                List list = this.totalService.totalPerson(employeeId, null, null);
                list = this.getPandM(list);
                if (list != null && list.size() > 0) {
                    final Map m_temp = (Map) list.get(0);
                    final Double mrate = Double.valueOf(m_temp.get("mrate").toString());
                    star = this.getStar(mrate);
                }
                else {
                    star = "5";
                }
            }
            else {
                star = this.m.get("Phone").toString();
            }
            json.put((Object)"star", (Object)star);
            this.msg = json.toString();
        }
        return "success";
    }

    public String employeeGetPic() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String cardnum = request.getParameter("cardnum");
        cardnum = Mysql.mysql(cardnum);
        this.m = this.employeeService.employeeFindByCardnum(cardnum);
        if (this.m == null) {
            this.msg = "getPicError";
        }
        else {
            this.msg = this.m.get("picture").toString();
        }
        return "success";
    }

    public String employeeGetPicAndInfo() throws Exception {
        return "success";
    }

    public String employeeGetPicAndInfo_back() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = getServletContext().getRealPath("/");
        HttpSession session = request.getSession();
        final SundynSet sundynSet = SundynSet.getInstance(path);
        final String star = sundynSet.getM_system().get("star").toString();
        String cardnum = request.getParameter("cardnum");
        cardnum = Mysql.mysql(cardnum);
        this.m = this.employeeService.employeeFindByCardnum(cardnum);
        if (this.m != null) {
            final String danweiName = this.deptService.getDanweiName();
            this.m.put("danweiName", danweiName);
        }
        if (this.m != null) {
            final String employeeId = this.m.get("Id").toString();
            List list = this.totalService.totalPerson(employeeId, null, null);
            list = this.getPandM(list);
            if (list != null && list.size() > 0) {
                final Map m_temp = (Map) list.get(0);
                final Double mrate = Double.valueOf(m_temp.get("mrate").toString());
                int j;
                List l_star;
                Map star_level;
                Double star2;
                for (j = 0, l_star = sundynSet.getL_star(); j < l_star.size(); ++j) {
                    star_level = (Map) l_star.get(j);
                    star2 = Double.valueOf(star_level.get("star100").toString());
                    if (mrate >= star2) {
                        this.m.put("star", star_level.get("star"));
                        break;
                    }
                }
                if (j == l_star.size()) {
                    this.m.put("star", " ");
                }
            }
        }
        if (this.m == null) {
            this.msg = "getInfoError";
            return "error";
        }
        if (session.getAttribute("employee") != null) {
            session.invalidate();
        }
        else {
            session = request.getSession();
            session.setAttribute("employee", (Object)this.m);
        }
        final String userId = this.m.get("Id").toString();
        final String d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        final String t = new SimpleDateFormat("HH:mm:ss").format(new Date());
        final AttendanceVo attendanceVo = new AttendanceVo();
        final Map temp = this.attendanceService.query(d, Integer.parseInt(userId));
        if (temp == null) {
            attendanceVo.setUserId(Integer.parseInt(userId));
            attendanceVo.setAttendDate(d);
            attendanceVo.setAttendUp(t);
            attendanceVo.setAttendDown(t);
            this.attendanceService.add(attendanceVo);
        }
        else {
            attendanceVo.setAttendanceId(Integer.parseInt(temp.get("attendanceId").toString()));
            attendanceVo.setUserId(Integer.parseInt(userId));
            attendanceVo.setAttendDate(d);
            attendanceVo.setAttendUp(temp.get("attendUp").toString());
            attendanceVo.setAttendDown(t);
            this.attendanceService.edit(attendanceVo);
        }
        final JSONObject json = JSONObject.fromObject((Object)this.m);
        request.setAttribute("json", (Object)json);
        request.setAttribute("star", (Object)star);
        return "success";
    }

    public String employeeHeart() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        final String cardNum = request.getParameter("cardNum");
        final String ipadd=request.getParameter("ipadd");
        final String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        if (cardNum != null && cardNum != "") {
            final Map employee = this.employeeService.findByCardnum(cardNum);
            if (employee != null) {
                session.setAttribute("employee", (Object)employee);
                employeeService.updateOnline(cardNum,1);
            }
        }
        final String mac = request.getParameter("mac");
        String clientVer = request.getParameter("clientVer");
        if (mac != null && !mac.equals("")) {
            final Map m = this.deptService.findDeptByMac(mac);
            if (m != null && !"".equals(m.get("id"))) {
                if (clientVer == null) {
                    clientVer = "";
                }
                this.deptService.updateDeptMacTime(mac, dt, clientVer);
                if(null!=ipadd)
                    this.deptService.updateIpAdd(mac, ipadd);
            }
            else {
                this.deptService.addErrorMac(mac);
            }
        }
        this.msg = "online";
        return "success";
    }

    public String employeeIn() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Integer employeeId = Integer.valueOf(request.getParameter("employeeId"));
        this.employeeService.UpdateRemoveEmployee(this.deptId, employeeId);
        return "success";
    }

    public String employeeLogin() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String cardnum = request.getParameter("cardnum");
        String psw = request.getParameter("psw");
        cardnum = Mysql.mysql(cardnum);
        psw = Mysql.mysql(psw);
        Map employee = this.employeeService.findByCardnum(cardnum);
        if (employee == null) {
            this.msg = "卡号不存在";
            return "success";
        }
        employee = this.employeeService.employeeLogin(cardnum, psw);
        if (employee == null) {
            this.msg = "密码错误";
        }
        else {
            this.msg = "loginSuccess";
        }
        return "success";
    }

    public String employeeLogin2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String psw = request.getParameter("psw");
        name = Mysql.mysql(name);
        psw = Mysql.mysql(psw);
        Map employee = this.employeeService.findEmployeeByExt2(name);
        if (employee == null) {
            this.msg = "loginMsg:卡号不存在";
            return "success";
        }
        employee = this.employeeService.employeeLogin2(name, psw);
        if (employee == null) {
            this.msg = "loginMsg:密码错误";
        }
        else {
            String mac = req.getString("mac");
            if (mac!=null && !mac.equals("")){
                Map deptmap = deptService.findByMac(mac);
                if (deptmap!=null && !deptmap.get("fatherId").toString().equals(employee.get("deptid").toString())){
                    this.msg = "loginMsg::请在本营业厅登录";
                    return "success";
                }
            }
                this.msg = "loginSuccess|" + employee.get("CardNum");
        }
        return "success";
    }

    public String employeeLogin3() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String psw = request.getParameter("psw");
        name = Mysql.mysql(name);
        psw = Mysql.mysql(psw);
        final Map employee = this.employeeService.employeeLogin2(name, psw);
        if (employee == null) {
            this.msg = "loginError";
        }
        else {
            session.setAttribute("employee", (Object)employee);
            this.msg = "loginSuccess|" + employee.get("CardNum");
        }
        return "success";
    }

    public String employeeLogin4() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        String name = request.getParameter("managerVo.name");
        String psw = request.getParameter("managerVo.password");
        name = Mysql.mysql(name);
        psw = Mysql.mysql(psw);
        if (name == null || name.equals("")) {
            this.msg = "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a";
            return "error";
        }
        if (psw == null || psw.equals("")) {
            this.msg = "\u5bc6\u7801\u4e0d\u80fd\u4e3a\u7a7a";
            return "error";
        }
        psw = this.des.decode(psw);
        final Map employee = this.employeeService.employeeLogin2(name, psw);
        if (employee == null) {
            this.msg = "\u7528\u6237\u540d\u5bc6\u7801\u9519\u8bef";
            return "error";
        }
        session.setAttribute("emp", (Object)employee);
        return "success";
    }

    public String employeeLogout() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        final String cardnum = request.getParameter("cardnum");
        final Object abc = session.getAttribute("employee");
        final Map e = (Map)session.getAttribute("employee");
        /*while (iter.hasNext()) {
            String key = iter.next();
            Object valuevv = employee.get(key);
            System.out.println("employee.keyand value: " + key + " value:" + valuevv);
        }*/
        final String userId = e.get("id").toString();
        final String d = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        final String t = new SimpleDateFormat("HH:mm:ss").format(new Date());
        final AttendanceVo attendanceVo = new AttendanceVo();
        attendanceVo.setUserId(Integer.parseInt(userId));
        attendanceVo.setAttendDate(d);
        attendanceVo.setAttendDown(t);
        final Map temp = this.attendanceService.query(d, Integer.parseInt(userId));
        final String mac = null;
        if (mac != null) {
            this.attendanceService.addToList(Integer.parseInt(userId), d, t, this.deptService.findByMac(request.getParameter("mac")).get("name").toString());
            if (temp == null) {
                this.attendanceService.add(attendanceVo);
            }
            else {
                attendanceVo.setAttendanceId(Integer.parseInt(temp.get("attendanceId").toString()));
                attendanceVo.setAttendUp(temp.get("attendUp").toString());
                this.attendanceService.edit(attendanceVo);
            }
        }
        session.invalidate();
        return "success";
    }

    public String employeeLogout2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        session.invalidate();
        return "success";
    }

    public String employeeMac() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        final String head = "00000";
        if (mac != null) {
            this.msg = this.deptService.getDeptByMac(mac);
        }
        if (this.msg == null) {
            this.msg = "";
        }
        if (this.msg.length() < 5) {
            this.msg = head.substring(0, 5 - this.msg.length()).concat(this.msg);
        }
        return "success";
    }

    public String employeeManage() throws Exception {
        //final boolean flag = this.deptService.isLastestTwo(this.deptId);
        Map dept = this.deptService.findDeptById(this.deptId);
        if (dept!=null && dept.get("deptType").toString().equals("1")) {//服务厅
            final HttpServletRequest request = ServletActionContext.getRequest();
            String keyword = request.getParameter("keyword");
            if (keyword == null) {
                keyword = "";
            }
            keyword = keyword.trim();

            final int num = this.employeeService.countEmployeeByDeptid(this.deptId);
            this.pager = new Pager("currentPage", pageSize, num, request, this);
            this.list = this.employeeService.findEmployeeByDeptid(this.deptId, keyword, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
            this.pager.setPageList(this.list);
            final Map m = EmployeeList.getList();
            request.setAttribute("online", m);
            request.setAttribute("keyword", keyword);
            return "success";
        }
        return "error";


        /*final HttpServletRequest request = ServletActionContext.getRequest();

        final int num = this.employeeService.countEmployeeByName(keyword);
        this.pager = new Pager("currentPage", pageSize, num, request, "keywordpage", this);
        this.list = this.employeeService.findEmployeeByName(this.deptId, keyword, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.list);
        request.setAttribute("keyword", (Object)keyword);
        request.setAttribute("online", EmployeeList.getList());
        return "success";*/
    }

    public String employeeOut() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        this.employeeService.UpdateMoveEmployee(Integer.valueOf(employeeId));
        return "success";
    }

    public String employeeOutView() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final int num = this.employeeService.countMovedEmployee();
        this.pager = new Pager("currentPage", pageSize, num, request, "employeeOutPage", this);
        this.list = this.employeeService.findMovedEmployee((this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.list);
        return "success";
    }

    public String employeeQueryKeyword() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim();
        final int num = this.employeeService.countEmployeeByName(keyword);
        this.pager = new Pager("currentPage", pageSize, num, request, "keywordpage", this);
        this.list = this.employeeService.findEmployeeByName(this.deptId, keyword, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(this.list);
        request.setAttribute("keyword", (Object)keyword);
        request.setAttribute("online", EmployeeList.getList());
        return "success";
    }

    public String employeeReset() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String employeeId = request.getParameter("employeeId");
        this.employeeService.passwordReSet(Integer.valueOf(employeeId));
        return "success";
    }

    public String employeeServerTime() {
        final TimeZone tz = TimeZone.getTimeZone("");
        final DateFormat df = new SimpleDateFormat("yyyy|MM|dd|HH|mm|ss");
        this.msg = df.format(new Date());
        return "success";
    }

    public String employeeUpdate() throws IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String mac = request.getParameter("mac");
        final String ip = request.getRemoteAddr();
        this.url = "";
        String playListName = "";
        String windowName = "";
        String version = request.getParameter("version");
        final String config = request.getParameter("config");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String basepath = getServletContext().getRealPath("/");
        String playListId = "";
        final String dd = df.format(new Date());
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                windowName = dept.get("name").toString();
                final Map playListM = this.playListService.findById(Integer.valueOf(playListId));
                playListName = playListM.get("playListName").toString();
            }
        }
        if (mac != null && config != null && config.equals("true")) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
                final File f = new File(String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
                if (f.exists()) {
                    this.excel = new FileInputStream(f);
                    this.url = "update/" + playListId + "/CONFIG.XML";
                    this.filename = "CONFIG.XML";
                }
                else {
                    this.msg = String.valueOf(f.getAbsolutePath()) + "\u627e\u4e0d\u5230";
                    this.excel = new ByteArrayInputStream(this.msg.getBytes());
                    this.filename = "error.txt";
                }
            }
            else {
                this.msg = String.valueOf(mac) + "\u6240\u5728\u7684\u90e8\u95e8\u4e0d\u5b58";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                this.filename = "error.txt";
                mac = null;
            }
        }
        else if (mac != null && config != null && config.equals("false")) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
                final File f = new File(String.valueOf(m7binpath) + File.separator + "M7Update.bin");
                if (f.exists()) {
                    this.excel = new FileInputStream(f);
                    this.url = "update/" + playListId + "/M7Update.bin";
                    this.filename = "M7Update.bin";
                }
                else {
                    this.msg = String.valueOf(f.getAbsolutePath()) + "\u627e\u4e0d\u5230";
                    this.excel = new ByteArrayInputStream(this.msg.getBytes());
                    this.filename = "error.txt";
                }
            }
            else {
                this.msg = String.valueOf(mac) + "\u6240\u5728\u7684\u90e8\u95e8\u4e0d\u5b58";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                this.filename = "error.txt";
            }
        }
        else if (mac != null && version != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
                final File f = new File(String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".bin");
                if (f.exists()) {
                    this.excel = new FileInputStream(f);
                    this.filename = "M7Update" + version + ".bin";
                    this.url = "update/" + playListId + "/M7Update" + version + ".bin";
                }
                else {
                    this.msg = String.valueOf(f.getAbsolutePath()) + "\u627e\u4e0d\u5230";
                    this.filename = "error.txt";
                    this.excel = new ByteArrayInputStream(this.msg.getBytes());
                    version = null;
                }
            }
            else {
                this.msg = String.valueOf(mac) + "\u6240\u5728\u7684\u90e8\u95e8\u4e0d\u5b58";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                mac = null;
            }
        }
        if (this.msg != null && this.msg.equals("")) {
            this.msg = "\u4f60\u4f20\u7684\u53c2\u6570\u6709\u95ee\u9898";
            this.excel = new ByteArrayInputStream(this.msg.getBytes());
            this.filename = "error.txt";
            mac = null;
        }
        final Map m = new HashMap();
        m.put("mac", mac);
        if (version == null || version.equals("")) {
            final String filePath = String.valueOf(basepath) + "update" + File.separator + playListId + File.separator + "CONFIG.XML";
            SAXBuilder sb = new SAXBuilder();
            try {
                Document doc = sb.build(filePath);
                Element root = doc.getRootElement();
                m.put("version", root.getChildText("version"));
                root = null;
                doc = null;
                sb = null;
            }
            catch (JDOMException e) {
                m.put("version", "\u672a\u77e5");
                e.printStackTrace();
            }
        }
        else {
            m.put("version", version);
        }
        m.put("windowName", windowName);
        m.put("date", dd);
        m.put("ip", ip);
        m.put("playListName", playListName);
        if (mac != null && !mac.equals("")) {
            M7Info.getInstance().add(m);
        }
        M7Info.getInstance();
        M7Info.Save();
        if (this.url.equals("")) {
            return "error";
        }
        return "url";
    }

    public String employeeUpdateConfig() throws IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final String mac = request.getParameter("mac");
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                final String id = dept.get("dept_playListId").toString();
                final String basepath = getServletContext().getRealPath("/");
                final String m7binpath = String.valueOf(basepath) + "update" + File.separator + id;
                final File f = new File(String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
                if (f.exists()) {
                    response.sendRedirect("update/" + id + "/" + "CONFIG.XML");
                }
            }
            else {
                this.msg = "mac\u5730\u5740\u4e0d\u6b63\u786e";
            }
        }
        else {
            this.msg = "\u4f60\u6ca1\u6709\u4f20mac\u5730\u5740";
        }
        return "success";
    }

    public String getClientUpdateVersion() throws IOException, JDOMException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String mac = request.getParameter("mac");
        final String ip = request.getRemoteAddr();
        this.url = "";
        String playListName = "";
        String windowName = "";
        String version = request.getParameter("version");
        final String config = request.getParameter("config");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String basepath = getServletContext().getRealPath("/");
        String playListId = "";
        final String dd = df.format(new Date());
        List newestClient = this.clientService.getNewest();

        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                windowName = dept.get("name").toString();
                final Map playListM = this.playListService.findById(Integer.valueOf(playListId));
                playListName = playListM.get("playListName").toString();
            }
        }
        final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
        if (config != null && config.equals("true")) {
            final Map dept = this.deptService.findByMac(mac);
            if (newestClient!=null && newestClient.size()==1){
                int newestClientNo = Integer.valueOf(((Map)newestClient.get(0)).get("ClientNo").toString());
                this.msg = "Version=" + newestClientNo;
                return "success";
            }
        } else if (config != null && config.equals("false")) {
            if (newestClient!=null && newestClient.size()==1){
                int newestClientNo = Integer.valueOf(((Map)newestClient.get(0)).get("ClientNo").toString());
                String newestClientPath = ((Map)newestClient.get(0)).get("FilePath").toString();
                this.filename = "client.7z";
                this.url = newestClientPath;
                return "url";
            }
        }
        return "error";
    }

    public String getUpdateVersion() throws IOException, JDOMException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String mac = request.getParameter("mac");
        String prdcode = request.getParameter("prdcode");
        final String ip = request.getRemoteAddr();
        this.url = "";
        String playListName = "";
        String windowName = "";
        String version = request.getParameter("version");
        String androidVer = request.getParameter("androidVer");
        final String config = request.getParameter("config");
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String basepath = getServletContext().getRealPath("/");
        String playListId = "";
        final String dd = df.format(new Date());
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                windowName = dept.get("name").toString();
                final Map playListM = this.playListService.findById(Integer.valueOf(playListId));
                playListName = playListM.get("playListName").toString();
            }
        }
        final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
        if (mac != null && config != null && config.equals("true")) {
            this.deviceService.findAndAddByMac(mac, prdcode, androidVer);
            deptService.hasAdnAddDeptByMac(mac);
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                final File f = new File(String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
                if (f.exists()) {
                    this.excel = new FileInputStream(f);
                    this.url = "update/" + playListId + "/NEWCONFIG.XML";
                    this.filename = "NEWCONFIG.XML";
                    final Format format = Format.getPrettyFormat();
                    format.setIndent("    ");
                    format.setEncoding("gb2312");
                    final XMLOutputter XMLOut = new XMLOutputter(format);
                    final SAXBuilder sb = new SAXBuilder();
                    final Document doc = sb.build(String.valueOf(m7binpath) + File.separator + "NEWCONFIG.XML");
                    final Element root = doc.getRootElement();
                    final Element Software = root.getChild("Software");
                    final String version2 = Software.getChild("Version").getText();
                    this.msg = "Version=" + version2;
                    return "success";
                }
                this.msg = String.valueOf(f.getAbsolutePath()) + "找不到";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                this.filename = "error.txt";
            }
            else {
                this.msg = String.valueOf(mac) + "部门不存在";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                this.filename = "error.txt";
                mac = null;
            }
        }
        else if (mac != null && config != null && config.equals("false")) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                final File cf = new File(String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
                if (cf.exists()) {
                    this.excel = new FileInputStream(cf);
                    this.url = "update/" + playListId + "/NEWCONFIG.XML";
                    this.filename = "NEWCONFIG.XML";
                    final Format format = Format.getPrettyFormat();
                    format.setIndent("    ");
                    format.setEncoding("gb2312");
                    final XMLOutputter XMLOut = new XMLOutputter(format);
                    final SAXBuilder sb = new SAXBuilder();
                    final Document doc = sb.build(String.valueOf(m7binpath) + File.separator + "NEWCONFIG.XML");
                    final Element root = doc.getRootElement();
                    final Element Software = root.getChild("Software");
                    version = Software.getChild("Version").getText();

                    playListId = dept.get("dept_playListId").toString();
                    logger.info("升级资源路径： " + String.valueOf(m7binpath) + File.separator + "M7Update"+version+".zip");
                    final File fd = new File(String.valueOf(m7binpath) + File.separator + "M7Update"+version+".zip");
                    if (fd.exists()) {
                        this.excel = new FileInputStream(fd);
                        this.url = "update/" + playListId + "/M7Update"+version+".zip";
                        logger.info("getUpdateVersion-完整升级-url=" + this.url);
                        this.filename = "M7Update"+version+".zip";
                    }
                    else {
                        System.out.println("fd not exists");
                        this.url = "";
                        this.msg = "找不到资源文件" + fd.getAbsolutePath();
                        this.excel = new ByteArrayInputStream(this.msg.getBytes());
                        this.filename = "error.txt";
                    }
                }
            }
            else {
                this.msg = String.valueOf(mac) + "不存在";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                this.filename = "error.txt";
            }
        }
        else if (mac != null && version != null) {
            System.out.println("3两个参数都传， 下载增量升级包");
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                playListId = dept.get("dept_playListId").toString();
                final File f = new File(String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".zip");
                if (f.exists()) {
                    this.excel = new FileInputStream(f);
                    this.filename = "M7Update" + version + ".zip";
                    this.url = "update/" + playListId + "/M7Update" + version + ".zip";
                }
                else {
                    this.msg = String.valueOf(f.getAbsolutePath()) + "找不到";
                    this.filename = "error.txt";
                    this.excel = new ByteArrayInputStream(this.msg.getBytes());
                    version = null;
                }
            }
            else {
                this.msg = String.valueOf(mac) + "部门不存在";
                this.excel = new ByteArrayInputStream(this.msg.getBytes());
                mac = null;
            }
        }
        if (this.msg != null && this.msg.equals("")) {
            this.msg = "参数有问题";
            this.excel = new ByteArrayInputStream(this.msg.getBytes());
            this.filename = "error.txt";
            mac = null;
        }
        final Map m = new HashMap();
        m.put("mac", mac);
        if (version == null || version.equals("")) {
            logger.info("没有发送版本号，是请求配置文件，去配置文件读取版本号");
            final String filePath = String.valueOf(basepath) + "update" + File.separator + playListId + File.separator + "CONFIG.XML";
            SAXBuilder sb2 = new SAXBuilder();
            try {
                Document doc2 = sb2.build(filePath);
                Element root2 = doc2.getRootElement();
                m.put("version", root2.getChildText("version"));
                root2 = null;
                doc2 = null;
                sb2 = null;
            }
            catch (JDOMException e) {
                m.put("version", "未知");
                e.printStackTrace();
            }
        }
        else {
            m.put("version", version);
        }
        m.put("windowName", windowName);
        m.put("date", dd);
        m.put("ip", ip);
        m.put("playListName", playListName);
        if (mac != null && !mac.equals("")) {
            M7Info.getInstance().add(m);
        }
        M7Info.getInstance();
        M7Info.Save();
        if (this.url.equals("")) {
            return "error";
        }
        return "url";
    }

    public String useVideo() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final String mac = request.getParameter("mac");
        String msg;
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                if (dept.get("useVideo") == null) {
                    msg = "0";
                }
                else {
                    msg = dept.get("useVideo").toString();
                }
            }
            else {
                msg = "\u672a\u627e\u5230\u7ed1\u5b9a\u6b64mac\u7684\u7a97\u53e3";
            }
        }
        else {
            msg = "mac\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165";
        }
        return "success";
    }

    public String getDeptVideo() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final String mac = request.getParameter("mac");
        String msg;
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                if (dept.get("useVideo") == null) {
                    msg = "0";
                }
                else {
                    msg = dept.get("useVideo").toString();
                }
            }
            else {
                msg = "\u672a\u627e\u5230\u7ed1\u5b9a\u6b64mac\u7684\u7a97\u53e3";
            }
        }
        else {
            msg = "mac\u4e3a\u7a7a\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165";
        }
        request.setAttribute("msg", (Object)msg);
        return "success";
    }

    public String getDeptNotice() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        final String mac = request.getParameter("mac");
        String msg;
        if (mac != null) {
            final Map dept = this.deptService.findByMac(mac);
            if (dept != null) {
                if (dept.get("notice") == null) {
                    msg = "";
                }
                else {
                    msg = dept.get("notice").toString();
                }
            }
            else {
                msg = "";
            }
        }
        else {
            msg = "";
        }
        request.setAttribute("msg", (Object)msg);
        return "success";
    }

    public String employeeUpload() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        String imgName = null;
        String impPath = "";
        final String stype = request.getParameter("tt");
        if ("c".equals(stype)) {
            imgName = request.getParameter("imgName2");
            if (this.img2 != null) {
                String dstPath = getServletContext().getRealPath("/");
                dstPath = dstPath.replaceAll("%20", " ");
                final long curTime = System.currentTimeMillis();
                final String filename = "upload/" + curTime + Math.round(Math.random() * 100.0) + this.getExtFileName(imgName);
                final File dst = new File(String.valueOf(dstPath) + filename);
                CommonUtil.copy(this.img2, dst);
                this.cPic.dealPic(dstPath, dstPath, filename, filename, 150, 160);
                impPath = filename;
            }
            else {
                impPath = "";
            }
        }
        else {
            imgName = request.getParameter("imgName");
            String dstPath = getServletContext().getRealPath("/");
            if (this.img != null) {
                dstPath = dstPath.replaceAll("%20", " ");
                final long curTime = System.currentTimeMillis();
                final String filename = "upload/" + curTime + Math.round(Math.random() * 100.0) + this.getExtFileName(imgName);
                final File dst = new File(String.valueOf(dstPath) + filename);
                CommonUtil.copy(this.img, dst);
                this.cPic.dealPic(dstPath, dstPath, filename, filename, 150, 160);
                impPath = filename;
            }
            else {
                String _fileType = this.getText("sundyn.parameters.userimgext");
                JSONObject jo = uploadFile(_fileType, "upload");
                request.setAttribute("msg", jo.toString());
                return "uploadsucc";
            }
        }
        request.setAttribute("imgPath", (Object)impPath);
        return "success";
    }

    public String employeeView() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.list = this.deptService.findChildALL(super.getUserDept().toString());
        if (this.list != null && this.list.size() > 0) {
            final Map dept = (Map) this.list.get(0);
            if (dept.get("fatherId").toString() != "-1") {
                dept.put("fatherId", -1);
                this.list.add(dept);
            }
        }
        return "success";
    }

    public String employeeFindByName() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final Map employee = this.employeeService.findEmployeeByName(name);
        String cardNum = "";
        if (employee != null) {
            cardNum = employee.get("CardNum").toString();
            request.setAttribute("msg", (Object)cardNum);
        }
        else {
            request.setAttribute("msg", (Object)"error");
        }
        return "success";
    }

    public String employeeFindByExt2() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ext2 = request.getParameter("ext2");
        final Map employee = this.employeeService.findEmployeeByExt2(ext2);
        String cardNum = "";
        if (employee != null) {
            cardNum = employee.get("CardNum").toString();
            request.setAttribute("msg", (Object)cardNum);
        }
        else {
            request.setAttribute("msg", (Object)"error");
        }
        return "success";
    }

    public AttendanceService getAttendanceService() {
        return this.attendanceService;
    }

    public Integer getDeptId() {
        return this.deptId;
    }

    public DeptService getDeptService() {
        return this.deptService;
    }

    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    private String getExtFileName(final String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public File getImg() {
        return this.img;
    }

    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }

    public List getList() {
        return this.list;
    }

    public Map getM() {
        return this.m;
    }

    public String getMsg() {
        return this.msg;
    }

    public Pager getPager() {
        return this.pager;
    }

    public List getPandM(final List list) throws Exception {
        final String path = getServletContext().getRealPath("/");
        boolean k7 = true;
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            k7 = Boolean.valueOf(sundynSet.getM_system().get("k7").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        final List mls = this.keyTypeService.findByApprieserId(1, 1, "on");
        final List bmls = this.keyTypeService.findByApprieserId(1, 1, "");
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
                final double prate = Math.rint((1.0 - key7 * 1.0 / (key0 + key2 + key3 + key4 + key5 + key6 + key7)) * 1000.0) / 10.0;
                double mrate = 0.0;
                final List km = new ArrayList();
                final List kbm = new ArrayList();
                int msum = 0;
                int bmsum = 0;
                int p = 0;
                int sum = 0;
                if (k7) {
                    for (int j = 0; j < mls.size(); ++j) {
                        final Map l = (Map) mls.get(j);
                        if (!l.get("keyNo").toString().equals("6")) {
                            msum += key8[Integer.parseInt(l.get("keyNo").toString())];
                            km.add(key8[Integer.parseInt(l.get("keyNo").toString())]);
                        }
                        else {
                            mls.remove(j);
                        }
                    }
                    for (int j = 0; j < bmls.size(); ++j) {
                        final Map l = (Map) bmls.get(j);
                        if (!l.get("keyNo").toString().equals("6")) {
                            bmsum += key8[Integer.parseInt(l.get("keyNo").toString())];
                            kbm.add(key8[Integer.parseInt(l.get("keyNo").toString())]);
                        }
                        else {
                            bmls.remove(j);
                        }
                    }
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 1000.0 / 10.0);
                    for (int j = 0; j < key8.length - 1; ++j) {
                        p += key8[j];
                    }
                    for (int j = 0; j < key8.length; ++j) {
                        sum += key8[j];
                    }
                }
                else {
                    for (int j = 0; j < mls.size(); ++j) {
                        final Map l = (Map) mls.get(j);
                        msum += key8[Integer.parseInt(l.get("keyNo").toString())];
                        km.add(key8[Integer.parseInt(l.get("keyNo").toString())]);
                    }
                    for (int j = 0; j < bmls.size(); ++j) {
                        final Map l = (Map) bmls.get(j);
                        bmsum += key8[Integer.parseInt(l.get("keyNo").toString())];
                        kbm.add(key8[Integer.parseInt(l.get("keyNo").toString())]);
                    }
                    mrate = Math.rint(msum * 1.0 / (msum * 1.0 + bmsum * 1.0) * 1000.0 / 10.0);
                    for (int j = 0; j < key8.length; ++j) {
                        p += key8[j];
                    }
                    for (int j = 0; j < key8.length; ++j) {
                        sum += key8[j];
                    }
                }
                m.put("prate", prate);
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

    public String getStar() {
        final String path = getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.star = sundynSet.getM_system().get("star").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.star;
    }

    public TotalService getTotalService() {
        return this.totalService;
    }

    public void setAttendanceService(final AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void setDeptId(final Integer deptId) {
        this.deptId = deptId;
    }

    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }

    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setImg(final File img) {
        this.img = img;
    }

    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }

    public void setList(final List list) {
        this.list = list;
    }

    public void setM(final Map m) {
        this.m = m;
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

    public void setStar(final String star) {
        final String path = getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.star = sundynSet.getM_system().get("star").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTotalService(final TotalService totalService) {
        this.totalService = totalService;
    }

    public File getImg2() {
        return this.img2;
    }

    public void setImg2(final File img2) {
        this.img2 = img2;
    }

    public String employeeGetAllNameAndCardNum() throws JDOMException, IOException {
        final String path = getServletContext().getRealPath("/");
        final List ls = this.employeeService.findAllEmployee();
        Element root = new Element("employeeList");
        final Document doc = new Document(root);
        root = doc.getRootElement();
        if (ls != null && ls.size() > 0) {
            for (int i = 0; i < ls.size(); ++i) {
                final Map m = (Map) ls.get(i);
                final Element employee = new Element("employee");
                if (m.get("Name") != null) {
                    employee.addContent((Content)new Element("name").setText(m.get("Name").toString()));
                }
                else {
                    employee.addContent((Content)new Element("name").setText(""));
                }
                if (m.get("CardNum") != null) {
                    employee.addContent((Content)new Element("cardNum").setText(m.get("CardNum").toString()));
                }
                else {
                    employee.addContent((Content)new Element("CardNum").setText(""));
                }
                if (m.get("ext2") != null) {
                    employee.addContent((Content)new Element("ext2").setText(m.get("ext2").toString()));
                }
                else {
                    employee.addContent((Content)new Element("ext2").setText(""));
                }
                root.addContent((Content)employee);
            }
        }
        final Format format = Format.getPrettyFormat();
        format.setIndent("    ");
        format.setEncoding("gb2312");
        XMLOutputter XMLOut = new XMLOutputter(format);
        XMLOut.output(doc, (OutputStream)new FileOutputStream(String.valueOf(path) + "update/employee.xml"));
        XMLOut = null;
        return "success";
    }

    public String employeeExcel() throws Exception {
        final String path = getServletContext().getRealPath("/");
        List ls = this.employeeService.employeeExcel();
        final Poi poi = new Poi();
        String[] args = null;
        this.employeeJobNum = this.getEmployeeJobNum();
        if (this.getText("sundyn.language").equals("en")) {
            if (this.employeeJobNum.equals("true")) {
                final String[] arg = args = new String[] { "name", "job number", "sex", "card number", "phone", "window", "department", "unit" };
            }
            else {
                final String[] arg = args = new String[] { "name", "user name", "sex", "card number", "phone", "window", "department", "unit" };
            }
        }
        else if (this.employeeJobNum.equals("true")) {
            final String[] arg = args = new String[] { "\u59d3\u540d", "\u5458\u5de5\u5de5\u53f7", "\u6027\u522b", "\u5458\u5de5\u5361\u53f7", "\u7535\u8bdd", "\u7a97\u53e3", "\u90e8\u95e8", "\u5355\u4f4d" };
        }
        else {
            final String[] arg = args = new String[] { "\u59d3\u540d", "\u7528\u6237\u540d", "\u6027\u522b", "\u5458\u5de5\u5361\u53f7", "\u7535\u8bdd", "\u7a97\u53e3", "\u90e8\u95e8", "\u5355\u4f4d" };
        }
        if (ls != null && ls.size() > 0) {
            final List temp = new ArrayList();
            for (int i = 0; i < ls.size(); ++i) {
                final Map m = (Map) ls.get(i);
                final Map t = (Map)new LinkedMap();
                t.put("Name", m.get("Name"));
                t.put("ext2", m.get("ext2"));
                t.put("Sex", m.get("Sex"));
                t.put("CardNum", m.get("CardNum"));
                t.put("Phone", m.get("Phone"));
                t.put("showWindowName", m.get("showWindowName"));
                t.put("showDeptName", m.get("showDeptName"));
                t.put("companyName", m.get("companyName"));
                temp.add(t);
            }
            ls = temp;
        }
        poi.addTitle("\u5bfc\u51fa\u4eba\u5458", 0, 3);
        poi.addListTitle(args, 3);
        poi.addList(ls);
        poi.createFile(String.valueOf(path) + "standard.xls");
        return "success";
    }

    public String employeeByWindowMac() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        try {
            final Map m = this.deptService.findByMac(mac);
            final Integer deptId = Integer.valueOf(m.get("fatherId").toString());
            this.list = this.employeeService.findEmployeeByDeptId(deptId);
            final String path = getServletContext().getRealPath("/");
            final SundynSet sundynSet = SundynSet.getInstance(path);
            String star = sundynSet.getM_system().get("star").toString();
            if (star.equals("true")) {
                for (int i = 0; i < this.list.size(); ++i) {
                    final Map mm = (Map) this.list.get(i);
                    final String employeeId = mm.get("Id").toString();
                    List list = this.totalService.totalPerson(employeeId, null, null);
                    list = this.getPandM(list);
                    if (list != null && list.size() > 0) {
                        final Map m_temp = (Map) list.get(0);
                        final Double mrate = Double.valueOf(m_temp.get("mrate").toString());
                        star = this.getStar(mrate);
                    }
                    else {
                        star = "";
                    }
                    mm.put("star", star);
                }
            }
            else {
                for (int i = 0; i < this.list.size(); ++i) {
                    final Map mm = (Map) this.list.get(i);
                    star = mm.get("Phone").toString();
                    mm.put("star", star);
                }
            }
            final Map employee = new HashMap();
            employee.put("employees", this.list);
            final JSONObject json = JSONObject.fromObject((Object)employee);
            request.setAttribute("json", (Object)json);
        }
        catch (Exception e) {
            this.list = null;
            request.setAttribute("json", (Object)"");
            e.printStackTrace();
        }
        return "success";
    }

    public String employeeTotalByCardNum() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String cardNum = request.getParameter("cardNum");
        final String startDate = request.getParameter("startDate");
        final String endDate = request.getParameter("endDate");
        try {
            Map j = this.totalService.totalPersonM7(cardNum, startDate, endDate);
            if (j == null) {
                j = (Map)new LinkedMap();
                j.put("key0", 0);
                j.put("key1", 0);
                j.put("key2", 0);
                j.put("key3", 0);
                j.put("key4", 0);
                j.put("key5", 0);
                j.put("key6", 0);
            }
            final JSONObject json = JSONObject.fromObject((Object)j);
            request.setAttribute("json", (Object)json);
        }
        catch (Exception e) {
            request.setAttribute("json", (Object)"");
            e.printStackTrace();
        }
        return "success";
    }

    public InputStream getExcel() {
        return this.excel;
    }

    public void setExcel(final InputStream excel) {
        this.excel = excel;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(final String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String staffMobility() throws Exception {
        return "success";
    }

    public String getEmployeeJobNum() {
        final String path = getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            final Map<String, String> employeeInfoSet = sundynSet.getM_employee();
            return this.employeeJobNum = employeeInfoSet.get("employeeJobNum");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setEmployeeJobNum(final String employeeJobNum) {
        this.employeeJobNum = employeeJobNum;
    }
}
