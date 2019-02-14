<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
		<div id="man_zone">
			<div class="sundyn_main">
			<div class="fengge">&nbsp;</div>
				<table width="98%" class="t" >
					<tr>
						<td width="15%" class="tdtitle">
							员工卡号
						</td>

						<td width="15%" class="tdtitle">
							员工姓名
						</td>
						<td width="15%" class="tdtitle">
							机构
						</td>
						<td width="15%" class="tdtitle">
							评价结果
						</td>
						<td width="23%" class="tdtitle">
							评价时间
						</td>
						<td width="15%" class="tdtitle">
							录像
						</td>
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
							<td width="15%" class="td">
								${query.CardNum}
							</td>
							<td width="15%" class="td">
								${query.employeeName}
							</td>
							<td width="15%" class="td">
								${query.deptName}
							</td>
							<td width="15%" class="td">
								${query.keyName}
							</td>
							<td width="23%" class="td">
								${query.JieshouTime}
							</td>
							<td width="15%" class="td">
								<a href="http://10.95.10.86/playback.jsp?IP=${fn:replace(query.dept_camera_url,',','&Channel=')}&StartTime=${query.serviceDate}|${query.appriesTime}&StopTime=${query.serviceDate}|${query.CustorTime}&Port=8000&UserName=a&Password=a" target="_blank"><img src="images/lx.jpg"/></a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="sundyn_rows">
					${pager.pageTextCn}
				</div>
				<div class="sundyn_rows">
					 当前记录共记${pager.rowsCount}条
				</div>
				<div class="quicklyButton" onclick="quicklyAddDialog()"></div>
  			</div>
		</div>
		<div id="dialog" style="width: 479px; height: 328px; display: none;"></div>
 	</body>
</html>