<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
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
						<td width="8%" class="tdtitle">
							<s:text name='sundyn.column.employeeCardNum'/>
						</td>

						<td width="8%" class="tdtitle">
							<s:text name='sundyn.column.employeeName'/>
						</td>
						<td width="12%" class="tdtitle">
							<s:text name='sundyn.column.atDating'/>
 						</td>
						<td width="15%" class="tdtitle">
							<s:text name='sundyn.column.appriesResult'/>
						</td>
						<td width="18%" class="tdtitle">
							<s:text name='sundyn.column.appriesTime'/>
						</td>
						<td width="10%" class="tdtitle">
							录像
						</td>
						<td width="12%" class="tdtitle">
							业务办理时间
						</td>
						<td width="6%"  class="tdtitle">
						    <s:text name='sundyn.column.demo'/>
						</td> 
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
							<td width="8%" class="td">
								${query.CardNum}
							</td>
							<td width="8%" class="td">
								${query.employeeName}
							</td>
							<td width="12%" class="td">
								${query.fatherName}
							</td>
							<td width="15%" class="td">
								${query.keyName}
							</td>
							<td width="18%" class="td">
								<fmt:formatDate value="${query.JieshouTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td width="10%" class="td">
								<c:if test="${empty query.videofile}">
 							    <c:out value="没有录像"></c:out>
 							 </c:if>
 							 <c:if test="${!empty query.videofile}">
 							    <a  href="query/videoPlay.jsp?videofile=${query.videofile}"  target="_blank"><img src="images/lx.jpg"/></a>
 							 </c:if>  
							</td>
							 <td width="12%" class="td">
 							  <c:if test="${!empty query.businessTime}">
								${query.businessTime}
							  </c:if>
							  <c:if test="${empty query.businessTime}">
								 <c:out value="无记录"></c:out>
							  </c:if>
							</td>
							<td width="6%" class="td">
							  <a href="#" onclick="queryDemo(${query.id})" ><s:text name='sundyn.column.detail'/></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			  
				<c:if test="${pager.rowsCount == 0}">
					<div class="sundyn_rows">
						<s:text name="sundyn.system.checkM7Info.noRecord" />
					</div> 
				</c:if>
			    <c:if test="${pager.rowsCount > 0}">
					<div class="sundyn_rows">
						${pager.pageTextCn}
					</div>
					<div class="quicklyButton" style="background-image: url('<s:text name="sundyn.query.pic.exportExcel"/>')" onclick="queryExcel()"></div>
					<div class="quicklyButton" onclick="quicklyAddDialog()"></div>
				 </c:if>
  			</div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
		<div id="dialog" style="width: 479px; height: 328px; display: none;"></div>
 	</body>
</html>