<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sudnyn.playList.updateM7' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div style="margin-top: 100px;">
				<form id="temp" enctype="multipart/form-data"
					action="playListUpdateDeal.action" method="post">
					<input type="hidden" name="imgName" id="imgName"  />
					<input type="hidden" name="playListId" id="playListId" value="${playListId}" />
					<input type="file" name="img"    id="img" onblur="getFileName()" style="width: 200px;"  /> 
				</form>
			</div>
		</div>
	</div>
	<div class="bottom">
 			<a href="#"><img src="<s:text name='sudnyn.playList.pic.update' />"
					onclick="playListUpdateDeal()" class="hand" /> </a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 		 
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
 	</div>
</div>
