package com.sundyn.action;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sundyn.entity.AppriesFunc;
import com.sundyn.entity.AppriesMenu;
import com.sundyn.service.IAppriesFuncService;
import com.sundyn.service.IAppriesManagerpowerService;
import com.sundyn.service.IAppriesMenuService;
import com.sundyn.service.MenuService;
import com.sundyn.util.EhCacheHelper;
import com.sundyn.util.ValidateUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuAction extends MainAction {
    @Resource
    private IAppriesMenuService appriesMenuService;
    @Resource
    private IAppriesFuncService appriesFuncService;
    @Resource
    private IAppriesManagerpowerService managerpowerService;
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public String leftMenu(){
	    return getQuery();
    }

    public String topMenu(){
        return getQuery();
    }

    public String getQuery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        List menuAll = (List) request.getSession().getAttribute("menus");
        if (menuAll == null){
            menuAll = this.menuService.GetMenuByName(super.UserID);
            request.getSession().setAttribute("menus", menuAll);
        }

        final JSONArray json = JSONArray.fromObject((Object)menuAll);
        request.setAttribute("json", (Object)json);
        return SUCCESS;
    }

    public String menuQuery(){
        return "success";
    }

    public String menuQueryJson(){
        JSONArray jo = new JSONArray();

        long systemid = req.getLong("systemid");
        int pid = req.getInt("pid",0);

        JSONObject jroot = new JSONObject();
        jroot.put("id", "");
        jroot.put("name", this.getText("menu.root.name"));
        jroot.put("parentId", "0");
        jroot.put("open", true);
        jroot.put("level", 1);

        EntityWrapper ew=new EntityWrapper();
        ew.setEntity(new AppriesMenu());

        ew.where("parentId={0} and isshow=1",pid).orderBy("menuorder");
        List<AppriesMenu> menus = appriesMenuService.selectList(ew);//(pid);
        JSONArray ja1 = new JSONArray();
        for(int i=0; i < menus.size(); i++) {
            JSONObject jo2 = new JSONObject();
            jo2.put("id",menus.get(i).getId());
            jo2.put("name",menus.get(i).getMenuName());
            if(menus.get(i).getParentId()!=null && !menus.get(i).getParentId().equals(0L))
                jo2.put("iconCls", "fa fa-chevron-circle-right");
            jo2.put("parentId", menus.get(i).getParentId());
            jo2.put("open", true);
            jo2.put("level", 2);

            ew=new EntityWrapper();
            ew.where("parentId={0} and isshow=1", Integer.valueOf(menus.get(i).getId()));
            List<AppriesMenu> menuChild = appriesMenuService.selectList(ew.orderBy("menuorder"));//.selectChildList(Integer.valueOf(menus.get(i).ID));
            JSONArray ja2 = new JSONArray();
            for(int j=0; j<menuChild.size(); j++) {
                JSONObject subjo2 = new JSONObject();
                subjo2.put("id",menuChild.get(j).getId());
                subjo2.put("name",menuChild.get(j).getMenuName());
                if(menuChild.get(j).getParentId()!=null && !menuChild.get(j).getParentId().equals(0L))
                    subjo2.put("iconCls", "fa fa-chevron-circle-right");
                subjo2.put("parentId", menuChild.get(j).getParentId());
                subjo2.put("open", true);
                subjo2.put("level", 3);
                ja2.add(subjo2);

            }
            jo2.put("children", ja2);
            ja1.add(jo2);
        }
        jroot.put("children", ja1);
        jo.add(jroot);
        request.setAttribute("msg", jo.toString());

        return SUCCESS;
    }

    public String menuEdit(){
        final HttpServletRequest request = ServletActionContext.getRequest();

        int id = req.getInt("id");
        Integer parentid = req.getInt("parentid");
        if (id!=0){ //修改
            HashMap menu = appriesMenuService.selectByIdEx(id);
            if (menu!=null){
                request.setAttribute("model", menu);
                request.setAttribute("funcList", appriesFuncService.selectListExt(new EntityWrapper<Map>().where("","")));
            }
        }
        if (parentid!=null){ //添加
            HashMap parentM = appriesMenuService.selectByIdEx(parentid);
            if (parentM!=null){
                request.setAttribute("parentModel", parentM);
            }
        }

        return SUCCESS;
    }

    public String menuEditPost(){
        final HttpServletRequest request = ServletActionContext.getRequest();
        String errmsg = "";

        Integer id = req.getInt("id");
        String menuName = req.getString("menuName");
        String nav = req.getString("nav");
        int menuorder = req.getInt("menuorder",0);
        int parentId = req.getInt("parentId",0);
        int isnotgeneral = req.getInt("isnotgeneral");
        int isjy = req.getInt("isjy");
        int iscore = req.getInt("iscore");

        String funccode = req.getString("funccode");
        AppriesMenu menu  = new AppriesMenu();
        String cls = "fa fa-chevron-circle-right";

        if (id!=null && id != 0){
            menu  = appriesMenuService.selectById(id);
            if (menu==null)
            {
                errmsg = this.getText("main.systemerror");
                request.setAttribute("msg", errmsg);
                return "success";
            }
        }
        else{
            AppriesMenu parentM = appriesMenuService.selectById(parentId);
            if (parentM!=null && parentM.getParentId().equals("0")){
                cls = "fa fa-share";
            }
        }

        try {
            menu.setMenuName(menuName);
            menu.setNav(nav);
            menu.setMenuorder(menuorder);
            menu.setParentId(parentId);
            menu.setIconCls(cls);
            menu.setIsshow(1);
            menu.setIsnotgeneral(isnotgeneral);
            menu.setIscore(iscore);
            menu.setIsjy(isjy);
            if(funccode!=null && !funccode.equals("")){
                AppriesFunc f = appriesFuncService.selectById(Integer.valueOf(funccode.replace(",", "")));
                menu.setFuncCode(f.getFuncCode());
            }

            ValidateUtil.validate(menu, this.getLocale());
        } catch (Exception e) {
            errmsg = e.getMessage();
            request.setAttribute("msg", errmsg);
            return "success";
        }

        if (id!=null && id!=0){
            boolean isUpdate = appriesMenuService.updateById(menu);
        }else{
            boolean isAdd = appriesMenuService.insert(menu);
        }
        appriesMenuService.getAllCache(true);
        return SUCCESS;
    }

    public String menuDelPost(){
        final HttpServletRequest request = ServletActionContext.getRequest();

        int id = req.getInt("id");
        if (id!=0){ //修改
            EntityWrapper ew=new EntityWrapper();
            ew.setEntity(new AppriesMenu());

            ew.where("parentId = {0}",id);
            boolean isdel = appriesMenuService.delete(ew);

            appriesMenuService.deleteById(id);
            appriesMenuService.getAllCache(true);
        }

        return SUCCESS;
    }
}
