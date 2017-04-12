package com.sundyn.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.dao.*;
import com.sundyn.service.PowerService;
import com.sundyn.vo.AdviceVo;
import com.sundyn.vo.QuestionVo;

import org.apache.struts2.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.*;

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
	
	public void setPowerService(final PowerService powerService) {
        this.powerService = powerService;
    }
	
	public PowerService getPowerService() {
        return this.powerService;
    }
}
