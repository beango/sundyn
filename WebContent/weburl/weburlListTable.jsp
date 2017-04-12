<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div class="fengge">&nbsp;<input type="hidden" name="managerId" id="managerId" value="${managerId}" /></div>
			<div style="width:640px;"><img src="<s:text name='sundyn.weburl.title' />" /></div>
			<div style="width:638px; height:290px;" class="kuang">
			    <div class="fengge" style="height:25px;">&nbsp;</div>
			    <div>
				    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
				       <tr>
				         <td style="border-color:#FFFFFF;" align="center">	
			             <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="55" height="25" onclick="weburToAdd();" class="hand"/></td>
			      </tr>
 </table>
			    </div>
			    <div class="fengge" style="height:25px;">&nbsp;</div>
				<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right: 1px solid #bad6ec;">
				  <tr>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.weburl.name' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.weburl.url' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
				    </tr>
				   <c:forEach items="${weburls}" var="webUrl" varStatus="index">
						<tr>
						    
							<td style="text-align: center;">
							   ${index.index+1}
							</td>
							
							<td style="text-align: center;">
							<div style="width:70px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
									       ${webUrl.name}
									</div>
							</td>
							<td style="text-align: center;">
							        <div style="width:340px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
									       ${webUrl.url}
									</div>
							</td>
							<td style="text-align: center;">
								<a href="javascript:weburlToUpate('${webUrl.id}');"><s:text name='sundyn.modifyOrupdate' /></a>
								<a href="javascript:weburlDelete('${webUrl.id}');"><s:text name='sundyn.del' /></a>
							</td>
						</tr>
				 </c:forEach>
				</table>
			    <div class="fengge" style="height:15px;">&nbsp;</div>
				<div> ${pager.pageTextAjax} </div>
		    </div> 