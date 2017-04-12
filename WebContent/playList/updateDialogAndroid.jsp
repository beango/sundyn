<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sudnyn.playList.updateM7' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			
			<div style="margin-top: 20px;">
			<input type="hidden" name="Version" id="Version"   value="${config.Version }"/>
			<input type="hidden" name="Welcometime" id="Welcometime"  value="${config.Welcometime }" />
			<input type="hidden" name="Approvertime" id="Approvertime"  value="${config.Approvertime }"  />
			<input type="hidden" name="Shutdownhh" id="Shutdownhh"  value="${config.Shutdownhh }"  />
			<input type="hidden" name="Shutdownmm" id="Shutdownmm"  value="${config.Shutdownmm }"  />
			<input type="hidden" name="Boothh" id="Boothh"  value="${config.Boothh }"  />
			<input type="hidden" name="Bootmm" id="Bootmm"  value="${config.Bootmm }"  />
			<input type="hidden" name="ShowEmployeePage" id="ShowEmployeePage"  value="${config.ShowEmployeePage }"  />
			 <input type="hidden" name="Type" id="Type"  value="${config.Type }" />
			<table width="90%" height="100" border="0" cellpadding="0"
					cellspacing="0" style="border-color: #e9f5fd;">
					<tr>
				    <td style="border-color: #e9f5fd;" align="right"><s:text name='sundyn.playList.serverIp' />:</td>
				    <td align="left" style="border-color: #e9f5fd;">
				      <input type="text" name="IP" id="IP" onblur="isIP(this.value)"  value="${config.IP }"/>
				    </td>
				  </tr>
				  <tr>
				    <td style="border-color: #e9f5fd;" align="right"><s:text name='sundyn.playList.serverPort' />:</td>
				    <td align="left" style="border-color: #e9f5fd;" >
				      <input type="text" name="Port" id="Port" onblur="isNumber(this.value)"  value="${config.Port }" />
				    </td>
				  </tr>
				  <tr>
				    <td style="border-color: #e9f5fd;" align="right"><s:text name="sundyn.system.playlist.useWireless"></s:text>:</td>
				    <td align="left" style="border-color: #e9f5fd;"><label>
				      <s:text name="sundyn.conncet.yes"></s:text><input type="radio" name="Type"   value="1"   <c:if test="${config.Type == '1' }"> checked="checked" </c:if> />
				      <s:text name="sundyn.connect.no"></s:text><input type="radio" name="Type"   value="0"   <c:if test="${config.Type == '0' }"> checked="checked" </c:if> />
				    </label></td>
				  </tr>
				  <tr><td style="border-color: #e9f5fd;" colspan="2">
				  	<span style="font-size: 12px; color: red;" id="tip">(<s:text name="sundyn.alert.updata1" />)</span>
				  </td></tr>
				  <tr><td style="border-color: #e9f5fd;" colspan="2">
				  	<span style="font-size: 12px; color: red;" id="tip">(<s:text name="sundyn.alert.updata2" />)</span>
				  </td></tr>
				  </table>
				<form id="temp" enctype="multipart/form-data"
					action="playListUpdateDeal.action" method="post">
					<input type="hidden" name="imgName" id="imgName"  />
					<input type="hidden" name="playListId" id="playListId" value="${playListId}" />
					<input type="file" name="img" id="img" onblur="getFileName()" style="width: 200px;"  /> 
				</form>
			</div>
		</div>
	</div>
	<div class="bottom">
<%-- 			<a href="#"><img src="<s:text name='sudnyn.playList.pic.update' />"--%>
<%--					onclick="playListUpdateDeal()" class="hand" /> </a>--%>
<%--					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%-- 			<a href="#"><img src="<s:text name='sudnyn.playList.pic.update' />"--%>
<%--					onclick="playListUpdateDeal2()" class="hand" /> </a>--%>
<%--					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
<a href="#"><img src="<s:text name='sundyn.pic.save' />"  onclick="playListConfigSaveAndroid2()" class="hand" /> </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 			<a href="#"><img src="<s:text name='sudnyn.playList.pic.update' />"
					onclick="playListUpdateDeal3()" class="hand" /> </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 		 
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
 	</div>
</div>
