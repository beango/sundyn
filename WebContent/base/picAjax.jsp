<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			上传图片
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div style="margin-top: 100px;">
				<form onsubmit="return basePicAjaxCheck();" id="pic123" enctype="multipart/form-data"
					action="basePicUpload.action" method="post" >
					<input type="hidden" name="imgName" id="imgName"
						value="${fileName}" />
					<input type="file" name="img" id="img" />
				</form>
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<a href="#"><img src="images/dialog_button_upload.gif"
					onclick="pic123.onsubmit()" class="hand" /> </a>
		</div>
		<div class="nofind">
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
		</div>
	</div>
</div>
