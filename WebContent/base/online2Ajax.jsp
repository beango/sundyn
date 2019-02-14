<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div style="width: 100%;">
	<img src="<s:text name='sundyn.online.pic.title2'/>" width="480" height="29" />
</div>
<div style="height: 380px;" class="kuang">
	<div class="fengge">
		&nbsp;
	</div>
	<div>
		 当前部门在线员工人数：${num}  
		 当前部门所有员工人数：${allNum}  
 	</div>
	<div class="fengge">
		&nbsp;
	</div>
 	<table width="100%" border="0" cellspacing="1" cellpadding="0" style="border-top: 1px solid #bdd9ee;">
    		  <tr>
      			  <td height="27" align="center" background="images/di_1.gif" bgcolor="#FFFFFF" class="td" style="border-left-width:0px;"><s:text name="sundyn.column.name"/></td>
       			  <td align="center" background="images/di_1.gif" bgcolor="#FFFFFF" class="td"><s:text name="sundyn.column.sex"/></td>
       			  <td align="center" background="images/di_1.gif" bgcolor="#FFFFFF" class="td"><s:text name="sundyn.column.employeeCardNum"/></td>
		          <td align="center" background="images/di_1.gif" bgcolor="#FFFFFF" class="td"><s:text name="sundyn.column.windowName"/></td>
		          <td align="center" background="images/di_1.gif" bgcolor="#FFFFFF" class="td">是否在线</td>
		      </tr>
		      <c:forEach items="${pager.pageList}" var="employee">
			      <tr>
			        <td height="30" align="center" bgcolor="#FFFFFF" class="td1" style="border-left-width:0px;">
			         ${employee.employeeName}
					 <c:if test="${employee.employeeName==null || employee.employeeName==''}">
					 &nbsp;
					 </c:if>
					</td>
			        <td align="center" bgcolor="#FFFFFF" class="td1">
			         	<c:if test="${employee.Sex==1}">
						   <s:text name='sundyn.male'/>
						</c:if>
						<c:if test="${employee.Sex!=1}">
						   <s:text name='sundyn.female'/>
						</c:if>
 			        </td>
			        <td align="center" bgcolor="#FFFFFF" class="td1">
			          ${employee.CardNum}
					  <c:if test="${employee.CardNum==null || employee.CardNum==''}">
					  &nbsp;
					  </c:if>
 			        </td>
			        <td align="center" bgcolor="#FFFFFF" class="td1">
				          ${employee.deptName}
						  <c:if test="${employee.deptName==null || employee.deptName==''}">
						  &nbsp;
						  </c:if>
			        </td>
			        <td align="center" bgcolor="#FFFFFF" class="td1">
				         ${employee.isline}
			        </td>
			      </tr>
		     </c:forEach>
    </table> 
	<div class="fengge" style="height: 15px;">
		&nbsp;
	</div>
	<div>
		 ${pager.pageTextAjax}
	</div>
</div>