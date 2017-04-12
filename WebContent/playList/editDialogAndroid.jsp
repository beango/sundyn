<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.playList.editTitle' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div  style="margin-top: 20px;width: 100%">
				<table width="100%" height="60" border="0" cellpadding="0"
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
				  <tr><td style="border-color: #e9f5fd;" colspan="2">
				  	<div style="width:323px; height:35px;margin: 0 auto;">
 					<img id="pbar" src="images/update_processstart.gif" />
  				</div>
				  </td></tr>
					<tr>
						<td style="border-color: #e9f5fd;" width="32%" align="right">
							<s:text name='sudnyn.playList.playListName' />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="playListId" id="playListId" type="hidden" value="${p.playListId}" /></input>
							<input type="hidden" name="Version" id="Version"   value="${config.Version }"/>
							<input type="hidden" name="Welcometime" id="Welcometime"  value="${config.Welcometime }" />
							<input type="hidden" name="Approvertime" id="Approvertime"  value="${config.Approvertime }"  />
							<input type="hidden" name="Shutdownhh" id="Shutdownhh"  value="${config.Shutdownhh }"  />
							<input type="hidden" name="Shutdownmm" id="Shutdownmm"  value="${config.Shutdownmm }"  />
							<input type="hidden" name="Boothh" id="Boothh"  value="${config.Boothh }"  />
							<input type="hidden" name="Bootmm" id="Bootmm"  value="${config.Bootmm }"  />
							<input type="hidden" name="ShowEmployeePage" id="ShowEmployeePage"  value="${config.ShowEmployeePage }"  />
							 
							 <input type="hidden" name="Type" id="Type"  value="${config.Type }" />
							<input name="playListName" id="playListName" value="${p.playListName}" /></input>
 						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sudnyn.playList.description' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
							<input type="text" name="playListDescription" id="playListDescription" value="${p.playListDescription}" />
						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right">
							<s:text name='sudnyn.playList.please' />
						</td>
						<td align="left" style="border-color: #e9f5fd;">
						   <c:forEach items="${pls}" var="play">
								<input type="checkbox" onclick="setvalue();" value="${play.playId}" checked="checked"  id="key${play.playId}" />
								<label for="key${play.playId}">${play.playName}</label>   
						   </c:forEach>
						   <c:forEach items="${als}" var="play">
								<input type="checkbox" onclick="setvalue();" value="${play.playId}"    id="key${play.playId}"  />
								<label for="key${play.playId}">${play.playName}</label>   
						   </c:forEach>
						   <br/>
						  
						   <input id="playIds" style="display: none;" name="playIds" readonly="readonly" value="${ids}"  />
						</td>
					</tr>
  				</table>
 				
			</div>
		</div>
	</div>
	<div class="bottom">
 			<img src="<s:text name='sundyn.pic.save' />"  onclick="playListEdit()"
				alt="<s:text name='sundyn.system.playlist.modify'/>" class="hand" />
<%-- 		    <img  src="<s:text name='sudnyn.playList.pic.update' />"  onclick="playListCreateUpdateFile(${p.playListId})"--%>
<%--				alt="生成Bin包" class="hand"  />--%>
<%-- 		    <img  src="<s:text name='sudnyn.playList.pic.update' />"  onclick="playListCreateUpdateZip(${p.playListId})"--%>
<%--				alt="生成Zip包" class="hand"  />--%>
 		    <img  src="<s:text name='sudnyn.playList.pic.update' />"  onclick="playListCreateUpdateZipFile(${p.playListId})"
				alt="生成Zip和Bin包" class="hand"  />
 			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()" class="hand">
 	</div>
</div>