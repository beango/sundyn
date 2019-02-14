 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="fengge">&nbsp;</div>
<div style="width:640px;"><img src="<s:text name='sundyn.attendance.pic.title' />" /></div>
<div style="width:638px; height:290px;" class="kuang">
    <div class="fengge" style="height:25px;">&nbsp;</div>
    <div>
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
	       <tr>
	         <td style="border-color:#FFFFFF;" align="center"> <s:text name='sundyn.attendance.please' /> <input name="startDate" id="startDate" class="input_comm" style="width: 130px;" <s:text name="sundyn.language.calendar.setDay"/> value="${startDate}" /><s:text name='sundyn.attendance.to' /><input name="endDate" id="endDate" class="input_comm" style="width: 130px;" <s:text name="sundyn.language.calendar.setDay"/> value="${endDate}" /></td>
	         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="attendanceQueryAjax()" class="hand"/>  <img src="<s:text name='sundyn.attendance.pic.queqin' />" width="55" height="25" onclick="attendanceNO()" class="hand"/> </td>
	      </tr>
	     </table>
    </div>
    <div class="fengge" style="height:25px;">&nbsp;</div>
	<div class="online">
	  <div>
	  <s:text name='sundyn.attendance.attendanceNum' >
	  	<s:param>${fn:length(ls)}</s:param>
	  </s:text>
	  </div>
		<ul>
		  <c:forEach items="${ls}" var="employee">
		   <li> ${employee.Name} </li>
		  </c:forEach>  
	  </ul>
	</div>
    <div class="fengge" style="height:15px;">&nbsp;</div>
 </div> 