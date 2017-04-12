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
 		    1.click "system" button<br/>
		    <img src="images/helpen11.gif"  /><br/>
 			2.click "function config"  <br/><img src="images/helpen101.gif" /><br />
 			3.system<br/>
 			&nbsp;&nbsp;&nbsp;3.1  monitor(if ticking , Enable monitor)  <br/>
 			&nbsp;&nbsp;&nbsp;3.2  not evalution    (if ticking , Enable the key of not evalution)<br/>
 			&nbsp;&nbsp;&nbsp;3.1  administrative levels     (if ticking , Enable administrative levels that the client indentify oneself)<br/>
 			&nbsp;&nbsp;&nbsp;3.1  The staff card binding    (if ticking , The staff card is binded to MAC address)<br/>
  			<img src="images/helpen102.gif" /><br />
  			4.information <br/>
  			&nbsp;&nbsp;&nbsp;4.1 unit name:displayed unit name on Login Interface.<br/>
  			&nbsp;&nbsp;&nbsp;4.2 logo; displayed logo on Login Interface;<br/>
  			<img src="images/helpen103.gif" /><br />
  			5.date
  			&nbsp;&nbsp;&nbsp;Date setting,set date that is on and off working. it is invalid  for all  evalution data that isn`t on working.。
  			<img src="images/helpen104.gif" /><br />
  			5.model  template setting,it is used in M7 and can be choosed .it is directly used upgating M7.<br />
  			<img src="images/helpen105.gif" /><br />
 			6.levels level setting ,it  can count and classify level oneself according to degree of contentment<br/>
 			<img src="images/helpen106.gif" /><br />
 			7.all set ，click  "save" button. 
 		  </div>
 	   </div>
 	   <div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
	</body>
</html>
