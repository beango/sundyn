package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.service.IAppriesFuncService;
import com.sundyn.util.ValidateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AuthAction extends MainAction
{
    @Resource
    private IAppriesFuncService appriesFuncService;

    public String authQuery() throws Exception {
        return "success";
    }

    public String authQueryJSON() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();
        JSONArray jo = new JSONArray();

        long systemid = req.getLong("systemid");
        int pid = req.getInt("pid",0);
        boolean isAll = req.getInt("isAll")==1;
        boolean isCheck = req.getInt("isCheck")==1;

        JSONObject jroot = new JSONObject();
        jroot.put("id", "");
        jroot.put("name", "顶级权限");
        jroot.put("parentId", "0");
        jroot.put("open", true);
        jroot.put("level", 1);

        EntityWrapper ew=new EntityWrapper();
        ew.setEntity(new AppriesFunc());
        ew.where("parentId = {0}",pid);
        List<AppriesFunc> menus = appriesFuncService.selectList(ew);//(pid);
        //put("topmenu", menus);

        JSONArray ja1 = new JSONArray();
        for(int i=0; i < menus.size(); i++) {
            JSONObject jo2 = new JSONObject();
            jo2.put("id",menus.get(i).getId());
            jo2.put("name",menus.get(i).getFuncName());
            if(menus.get(i).getParentId()!=null && !menus.get(i).getParentId().equals(0L))
                jo2.put("iconCls", "fa fa-chevron-circle-right");
            jo2.put("parentId", menus.get(i).getParentId());
            jo2.put("open", true);
            jo2.put("level", 2);
            jo2.put("onclick", "selectAuth("+menus.get(i).getId()+")");

            ew=new EntityWrapper();
            ew.where("parentId = {0}", Integer.valueOf(menus.get(i).getId()));
            List<AppriesFunc> menuChild = appriesFuncService.selectList(ew);//.selectChildList(Integer.valueOf(menus.get(i).ID));
            JSONArray ja2 = new JSONArray();
            for(int j=0; j<menuChild.size(); j++) {
                JSONObject subjo2 = new JSONObject();
                subjo2.put("id",menuChild.get(j).getId());
                subjo2.put("name",menuChild.get(j).getFuncName());
                if(menuChild.get(j).getParentId()!=null && !menuChild.get(j).getParentId().equals(0L))
                    subjo2.put("iconCls", "fa fa-chevron-circle-right");
                subjo2.put("parentId", menuChild.get(j).getParentId());
                subjo2.put("open", true);
                subjo2.put("level", 3);
                if (isAll){
                    ew=new EntityWrapper();
                    ew.where("parentId = {0}", Integer.valueOf(menuChild.get(j).getId()));
                    List<AppriesFunc> menuChild2 = appriesFuncService.selectList(ew);//.selectChildList(Integer.valueOf(menus.get(i).ID));
                    JSONArray ja22 = new JSONArray();
                    for(int k=0; k<menuChild2.size(); k++) {
                        JSONObject subjo22 = new JSONObject();
                        subjo22.put("id",menuChild2.get(k).getId());
                        subjo22.put("name",menuChild2.get(k).getFuncName());
                        if(menuChild.get(k).getParentId()!=null && !menuChild2.get(k).getParentId().equals(0L))
                            subjo22.put("iconCls", "fa fa-chevron-circle-right");
                        subjo22.put("parentId", menuChild2.get(k).getParentId());
                        subjo22.put("open", true);
                        subjo22.put("level", 4);
                        ja22.add(subjo22);
                    }
                    if(ja22.size()>0)
                        subjo2.put("children", ja22);
                }
                ja2.add(subjo2);
            }
            if(ja2.size()>0)
                jo2.put("children", ja2);
            ja1.add(jo2);
        }
        if(ja1.size()>0)
            jroot.put("children", ja1);
        jo.add(jroot);
        request.setAttribute("msg", jo.toString());

        return "success";
    }

    public String authCode() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        int parentId = req.getInt("parentId");
        if (parentId!=0){ //修改
            AppriesFunc func = appriesFuncService.selectById(parentId);
            if (func!=null){
                request.setAttribute("parentModel", func);

                EntityWrapper ew=new EntityWrapper();
                ew.setEntity(new AppriesFunc());

                ew.where("parentId = {0}", parentId);
                List<AppriesFunc> funcCodes = appriesFuncService.selectList(ew);//(pid);
                request.setAttribute("authCodeList",funcCodes);
            }
        }
        return "success";
    }

    public String getAuthData(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        int id = req.getInt("id");
        AppriesFunc func = appriesFuncService.selectById(id);
        if (func!=null){
            JSONObject jo = new JSONObject();
            jo.put("id", func.getId());
            jo.put("name", func.getFuncName());
            jo.put("code", func.getFuncCode());
            request.setAttribute("msg", jo);
        }
        return "success";
    }

    public String authEdit(){
        final HttpServletRequest request = ServletActionContext.getRequest();

        int id = req.getInt("id");
        Integer parentid = req.getInt("parentid");
        if (id!=0){ //修改
            AppriesFunc func = appriesFuncService.selectById(id);
            if (func!=null){
                request.setAttribute("model", func);
            }
        }
        if (parentid!=null){ //添加
            AppriesFunc parentF = appriesFuncService.selectById(parentid);
            if (parentF!=null){
                request.setAttribute("parentModel", parentF);
            }
        }

        return "success";
    }

    public String authEditPost(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        String errmsg = "";

        Integer id = req.getInt("id");
        String funcName = req.getString("funcName");
        String funcCode = req.getString("funcCode");
        int orderId = req.getInt("orderId",0);
        int parentId = req.getInt("parentId",0);
        AppriesFunc func  = new AppriesFunc();

        if (id!=null && id != 0){
            func  = appriesFuncService.selectById(id);
            if (func==null)
            {
                errmsg = "系统错误";
                request.setAttribute("msg", errmsg);
                return "success";
            }
        }

        try {
            func.setFuncName(funcName);
            func.setFuncCode(funcCode);
            func.setOrderId(orderId);
            func.setParentId(parentId);
            System.out.println(">>>>>>>>>>func.code:" + func.getFuncCode() + ", " + func.getId());
            ValidateUtil.validate(func);
        } catch (Exception e) {
            errmsg = e.getMessage();
            request.setAttribute("msg", errmsg);
            return "success";
        }

        if (id!=null && id!=0){
            boolean isUpdate = appriesFuncService.updateById(func);
        }else{
            boolean isAdd = appriesFuncService.insert(func);
        }
        return "success";
    }

    public String authDelPost(){
        final HttpServletRequest request = ServletActionContext.getRequest();

        int id = req.getInt("id");
        if (id!=0){ //修改
            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new AppriesFunc());

            ew.where("parentId = {0}",id);
            boolean isdel = appriesFuncService.delete(ew);

            appriesFuncService.deleteById(id);
        }

        return "success";
    }
}
