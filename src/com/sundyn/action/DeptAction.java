package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.entity.*;
import com.sundyn.utils.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;
import java.text.*;
import com.sundyn.vo.*;
import net.sf.json.*;
import com.sundyn.util.*;
import java.sql.*;

public class DeptAction extends MainAction
{
    private String bind;
    private BusinessService businessService;
    private Map dept;
    private String dept_camera_url;
    private Integer deptId;
    private String deptName;
    private String deptPause;
    private DeptService deptService;
    private List list;
    private PlayListService playListService;
    private PowerService powerService;
    private String reMark;
    private String deptLogoPic;
    private List<Province> provinces;
    private String citysString;
    private Province province;
    private int cityid;
    private List<City> citys;
    private CitysUtils cityutils;
    private WeburlService weburlService;
    private List<WeburlVo> haveList;
    private List<WeburlVo> noList;

    /*public DeptAction(){
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<DeptAction");
        super.setPowerService(powerService);
        super.setDeptService(this.deptService);
    }*/
    public String getDeptLogoPic() {
        return this.deptLogoPic;
    }
    
    public void setDeptLogoPic(final String deptLogoPic) {
        this.deptLogoPic = deptLogoPic;
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
        System.out.println("useVideo-msg=" + msg);
        request.setAttribute("json", (Object)msg);
        return "success";
    }
    
