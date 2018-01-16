<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div><img src="<s:text name='sundyn.dept.pic.deptManage' />" /></div>
<div class="center_04_right_01 kuang" align="center">
	<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
	  <tr>
	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" /> </td>
	    <td width="40%" align="left" style="border-color:#FFFFFF;">${dept.name}</td>
	  </tr>

<%--	  <tr>--%>
<%--	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.clientConnectType" /><s:text name="sundyn.colon" /> </td>--%>
<%--	    <td width="40%" align="left" style="border-color:#FFFFFF;">--%>
<%--		    <c:if test="${dept.client_type==1}">--%>
<%-- 				<s:text name="sundyn.guide.comServer" />--%>
<%--			</c:if>--%>
<%--			<c:if test="${dept.client_type==2}">--%>
<%--				<s:text name="sundyn.guide.clientProgram" />--%>
<%--			</c:if>--%>
<%--		</td>--%>
<%--	  </tr>--%>
	  <tr>
	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.deviceInfo" /></td>
	    <td width="40%" align="left" style="border-color:#FFFFFF;">${dept.remark}</td>
	  </tr>
 	  <tr>
	    <td align="center" style="border-color:#FFFFFF;" colspan="2">
	    <div style="width: 270px;margin: 0px auto;">
	    <div class="button left" onclick="deptEditDialog()" ><s:text name="sundyn.dept.modifyWindow" /></div>
	    <div class="button left" onclick="bindWeburlDialog()" style="margin-left: 4px;" ><s:text name="sundyn.weburl.bind" /></div>
	    <div class="button left" onclick="del()" style="margin-left: 4px;"><s:text name="sundyn.del" /> </div>
	    </div>
 	    </td>
	  </tr>
	</table>
</div>