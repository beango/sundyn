package com.sundyn.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sundyn.entity.InteLog;
import com.sundyn.helper.TokenHelper;
import com.sundyn.service.IInteLogService;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Scanner;

public class SignatureInterceptor extends AbstractInterceptor {
    @Resource
    private IInteLogService logService;
    private static final Logger LOG = LoggerFactory.getLogger(SignatureInterceptor.class);
    private static final String SK = "B548EC106017EFB2429B7528E65055E5";
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletRequest request = (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        HttpServletResponse response = (HttpServletResponse) actionContext.get(StrutsStatics.HTTP_RESPONSE);
        try{

            String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
            String queryParam = request.getQueryString();

            JSONObject signParam = new JSONObject();
            String sign = null;
            String contentType = request.getContentType();
            StringBuilder sb = new StringBuilder();
            if(MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType) ||
                    MediaType.APPLICATION_JSON_VALUE.equals(contentType)||
                    MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)){
                Scanner scanner = new Scanner(request.getInputStream(),"utf-8");
                while (scanner.hasNextLine()) {
                    sb.append(scanner.nextLine());
                }
                signParam = JSONObject.parseObject(sb.toString());
                sign = request.getParameter("token");
                signParam.remove("sign");
            } else{
                Enumeration<String> names = request.getParameterNames();
                while(names.hasMoreElements()) {
                    String name = names.nextElement();
                    String value = new String(request.getParameter(name).getBytes("iso-8859-1"),"utf-8");
                    if(name.equals("sign")){
                        sign = value;
                    }else{
                        signParam.put(name, value);
                    }
                }
            }
            String ywlsh = signParam.getString("ywlsh");
            int logid = addLog(request, sb.toString(), ywlsh);
            String tt = signParam.getString("tt");
            String password = signParam.getString("password");

            if(tt == null || "".equals(tt) || password == null || "".equals(password)){
                LOG.warn("签名参数不存在，验证不通过");
                logService.updateForSet("status=-1, [note]='签名参数不存在，验证不通过'", new EntityWrapper<InteLog>().where("id={0}", logid));
                JSONObject j = new JSONObject();
                j.put("succ", false);
                j.put("msg", "签名参数不存在，验证不通过");
                this.returnJson(response, j.toString());
                return Action.ERROR;
            }
            String signRst = tt + SK;
            boolean bsign = new TokenHelper().MD5(signRst).equals(password);//SignUtil.makeVeriSignJson(signParam);

            if(!bsign){
                LOG.warn("签名不正确，验证不通过");
                logService.updateForSet("status=-1, [note]='签名不正确，验证不通过'", new EntityWrapper<InteLog>().where("id={0}", logid));
                JSONObject j = new JSONObject();
                j.put("succ", false);
                j.put("msg", "签名不正确，验证不通过");
                this.returnJson(response, j.toString());
                return "inte_err";
            }
            logService.updateForSet("status=1, [note]='签名正确，验证通过'", new EntityWrapper<InteLog>().where("id={0}", logid));
            request.setAttribute("id", logid);
            request.setAttribute("data", sb.toString());
            return invocation.invoke();
        }
        catch (Exception e){
            JSONObject j = new JSONObject();
            j.put("succ", false);
            j.put("msg", "系统错误，请检查参数是否正确");
            j.put("desc", FormatStackTrace(e));
            this.returnJson(response, j.toString());
            return Action.ERROR;
        }
    }

    private void returnJson(HttpServletResponse response, String json) throws Exception{
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    public static String FormatStackTrace(Throwable throwable) {
        if(throwable==null) return "";
        String rtn = throwable.getStackTrace().toString();
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.flush();
            writer.flush();
            rtn = writer.toString();
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
        return rtn;
    }

    private Integer addLog(HttpServletRequest request, String body, String ywlsh) throws UnsupportedEncodingException {
        String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI();
        if (null!=request.getQueryString())
            url += "?"+request.getQueryString();

        InteLog log = new InteLog();
        log.setToken(request.getParameter("token"));
        log.setYwlsh(ywlsh);
        log.setInteurl(url);
        log.setIntedata(body);
        log.setReqtime(new Date());

        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getHeader("WL-Proxy-Client-IP");
        if(ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) ip = request.getRemoteAddr();
        log.setReqip(ip);
        log.setStatus(0);
        log.insert();
        return log.getId();
    }
}