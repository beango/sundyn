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
        int extInt;
        if (ext1 == null || ext1.equals("")){
            request.setAttribute("msg", "权值不能为空!");
            return "success";
        }
        try {
            extInt = Integer.valueOf(ext1);
            if(extInt>10){
                request.setAttribute("msg", "权值不能大于10!");
                return "success";
            }
        }
        catch (Exception e){
            request.setAttribute("msg", "权值只能为整数!");
            return "success";
        }
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
        if(msg.length()>0)
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

    public String getKeyAll() {
        final HttpServletRequest request = ServletActionContext.getRequest();
        try {
            this.list = this.keyTypeService.findByApprieserYes(1, 1);
            List<JSONObject> keyList = new ArrayList<JSONObject>();
            if(list.size()<5){
                for (int i=0; i<list.size(); i++) {
                    final Object o = list.get(i);
                    JSONObject json = new JSONObject();
                    json.put("detail", ((Map)o).get("name").toString());
                    json.put("img", "eval_"+((Map)o).get("keyNo").toString() + ".png");
                    json.put("x", ""+i);
                    json.put("y", "0");
                    keyList.add(json);
                }
            }else{
                int line0 = list.size()%4;
                int startX = 0;
                for (int i=0; i<line0; i++) {
                    startX = i;
                    if(line0==1)//只有一个按钮
                        startX = i+1;
                    if(line0==2)//2个按钮
                        startX = i+1;
                    final Object o = list.get(i);
                    JSONObject json = new JSONObject();
                    json.put("detail", ((Map)o).get("name").toString());
                    json.put("img", "eval_"+((Map)o).get("keyNo").toString() + ".png");
                    json.put("x", ""+startX);
                    json.put("y", "0");
                    keyList.add(json);
                }
                for (int i=line0; i<list.size(); i++) {
                    final Object o = list.get(i);
                    JSONObject json = new JSONObject();
                    json.put("detail", ((Map)o).get("name").toString());
                    json.put("img", "eval_"+((Map)o).get("keyNo").toString() + ".png");
                    json.put("x", ""+(i-line0));
                    json.put("y", "1");
                    keyList.add(json);
                }
            }

            /*JSONObject json1 = new JSONObject();
            json1.put("detail", "非常满意1");
            json1.put("img", "eval_0");
            json1.put("x", "1");
            json1.put("y", "0");
            keyList.add(json1);
            json1 = new JSONObject();
            json1.put("detail", "非常满意2");
            json1.put("img", "eval_0");
            json1.put("x", "2");
            json1.put("y", "0");
            keyList.add(json1);
            json1 = new JSONObject();
            json1.put("detail", "非常满意3");
            json1.put("img", "eval_0");
            json1.put("x", "0");
            json1.put("y", "1");
            keyList.add(json1);
            json1 = new JSONObject();
            json1.put("detail", "非常满意4");
            json1.put("img", "eval_0");
            json1.put("x", "1");
            json1.put("y", "1");
            keyList.add(json1);
            json1 = new JSONObject();
            json1.put("detail", "非常满意5");
            json1.put("img", "eval_0");
            json1.put("x", "2");
            json1.put("y", "1");
            keyList.add(json1);
            json1 = new JSONObject();
            json1.put("detail", "非常满意6");
            json1.put("img", "eval_0");
            json1.put("x", "3");
            json1.put("y", "1");
            keyList.add(json1);*/

            final Map<String, List<?>> keyType = new HashMap<String, List<?>>();
            keyType.put("data", keyList);
            final JSONObject json = JSONObject.fromObject((Object)keyType);
            json.put("success", true);
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
