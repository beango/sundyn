<%@ page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div style="height: 100%;" class="kuang">
    <div>
        <table width="90%" height="41" border="0" cellpadding="0"
               cellspacing="0" style="border-color: #FFFFFF;">
            <tr>
                <td style="border-color: #FFFFFF;" align="center">
                    姓名或工号：<input type="text" name="keyword" id="keyword" value="${keyword}" class="input_comm"/>
                </td>
                <td style="border-color: #FFFFFF;" align="left">
                    <img src="<s:text name='sundyn.pic.query' />" width="55" height="25"
                         onclick="employeeQueryKeyword()" class="hand"/>
                </td>
                <td style="border-color: #FFFFFF;" align="left">
                    <img src="<s:text name='sundyn.employee.pic.addEmployee' />" width="83" height="25"
                         onclick="employeeAddDialog('<s:text name="sundyn.employee.addEmployee"/>')" class="hand"/>
                </td>
                <%--<td style="border-color: #FFFFFF;" align="left">
                    <img src="<s:text name='sundyn.employee.pic.seeFreeEmployee' />" width="108" height="25"
                         onclick="employeeOutView()" class="hand"/>
                </td>--%>
            </tr>
        </table>
    </div>
    <table width="100%" class="tablelist">
        <thead>
        <tr>
            <th align="center" style="text-align:center;" background="images/table_bg_03.jpg" bgcolor="#FFFFFF"><s:text
                    name="sundyn.column.name"/></th>
            <th align="center" style="text-align:center;" background="images/table_bg_03.jpg" bgcolor="#FFFFFF"><s:text
                    name="sundyn.column.sex"/></th>
            <th align="center" style="text-align:center;" background="images/table_bg_03.jpg" bgcolor="#FFFFFF">工号</th>
            <%--<th align="center" background="images/table_bg_03.jpg">在线</th>--%>
            <th align="center" style="text-align:center;" background="images/table_bg_03.jpg"><s:text
                    name="sundyn.column.operation"/></th>
        </tr>
        </thead>
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
                <td align="center">${employee.cardnum}</td>
               <%-- <td align="center">
                    <c:if test="${online!=null}">
                        <c:if test="${fn:contains(online, employee.id)}">
                            在线
                        </c:if>
                        <c:if test="${!fn:contains(online, employee.id)}">
                            不在线
                        </c:if>
                    </c:if>
                </td>--%>
                <td align="center"><a
                        href="javascript:employeeEditDialog(${employee.Id},'<s:text name="sundyn.employee.editEmployee" />')"><s:text
                        name="sundyn.modify"/></a> <s:text name="sundyn.separator"/>
                    <a href="javascript:employeeDel(${employee.Id})"><s:text name="sundyn.del"/></a><s:text
                            name="sundyn.separator"/>
                    <%--<a href="javascript:employeeOut(${employee.Id})"><s:text name="sundyn.employee.out"/></a>--%><s:text
                            name="sundyn.separator"/>
                    <a href="javascript:employeeReset(${employee.Id})"><s:text
                            name="sundyn.employee.resetPassword"/></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>
        ${pager.pageTextAjax}
    </div>
</div>