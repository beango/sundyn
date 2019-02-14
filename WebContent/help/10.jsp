<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/ddtree.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
	<script>
	</script>
 		<div id="man_zone">
 		  <div style="margin-left: 10px;width: 90%;text-align: left;">
 		    1、单击系统管理<br/><img src="images/help1.gif" /><br />
 			2、单击"软件设置"<br/><img src="images/help29.gif" /><br />
 			3、系统设置<br/>
 			&nbsp;&nbsp;&nbsp;3.1监控:配置摄像头使用。   <br/>
 			&nbsp;&nbsp;&nbsp;3.2未评价:是否使用“未评价”按键盘，如果勾上，那么是未评价，如果不勾上，为满意，或不满意，在“评价器管理”里设置。<br/>
 			&nbsp;&nbsp;&nbsp;3.1星级: 勾上用，自定义星级，不勾上，用系统自动的星级。<br/>
 			&nbsp;&nbsp;&nbsp;3.1员工卡绑定:可以把员工绑到窗口上，实现自动登录。<br/>
  			<img src="images/help30.gif" /><br />
  			4、信息设置<br/>
  			&nbsp;&nbsp;&nbsp;4.1单位名称:登陆界面显示的单位名称;<br/>
  			&nbsp;&nbsp;&nbsp;4.2设备底部显示信息:非常规设置，不做解释.<br/>
  			&nbsp;&nbsp;&nbsp;4.3提示服务请求地址:非常规设置，不做解释.<br/>
  			&nbsp;&nbsp;&nbsp;4.4logo:登陆界面显示的logo;<br/>
  			<img src="images/help31.gif" /><br />
  			5、时间设置
  			&nbsp;&nbsp;&nbsp;，设置上下班的时间，所有不在上下班时间内的评价数据，为无效数据。
  			<img src="images/help32.gif" /><br />
  			5、模板设置 ,用与设备，可以选择使用模板，设备自动升级后，直接使用<br />
  			<img src="images/help33.gif" /><br />
 			6、星级设置,根据满意度百分比，自己计算星级<br/>
 			<img src="images/help34.gif" /><br />
 			7、所有设置，单击“保存”按钮生效. 
 			 <img src="images/help35.gif" /><br />
 		  </div>
 	   </div>
 	   <div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
	</body>
</html>
