package com.sundyn.action;

import com.sundyn.entity.AppriesMenu;
import com.sundyn.service.MenuService;
import com.sundyn.util.Pager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static javax.swing.UIManager.put;

public class AuthAction extends MainAction
{
    private Pager pager;
    private String msg;

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    private MenuService menuService;

    public String authQuery() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();

        long systemid = req.getLong("systemid");
        int pid = req.getInt("pid");
        List<AppriesMenu> menus = menuService.selectChildList(pid);
        request.setAttribute("menus", menus);
        return "success";
    }

    public String authQueryJSON() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final HttpServletResponse response = ServletActionContext.getResponse();

        long systemid = req.getLong("systemid");
        int pid = req.getInt("pid",0);
        List<AppriesMenu> menus = menuService.selectChildList(pid);
        put("topmenu", menus);
        JSONArray jo = new JSONArray();
        System.out.println("mmm:" + menus.size());
        for(int i=0; i < menus.size(); i++) {
            JSONObject jo2 = new JSONObject();
            jo2.put("id",menus.get(i).getId());
            jo2.put("name",menus.get(i).getMenuName());
            //jo2.put("url",request.getContextPath() + menus.get(i).getNav());
            if(menus.get(i).getParentId()!=null && !menus.get(i).getParentId().equals(0L))
                jo2.put("iconCls", "fa fa-chevron-circle-right");
            jo2.put("parentId", menus.get(i).getParentId());
            System.out.println("mmm>>>1:" + menus.get(i).getParentId()  + ", " + menus.get(i));


            List<AppriesMenu> menuChild = menuService.selectChildList(menus.get(i).getId());
            JSONArray ja2 = new JSONArray();
            for(int j=0; j<menuChild.size(); j++) {
                JSONObject subjo2 = new JSONObject();
                subjo2.put("id",menuChild.get(j).getId());
                subjo2.put("name",menuChild.get(j).getMenuName());
                //subjo2.put("url",request.getContextPath() + menuChild.get(j).getNav());
                if(menuChild.get(j).getParentId()!=null && !menuChild.get(j).getParentId().equals(0L))
                    subjo2.put("iconCls", "fa fa-chevron-circle-right");
                subjo2.put("parentId", menuChild.get(j).getParentId());
                System.out.println("mmm>>>2:" + menuChild.get(j).getParentId()  + ", " + menuChild.get(j));
                ja2.add(subjo2);

            }
            jo2.put("children", ja2);
            jo.add(jo2);
        }
        request.setAttribute("msg", jo.toString());

        return "success";
    }
}
