<%@ page import="java.util.Date" %>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/ddtree.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?<%=new Date().getTime()%>"></script>
 		<style type="text/css">
			a:link { font-family:12px; color:#000; text-decoration:none}
			a:visited { font-family:12px; color: #000; text-decoration:none}
			a:hover { font-family:12px; color: #09437d; text-decoration:none}
			a:active { font-family:12px; color: #000; text-decoration:none}
			.td{
				font-size:12px;
				color:#09437d;
				font-family:"宋体";
				line-height:210%;
				font-weight: bold;
			}
			.td1{
				font-size:12px; color:#333333; font-family:"宋体"; line-height:210%;
			}
		</style>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge" style="height: 15px;">
				&nbsp;
			</div>
			<div class="center_04" style="width: 730px;">
				<div class="center_04_left">
					<div>
						<img src="<s:text name='sundyn.query.pic.selectDept' />" />
					</div>
					<div class="center_04_left_01 kuang">
						<div class="dtree" id="tree" style="text-align: left;">
							<script type="text/javascript">
							d = new dTree('d');
							<c:forEach items="${list}" var="dept">
								d.add(${dept.id},${dept.fatherId},'${dept.name}','javascript:employeeManage(${dept.id})');
							</c:forEach>
							document.write(d);
						</script>
						</div>
					</div>
				</div>
				<input type="hidden" id="deptId" />
				<div class="center_04_right" style="width: 480px; height: 400px;" id="employeeView">
					<div style="width: 100%;">
						<img src="<s:text name='sundyn.employee.pic.employeeManager'/>" width="480" height="29" />
					</div>
					<div style="height: 380px;overflow: hidden;" class="kuang">
						<s:text name='sundyn.employee.info' /><br/>
						<div   class="employeeHr"> </div>
						<img src="<s:text name='sundyn.employee.pic.employeeExcel' />" class="hand" onclick="employeeExcel()" />
					</div>
				</div>
			</div>
		</div>
		<div id="dialog" style="width: 700px; display: none;">
		</div>
	</body>
</html>
