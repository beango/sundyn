<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title></title>
		<style type="text/css">
table {
	font-size: 12px;
	font-weight: 10px;
	text-align: center;
}
body{
background-color: #fff;
}
</style>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0
			id=wb name=wb width=0></OBJECT>
			<div class="sundyn_main" style="width:600px;">
				<div id="printTop">
					<table align="center" width="100%">
						<tr>
							<td id="pr" style="border: 1px solid #bad6ec;">
								<input type=button name=button_print value="打印"
									onclick="javascript:printit();" />
								<input type=button name=button_ps value="打印页面设置"
									onclick="printsetup();"/>
								<input type=button name=button_show value="打印预览"
									onclick="printpreview();"/>
								<input type=button name=button_fh value="关闭"  onclick="javascript:window.close();"/>
								<br />
								<font color=red><strong> 请设置为A4纸、横向进行打印！</strong> </font>
							</td>
						</tr>
					</table>
				</div>
				<div class="sundyn_row">
				<c:forEach items="${list}" var="total" varStatus="index">
				   <c:if test="${index.index  mod 20 ==0}">
				        <div class="sundyn_row" align="left">
					     以下是按人员汇总${startDate}到${endDate}的评价信息
				        </div>
						<table width="600" border="1" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">一级指标</td>
							    <td width="20%"   align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">二级指标</td>
							    <td width="20%"   align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">三级指标</td>
							    <td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">四级指标</td>
							    <td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">满意度</td>
							</tr>
							 <c:forEach items="${list}" var="d">
								  <tr>
								 	 <td>${d.oneName}</td>
								 	 <td>${d.twoName}</td>
								 	 <td>${d.threeName}</td>
									 <td>${d.businessName}</td>
									 <td>${d.d}</td> 
								  </tr>
							  </c:forEach>
					     </table>
					     <div style="width:100%;height: 20px;"> &nbsp;</div>
					   </c:if>
				  </c:forEach>
				</div>
			</div>
 	</body>
</html>
							