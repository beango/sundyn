<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="FCK" uri="http://java.fckeditor.net" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.play.addTitle' />
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
						<td style="border-color: #e9f5fd;" width="20%" align="right">
							<s:text name='sundyn.play.playName' />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="playName" id="playName" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							 <s:text name='sundyn.play.playType' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						  <select id="playType" onchange="playTypeChange(this.value)">
						     <option value="img" selected="selected"><s:text name='sundyn.play.pic' /></option>
						     <option value="text"><s:text name='sundyn.play.txt' /></option>
						     <option value="video"><s:text name='sundyn.play.vio' /></option>
						     <option value="doc"><s:text name='sundyn.play.doc' /></option>
						  </select>
							  
						</td>
					</tr>
					<tr id="other" >
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.playSource' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							 <input type="text" name="playSource" id="playSource" readonly="readonly" style="background-color: #c3c3c3;"/>
 							 <form id="pic" enctype="multipart/form-data" name="pic"
								action="playUpload.action" method="post">
								<input type="hidden" name="imgName" id="imgName" />
								<input type="file" name="img" id="img" onchange="getFileName()"   />
								<input type="button" value=" <s:text name='sundyn.upload' />" onclick="playupload();"/>
							</form>
   						</td>
					</tr>
					<tr id="txt"  style="display:none;">
 						<td style="border-color: #e9f5fd;" align="right">
							 <s:text name='sundyn.play.playSource' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<s:text name='sundyn.play.biaoti' /><input type="text" id="playTitle" name="title"></input><br/>
<%-- 							<FCK:editor instanceName="playContent"  toolbarSet="sundyn" basePath="fckeditor/" value="" >--%>
<%-- 							</FCK:editor>--%>
                               <textarea rows="10" cols="30" id="playContent" name="playContent"></textarea>
  						</td>
					</tr>
					
					<tr>
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.playTime' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						 <input name="playTimes" id="playTimes" value="5000" /><s:text name='sundyn.play.info' />
						</td>
					</tr>
					<tr>
 						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sundyn.play.index' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						   <input name="playIndex" id="playIndex" value="0"/> 
						</td>
					</tr>
  				</table>
			</div>
		</div>
	</div>
	<div class="bottom">
			<img src="<s:text name='sundyn.pic.ok' />"  onclick="playAdd()"
				 class="hand" />&nbsp;
			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
	</div>
</div>