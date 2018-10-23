package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.entity.*;
import com.sundyn.service.*;
import lombok.Getter;
import lombok.Setter;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

public class QueueInteAction extends ActionSupport {
    @Resource
    private IInteLoginService inteLoginService;
    @Resource
    private IInteUnloginService inteunLoginService;
    @Resource
    private IInteLogService logService;
    @Resource
    private IInteTicketService inteAddticketService;
    @Resource
    private IQueueDetailService queueDetailService;
    @Resource
    private ISysBlacklistService sysBlacklistService;
    @Resource
    private ISysProxyService proxyService;
    @Resource
    private ISysQueuehallService hallService;
    @Resource
    private ISysQueueserialService serialService;
    @Resource
    private ISysQueuecounterService counterService;
    @Resource
    private EmployeeService employeeService;
    @Getter @Setter
    private String DATA;
    public String gethalldata(){
        List<SysQueuehall> halllist = hallService.selectList(null);
        JSONArray ja = new JSONArray();
        for (int i=0; i< halllist.size(); i++){
            JSONObject o = new JSONObject();
            o.put("hallno", halllist.get(i).getHallno());

            List<SysQueueserial> serialList = serialService.selectList(new EntityWrapper<SysQueueserial>().where("hallid={0}", halllist.get(i).getId()));
            List<String> l1 = new ArrayList<>();
            for (int j=0; j< serialList.size(); j++){
                l1.add(serialList.get(j).getBizid());
            }
            o.put("bizlist", l1);

            List<SysQueuecounter> counterList = counterService.selectList(new EntityWrapper<SysQueuecounter>().where("hallid={0}", halllist.get(i).getId()));
            l1 = new ArrayList<>();
            for (int j=0; j< counterList.size(); j++){
                l1.add(counterList.get(j).getCounterno());
            }
            o.put("counterlist", l1);

            List eList = employeeService.findEmployeeByDeptId(halllist.get(i).getDeptid());
            l1 = new ArrayList<>();
            for (int j=0; j< eList.size(); j++){
                l1.add(((Map)eList.get(j)).get("cardNum").toString());
            }
            o.put("elist", l1);

            ja.add(o);
        }
        DATA = ja.toString();
        return Action.SUCCESS;
    }
    /*
    窗口登录通知信息
     */
    public String sysqueueinterface() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        Object logid =request.getAttribute("id");

