<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="FCK" uri="http://java.fckeditor.net" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.play.editTitle' />
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
							<s:text name='sundyn.play.playName' />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input type="hidden" name="playId" id="playId" value="${p.playId}" />
							<input name="playName" id="playName" value="${p.playName}" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							 <s:text name='sundyn.play.playType' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						  <select id="playType" onchange="playTypeChange(this.value)">
 						     <option value="img" <c:if test="${p.playType=='img'}">selected="selected"</c:if> ><s:text name='sundyn.play.pic' /></option>
						     <option value="text" <c:if test="${p.playType=='text'}">selected="selected"</c:if> ><s:text name='sundyn.play.txt' /></option>
						     <option value="html" <c:if test="${p.playType=='html'}">selected="selected"</c:if> ><s:text name='sundyn.play.html' /></option>
						     <option value="video" <c:if test="${p.playType=='video'}">selected="selected"</c:if> ><s:text name='sundyn.play.vio' /></option>
						     <option value="doc" <c:if test="${p.playType=='doc'}">selected="selected"</c:if> ><s:text name='sundyn.play.doc' /></option>
						  </select>
							  
						</td>
					</tr>
					<tr id="other"  <c:if test="${p.playType == 'text'}">  style="display:none;" </c:if> >
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.playSource' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							 <input type="text" name="playSource" id="playSource" readonly="readonly" style="background-color: #c3c3c3;" value="${p.playSource}"    />
 							 <form id="pic" enctype="multipart/form-data" name="pic"
								action="employeeAdd.action" method="post">
								<input type="hidden" name="imgName" id="imgName" />
								<input type="file" name="img" id="img" onblur="getFileName()" style="width: 180px;" />
								 <input type="button" value="<s:text name='sundyn.upload' />" onclick="playupload();"/>
							</form>
 						</td>
					</tr>
 					<tr id="txt"  <c:if test="${p.playType != 'text'}">  style="display:none;" </c:if>>
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.playSource' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<s:text name='sundyn.play.biaoti' /><input type="text" id="playTitle" name="playTitle" value="${p.playTitle}"></input><br/>
<%-- 							<FCK:editor instanceName="playContent"  toolbarSet="sundyn" basePath="fckeditor/" value="${p.playContent }" >--%>
<%-- 							</FCK:editor>--%>
 							<textarea rows="10" cols="30" id="playContent" name="playContent">${p.playContent }</textarea>
  						</td>
					</tr>
 					<tr>
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.playTime' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						 <input name="playTimes" id="playTimes" value="${p.playTimes}" /><s:text name='sundyn.play.info' />
						</td>
					</tr>
					<tr>
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.index' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						   <input name="playIndex" id="playIndex" value="${p.playIndex}" /> 
						</td>
					</tr>
  				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
			<img src="<s:text name='sundyn.pic.ok' />"  onclick="playEdit()"
				 class="hand" />&nbsp;
			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
	</div>
</div>