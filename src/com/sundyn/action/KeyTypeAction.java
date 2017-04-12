package com.sundyn.action;

import com.opensymphony.xwork2.*;
import com.sundyn.service.*;
import org.apache.struts2.*;
import javax.servlet.http.*;
import java.util.*;
import net.sf.json.*;

public class KeyTypeAction extends ActionSupport
{
    private Integer id;
    private KeyTypeService keyTypeService;
    private List list;
    
    public Integer getId() {
        return this.id;
    }
    
    public KeyTypeService getKeyTypeService() {
        return this.keyTypeService;
    }
    
    public List getList() {
        return this.list;
    }
    
    public String keyTypeEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String name = request.getParameter("name");
        final String isJoy = request.getParameter("isJoy");
        final String yes = request.getParameter("yes");
        final String ext1 = request.getParameter("ext1");
        String ext2 = name;
        if (this.getText("sundyn.language").equals("en")) {
            name.replace(" ", " ");
            final String[] names = name.split(" ");
            String shortName = "";
            if (names.length > 1) {
                for (int j = 0; j < names.length; ++j) {
                    if (j == 0) {
                        shortName = String.valueOf(shortName) + names[j].toCharArray()[0];
                    }
                    else {
                        shortName = String.valueOf(shortName) + "." + names[j].toCharArray()[0];
                    }
                }
            }
            else {
                shortName = new StringBuilder(String.valueOf(names[0].toCharArray()[0])).toString();
            }
            ext2 = shortName.toUpperCase();
        }
        this.keyTypeService.update(this.id, name, isJoy, yes, ext1, ext2);
        return "success";
    }
    
    public String keyTypeQueryDialog() throws Exception {
        this.list = this.keyTypeService.findByApprieserId(this.id);
        return "success";
    }
    
    public void setId(final Integer id) {
        this.id = id;
    }
    
    public void setKeyTypeService(final KeyTypeService keyTypeService) {
        this.keyTypeService = keyTypeService;
    }
    
    public void setList(final List list) {
        this.list = list;
    }
    
    public String getDissatisfiedKey() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final List l = this.keyTypeService.getDissatisfiedKey();
        String msg = "";
        for (final Object o : l) {
            msg = String.valueOf(msg) + ((Map)o).get("keyNo").toString() + ",";
        }
        msg = msg.substring(0, msg.length() - 1);
        request.setAttribute("msg", (Object)msg);
        return "success";
    }
    
    public String getKeyTypeAll() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        try {
            this.list = this.keyTypeService.findByApprieserYes(1, 1);
            final Map<String, List<?>> keyType = new HashMap<String, List<?>>();
            keyType.put("keyTypes", this.list);
            final JSONObject json = JSONObject.fromObject((Object)keyType);
            request.setAttribute("json", (Object)json);
        }
        catch (Exception e) {
            this.list = null;
            request.setAttribute("json", (Object)"");
            e.printStackTrace();
        }
        return "success";
    }
}
