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
				<table width="100%" height="140px" border="0" cellpadding="0"
					cellspacing="0" style="border-color: #e9f5fd;">
					<tr>
						<td style="border-color: #e9f5fd;" width="32%" align="right">
							<s:text name='sudnyn.playList.playListName' />
						</td>
						<td width="68%" align="left" style="border-color: #e9f5fd;">
							<input name="playListId" id="playListId" type="hidden" value="${p.playListId}" /></input>
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
 				<div style="width:323px; height:35px;margin: 0 auto;">
 					<img id="pbar" src="images/update_processstart.gif" />
  				</div>
			</div>
		</div>
	</div>
	<div class="bottom">
 			<img src="<s:text name='sundyn.pic.save' />"  onclick="playListEdit()"
				alt="修改" class="hand" />
 		    <img  src="<s:text name='sudnyn.playList.pic.update' />"  onclick="playListCreateUpdateFile(${p.playListId})"
				alt="生成升级包" class="hand"  />
 			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
 	</div>
</div>