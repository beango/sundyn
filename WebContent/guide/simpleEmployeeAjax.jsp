<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<table  cellspacing="0" cellpadding="0"  >
  <tr>
    <td class="guidetitle"><s:text name='sundyn.column.name'/></td>
    <td class="guidetitle"><s:text name='sundyn.column.sex'/></td>
    <td class="guidetitle"><s:text name='sundyn.column.employeeCardNum'/></td>
    <td class="guidetitle"><s:text name='sundyn.column.operation'/></td>
  </tr>
  <c:forEach items="${ls}" var="employee">
  <tr>
    <td class="text">${employee.name}</td>
    <td class="text">
       <c:if test="${employee.Sex==1}">
		   <s:text name='sundyn.male'/>
	   </c:if>
	   <c:if test="${employee.Sex!=1}">
		   <s:text name='sundyn.female'/>
	   </c:if>
    </td>
    <td class="text">${employee.cardnum}</td>
    <td class="text"><a href="javascript:guideSimpleEmployeeEdit(${employee.Id})"><s:text name='sundyn.modify'/></a>&nbsp;<a href="javascript:guideSimpleEmployeeDel(${employee.Id})"><s:text name='sundyn.del'/></a> </td>
  </tr>
   </c:forEach>
</table>