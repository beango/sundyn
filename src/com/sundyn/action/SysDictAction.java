package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.SysDictinfo;
import com.sundyn.service.ISysDictinfoService;
import com.sundyn.service.KeyTypeService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SysDictAction extends MainAction {
    @Resource
    private KeyTypeService keyTypeService;
    @Resource
    private ISysDictinfoService dictinfoService;

    /*
    字典配置
     */
    public String sysDict() {
        try {
            List<SysDictinfo> dictinfos = dictinfoService.selectList(new EntityWrapper<SysDictinfo>().where("isEnable=1"));

            request.setAttribute("dictinfos", dictinfos);
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
                SysDictinfo info = new SysDictinfo();
                info.setId(Integer.parseInt(jo.get("dictkey").toString()));
                info.setDictvalue(jo.get("dictvalue").toString());
                dictinfos.add(info);
            }
        }
        dictinfoService.updateBatchById(dictinfos);
        return SUCCESS;
    }
}
