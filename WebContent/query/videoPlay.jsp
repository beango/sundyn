<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
	</head>
<% String videofile=(String)request.getParameter("videofile");
%>

	<object id="PlayerEx2"
		classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="840"
		height="634"
		 codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715" 
		>
		<param name="autoStart" value="true" />
		<param name="volume" value="100">
		<param name="Filename" value="\download\<%=videofile%>"/>
		<param name="src" value="\download\<%=videofile%>"/>
		<param name="URL" value="\download\<%=videofile%>" />
		<param name="stretchToFit" value="1"/>
		<param name="enabled" value="1">
		<embed autostart="true"
			src="\download\<%=videofile%>"
			type="video/x-ms-wmv" width="640" height="434" controls="ImageWindow"
			console="cons">
		</embed>
	</object>

	

	<br/><br/> <a href="/update/WMPPlugins.exe">视频播放插件</a><td>如果视频没有声音， 请下载安装此插件</td><br/>

</html>
