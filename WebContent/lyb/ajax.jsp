<%@ page pageEncoding="UTF-8"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
 <div class="fengge">&nbsp;</div>
	 <div class="title732"><h3><s:text name='sundyn.faqManage.title' /></h3></div>
		    <div class="fengge" style="height: 25px;">&nbsp;</div>
		    <div>
			    <table style="border-color: rgb(255, 255, 255);" border="0" cellpadding="0" cellspacing="0" width="90%">
			       <tbody><tr>
			         <td style="border-color: rgb(255, 255, 255);" align="left"><img src="<s:text name='sundyn.pic.add' />" onclick="lybAdd()" class="hand" width="63" height="25"></td>
			      </tr>
			     </tbody></table>
		    </div>
		    <div class="fengge" style="height: 25px;">&nbsp;</div>
			<table style="border-top: 1px solid rgb(186, 214, 236); border-right: 1px solid rgb(186, 214, 236);" cellpadding="0" cellspacing="0" width="90%">
			  <tbody><tr>
			    <td class="px13_1" align="center" background="images/table_bg_03.jpg" valign="middle"><s:text name="sundyn.column.title" /></td>
			    <td class="px13_1" align="center" background="images/table_bg_03.jpg" valign="middle"><s:text name="sundyn.column.state" /></td>
			    <td class="px13_1" align="center" background="images/table_bg_03.jpg" valign="middle"><s:text name="sundyn.column.operation" /></td>
			    </tr>
			    <c:forEach var="lyb" items="${list}">
					<tr>
						<td style="text-align: center;">
							${lyb.lybTitle }
						</td>
						<td style="text-align: center;">
							 <c:if test="${lyb.lybState ==0 }"><s:text name="sundyn.faqManage.noanswered" /></c:if>
							 <c:if test="${lyb.lybState ==1 }"><s:text name="sundyn.faqManage.answered" /></c:if>
						</td>
						<td style="text-align: center;">
						<a href="javascript:lybDel(${lyb.lybId});"><s:text name="sundyn.del" /></a> &nbsp;&nbsp;
						<a href="javascript:lybEdit(${lyb.lybId});"><s:text name="sundyn.faqManage.answer" /></a> 
						</td>
					</tr>
				</c:forEach>
			</tbody></table>
<div class="fengge" style="height: 15px;">&nbsp;</div>