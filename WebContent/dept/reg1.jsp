<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div><img src="<s:text name='sundyn.dept.pic.deptManage' />" /></div>
<div class="center_04_right_01 kuang" align="center">
	<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
	  <tr>
	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.column.datingName" /><s:text name="sundyn.colon" /> </td>
	    <td width="40%" align="left" style="border-color:#FFFFFF;">${dept.name}</td>
	  </tr>
	  <tr>
	    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.column.deviceInfo" /><s:text name="sundyn.colon" /></td>
	    <td align="left" style="border-color:#FFFFFF;">
		    ${dept.reMark}
	    </td>
	  </tr>
 	  <tr>
	    <td align="center" style="border-color:#FFFFFF;" colspan="2" >
	    <div style="margin: 0px auto;width: 260px;"><div class="button left" onclick="deptEditDialog(1)"  ><s:text name="sundyn.dept.modifyDating" /></div>
	    <div class="button left" onclick="deptAddDialog(0)" style="margin-left: 5px;" ><s:text name="sundyn.dept.addWindow" /></div>
	    <div class="button left" onclick="del()" style="margin-left: 5px;"><s:text name="sundyn.del" /></div></div>
	    </td>
	  </tr>
	</table>
</div>