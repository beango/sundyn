<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div>
	<div class="content">
		<div class="content_main">
			<div  style="margin-top: 10px;width: 100%">
			<input  type="hidden" name="weburl.id" id="uid" value="${weburl.id }"/>
				 <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
					  <tr>
						    <td width="32%" align="right" style="border-color: #e9f5fd;width:30%;">
						    <s:text name="sundyn.weburl.name"/><s:text name='sundyn.colon' />
	 					    </td>
	 					    
						    <td width="50%" align="left" style="border-color: #e9f5fd;">
						       <input name="weburl.name" class="input_comm" id="webname"  value="${weburl.name }" />
						    </td>
					    </tr>
					  <tr>
					    <td colspan="2" height="15px" style="border-color: #e9f5fd;">	 </td>
					  </tr>
					   <tr >
					    <td align="right"  style="border-color: #e9f5fd;"><s:text name="sundyn.weburl.url"/><s:text name='sundyn.colon' /></td>
					    <td align="left"  style="border-color: #e9f5fd;width:90%;">
					    	<textarea name="weburl.url" id="weburl">${weburl.url}</textarea>
					    </td>
					  </tr>
					 <tr>
					 	<td colspan="2" style="text-align:center;">
							<img src="<s:text name='sundyn.pic.ok' />" onclick="weburlUpate();"
								 class="hand" />
							<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog();"
								 class="hand">
					 	</td>
					 </tr>
 				 </table>
			</div>
		</div>
	</div>
</div>