<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.advice.statistics' /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge">&nbsp;<input type="hidden" name="managerId" id="managerId" value="${managerId}" /></div>
			
			<div style="width:638px; height:290px;" class="kuang">
			    <div class="fengge" style="height:25px;">&nbsp;</div>
<!--			    <div>-->
<%--				    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">--%>
<%--				       <tr>--%>
<%--				         <td style="border-color:#FFFFFF;" align="center">	<input name="keyword" id="keyword" class="input_comm" /></td>--%>
<%--				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerManagerQueryAjax()" class="hand"/></td>--%>
<%--				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>--%>
<%--				      </tr>--%>
<%--				     </table>--%>
<!--				    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">-->
<!--				       <tr>-->
<!--				         <td style="border-color:#FFFFFF;" align="center">	-->
<!--				         <input name="keyword" id="keyword" class="input_comm" /></td>-->
<!--			         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="55" height="25" onclick="window.location.href='${pageContext.request.contextPath}/adviceToAdd'" class="hand"/></td>-->
<!--			         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>--%>-->
<!--			      </tr>-->
<!-- </table>-->
<!--			    </div>-->
			    <div class="fengge" style="height:25px;">&nbsp;</div>
				<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right: 1px solid #bad6ec;">
				  <tr>
				    <td width="40px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.advice.statistics' /></td>
<!--				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.tip' /></td>-->
<!--				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>-->
				    </tr>
		         <s:iterator value="adviceStatistics"  var="ll" status="num">
						<tr>
						    
							<td style="text-align: center;">
							  ${num.index+1 }
							</td>
							
							<td style="text-align: left;">
							 <s:iterator value="ll" id="index1" var="m1">
							 
							  <div style="width:540;word-break:break-all;"> <s:text name='sundyn.advice.question' /><s:text name='sundyn.colon' />  &nbsp;   
							      <s:property value="key"/></div> 
<!--							      <s:property value='value' />-->
                                       &nbsp; <table border="0" width="540px">
							      <s:iterator value="#m1.value" var="lll">
										
							            <s:iterator value="lll" var="m2" status="number">
							                <tr>
							                   <td>
							          			<s:text name='sundyn.advice.check' /><s:text name='sundyn.colon' /><s:property value="key"/>&nbsp;&nbsp;</td>
							               	   <td> 
							               	   <s:iterator value="value" id="entry">
							               	   <s:if test="value==0.0"><div style="width:5px;height:20px;background-color:green;float:left;" nowrap></s:if> <s:else>  <div style="width:${value*1.2 }px;height:20px;background-color:green;float:left;" nowrap></s:else></div>
							               	   </s:iterator>
							               	   </td> 
							               	   <td> <s:iterator value="value" id="entry">
							               	    <s:property value="value"/>%  &nbsp;&nbsp;&nbsp;   
							               	    </s:iterator></td>
							               	    <td>
							               	    <s:iterator value="value" id="entry">
							               	    <s:property value="key"/>  &nbsp;&nbsp;&nbsp;   
							               	    </s:iterator>
							               	    </td>
							                   
							                </tr>
<!--							                  <s:if test="(#num1.index%2)==0">-->
<!--											     <br/>-->
<!--											  </s:if>-->
							            </s:iterator>
							          
							      </s:iterator>
							        </table>
							 </s:iterator>
									${question.question}
							</td>
<!--							<td style="text-align: center;">-->
<!--<%--								<c:if test="${manager.remark==0}"><s:text name='sundyn.user.select1' />  </c:if>--%>-->
<!--<%--								<c:if test="${manager.remark==1}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}</c:if>--%>-->
<!--<%--								<c:if test="${manager.remark==2}"><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>--%>-->
<!--<%--								<c:if test="${manager.remark==3}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}<s:text name='sundyn.comma' /><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>--%>-->
<!--							 <s:iterator value="answers"  >${answer};</s:iterator>-->
<!--							</td>-->
<!--							<td style="text-align: center;">-->
<!--								<a href="javascript:window.location.href='${pageContext.request.contextPath}/adviceToUpdate?advice.question.id=${question.id}';"><s:text name='sundyn.modify' /></a>-->
<!--								<a href="javascript:window.location.href='${pageContext.request.contextPath}/adviceDelete?advice.question.id=${question.id}';"><s:text name='sundyn.del' /></a>-->
<!--							</td>-->
						</tr>
			      </s:iterator>
				</table>
			    <div class="fengge" style="height:15px;">&nbsp;</div>
				<div> ${pager.pageTextCn} </div>
		    </div> 
		</div>
		<div id="dialog" style="width: 600px; display: none;">
	</body>
</html>
