package com.sundyn.filter;

import org.apache.struts2.dispatcher.StrutsRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UeditorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getRequestURI();

        if (decideURI(url)) {
            chain.doFilter(new StrutsRequestWrapper((HttpServletRequest) req), res);
        }else{
            chain.doFilter(req, res);
        }

    }

    /**
     * ueditor编辑器中的图片上传和文件上传
     * @param url
     * @return
     */
    private boolean decideURI(String url){
        if(url.endsWith("imageUp.jsp")){
            return true;
        }else if(url.endsWith("fileUp.jsp")){
            return true;
        }else if(url.endsWith("/ueditor/jsp/controller.jsp")){
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}