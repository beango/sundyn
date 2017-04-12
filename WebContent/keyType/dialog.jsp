<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name="sundyn.apprieser.dialogTitle" />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0">
				<tr>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1">
						<s:text name="sundyn.column.keyValue" />
					</td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1">
						<s:text name="sundyn.column.keyDescription" />
					</td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1">
						<s:text name="sundyn.column.quanValue" />
					</td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1">
						<s:text name="sundyn.column.isContent" />
					</td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1">
						<s:text name="sundyn.column.isUse" />
					</td>
				</tr>
				<c:forEach items="${list}" var="keyType">
					<tr>
						<td style="text-align: center;">
							<s:text name="sundyn.apprieser.key" />${keyType.id}
						</td>
						<td style="text-align: center;">
							<input type="text" value="${keyType.name}" id="name${keyType.id}" />
						</td>
						<td style="text-align: center;">
							<input type="text" value="${keyType.ext1}" id="ext1${keyType.id}" style="width: 30px;"/>
						</td>
						<td style="text-align: center;">
							<input type="checkbox" id="isJoy${keyType.id}"
								<c:if test="${keyType.isJoy=='on'}">checked="checked"</c:if> />
						</td>
						<td style="text-align: center;">
							<input type="checkbox" id="yes${keyType.id}"
								<c:if test="${keyType.yes==true  }">checked="checked"</c:if> />
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
	<div class="bottom">
			<img src="<s:text name='sundyn.pic.save' />" onclick="keyTypeEditAll()"
				class="hand" />
			<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()"
				class="hand" />
			
	</div>
</div>