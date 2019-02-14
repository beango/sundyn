<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.faqManage.answerQusation' />
		</div>
		<div class="close">
			<img src="images/dialog_close.gif" class="hand" onclick="closeDialog()" border="0">
		</div>
	</div>
	<div class="content">
		<div class="content_main">
				<table style="border-color: rgb(233, 245, 253);" border="0" cellpadding="0" cellspacing="0" width="100%">
					<tbody><tr>
						<td style="border-color: rgb(233, 245, 253);" class="ff" align="right" width="30%">
							<s:text name='sundyn.column.title' /><s:text name="sundyn.colon" /> 
						</td>
						<td style="border-color: rgb(233, 245, 253);" align="left" width="70%">
						<input type="hidden" value="${lyb.lybId}" id="lybId">
						 ${lyb.lybTitle}
						</td>
					</tr>
					<tr>
						<td style="border-color: rgb(233, 245, 253);" class="ff" align="right" width="30%">
							<s:text name='sundyn.faqManage.content' /><s:text name="sundyn.colon" /> 
						</td>
						<td style="border-color: rgb(233, 245, 253);" align="left" width="70%">
							 	 ${lyb.lybAsk}
						</td>
					</tr>
					<tr>
						<td style="border-color: rgb(233, 245, 253);" class="ff" align="right" width="30%">
							<s:text name='sundyn.faqManage.answer2' /><s:text name="sundyn.colon" /> 
						</td>
						<td style="border-color: rgb(233, 245, 253);" align="left" width="70%">
							 	<textarea rows="10" cols="50" id="lybAnswer">${lyb.lybAnswer}</textarea>
						</td>
					</tr>
 				</tbody></table>
 		</div>		
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />" onclick="lybEditDeal()"  class="hand">
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()"  class="hand">
		</div>
	</div>
</div>
