<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name="sundyn.quickkyQuery.add" />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" onclick="closeDialog()" class="hand" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div style="margin-top: 100px;">
				 <s:text name="sundyn.quickkyQuery.pleaseEnterQucklyQueryName" /><input type="text" name="quicklyQueryName" id="quicklyQueryName" />
			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<a href="#"><img src="<s:text name='sundyn.pic.ok' />"
					onclick="quicklyAdd()" class="hand" /> </a>
		</div>
		<div class="nofind">
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
		</div>
	</div>
</div>
