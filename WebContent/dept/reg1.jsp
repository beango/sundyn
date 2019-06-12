<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="center_04_right_01 kuang" style="width:500px;" align="center">
	<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border:0;">
	  <tr>
	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.column.datingName" /><s:text name="sundyn.colon" /> </td>
	    <td align="left">${dept.name}</td>
	  </tr>
	  <tr>
	    <td align="right"><s:text name="sundyn.column.deviceInfo" /><s:text name="sundyn.colon" /></td>
	    <td align="left">
		    ${dept.reMark}
	    </td>
	  </tr>
 	  <tr>
	    <td align="center" style="border-color:#FFFFFF;" colspan="2">
	    <div class="button left" onclick="deptEditDialog('<s:text name="sundyn.dept.modifyDating" />','${dept.fatherId}')"><s:text name="sundyn.dept.modifyDating" /></div>
        <div style="margin-left: 5px;" class="button left" onclick="deptCfgDialog('<s:text name="main.hallcfg" />','${dept.name}'); return false;"><s:text name="main.hallcfg" /></div>
	    <div style="margin-left: 5px;" class="button left" onclick="deptAddDialog(0,'<%=request.getParameter("deptId")%>','<s:text name="sundyn.dept.addWindow" />')" ><s:text name="sundyn.dept.addWindow" /></div>
	    <div style="margin-left: 5px;" class="button left" onclick="del()"><s:text name="sundyn.del" /></div>
	    </td>
	  </tr>
	</table>
</div>