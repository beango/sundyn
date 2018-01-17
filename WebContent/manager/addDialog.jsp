<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name="sundyn.user.addUser" />
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
							<s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="name" id="name" onblur="managerExist()" /><span id="tip" style="color: red;font-size: 12px;"></span>
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name="sundyn.column.realName" /><s:text name="sundyn.colon" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input name="realname" id="realname" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name="sundyn.user.role" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<select id="userGroupId" >
								<c:forEach items="${list}" var="power" varStatus="index">
									<option
										<c:if test="${index.index==0}"> selected="selected"</c:if>
										value="${power.id}">
										${power.name}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name="sundyn.user.tipType" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<select name="remark" id="remark"
								onchange="tipChange(this.value)">
								<option value="0">
									<s:text name="sundyn.user.select1" />
								</option>
								<option value="1">
									<s:text name="sundyn.user.select2" />
								</option>
								<option value="2">
									<s:text name="sundyn.user.select3" />
								</option>
								<option value="3" selected="selected">
									<s:text name="sundyn.user.select4" />
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name="sundyn.user.tipMobile" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input name="ext1" id="ext1" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name="sundyn.user.tipPc" />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input name="ext2" id="ext2" />
						</td>
					</tr>
                    <tr>
                        <td style="border-color: #e9f5fd;color:red;" align="center" colspan="2">
                        默认密码：123456
                        </td>
                    </tr>
				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />" onclick="managerAdd()"
				 class="hand" />
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />"   onclick="closeDialog()"
				 class="hand">
		</div>
	</div>
</div>