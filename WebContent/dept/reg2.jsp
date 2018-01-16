<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div><img src="<s:text name='sundyn.dept.pic.deptManage' />" /></div>
<div class="center_04_right_01 kuang" align="center">
	<table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
	  <tr>
	    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.deptName" /></td>
	    <td width="40%" align="left" style="border-color:#FFFFFF;">${dept.name}</td>
	    </tr>
 	  <tr>
	    <td align="center" style="border-color:#FFFFFF;" colspan="2">
	    <div class="button left " onclick="deptEditDialog()"   ><s:text name="sundyn.dept.modifyDept" /></div>
	    <div class="button left " style="background-image: url('images/button6_bg.gif');width: 100px;"  onclick="deptAddDialog(2)" ><s:text name="sundyn.dept.addDept" /></div>
	    <div class="button left " onclick="deptAddDialog(1)"  ><s:text name="sundyn.dept.addDating" /></div>
	    <div class="button left " onclick="del()"  ><s:text name="sundyn.del" /></div>
	    </td>
	  </tr>
	</table>
</div>

