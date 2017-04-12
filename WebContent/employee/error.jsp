<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div style="width: 100%;">
	<img src="<s:text name='sundyn.employee.pic.employeeManager'/>" width="480" height="29" />
</div>
<div style="height: 380px;" class="kuang">
	<s:text name='sundyn.employee.info' /><br/>
	<div   class="employeeHr"> </div> 
	<img src="<s:text name='sundyn.employee.pic.employeeExcel' />" class="hand" onclick="employeeExcel()" />
</div>