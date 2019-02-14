<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="fengge">&nbsp;</div>
<div style="width:640px;"><img src="<s:text name='sundyn.attendance.pic.title' />" /></div>
<div style="width:638px; height:290px;" class="kuang">
    <div class="fengge" style="height:25px;">&nbsp;</div>
    <div>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
	       <tr>
	         <td style="border-color:#FFFFFF;" align="center"><s:text name='sundyn.attendance.please' /><input name="startDate" id="startDate" class="input_comm" style="width: 130px;" <s:text name="sundyn.language.calendar.setDay"/> value="${startDate}" /><s:text name='sundyn.attendance.to' /><input name="endDate" id="endDate" class="input_comm" style="width: 130px;" <s:text name="sundyn.language.calendar.setDay"/> value="${endDate}" /></td>
	         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="attendanceQueryAjax()" class="hand"/> <img src="<s:text name='sundyn.attendance.pic.queqin' />" width="55" height="25" onclick="attendanceNO()" class="hand"/></td>
	      </tr>
	     </table>
    </div>
    <div class="fengge" style="height:25px;">&nbsp;</div>
	<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right:1px solid #bad6ec;">
	  <tr>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.name' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.cardNum' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.deptName' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.date' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.upTime' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.downTime' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.attendance'/></td>
	  </tr>
	   <c:forEach items="${pager.pageList}" var="attendance">
			<tr>
				<td style="text-align: center;">
					${attendance.Name}
				</td>
				<td style="text-align: center;">
			   	   ${attendance.CardNum}
				</td>
				<td style="text-align: center;">
				   ${attendance.deptName}
				</td>
				<td style="text-align: center;">
				   ${attendance.attendDate}
				</td>
				<td style="text-align: center;">
					 ${attendance.attendUp}
				</td>
				<td style="text-align: center;">
					 ${attendance.attendDown}
				</td>
			    <td style="text-align: center;">
				    <c:if test="${attendance.attendUp > sam   }"> <span style="color: red;font-size: 12px;"><s:text name='sundyn.attendance.late' /></span></c:if>
				    <c:if test="${attendance.attendDown < epm   }"><span style="color: red;font-size: 12px;"><s:text name='sundyn.attendance.leaveEarly' /></span></c:if>
				    <c:if test="${attendance.attendUp < sam &&  attendance.attendDown > epm }"><s:text name='sundyn.attendance.normal' /></c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
    <div class="fengge" style="height:15px;">&nbsp;</div>
	<div>${pager.pageTextAjax}</div>
</div> 