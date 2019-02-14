<%@ page pageEncoding="UTF-8"%>
<%@page import="org.apache.tools.ant.taskdefs.Redirector"%>
<%
String city=request.getParameter("city");
String url="http://m.weather.com.cn/data/"+city+".html";
out.print("url="+url);
response.sendRedirect(url);
%>