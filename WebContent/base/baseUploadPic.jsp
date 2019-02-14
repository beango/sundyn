<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			上传图片
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div style="margin-top: 100px;">
				<form id="pic123" enctype="multipart/form-data"
					action="baseUploadPicDeal.action" method="post">
					<input type="hidden" name="imgName" id="imgName" />
					<input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" /> 
				</form>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<a href="#"><img src="<s:text name='sundyn.softSetup.pic.upload'/>"
					onclick="baseUploadPicDeal()" class="hand" /> </a>
		</div>
		<div class="nofind">
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
		</div>
	</div>
</div>
