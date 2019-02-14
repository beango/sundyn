<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.playList.addTitle' />
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
							<s:text name='sudnyn.playList.playListName' />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="playListName" id="playListName" ></input>
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sudnyn.playList.description' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input type="text" name="playListDescription" id="playListDescription" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sudnyn.playList.please' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						   <c:forEach items="${ls}" var="play">
								<input type="checkbox" value="${play.playId}" id="key${play.playId}" />
								<label for="key${play.playId}">${play.playName}</label>   
						   </c:forEach>
						</td>
					</tr>
 				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />"  onclick="playListAdd()"
				 class="hand" />
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
		</div>
	</div>
</div>