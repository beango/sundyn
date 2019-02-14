<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div style="width: 100%;">
	<img src="images/14_01_03.jpg" width="480" height="29" />
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
					<img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="employeeQueryKeyword()"   class="hand"  />
				</td>
				<td style="border-color: #FFFFFF;" align="left">
					<img src="<s:text name='sundyn.employee.pic.addEmployee' />" width="83" height="25"  onclick="employeeAddDialog()"   class="hand" />
				</td>
				<td style="border-color: #FFFFFF;" align="left">
					<img src="<s:text name='sundyn.employee.pic.seeFreeEmployee' />" width="108" height="25" onclick="employeeOutView()"   class="hand" />
				</td>
			</tr>
		</table>
	</div>
	<div class="fengge">
		&nbsp;
	</div>
    <table width="100%" class="layui-table">
        <thead>
        <tr>
            <td align="center" background="images/di_1.gif" style="padding:5px;" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.name"/></td>
            <td align="center" background="images/di_1.gif" style="padding:5px;" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.sex"/></td>
            <td align="center" background="images/di_1.gif" style="padding:5px;" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.employeeCardNum"/></td>
            <td align="center" background="images/di_1.gif" style="padding:5px;" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.tel"/></td>
            <td align="center" background="images/di_1.gif" style="padding:5px;" bgcolor="#FFFFFF" class="px13_1"><s:text name="sundyn.column.operation"/></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pager.pageList}" var="employee">
            <tr>
                <td>
                        ${employee.Name}
                    <c:if test="${employee.Name==null || employee.Name==''}">
                        &nbsp;
                    </c:if>
                </td>
                <td>
                    <c:if test="${employee.Sex==1}">
                        <s:text name='sundyn.male'/>
                    </c:if>
                    <c:if test="${employee.Sex!=1}">
                        <s:text name='sundyn.female'/>
                    </c:if>
                </td>
                <td>
                        ${employee.CardNum}
                    <c:if test="${employee.CardNum==null || employee.CardNum==''}">
                        &nbsp;
                    </c:if>
                </td>
                <td>
                        ${employee.Phone}
                    <c:if test="${employee.Phone==null || employee.Phone==''}">
                        &nbsp;
                    </c:if>
                </td>
                <td><a href="javascript:employeeIn(${employee.Id})"><s:text name="sundyn.employee.loadCurrentDept"/></a></td>
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