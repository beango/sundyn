<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div>
	<div class="content">
		<div class="content_main"><%-- <s:text name='sundyn.add.weburl' /> --%>
			<div  style="margin-top: 10px;width: 100%">
<%--				<table width="100%" height="173" border="0" cellpadding="0"--%>
<%--					cellspacing="0" style="border-color: #e9f5fd;">--%>
<%--					<tr>--%>
<%--						<td style="border-color: #e9f5fd;" width="32%" align="right">--%>
<%--							<s:text name='sundyn.weburl.name' /><s:text name='sundyn.colon' />--%>
<%--						</td>--%>
<%--						<td width="68%" align="left" style="border-color: #e9f5fd;">--%>
<%--							<input name="weburl.name" class="input_comm" id="webname"  value=""/><span id="tip" style="color: red;font-size: 12px;"></span>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--					<tr>--%>
<%--						<td style="border-color: #e9f5fd;" align="right">--%>
<%--							<s:text name='sundyn.weburl.url' /><s:text name='sundyn.colon' />--%>
<%--						</td>--%>
<%--						<td align="left" style="border-color: #e9f5fd;">--%>
<%--							<input name="weburl.url" class="input_comm" id="weburl"  value="" />--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--				</table>--%>
				
				 <table width="100%" height="121" border="0" cellpadding="0" cellspacing="0">
					  <tr>
						    <td width="32%" align="right" style="border-color: #e9f5fd;">
						    <s:text name="sundyn.weburl.name"/><s:text name='sundyn.colon' />
	 					    </td>
	 					    
						    <td width="50%" align="left" style="border-color: #e9f5fd;">
						       <input name="weburl.name" class="input_comm" id="webname"  value="" />
						    </td>
					    </tr>

					  <tr>
					    <td colspan="2" height="15px" style="border-color: #e9f5fd;">	 </td>
					  </tr>
					   <tr >
					    <td align="right"  style="border-color: #e9f5fd;"><s:text name="sundyn.weburl.url"/><s:text name='sundyn.colon' /></td>
					    <td align="left"  style="border-color: #e9f5fd;width:90%;">
					    	<textarea name="weburl.url"  id="weburl"></textarea>
					    </td>
					  </tr>
					 <tr><td colspan="2" style="text-align:center;padding-top:10px;">
					 	<img src="<s:text name='sundyn.pic.ok' />" onclick="weburlAdd();"
									 class="hand" />
						<img src="<s:text name='sundyn.pic.close' />" onclick="closeWindows();"
									 class="hand">
					 </td></tr>
 				 </table>
			</div>
		</div>
	</div>
</div>
