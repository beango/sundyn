<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.notice.list' /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>

			<div class="fengge">&nbsp;<input type="hidden" name="managerId" id="managerId" value="${managerId}" /></div>
			<div style="width:640px;"><img src="<s:text name='sundyn.notice.list.title' />" /></div>
			<div style="width:638px; height:290px;" class="kuang">
			    <div class="fengge" style="height:25px;">&nbsp;</div>
			    <div>
<%--				    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">--%>
<%--				       <tr>--%>
<%--				         <td style="border-color:#FFFFFF;" align="center">	<input name="keyword" id="keyword" class="input_comm" /></td>--%>
<%--				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerManagerQueryAjax()" class="hand"/></td>--%>
<%--				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>--%>
<%--				      </tr>--%>
<%--				     </table>--%>
				    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
				       <tr>
				         <td style="border-color:#FFFFFF;" align="center">	
<!--				         <input name="keyword" id="keyword" class="input_comm" /></td>-->
			         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="55" height="25" onclick="noticToAdd();" class="hand"/></td>
<!--			         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>--%>-->
			      </tr>
 </table>
			    </div>
			    <div class="fengge" style="height:25px;">&nbsp;</div>
				<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right: 1px solid #bad6ec;">
				  <tr>
				    <td width ="50px"  height="20px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
				    <td width ="100px" height="20px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.notice.title' /></td>
				    <td width ="100px" height="20px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.notice.content' /></td>
				    <td width ="80px"  height="20px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
				    </tr>
				    <c:if test="${notices==null}"><s:text name='sundyn.nodate' /> </c:if>
				    <c:if test="${notices!=null}">
				   <c:forEach items="${notices}" var="notice" varStatus="index">
						<tr>
						    
							<td width ="50px"  height="20px"  style="text-align: center;text-overflow:ellipsis;overflow:hidden;" height="20px">
							      ${index.index+1}
							</td>
							
							<td width ="100px"  height="20px"  style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
									<div style="width:96px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
									       ${notice.title}
									</div>
							</td>
							<td width ="300px"  height="20px"  style="text-align: left;overflow:visible;">
<%--								<c:if test="${manager.remark==0}"><s:text name='sundyn.user.select1' />  </c:if>--%>
<%--								<c:if test="${manager.remark==1}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}</c:if>--%>
<%--								<c:if test="${manager.remark==2}"><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>--%>
<%--								<c:if test="${manager.remark==3}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}<s:text name='sundyn.comma' /><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>--%>
								 <div style="width:296px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
								        ${notice.content}
								 </div>
							</td>
							<td  width ="140px"  height="20px" style="text-align: center;">
								<a href="javascript:noticToUpate('${notice.id}');"><s:text name='sundyn.modifyOrupdate' /></a>
								<a href="javascript:noticDelete('${notice.id}');"><s:text name='sundyn.del' /></a>
							</td>
						</tr>
				 </c:forEach>
				 </c:if>
				</table>
			    <div class="fengge" style="height:15px;">&nbsp;</div>
				<div> ${pager.pageTextAjax} </div>
		    </div> 

