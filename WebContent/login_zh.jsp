<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.sundyn.util.SundynSet"%>
	<%
	String path= request.getRealPath("/");
  	SundynSet set=SundynSet.getInstance(path);
	String title=set.getM_content().get("title").toString();
	String logo=set.getM_content().get("logo").toString();
 	String url = set.getM_content().get("requestAddress").toString();
 	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title> <s:text name="sundyn.title" /> </title>
		<link rel="stylesheet" href="css/login.css" type="text/css"></link>
	</head>
	<body>

				 <s:text name="login.info"></s:text><br/>
                 <a href="update/CP210x_VCP_Win_XP_S2K3_Vista_7.exe"><s:text name="login.usbDrive" /></a><s:text name="login.usbDrive.info" />
                 &nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;<br/>
				 <a href="update/client.exe"><s:text name="login.commClient" /></a> <s:text name="login.commClient.info" /><br/>
				 <a href="update/M7.exe"><s:text name="login.m7Client" /></a><s:text name="login.m7Client.info" /><br/>
				 <a href="update/M7-U.exe"><s:text name="login.m7Client-U" /></a><s:text name="login.m7Client.info-U" /><br/>
				 <a href="update/M7ForAndroid.exe"><s:text name="login.m7Client.android" /></a><td><s:text name="login.m7Clinet.android.description" /></td><br/>
				 <a href="update/WMPPlugins.exe"><s:text name="login.videoPlayPlug" /></a><td><s:text name="login.videoPlayPlug.description" /></td><br/>
				 <a href="update/usb_driver.rar"><s:text name="login.usbDriver.android" /></a><td><s:text name="login.usbDriver.android.description" /></td><br/>
				 <td ><s:text name="login.version" />1.0.0.7</td><a href="/version.txt"><s:text name="login.detail" /></a>
<%--				 <a href="createTxt.action">生成TXT文件</a><br/>--%>
</body>
  </html>
