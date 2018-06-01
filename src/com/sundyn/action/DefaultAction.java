package com.sundyn.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.DeptService;
import com.sundyn.service.PowerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

public class DefaultAction extends ActionSupport
{
	private PowerService powerService;

	public String TopFrame() {
		final HttpServletRequest request = ServletActionContext.getRequest();
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        
        final String baseSet = power.get("baseSet").toString();
        final String dataManage = power.get("dataManage").toString();
        int b = 0;
        int d = 0;
        if (baseSet.equals("true") || baseSet.equals("1")) {
            b = 1;
        }
        if (dataManage.equals("true") || dataManage.equals("1")) {
            d = 1;
        }
        final String language = ActionContext.getContext().getLocale().getLanguage();
        final String jsurl = String.valueOf(language) + "/" + "nav" + b + d + ".js";
        request.setAttribute("jsurl", (Object)jsurl);
        request.setAttribute("realname", manager.get("realname"));
        request.setAttribute("name", power.get("name"));
        return "success";
    }
	
	public String TopFrame2(){
		final HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();  
        response.setContentType("text/html;UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = null;    
        try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
       
        final Map manager = (Map)request.getSession().getAttribute("manager");
        final Integer groupid = Integer.valueOf(manager.get("userGroupId").toString());
        final Map power = this.powerService.getUserGroup(groupid);
        
        final String baseSet = power.get("baseSet").toString();
        final String dataManage = power.get("dataManage").toString();
        int b = 0;
        int d = 0;
        if (baseSet.equals("true") || baseSet.equals("1")) {
            b = 1;
        }
        if (dataManage.equals("true") || dataManage.equals("1")) {
            d = 1;
        }
        final String language = ActionContext.getContext().getLocale().getLanguage();
        final String jsurl = String.valueOf(language) + "/" + "nav" + b + d + ".js";
        request.setAttribute("jsurl", (Object)jsurl);
        request.setAttribute("realname", manager.get("realname"));
        request.setAttribute("name", power.get("name"));
        JSONObject json= new JSONObject();
        json.put("realname", manager.get("realname"));
        json.put("name", power.get("name"));
        out.println(json.toString()); 
        out.flush();    
        out.close(); 
        
        return "success";
    }
	
	public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
	
	public PowerService getPowerService() {
        return this.powerService;
    }


}
