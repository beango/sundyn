package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.DeptService;
import com.sundyn.service.EmployeeService;
import com.sundyn.service.PlayListService;
import com.sundyn.service.PowerService;
import com.sundyn.util.*;
import com.sundyn.utils.JavaXML;
import com.sundyn.vo.SaveTextVo;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.jdom.JDOMException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseAction extends MainAction
{
    private static final int BUFFER_SIZE = 16384;
    private static final long serialVersionUID = 1L;
    private String fileName;
    private File img;
    private PlayListService playListService;
    private PowerService powerService;
    private DeptService deptService;
    private EmployeeService employeeService;
    private Pager pager;
    private InputStream xml;
    private SaveTextVo saveTextVo;

    private static void copy(final File src, final File dst) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(src), 16384);
                out = new BufferedOutputStream(new FileOutputStream(dst), 16384);
                final byte[] buffer = new byte[16384];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            }
            finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String baseRestore() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("/");
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String fileName = request.getParameter("fileName");
        final String filepath = String.valueOf(basePath) + "backUp" + "\\" + fileName;
        final String command = String.valueOf(basePath) + "mysql.exe -uroot -proot appries < " + filepath;
        final Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String baseRestoreDialog() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("/");
        final HttpServletRequest request = ServletActionContext.getRequest();
        final File f = new File(String.valueOf(basePath) + "backUp");
        List ls = new ArrayList();
        if (f.exists() && f.isDirectory()) {
            ls = Arrays.asList(f.list());
        }
        request.setAttribute("ls", (Object)ls);
        return "success";
    }

    public String baseDelRestore() throws Exception {
        final ServletRequest request = (ServletRequest)ServletActionContext.getRequest();
        final String basePath = ServletActionContext.getServletContext().getRealPath("/");
        final String fileName = request.getParameter("fileName");
        File f = new File(String.valueOf(basePath) + "backUp" + File.separator + fileName);
        if (f.exists()) {
            f.delete();
        }
        f = new File(String.valueOf(basePath) + "backUp");
        List ls = new ArrayList();
        if (f.exists() && f.isDirectory()) {
            ls = Arrays.asList(f.list());
        }
        request.setAttribute("ls", (Object)ls);
        return "success";
    }

    public String baseBackUP() throws Exception {
        final String basePath = ServletActionContext.getServletContext().getRealPath("/");
        final String d = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        final String backPath = String.valueOf(basePath) + "backUp" + "\\appries" + d + ".bak";
        final String command = String.valueOf(basePath) + "mysqldump.exe -uroot -proot appries ";
        final Runtime runtime = Runtime.getRuntime();
        Process process = null;
        String line = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            process = runtime.exec(command);
            is = process.getInputStream();
            isr = new InputStreamReader(is, "utf8");
            br = new BufferedReader(isr);
            final File f = new File(backPath);
            if (f.createNewFile()) {
                BufferedWriter wb = new BufferedWriter(new FileWriter(f));
                while ((line = br.readLine()) != null) {
                    wb.write(String.valueOf(line) + "\r\n");
                }
                wb.close();
                wb = null;
            }
            is.close();
            isr.close();
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String baseDelTemp() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String index = request.getParameter("index");
        final Integer i = Integer.valueOf(index);
        final SundynSet sundynSet = SundynSet.getInstance(path);
        sundynSet.delete(i);
        request.setAttribute("msg", (Object)this.getText("sundyn.deleteSuccess"));
        return "success";
    }

    public String baseOnLineEmployee() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final EmployeeList employeeList = EmployeeList.getInstance();
        final List list = new ArrayList();
        final Map m = EmployeeList.getList();
        for (final Object key : m.keySet()) {
            list.add(m.get(key));
        }
        request.setAttribute("list", (Object)list);
        return "success";
    }

    public String baseOnLineEmployee2() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final EmployeeList employeeList = EmployeeList.getInstance();
        final Map m = EmployeeList.getList();
        final Iterator it = m.keySet().iterator();
        String employeeIds = "";
        Integer num = 0;
        while (it.hasNext()) {
            final Object key = it.next();
            employeeIds = String.valueOf(employeeIds) + key.toString() + ",";
            ++num;
        }
        if (employeeIds.endsWith(",")) {
            employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
        }
        List list = new ArrayList();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        list = this.deptService.findChildALL(deptIdGroup);
        if (list != null && list.size() > 0) {
            final Map dept = (Map) list.get(0);
            if (dept.get("fatherId").toString() != "-1") {
                dept.put("fatherId", -1);
                list.add(dept);
            }
        }
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
        final Integer allNum = this.employeeService.countEmployeeOnline(deptIds, null);
        request.setAttribute("num", (Object)num);
        request.setAttribute("list", (Object)list);
        return "success";
    }

    public String baseOnLineEmployee2Ajax() throws SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpSession session = request.getSession();
        final EmployeeList employeeList = EmployeeList.getInstance();
        final Map m = EmployeeList.getList();
        final Iterator it = m.keySet().iterator();
        String employeeIds = "-1,";
        Integer num = 0;
        while (it.hasNext()) {
            final Object key = it.next();
            employeeIds = String.valueOf(employeeIds) + key.toString() + ",";
        }
        if (employeeIds.endsWith(",")) {
            employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
        }
        final String deptId = request.getParameter("deptId").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptId);
        num = this.employeeService.countEmployeeOnline(deptIds, employeeIds);
        this.pager = new Pager("currentPage", pageSize, num, request, "onLinePage");
        final List list = this.employeeService.employeeOnline(deptIds, employeeIds, (this.pager.getCurrentPage() - 1) * this.pager.getPageSize(), this.pager.getPageSize());
        this.pager.setPageList(list);
        request.setAttribute("allNum", (Object)num);
        request.setAttribute("num", (Object)this.employeeService.countEmployeeOnline2(deptIds, employeeIds));
        //request.setAttribute("num",session.getAttribute("employee").);
        request.setAttribute("isOnLine", (Object)"\u5728\u7ebf");
        return "success";
    }

    public String basePic() {
        return "success";
    }

    public String basePicAjax() {
        return "success";
    }

    public String basePicUpload() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String imgName = request.getParameter("imgName");
        String impPath = "";
        if (this.img != null) {
            final String dstPath = ServletActionContext.getServletContext().getRealPath("\\");
            this.fileName = imgName;
            final File dst = new File(String.valueOf(dstPath) + "system\\upload\\" + this.fileName);
            copy(this.img, dst);
            impPath = "upload/" + this.fileName;
        }
        else {
            impPath = "";
        }
        final String id = this.fileName.substring(0, this.fileName.indexOf("."));
        final Map m = new HashMap();
        m.put("id", id);
        m.put("impPath", impPath);
        final JSONObject json = JSONObject.fromObject((Object)m);
        request.setAttribute("json", (Object)json);
        return "success";
    }

    public String basePreviewTemp() throws JDOMException, IOException, SQLException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String index = request.getParameter("index");
        final String tempUrl = request.getParameter("tempUrl");
        final Map manager = (Map)request.getSession().getAttribute("manager");
        List playListList = new ArrayList();
        if (manager.get("id").toString().equals("1")) {
            playListList = this.playListService.playListQuery("", null, null, null);
        }
        else {
            final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
            final Map power = this.powerService.getUserGroup(groupid);
            final String deptIdGroup = power.get("deptIdGroup").toString();
            final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);
            playListList = this.playListService.playListQuery("", deptIds, null, null);
        }
        request.setAttribute("index", (Object)index);
        request.setAttribute("tempUrl", (Object)tempUrl);
        request.setAttribute("playListList", (Object)playListList);
        return "success";
    }

    public String baseRead() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Properties p = new Properties();
        InputStream in = null;
        Label_0445: {
            try {
                final String filename = String.valueOf(request.getRealPath("/")) + "appraise.ini";
                in = new BufferedInputStream(new FileInputStream(filename));
                this.saveTextVo = new SaveTextVo();
                p.load(in);
                this.saveTextVo.setArrpaise(p.getProperty("Appraise"));
                this.saveTextVo.setButtonBackColor(p.getProperty("ButtonBackColor"));
                this.saveTextVo.setButtonBorderColor(p.getProperty("ButtonBorderColor"));
                this.saveTextVo.setCard(p.getProperty("DisplayCard"));
                this.saveTextVo.setContent(new String(p.getProperty("Content").getBytes("ISO-8859-1"), "GBK"));
                this.saveTextVo.setEmployeeInfo(p.getProperty("EmployeeInfo"));
                this.saveTextVo.setEmployeePicture(p.getProperty("EmployeePicture"));
                this.saveTextVo.setEmployeeStart(p.getProperty("EmployeeStart"));
                this.saveTextVo.setKey1(p.getProperty("Key1"));
                this.saveTextVo.setKey2(p.getProperty("Key2"));
                this.saveTextVo.setKey3(p.getProperty("Key3"));
                this.saveTextVo.setKey4(p.getProperty("Key4"));
                this.saveTextVo.setKey5(p.getProperty("Key5"));
                this.saveTextVo.setKey6(p.getProperty("Key6"));
                this.saveTextVo.setPicture(p.getProperty("GuangGao"));
                this.saveTextVo.setSpeed(p.getProperty("Speed"));
                this.saveTextVo.setStarBackColor(p.getProperty("StarBackColor"));
                this.saveTextVo.setStarColor(p.getProperty("StarColor"));
                this.saveTextVo.setTextBackColor(p.getProperty("TextBackColor"));
                this.saveTextVo.setTextColor(p.getProperty("TextColor"));
                in.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                try {
                    in.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                break Label_0445;
            }
            finally {
                try {
                    in.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                in.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        request.setAttribute("test", (Object)this.saveTextVo);
        return "success";
    }

    public String baseSave() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        FileWriter fw = null;
        final StringBuffer sb = new StringBuffer();
        Label_0807: {
            try {
                final String filepath = String.valueOf(request.getRealPath("/")) + "appraise.ini";
                sb.append("[text]\r\n\r\n");
                sb.append("Speed=" + this.saveTextVo.getSpeed() + "\r\n");
                sb.append("Content=" + this.saveTextVo.getContent() + "\r\n\r\n[key]\r\n");
                sb.append("Key1=" + this.saveTextVo.getKey1() + "\r\n");
                sb.append("Key2=" + this.saveTextVo.getKey2() + "\r\n");
                sb.append("Key3=" + this.saveTextVo.getKey3() + "\r\n");
                sb.append("Key4=" + this.saveTextVo.getKey4() + "\r\n");
                sb.append("Key5=" + this.saveTextVo.getKey5() + "\r\n");
                sb.append("Key6=" + this.saveTextVo.getKey6() + "\r\n\r\n[Employee]\r\n");
                sb.append("EmployeePicture=" + this.saveTextVo.getEmployeePicture() + "\r\n");
                sb.append("EmployeeInfo=" + this.saveTextVo.getEmployeeInfo() + "\r\n");
                sb.append("EmployeeStart=" + this.saveTextVo.getEmployeeStart() + "\r\n\r\n[Time]\r\n");
                sb.append("Appraise=" + this.saveTextVo.getArrpaise() + "\r\n");
                sb.append("DisplayCard=" + this.saveTextVo.getCard() + "\r\n");
                sb.append("GuangGao=" + this.saveTextVo.getPicture() + "\r\n\r\n[Color]\r\n");
                sb.append("TextColor=" + this.saveTextVo.getTextColor() + "\r\n");
                sb.append("TextBackColor=" + this.saveTextVo.getTextBackColor() + "\r\n");
                sb.append("ButtonBorderColor=" + this.saveTextVo.getButtonBorderColor() + "\r\n");
                sb.append("ButtonBackColor=" + this.saveTextVo.getButtonBackColor() + "\r\n");
                sb.append("StarColor=" + this.saveTextVo.getStarColor() + "\r\n");
                sb.append("StarBackColor=" + this.saveTextVo.getStarBackColor());
                fw = new FileWriter(filepath);
                fw.write(sb.toString());
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                try {
                    fw.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                break Label_0807;
            }
            finally {
                try {
                    fw.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            try {
                fw.close();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        request.setAttribute("test", (Object)this.saveTextVo);
        return "success";
    }

    public String baseSelectTemp() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String index = request.getParameter("index");
        final String k = request.getParameter("k");
        final String playListId = request.getParameter("playListId");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        sundynSet.select(Integer.valueOf(index), k);
        final List tempList = sundynSet.getL_m7Temp();
        Map map = (Map)tempList.get(Integer.parseInt(index));
        final String tempUrl = map.get("tempUrl").toString();
        if (playListId != null) {
            final String basepath = ServletActionContext.getServletContext().getRealPath("/");
            final String m7apppath = String.valueOf(basepath) + "m7app" + File.separator + playListId;
            final String m7binpath = String.valueOf(basepath) + "update" + File.separator + playListId;
            final String sourcepath = String.valueOf(basepath) + "m7temp" + File.separator + tempUrl + File.separator + k;
            MyFile.copyDirectory(sourcepath, m7apppath);
            final Config c = new Config(String.valueOf(m7apppath) + File.separator + "CONFIG.XML");
            c.versionIncrease();
            c.saveAs(String.valueOf(m7apppath) + File.separator + "CONFIG.XML");
            c.saveAs(String.valueOf(m7binpath) + File.separator + "CONFIG.XML");
            final String version = c.getVersion();
            final Update upadd = new Update(String.valueOf(sourcepath) + File.separator);
            upadd.createUpdateFile(String.valueOf(m7binpath) + File.separator + "M7Update" + version + ".bin");
            final Update up = new Update(String.valueOf(m7apppath) + File.separator);
            up.createUpdateFile(String.valueOf(m7binpath) + File.separator + "M7Update.bin");
        }
        final Map msg = new HashMap();
        msg.put("k", k);
        msg.put("index", index);
        msg.put("tempUrl", tempUrl);
        final JSONObject json = JSONObject.fromObject((Object)msg);
        request.setAttribute("json", (Object)json);
        return "success";
    }

    public String baseSetSave() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final Map sys = new HashMap();
        sys.put("camera", request.getParameter("camera"));
        sys.put("k7", request.getParameter("k7"));
        sys.put("star", request.getParameter("star"));
        sys.put("bind", request.getParameter("bind"));
        sys.put("guide", request.getParameter("guide"));
        sys.put("tipLanguage", request.getParameter("tipLanguage"));
        final Map content = new HashMap();
        content.put("title", request.getParameter("title"));
        content.put("logo", request.getParameter("logo"));
        content.put("buttom", request.getParameter("buttom"));
        content.put("requestAddress", request.getParameter("requestAddress"));
        content.put("standard", request.getParameter("standard"));
        final Map work2 = new HashMap();
        work2.put("sam", request.getParameter("sam"));
        work2.put("eam", request.getParameter("eam"));
        work2.put("spm", request.getParameter("spm"));
        work2.put("epm", request.getParameter("epm"));
        final Map work3 = new HashMap();
        work3.put("start", request.getParameter("start"));
        work3.put("end", request.getParameter("end"));
        final String prates = request.getParameter("prates");
        final String pgrades = request.getParameter("pgrades");
        final String pstars = request.getParameter("pstars");
        final String[] l_prate = prates.split(",");
        final String[] l_pgrade = pgrades.split(",");
        final String[] l_pstar = pstars.split(",");
        final List ls = new ArrayList();
        for (int i = 0; i < l_prate.length; ++i) {
            final Map star = new HashMap();
            star.put("star10", l_pgrade[i]);
            star.put("star100", l_prate[i]);
            star.put("star", l_pstar[i]);
            ls.add(star);
        }
        final Map<String, String> employeeInfoSet = new HashMap<String, String>();
        employeeInfoSet.put("employeeName", request.getParameter("est1"));
        employeeInfoSet.put("job_desc", request.getParameter("est2"));
        employeeInfoSet.put("employeeJobNum", request.getParameter("est3"));
        employeeInfoSet.put("employeeCardNum", request.getParameter("est4"));
        employeeInfoSet.put("star", request.getParameter("est5"));
        employeeInfoSet.put("phone", request.getParameter("est6"));
        employeeInfoSet.put("windowName", request.getParameter("est7"));
        employeeInfoSet.put("deptname", request.getParameter("est8"));
        employeeInfoSet.put("unitName", request.getParameter("est9"));
        final SundynSet sundynSet = SundynSet.getInstance(path);
        sundynSet.update(sys, content, work2, work3, ls, employeeInfoSet);
        JavaXML.downloadEmployeeInfoSet(employeeInfoSet);
        request.setAttribute("msg", (Object)this.getText("sundyn.saveSuccess"));
        Reg.reset();
        return "success";
    }

    public String baseSundynSet() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        request.setAttribute("system", (Object)sundynSet.getM_system());
        request.setAttribute("content", (Object)sundynSet.getM_content());
        request.setAttribute("work2", (Object)sundynSet.getM_work2());
        request.setAttribute("work4", (Object)sundynSet.getM_work4());
        request.setAttribute("m7temp", (Object)sundynSet.getL_m7Temp());
        request.setAttribute("stars", (Object)sundynSet.getL_star());
        final Map<String, String> m = sundynSet.getM_employee();
        request.setAttribute("employeeInfoSet", (Object)sundynSet.getM_employee());
        return "success";
    }

    public String baseTime() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String filepath = ServletActionContext.getServletContext().getRealPath("/update");
        filepath = String.valueOf(filepath) + File.separator + "time.ini";
        try {
            final InputStream in = new FileInputStream(filepath);
            final Properties p = new Properties();
            p.load(in);
            final String sam = p.getProperty("sam", "08:30");
            final String eam = p.getProperty("eam", "12:00");
            final String spm = p.getProperty("spm", "14:30");
            final String epm = p.getProperty("epm", "17:00");
            request.setAttribute("sam", (Object)sam);
            request.setAttribute("eam", (Object)eam);
            request.setAttribute("spm", (Object)spm);
            request.setAttribute("epm", (Object)epm);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String baseTimeSave() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String sam = request.getParameter("sam");
        final String eam = request.getParameter("eam");
        final String spm = request.getParameter("spm");
        final String epm = request.getParameter("epm");
        String filepath = ServletActionContext.getServletContext().getRealPath("/update");
        filepath = String.valueOf(filepath) + File.separator + "time.ini";
        try {
            final StringBuffer sb = new StringBuffer();
            sb.append("[time]\r\n\r\n");
            sb.append("sam=" + sam + "\r\n");
            sb.append("eam=" + eam + "\r\n");
            sb.append("spm=" + spm + "\r\n");
            sb.append("epm=" + epm + "\r\n");
            final FileWriter fw = new FileWriter(filepath);
            fw.write(sb.toString());
            fw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("sam", (Object)sam);
        request.setAttribute("eam", (Object)eam);
        request.setAttribute("spm", (Object)spm);
        request.setAttribute("epm", (Object)epm);
        return "success";
    }

    public String baseUploadPic() {
        return "success";
    }

    public String baseUploadPicDeal() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String imgName = request.getParameter("imgName");
        String impPath = "";
        if (this.img != null) {
            final String dstPath = ServletActionContext.getServletContext().getRealPath("\\");
            final long curTime = System.currentTimeMillis();
            final String filename = String.valueOf(curTime) + Math.round(Math.random() * 100.0) + this.getExtFileName(imgName);
            final File dst = new File(String.valueOf(dstPath) + "upload\\" + filename);
            copy(this.img, dst);
            impPath = "upload/" + filename;
        }
        else {
            impPath = "";
        }
        request.setAttribute("imgPath", (Object)impPath);
        return "success";
    }

    public String baseUploadTemp() {
        return "success";
    }

    public String baseUploadTempDeal() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String imgName = request.getParameter("imgName");
        String impPath = "";
        Map info = new HashMap();
        if (this.img != null) {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            info = sundynSet.add(this.img);
        }
        else {
            impPath = "";
        }
        final JSONObject json = JSONObject.fromObject((Object)info);
        request.setAttribute("json", (Object)json);
        return "success";
    }

    public String getRecorderTime() throws JDOMException, IOException {
        final HttpServletResponse response = ServletActionContext.getResponse();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        response.getWriter().print(String.valueOf(sundynSet.getM_work2().get("sam")) + "||" + sundynSet.getM_work2().get("epm"));
        response.getWriter().flush();
        return "none";
    }

    public String baseGuide() throws JDOMException, IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        final String operate = request.getParameter("operate");
        final SundynSet sundynSet = SundynSet.getInstance(path);
        String guide = "";
        if (operate != null) {
            if (operate.equals("get")) {
                guide = sundynSet.getM_system().get("guide").toString();
            }
            else if (operate.equals("false")) {
                final Map system = sundynSet.getM_system();
                final Map m = (Map)((HashMap)system).clone();
                m.put("guide", "false");
                sundynSet.update(m, null, null, null);
            }
            else {
                if (operate.equals("true")) {
                    final Map system = sundynSet.getM_system();
                    final Map m = (Map)((HashMap)system).clone();
                    m.put("guide", "true");
                    sundynSet.update(m, null, null, null);
                    return "guide";
                }
                if (operate.equals("start")) {
                    final Map system = sundynSet.getM_system();
                    guide = sundynSet.getM_system().get("guide").toString();
                    if (guide.equals("true")) {
                        return "guide";
                    }
                    return "true";
                }
            }
        }
        else {
            guide = sundynSet.getM_system().get("guide").toString();
        }
        request.setAttribute("msg", (Object)guide);
        return "success";
    }

    public String baseHelp() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String help = request.getParameter("help");
        if (help == null) {
            help = "1";
        }
        if (this.getText("sundyn.language").equals("en")) {
            help = String.valueOf(help) + "_en";
        }
        request.setAttribute("help", (Object)"help");
        return help;
    }

    public String baseSession() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String sessionName = request.getParameter("sessionName");
        final String value = (String)request.getSession().getAttribute(sessionName);
        request.setAttribute("msg", (Object)value);
        return "success";
    }

    public String employeeInfoSetDownload() {
        return "downloadOk";
    }

    private String getExtFileName(final String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public String getFileName() {
        return this.fileName;
    }

    public File getImg() {
        return this.img;
    }

    public PlayListService getPlayListService() {
        return this.playListService;
    }

    public SaveTextVo getSaveTextVo() {
        return this.saveTextVo;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setImg(final File img) {
        this.img = img;
    }

    public void setPlayListService(final PlayListService playListService) {
        this.playListService = playListService;
    }

    public void setSaveTextVo(final SaveTextVo saveTextVo) {
        this.saveTextVo = saveTextVo;
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

    public EmployeeService getEmployeeService() {
        return this.employeeService;
    }

    public void setEmployeeService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(final Pager pager) {
        this.pager = pager;
    }

    public InputStream getXml() {
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        final String filename = "employeeInfoSet.xml";
        final String url = String.valueOf(file) + filename;
        return ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/source/" + filename);
    }

    public void setXml(final InputStream xml) {
        this.xml = xml;
    }
}
