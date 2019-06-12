package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.opensymphony.xwork2.Action;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.ISysDictinfoService;
import com.sundyn.service.KeyTypeService;
import com.sundyn.utils.NumberUtils;
import com.sundyn.utils.StringUtils;
import com.xuan.xutils.utils.DateUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysDictAction extends MainAction {
    @Resource
    private KeyTypeService keyTypeService;
    @Resource
    private ISysDictinfoService dictinfoService;
    public Map<String, Object> getJsonData() {
        return jsonData;
    }

    public void setJsonData(Map<String, Object> jsonData) {
        this.jsonData = jsonData;
    }

    Map<String,Object> jsonData = new HashMap<String,Object>();
    /*
    字典配置
     */
    public String sysDict() {
        try {
            List<SysDictinfo> dictinfos_config = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup = 'config' and isEnable=1"));
            request.setAttribute("dictinfos_config", dictinfos_config);

            List<SysDictinfo> dictinfos_inte = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup = 'inte' and isEnable=1"));
            request.setAttribute("dictinfos_inte", dictinfos_inte);

            List<SysDictinfo> dictinfos_sms = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup = 'sms' and isEnable=1"));
            request.setAttribute("dictinfos_sms", dictinfos_sms);

            List<SysDictinfo> dictinfos_security = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup = 'security_para' and isEnable=1"));
            request.setAttribute("dictinfos_security", dictinfos_security);

            List<SysDictinfo> dictinfos_audit = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("dictgroup = 'audit_para' and isEnable=1"));
            request.setAttribute("dictinfos_audit", dictinfos_audit);

            request.setAttribute("KeyTypeList", keyTypeService.findAllKeyInUse());
            request.setAttribute("unkeydef", "1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    /*
    字典值保存
     */
    public String dictSave() {
        JSONArray postdata = req.getJson("postdata");
        List<SysDictinfo> dictinfos = new ArrayList<>();
        if (postdata != null) {
            for (Object postdatum : postdata) {
                JSONObject jo = (JSONObject)postdatum;
                String dictgroup = jo.get("dictgroup").toString();
                String dictvalue = jo.get("dictvalue").toString();
                String dictname = jo.get("dictname").toString();
                String dictkeyname = jo.get("dictkeyname").toString();
                if (dictvalue.length()>200){
                    jsonData.clear();
                    jsonData.put("succ", false);
                    jsonData.put("msg", dictname + "长度不能超过200！");
                    return Action.SUCCESS;
                }
                if (dictgroup.equalsIgnoreCase("dictinfos_config")){
                    if (com.xuan.xutils.utils.StringUtils.isBlank(dictvalue)){
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "不能为空！");
                        return Action.SUCCESS;
                    }
                    if (!NumberUtils.isNumber2(dictvalue) && !dictkeyname.equalsIgnoreCase("enableAccessIP")
                            && !dictkeyname.equalsIgnoreCase("unkeydef")){
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "只能为数字且大于0！");
                        return Action.SUCCESS;
                    }
                    if (dictkeyname.equalsIgnoreCase("enableAccessIP")){
                        if (!com.sundyn.util.InetAddressUtils.isIPv4Address2(dictvalue)){
                            jsonData.clear();
                            jsonData.put("succ", false);
                            jsonData.put("msg", dictname + "IP地址格式不正确！");
                            return Action.SUCCESS;
                        }
                    }
                }
                if (dictgroup.equalsIgnoreCase("dictinfos_security")){
                    if (com.xuan.xutils.utils.StringUtils.isBlank(dictvalue)){
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "不能为空！");
                        return Action.SUCCESS;
                    }
                    if (!NumberUtils.isNumber2(dictvalue) && (dictkeyname.equalsIgnoreCase("audit_logintrytimes_ip")
                            || dictkeyname.equalsIgnoreCase("audit_logintrytimes_account")
                            || dictkeyname.equalsIgnoreCase("audit_locktime_account")
                            || dictkeyname.equalsIgnoreCase("audit_locktime_ip"))){
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "只能为数字且大于0！");
                        return Action.SUCCESS;
                    }
                }
                if (dictgroup.equalsIgnoreCase("dictinfos_audit")){
                    if (com.xuan.xutils.utils.StringUtils.isBlank(dictvalue)){
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "不能为空！");
                        return Action.SUCCESS;
                    }
                }
                if (dictkeyname.equalsIgnoreCase("audit_accessmaxtimes")){ //高频访问
                    String[] sarr = dictvalue.split(",");
                    if (sarr == null || sarr.length!=2)
                    {
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "格式不正确！");
                        return Action.SUCCESS;
                    }
                    for (String item : sarr) {
                        if (!NumberUtils.isNumber2(item)){
                            jsonData.clear();
                            jsonData.put("succ", false);
                            jsonData.put("msg", dictname + "格式不正确！");
                            return Action.SUCCESS;
                        }
                    }
                }
                if (dictkeyname.equalsIgnoreCase("audit_specialaccesstime")) {
                    String[] sarr = dictvalue.split(",");
                    if (sarr == null || sarr.length==0)
                    {
                        jsonData.clear();
                        jsonData.put("succ", false);
                        jsonData.put("msg", dictname + "格式不正确1！");
                        return Action.SUCCESS;
                    }
                    for (String item : sarr) {
                        String[] sarr2 = item.split("~");
                        if (sarr2 == null || sarr2.length!=2)
                        {
                            jsonData.clear();
                            jsonData.put("succ", false);
                            jsonData.put("msg", dictname + "格式不正确2！");
                            return Action.SUCCESS;
                        }
                        for (String item2 : sarr2) {
                            if (!DateUtils.isTime(item2)){
                                jsonData.clear();
                                jsonData.put("succ", false);
                                jsonData.put("msg", dictname + "格式不正确3！");
                                return Action.SUCCESS;
                            }
                        }
                    }
                }
                if (!NumberUtils.isNumber2(dictvalue) && dictkeyname.equalsIgnoreCase("audit_logsavetime")){
                    jsonData.clear();
                    jsonData.put("succ", false);
                    jsonData.put("msg", dictname + "只能为数字且大于0！");
                    return Action.SUCCESS;
                }
                SysDictinfo info = new SysDictinfo();
                if (jo.containsKey("dictkey"))
                {
                    info.setId(Integer.parseInt(jo.get("dictkey").toString()));
                    info.setDictvalue(jo.get("dictvalue").toString());
                    dictinfos.add(info);
                }
                if (jo.containsKey("ssointe"))
                {
                    System.out.println("ssointe:" + jo.get("ssointe"));
                }
                if (jo.containsKey("appriesinte"))
                {
                    System.out.println("appriesinte:" + jo.get("appriesinte"));
                }
            }
        }
        dictinfoService.updateBatchById(dictinfos);
        jsonData.clear();
        jsonData.put("succ", true);
        jsonData.put("msg", "1111");
        dictinfoService.getAllCache(true);
        return Action.SUCCESS;
    }
}