        InteLog logentity = logService.selectOne(new EntityWrapper<InteLog>().where("id={0}", logid));
        if (null!=logentity){
            String postData = logentity.getIntedata();
            JSONObject jo = JSONObject.fromObject(postData);

            System.out.println("----------------" + jo.get("service"));
            String service = jo.getString("service");
            boolean rst = false;
            HashMap m = null;

            switch (service){
                case "login":
                    m = login(jo);
                    break;
                case "unlogin":
                    m = unlogin(jo);
                    break;
                case "pause":
                    m = pause(jo);
                    break;
                case "cancelpause":
                    m = cancelpause(jo);
                    break;
                case "addticketrecord":
                    m = addTicketRecord(jo);
                    break;
                case "call":
                    m = Call(jo);
                    break;
                case "service":
                    m = service(jo);
                    break;
                case "apprise":
                    m = apprise(jo);
                    break;
                case "getisinblacklist":
                    m = getisinblacklist(jo);
                    break;
                case "getisinproxy":
                    m = getisinproxy(jo);
                    break;
            }
            jsonData.clear();
            String errMsg = "未知错误";
            rst = m!=null && (boolean)m.get("succ");
            if (m!=null)
                errMsg = m.get("msg").toString();
            if (rst){
                logService.updateForSet("status=2,note='数据转储成功'", new EntityWrapper<InteLog>().where("id={0}", logid));
                jsonData.put("code", "1");
                jsonData.put("msg", errMsg);
            }
            else{
                jsonData.put("code", "0");
                jsonData.put("msg", errMsg);
            }
        }
        else{
            jsonData.clear();
            jsonData.put("code", "0");
            jsonData.put("msg", "失败，没有找到token参数");
        }
        return Action.SUCCESS;
    }

    /*
    检查黑名单
     */
    private HashMap getisinblacklist(JSONObject jo) {
        String cardid = jo.getString("cardid");
        HashMap m = new HashMap<String, Object>();
        if (cardid==null || cardid.equals(""))
        {
            m.put("succ", false);
            m.put("msg", "处理失败：cardid参数为空");
        }
        else{
            boolean b = sysBlacklistService.selectCount(new EntityWrapper<SysBlacklist>().where("idcard={0}", cardid))>0;
            m.put("succ", b);
            m.put("msg", b?"是黑名单":"不是黑名单");
        }
        return m;
    }

    /*
    檢查是否代理人
     */
    private HashMap getisinproxy(JSONObject jo) {
        String cardid = jo.getString("cardid");
        HashMap m = new HashMap<String, Object>();
        if (cardid==null || cardid.equals(""))
        {
            m.put("succ", false);
            m.put("msg", "处理失败：cardid参数为空");
        }
        else{
            boolean b = proxyService.selectCount(new EntityWrapper<SysProxy>().where("idcard={0}", cardid))>0;
            m.put("succ", b);
            m.put("msg", b?"是代理人":"不是代理人");
        }
        return m;
    }
    /*
    取号
     */
    private HashMap addTicketRecord(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();

        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteTicket entity = (InteTicket)JSONObject.toBean(jo, InteTicket.class);
            if (null!=entity){
                System.out.println(entity.toString());
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "addTicketRecord");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueTicket(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
            else{
                m.put("succ", true);
                m.put("msg", "处理失败：参数转换失败");
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg", "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    /*
   叫号
    */
    private HashMap Call(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteCall entity = (InteCall)JSONObject.toBean(jo, InteCall.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "Call");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueTicket(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    /*
  办理
   */
    private HashMap service(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteService entity = (InteService)JSONObject.toBean(jo, InteService.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "service");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueTicket(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    /*
 评价
  */
    private HashMap apprise(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteAppries entity = (InteAppries)JSONObject.toBean(jo, InteAppries.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "apprise");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueTicket(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    Map<String,Object> jsonData = new HashMap<String,Object>();

    private HashMap login(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteLogin entity = (InteLogin)JSONObject.toBean(jo, InteLogin.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "login");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueEmployee(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    private HashMap unlogin(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteUnlogin entity = (InteUnlogin)JSONObject.toBean(jo, InteUnlogin.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "unlogin");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueEmployee(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    private HashMap pause(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            IntePause entity = (IntePause)JSONObject.toBean(jo, IntePause.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "pause");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueEmployee(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    private HashMap cancelpause(JSONObject jo) {
        HashMap m = new HashMap<String, Object>();
        try {
            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss"}));
            InteCancelpause entity = (InteCancelpause)JSONObject.toBean(jo, InteCancelpause.class);
            if (null!=entity){
                entity.setCtime(new Date());
                boolean b = entity.insert();

                Map map = new HashMap();
                map.put("step", "cancelpause");
                map.put("id", entity.getId());
                map.put("outstr","");
                queueDetailService.ProcQueueEmployee(map);
                Object outstr = map.get("outstr");
                if (outstr == null || outstr.equals("") || outstr.equals("成功")){
                    m.put("succ", true);
                    m.put("msg", "处理成功");
                }
                else{
                    m.put("succ", false);
                    m.put("msg", "处理失败：" + outstr);
                }
                return m;
            }
        }
        catch (Exception e){
            m.put("succ", false);
            m.put("msg",  "处理失败："+e.getCause().getMessage());
            e.printStackTrace();
        }
        return m;
    }

    public String inte_err() throws IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        StringBuilder sb  = new StringBuilder();
        Scanner scanner = new Scanner(request.getInputStream(),"utf-8");
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }
        System.out.println("inte_err:err.."+sb.toString());
        String token = request.getParameter("token");
        System.out.println("token:err.."+token);

        jsonData.clear();
        jsonData.put("code", "0");
        jsonData.put("msg", "失败");
        return Action.SUCCESS;
    }
}
