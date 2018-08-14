package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.KeyTypeService;
import com.sundyn.util.XmlUtil;
import com.sundyn.utils.JavaXML;
import com.sundyn.vo.button;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public String keyTypeLayoutEdit() throws IOException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        JSONArray ja = JSONArray.fromObject(request.getParameter("data"));

        final Element root = new Element("root");
        final Document Doc = new Document(root);
        Doc.setRootElement(root);

        if (ja != null) {
            for (Object object : ja){
                JSONObject jo = (JSONObject)object;
                Element button = new Element("button");
                Element e = new Element("text").setText(String.valueOf(jo.get("text")));
                button.addContent((Content)e);

                e = new Element("size").setText(String.valueOf(jo.get("size")));
                button.addContent((Content)e);

                e = new Element("r").setText(String.valueOf(jo.get("r")));
                button.addContent((Content)e);

                e = new Element("g").setText(String.valueOf(jo.get("g")));
                button.addContent((Content)e);

                e = new Element("b").setText(String.valueOf(jo.get("b")));
                button.addContent((Content)e);

                e = new Element("x").setText(String.valueOf(jo.get("x")));
                button.addContent((Content)e);

                e = new Element("y").setText(String.valueOf(jo.get("y")));
                button.addContent((Content)e);

                e = new Element("img").setText(String.valueOf(jo.get("img")));
                button.addContent((Content)e);

                e = new Element("width").setText(String.valueOf(jo.get("width")));
                button.addContent((Content)e);

                e = new Element("height").setText(String.valueOf(jo.get("height")));
                button.addContent((Content)e);

                e = new Element("lx").setText(String.valueOf(jo.get("lx")));
                button.addContent((Content)e);

                e = new Element("ly").setText(String.valueOf(jo.get("ly")));
                button.addContent((Content)e);

                e = new Element("id").setText(String.valueOf(jo.get("id")));
                button.addContent((Content)e);

                root.addContent(button);
            }
        }
        final XMLOutputter XMLOut = new XMLOutputter();
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        XMLOut.output(Doc, (OutputStream)new FileOutputStream(String.valueOf(file) + "evalbuttons.xml"));
        return "success";
    }

    public String keyTypeEdit() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String ids = request.getParameter("ids");
        final String[] names = request.getParameter("names").split(",");
        final String[] isJoys = request.getParameter("isJoys").split(",");
        final String[] yess = request.getParameter("yess").split(",");
        final String[] ext1s = request.getParameter("ext1s").split(",");
        String[] idArray = ids.split(",");
        if (idArray == null || idArray.length==0){
            request.setAttribute("msg", "系统错误!");
            return "success";
        }
        for (int idx =0; idx < idArray.length; idx++){
            if (idArray[idx].equals(""))
                continue;

            int extInt;
            String ext1 = ext1s[idx];
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
            String ext2 = names.length > idx ? names[idx] : "";
            if (this.getText("sundyn.language").equals("en")) {
                ext2.replace(" ", " ");
                final String[] names2 = names[idx].split(" ");
                String shortName = "";
                if (names2.length > 1) {
                    for (int j = 0; j < names2.length; ++j) {
                        if (j == 0) {
                            shortName = String.valueOf(shortName) + names2[j].toCharArray()[0];
                        }
                        else {
                            shortName = String.valueOf(shortName) + "." + names2[j].toCharArray()[0];
                        }
                    }
                }
                else {
                    shortName = new StringBuilder(String.valueOf(names2[0].toCharArray()[0])).toString();
                }
                ext2 = shortName.toUpperCase();
            }
            String _p_name = names.length > idx ? names[idx] : "";
            this.keyTypeService.update(Integer.valueOf(idArray[idx]), _p_name, (isJoys.length>idx?isJoys[idx]:""), yess[idx], ext1, ext2);
        }
        request.setAttribute("msg", "保存成功!");
        return "success";
    }
    
    public String keyTypeQueryDialog() throws Exception {
        final HttpServletRequest request = ServletActionContext.getRequest();
        this.list = this.keyTypeService.findByApprieserId(this.id);

        JSONArray ja = ConvertXMLtoJSON();
        request.setAttribute("evalbuttons", ja.toString());
        return "success";
    }

    public JSONArray ConvertXMLtoJSON() throws IOException {
        String file = JavaXML.class.getClassLoader().getResource("").getPath();
        file = file.replaceAll("%20", " ");
        file = String.valueOf(file.substring(1, file.indexOf("classes"))) + "source/";
        File f = new File(String.valueOf(file) + "evalbuttons.xml");

        InputStream is = new FileInputStream(f);
        // 声明一个字节数组
        byte[] b = new byte[1024];
        StringBuffer str = new StringBuffer();
        int len ;
        // 循环读取
        while ((len = is.read(b)) != -1) {
            str.append(new String(b, 0, len, "utf-8"));
        }
        System.out.println(str.toString());
        List<button> o= (List<button>) XmlUtil.xmlToList(str.toString(), button.class);
        JSONArray ja = JSONArray.fromObject(o);
        return ja;
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
        }
        return "success";
    }
}
