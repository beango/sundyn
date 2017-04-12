package com.sundyn.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.MenuService;
import com.sundyn.util.Pager;

import net.sf.json.JSONArray;

public class MenuAction extends ActionSupport {
	private MenuService menuService;

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	public String getQuery() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List menuAll = this.menuService.GetAll();
        final JSONArray json = JSONArray.fromObject((Object)menuAll);
        request.setAttribute("json", (Object)json);
        return "success";
    }
}
