<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.faqManage.submitQusation' />
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
						  <input name="lybTitle" id="lybTitle" class="input_comm" style="width: 100px;" onblur="employeeExsits()" type="text"><span style="color: red; font-size: 12px;" id="tip"></span>
						</td>
					</tr>
					<tr>
						<td style="border-color: rgb(233, 245, 253);" class="ff" align="right" width="30%">
							<s:text name='sundyn.faqManage.content' /><s:text name="sundyn.colon" /> 
						</td>
						<td style="border-color: rgb(233, 245, 253);" align="left" width="70%">
							 <textarea rows="8" cols="50" id="lybAsk"></textarea>
						</td>
					</tr>
 				</tbody></table>
 		</div>		
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />" onclick="lybAddDeal()"  class="hand">
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()"  class="hand">
		</div>
	</div>
</div>
