<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@page import="java.text.DateFormat"%><%@page import="java.text.SimpleDateFormat"%><%
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	out.print(df.format(new Date()));
%>