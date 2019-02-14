<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
	 	 <STYLE type="text/css">
			 v\:* { Behavior: url(#default#VML) }
	         o\:* { behavior: url(#default#VML) }
	         #PieDiv{
		         font-family:arial;
 		      	 line-height: normal;
  	         }
  	         #PieDiv div{
  	            font-size: 9px;
             }
		 </STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 	</head>
	<body>
	<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}"  />
	<input type="hidden" name="startDate" id="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" id="endDate" value="${endDate}" />
	<input type="hidden" name="keys" id="keys" value="${keys}" />
	<input type="hidden" name="id" id="id" value="${id}" />
		<div id="man_zone">
			<div class="sundyn_main">
			<div class="fengge">&nbsp;</div>
				<table width="98%" class="t" >
					<tr>
						<td width="10%" class="tdtitle">
							业务流水号
						</td>
 						<td width="10%" class="tdtitle">
							员工工号
						</td>
						<td width="10%" class="tdtitle">
							姓名
						</td>
						<td width="10%" class="tdtitle">
							受理时间
						</td>
						<td width="10%" class="tdtitle">
							评价时间
						</td>
						<td width="10%" class="tdtitle">
							服务号码
						</td>
						<td width="10%"  class="tdtitle">
						    受理窗口
						</td>
						<td width="10%"  class="tdtitle">
						    评价结果
						</td>
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
							<td   class="td">
								${query.ext1}
							</td>
							<td   class="td">
								${query.userName}
							</td>
							<td   class="td">
								${query.employeeName}
							</td>
							<td   class="td">
 								<fmt:formatDate value="${query.ext9}" type="both"/>
							</td>
							<td   class="td">
							 
								<fmt:formatDate value="${query.JieshouTime}" type="both"/>
							</td>
							<td   class="td">
								${query.ext5}
							</td>
							<td   class="td">
							    ${query.deptName}
							</td>
							<td   class="td">
							    ${query.keyName}
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="sundyn_rows">
					${pager.pageTextCn}
				</div>
				<div class="quicklyButton" style="background-image: url('images/back_excel.gif')" onclick="threeExcel()"></div>
 			</div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
		<div id="dialog" style="width: 400px; height: 328px; display: none;"></div>
 	</body>
</html>