    public String deptAddChildItem() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String cityId = request.getParameter("cityid");
        String provinceId = request.getParameter("provinceid");
        final Integer client_type = Integer.valueOf(request.getParameter("client_type"));
        final String product_Type = request.getParameter("product_type");
        System.out.println("product_type=" + product_Type);
        final Integer deptType = Integer.valueOf(request.getParameter("deptType"));
        String dept_businessId = request.getParameter("dept_businessId");
        final String deptPause = request.getParameter("deptPause");
        final String deptPic = request.getParameter("deptPic");
        final String deptLogoPic = request.getParameter("deptLogoPic");
        final String useVideo = request.getParameter("useVideo");
        final String notice = request.getParameter("notice");
        if (dept_businessId == null || dept_businessId.equals("")) {
            dept_businessId = "-1";
        }
        String dept_playListId = request.getParameter("dept_playListId");
        if (dept_playListId == null || dept_playListId.equals("")) {
            dept_playListId = "-1";
        }
        if (this.deptId == null) {
            this.deptId = -1;
        }
        final Map temp = this.deptService.findDeptById(this.deptId);
        final Date cDate = new Date();
        final String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cDate);
        final Integer level = Integer.valueOf(temp.get("lenvel").toString()) + 1;
        final DeptVo deptVo = new DeptVo();
        deptVo.setName(Mysql.mysql(this.deptName));
        deptVo.setChild(0);
        deptVo.setFatherId(this.deptId);
        deptVo.setDeptLogoPic(deptLogoPic);
        deptVo.setRemark(this.reMark = this.reMark.toUpperCase());
        deptVo.setDeptPause(deptPause);
        deptVo.setDeptPic(deptPic);
        deptVo.setLenvel(level);
        deptVo.setClient_type(client_type);
        deptVo.setProduct_Type(product_Type);
        deptVo.setDeptType(deptType);
        deptVo.setDept_camera_url(this.dept_camera_url);
        deptVo.setDept_businessId(Integer.valueOf(dept_businessId));
        deptVo.setDept_playListId(Integer.valueOf(dept_playListId));
        deptVo.setUseVideo(useVideo);
        deptVo.setNotice(notice);
        deptVo.setExt5(dt);
        if (cityId == null || cityId.equals("")) {
            cityId = "0";
        }
        if (provinceId == null || provinceId.equals("")) {
            provinceId = "0";
        }
        deptVo.setCityid(Integer.parseInt(cityId));
        deptVo.setProvinceid(Integer.parseInt(provinceId));
        this.deptService.addDept(deptVo, this.deptId);
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.list = this.deptService.findChildALL(deptIdGroup);
        if (this.list != null && this.list.size() > 0) {
            final Map dept = (Map) this.list.get(0);
            dept.put("fatherId", -1);
        }
        final JSONArray json = JSONArray.fromObject((Object)this.list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String deptAddDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();

        final String deptType = request.getParameter("deptType");
        this.list = this.businessService.find(true);
        final List playListList = this.playListService.playListQuery();
        String remark = "";
        if (deptType.equals("1")) {
            remark = new StringBuilder().append(this.deptService.getMaxId() + 1).toString();
        }
        request.setAttribute("deptType", (Object)deptType);
        request.setAttribute("playListList", (Object)playListList);
        request.setAttribute("remark", (Object)remark);
        //this.provinces = this.cityutils.getProvinces();
        this.provinces = this.cityutils.getProvincesOnly();
        this.province = this.cityutils.getProvinceDef();
        this.citys = this.province.getCitys();
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }
    
    public String deptDel() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.deptService.deleteDept(this.deptId);
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.list = this.deptService.findChildALL(deptIdGroup);
        if (this.list != null && this.list.size() > 0) {
            final Map dept = (Map) this.list.get(0);
            dept.put("fatherId", -1);
        }
        final JSONArray json = JSONArray.fromObject((Object)this.list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String deptEditDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.dept = this.deptService.findDeptById(this.deptId);
        this.list = this.businessService.find(true);
/*

        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        final String deptIds = this.deptService.findChildALLStr123(deptIdGroup);//low
*/

        final int pid = (int) this.dept.get("provinceid");
        this.cityid = (int) this.dept.get("cityid");
        final int id = (int) this.dept.get("id");
        //this.provinces = this.cityutils.getProvinces();
        this.provinces = this.cityutils.getProvincesOnly();
        this.province = this.cityutils.getProvinceWithCitysById(pid);
        this.citys = this.province.getCitys();
        final List playListList = this.playListService.playListQuery();
        request.setAttribute("playListList", (Object)playListList);
        request.setAttribute("deptProvince", this.dept.get("pro"));
        if (this.getCamera().equals("true")) {
            return "camera";
        }
        return "success";
    }
    
    public String deptEditItem() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String cityId = request.getParameter("cityid");
        final String provinceId = request.getParameter("provinceid");
        final Map m = request.getParameterMap();
        final String client_type = request.getParameter("client_type");
        final String product_type = request.getParameter("product_type");
        String dept_businessId = request.getParameter("dept_businessId");
        String dept_playListId = request.getParameter("dept_playListId");
        final String deptPause = request.getParameter("deptPause");
        final String deptPic = request.getParameter("deptPic");
        final String deptLogoPic = request.getParameter("deptLogoPic");
        final String useVideo = request.getParameter("useVideo");
        final String notice = request.getParameter("notice");
        System.out.println("deptEditItem-notice=" + notice);
        System.out.println("deptEditItem-deptId=" + this.deptId);
        final String deptIds = this.deptService.findChildALLStr123(this.deptId.toString());
        this.deptService.updateUseVideo(deptIds, useVideo);
        if (!this.deptService.isLeafage(this.deptId)) {
            final String sonDeptIds = this.deptService.findChildALLStr123(this.deptId.toString());
            System.out.println("\u4fee\u6539\u6240\u6709\u5b50\u5b59\u8282\u70b9\uff1a" + sonDeptIds);
            this.deptService.updateDeptNotice(sonDeptIds, notice);
        }
        try {
            final Map dept = this.deptService.findDeptById(this.deptId);
            String olddept_playListId = "";
            if (dept.get("dept_playListId") != null) {
                olddept_playListId = dept.get("dept_playListId").toString();
            }
            if (dept_playListId != null && !dept_playListId.equals("") && !dept_playListId.equals(olddept_playListId)) {
                final String ids = this.deptService.findChildALLStr123(new StringBuilder().append(this.deptId).toString());
                this.deptService.updateDeptPlayList(ids, dept_playListId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (dept_businessId.equals("") || dept_businessId == null) {
            dept_businessId = "-1";
        }
        if (dept_playListId.equals("") || dept_playListId == null) {
            dept_playListId = "-1";
        }
        final DeptVo deptVo = new DeptVo();
        deptVo.setId(this.deptId);
        deptVo.setName(Mysql.mysql(this.deptName));
        deptVo.setRemark(this.reMark = this.reMark.toUpperCase());
        deptVo.setDeptPic(deptPic);
        deptVo.setDeptPause(deptPause);
        deptVo.setClient_type(Integer.valueOf(client_type));
        deptVo.setProduct_Type(product_type);
        deptVo.setDept_camera_url(this.dept_camera_url);
        deptVo.setDept_businessId(Integer.valueOf(dept_businessId));
        deptVo.setDept_playListId(Integer.valueOf(dept_playListId));
        deptVo.setDeptLogoPic(deptLogoPic);
        deptVo.setUseVideo(useVideo);
        deptVo.setNotice(notice);
        deptVo.setCityid(Integer.parseInt(cityId));
        deptVo.setProvinceid(Integer.parseInt(provinceId));
        this.deptService.updateDept(deptVo);
        this.deptService.updateCitys(this.deptId, deptVo.getCityid(), deptVo.getProvinceid());
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.list = this.deptService.findChildALL(deptIdGroup);
        if (this.list != null && this.list.size() > 0) {
            final Map dept2 = (Map) this.list.get(0);
            dept2.put("fatherId", -1);
        }
        final DeptVo d = new DeptVo();
        d.setCityid(1);
        System.out.println(d.getCityid());
        final JSONArray json = JSONArray.fromObject((Object)this.list);
        request.setAttribute("json", (Object)json);
        return "success";
    }
    
    public String deptExistMac() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String msg = "";
        final String mac = request.getParameter("mac");
        final Map dept = this.deptService.findByMac(mac);
        if (dept != null) {
            msg = this.getText("sundyn.exist", new String[] { "mac" });
        }
        else {
            msg = "";
        }
        request.setAttribute("json", (Object)msg);
        return "success";
    }
    
    public String deptReg() throws Exception {
        this.dept = this.deptService.findDeptById(this.deptId);
        final Integer deptType = Integer.valueOf(this.dept.get("deptType").toString());
        if (deptType == 0) {
            return "reg0";
        }
        if (deptType == 1) {
            return "reg1";
        }
        if (deptType == 2) {
            return "reg2";
        }
        return "reg2";
    }
    
    public String upDateFlag() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        String msg = "";
        final String mac = request.getParameter("mac");
        final Map dept = this.deptService.findByMac(mac);
        if (dept == null) {
            msg = String.valueOf(mac) + "\u65e0\u6548";
        }
        else {
            final int formNum = Integer.parseInt(dept.get("name").toString());
            if (!this.deptService.updateFormList(formNum)) {
                this.deptService.insertFormList(formNum);
            }
            msg = "ok";
        }
        request.setAttribute("msg", (Object)msg);
        return "success";
    }
    
    public String deptView() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        final String deptIdGroup = power.get("deptIdGroup").toString();
        this.list = this.deptService.findChildALL(deptIdGroup);
        if (this.list != null && this.list.size() > 0) {
        	for(final Object m : list){
        		Map dept = (Map) m;
        		System.out.println(dept.get("name"));
        	}
            final Map dept = (Map) this.list.get(0);
            if (dept.get("fatherId").toString() != "-1") {
                dept.put("fatherId", -1);
                this.list.add(dept);
            }
        }
        return "success";
    }
    
    public String getBind() {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.bind = sundynSet.getM_system().get("bind").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return this.bind;
    }
    
    public String getButtom() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        String tempStr = "";
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            tempStr = sundynSet.getM_content().get("buttom").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("msg", (Object)tempStr);
        return "error";
    }
    
    public BusinessService getBusinessService() {
        return this.businessService;
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
    
    public String toBindWeburl() {
        this.haveList = (List<WeburlVo>)this.weburlService.findWeburlChoose(this.deptId);
        this.noList = (List<WeburlVo>)this.weburlService.findWeburlNoChoose(this.deptId);
        return "ok";
    }
    
    public String deptWeburlAdd() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String weburlIds = request.getParameter("retVal");
        this.weburlService.updateWeburlChoose(weburlIds, this.deptId);
        return "ok";
    }
    
    public Map getDept() {
        return this.dept;
    }
    
    public String getDept_camera_url() {
        return this.dept_camera_url;
    }
    
    public Integer getDeptId() {
        return this.deptId;
    }
    
    public String getDeptName() {
        return this.deptName;
    }
    
    public String getDeptPause() {
        return this.deptPause;
    }
    
    public DeptService getDeptService() {
        return this.deptService;
    }
    
    public List getList() {
        return this.list;
    }
    
    public String getPauseByMac() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String mac = request.getParameter("mac");
        this.dept = this.deptService.findByMac(mac);
        if (this.dept == null) {
            request.setAttribute("msg", (Object)"error");
            return "error";
        }
        return "success";
    }
    
    public PlayListService getPlayListService() {
        return this.playListService;
    }
    
    public PowerService getPowerService() {
        return this.powerService;
    }
    
    public String getReMark() {
        return this.reMark;
    }
    
    public void setBind(final String bind) {
        final String path = ServletActionContext.getServletContext().getRealPath("/");
        try {
            final SundynSet sundynSet = SundynSet.getInstance(path);
            this.bind = sundynSet.getM_system().get("bind").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setBusinessService(final BusinessService businessService) {
        this.businessService = businessService;
    }
    
    public void setDept(final Map dept) {
        this.dept = dept;
    }
    
    public void setDept_camera_url(final String dept_camera_url) {
        this.dept_camera_url = dept_camera_url;
    }
    
    public void setDeptId(final Integer deptId) {
        this.deptId = deptId;
    }
    
    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }
    
    public void setDeptPause(final String deptPause) {
        this.deptPause = deptPause;
    }
    
    public void setDeptService(final DeptService deptService) {
        this.deptService = deptService;
    }
    
    public void setList(final List list) {
        this.list = list;
    }
    
    public void setPlayListService(final PlayListService playListService) {
        this.playListService = playListService;
    }
    
    public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
    
    public void setReMark(final String reMark) {
        this.reMark = reMark;
    }
    
    public List<Province> getProvinces() {
        return this.provinces;
    }
    
    public void setProvinces(final List<Province> provinces) {
        this.provinces = provinces;
    }
    
    public String getCityss() throws SQLException {
        final List<City> c = this.cityutils.getCitys(this.province.getId());
        final JSONArray obj = JSONArray.fromObject((Object)c);
        this.citysString = obj.toString();
        return "success";
    }
    
    public String getCitysString() {
        return this.citysString;
    }
    
    public void setCitysString(final String citysString) {
        this.citysString = citysString;
    }
    
    public Province getProvince() {
        return this.province;
    }
    
    public void setProvince(final Province province) {
        this.province = province;
    }
    
    public int getCityid() {
        return this.cityid;
    }
    
    public void setCityid(final int cityid) {
        this.cityid = cityid;
    }
    
    public void setCitys(final List<City> citys) {
        this.citys = citys;
    }
    
    public List<City> getCitys() {
        return this.citys;
    }
    
    public CitysUtils getCityutils() {
        return this.cityutils;
    }
    
    public void setCityutils(final CitysUtils cityutils) {
        this.cityutils = cityutils;
    }
    
    public List<WeburlVo> getHaveList() {
        return this.haveList;
    }
    
    public void setHaveList(final List<WeburlVo> haveList) {
        this.haveList = haveList;
    }
    
    public List<WeburlVo> getNoList() {
        return this.noList;
    }
    
    public void setNoList(final List<WeburlVo> noList) {
        this.noList = noList;
    }
    
    public void setWeburlService(final WeburlService weburlService) {
        this.weburlService = weburlService;
    }
}
