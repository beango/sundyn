<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div style="width: 100%;">
	<img src="<s:text name='sundyn.employee.pic.employeeManager'/>" width="480" height="29" />
</div>
<div style="height: 380px;" class="kuang">
	<div class="fengge">
		&nbsp;
	</div>
	<div>
		<table width="90%" height="41" border="0" cellpadding="0"
			cellspacing="0" style="border-color: #FFFFFF;">
			<tr>
				<td style="border-color: #FFFFFF;" align="center">
					<input type="text" name="keyword" id="keyword" value="${keyword}" class="input_comm" />
				</td>
				<td style="border-color: #FFFFFF;" align="left">
					<img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="employeeQueryKeyword()"  class="hand"  />
				</td>
				<td style="border-color: #FFFFFF;" align="left">
					<img src="<s:text name='sundyn.employee.pic.addEmployee' />" width="83" height="25"  onclick="employeeAddDialog('<s:text name="sundyn.employee.addEmployee" />')"   class="hand" />
				</td>
				<td style="border-color: #FFFFFF;" align="left">
					<img src="<s:text name='sundyn.employee.pic.seeFreeEmployee' />" width="108" height="25" onclick="employeeOutView()" class="hand" />
				</td>
			</tr>
		</table>
	</div>
	<div class="fengge">
		&nbsp;
	</div>
 	<table width="100%" class="layui-table">
        <tr>
            <td align="center" background="images/table_bg_03.jpg" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.name"/></td>
            <td align="center" background="images/table_bg_03.jpg" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.sex"/></td>
            <td align="center" background="images/table_bg_03.jpg" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.employeeCardNum"/></td>
            <td align="center" background="images/table_bg_03.jpg" bgcolor="#FFFFFF" class="px13_1">在线</td>
            <td align="center" background="images/table_bg_03.jpg" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.operation"/></td>
        </tr>
		      <tbody>
              <c:forEach items="${pager.pageList}" var="employee">
                  <tr>
                      <td align="center">
                              ${employee.Name}
                      </td>
                      <td align="center">
                          <c:if test="${employee.Sex==1}">
                              <s:text name='sundyn.male'/>
                          </c:if>
                          <c:if test="${employee.Sex!=1}">
                              <s:text name='sundyn.female'/>
                          </c:if>
                      </td>
                      <td align="center">${employee.CardNum}</td>
                      <td align="center">
                          <c:if test="${online!=null}">
                              <c:forEach items="${online}" var="onlineitem">
                                  <c:choose>
                                      <c:when test="${onlineitem.key==employee.id}">在线</c:when>
                                      <c:otherwise>不在线</c:otherwise>
                                  </c:choose>
                              </c:forEach>
                          </c:if>
                      </td>
                      <td align="center"><a href="javascript:employeeEditDialog(${employee.Id},'<s:text name="sundyn.employee.editEmployee" />')"><s:text name="sundyn.modify"/></a> <s:text name="sundyn.separator"/>
                          <a href="javascript:employeeDel(${employee.Id})"><s:text name="sundyn.del"/></a><s:text name="sundyn.separator"/>
                          <a href="javascript:employeeOut(${employee.Id})"><s:text name="sundyn.employee.out"/></a><s:text name="sundyn.separator"/>
                          <a href="javascript:employeeReset(${employee.Id})"><s:text name="sundyn.employee.resetPassword"/></a></td>
                  </tr>
              </c:forEach>
              </tbody>
    </table> 
	<div class="fengge" style="height: 15px;">
		&nbsp;
	</div>
    <div>
        ${pager.pageTextAjax}
    </div>
</div>