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
 		  <div class="fengge" style="height:15px;">&nbsp;</div>
 		  <div class="guide">
 		     <div class="tip">
 		        <h1>配置向导</h1>
 		        <h2>添加完成,你可以连上客户端测试了。</h2>
 		        <h3>1、要确保网络正常</h3>
 		        <h3>2、要确保客户端设置正确，IP，端口</h3>
 		        <h3>3、欢迎使用设备</h3>
  		        <img src="images/guide_end.gif" onclick="endGuide()"/>
 		     </div>
 		  </div>
  	   </div>
 	   <div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
	</body>
</html>
