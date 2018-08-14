package com.sundyn.action;

import com.opensymphony.xwork2.ActionSupport;
import com.sundyn.service.AdviceService;
import com.sundyn.utils.NumberUtils;
import com.sundyn.utils.StringHql;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DownVideo extends ActionSupport
{
    private static final long serialVersionUID = 1L;
    private String fileName;

    public void setFileName() {
        String fname = ServletActionContext.getRequest().getParameter("videofile");
        System.out.println("videofile\u7f16\u7801\u524d=" + fname);
        try {
            fname = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.fileName = fname;
        System.out.println("videofile\u7f16\u7801\u540e=" + fname);
    }
    
    public String getFileName() throws UnsupportedEncodingException {
        return this.fileName = new String(this.fileName.getBytes(), "ISO-8859-1");
    }
    
    public InputStream getDownloadFile() {
        this.setFileName();
        System.out.println("\u5f97\u5230\u4e0b\u8f7d\u7684\u6587\u4ef6\u540d*****" + this.fileName);
        return ServletActionContext.getServletContext().getResourceAsStream("/download/" + this.fileName);
    }
    
    public String execute() throws Exception {
        final HttpServletResponse response = ServletActionContext.getResponse();
        final HttpServletRequest request = ServletActionContext.getRequest();
        final String videofile = request.getParameter("videofile");
        InputStream in = null;
        String excelName = videofile;
        String path = "/download/recorder/" + videofile;
        path = path.replace("\\", "/");
        System.out.println("视频下载地址:" + path);

        in = ServletActionContext.getServletContext().getResourceAsStream(path);
        if (in==null)
        {
            System.out.println("视频下载地址:" + path + "不存在");
            request.setAttribute("json","文件不存在");
            return "fail";
        }
        response.reset();
        response.setCharacterEncoding("UTF-8");
        excelName = URLEncoder.encode(excelName, "UTF-8");
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String(excelName.getBytes("UTF-8"), "UTF-8"));
        final BufferedInputStream bis = new BufferedInputStream(in);
        final byte[] buf = new byte[1024];
        int len = 0;
        final OutputStream out = (OutputStream)response.getOutputStream();
        while ((len = bis.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.flush();
        out.close();
        return null;
    }
}
