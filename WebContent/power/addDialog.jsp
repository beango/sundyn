<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.role.addRole' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div  style="margin-top: 20px;width: 100%">
				<table width="100%" height="173" border="0" cellpadding="0"
					cellspacing="0" style="border-color: #e9f5fd;">
					<tr>
						<td style="border-color: #e9f5fd;" width="32%" align="right">
							<s:text name='sundyn.role.roleName' /><s:text name="sundyn.colon" /> 
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="name" id="name" onblur="powerExist()" /><span id="tip" style="font-size: 12px;color: red;"></span>
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.column.systemSetup' /><s:text name="sundyn.colon" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input type="checkbox" name="baseSet" id="baseSet" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.column.baseSetup' /><s:text name="sundyn.colon" /> 
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							 <input type="checkbox" name="dataManage" id="dataManage" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.role.atDept' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							 <select name="deptId" id="deptId">
									<c:forEach items="${list}" var="dept" varStatus="index">
										<option value="${dept.id}"
											<c:if test="index.index==0">selected="selected"</c:if>>
											<c:forEach begin="0" end="${dept.lenvel}">
											&nbsp; 
											</c:forEach>
											${dept.name}
										</option>
									</c:forEach>
							 </select>
						</td>
					</tr>
 				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />"  onclick="powerAdd()"
				 class="hand" />
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
		</div>
	</div>
</div>