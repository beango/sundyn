<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title></title>
		<style type="text/css">
table {
	font-size: 12px;
	font-weight: 10px;
	text-align: center;
}
body{
background-color: #fff;
}
</style>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height=0
			id=wb name=wb width=0></OBJECT>
			<div class="sundyn_main" style="width:600px;">
				<div id="printTop">
					<table align="center" width="100%">
						<tr>
							<td id="pr" style="border: 1px solid #bad6ec;">
								<input type=button name=button_print value="<s:text name='sundyn.total.print.print'/>"
									onclick="javascript:printit();" />
								<input type=button name=button_ps value="<s:text name='sundyn.total.print.printSet'/>"
									onclick="printsetup();"/>
								<input type=button name=button_show value="<s:text name='sundyn.total.print.printPreview'/>"
									onclick="printpreview();"/>
								<input type=button name=button_fh value="<s:text name='sundyn.total.print.close'/>"  onclick="javascript:window.close();"/>
								<br />
								<font color=red><strong> <s:text name='sundyn.total.printInfo'/></strong> </font>
							</td>
						</tr>
					</table>
				</div>
				<div class="sundyn_row">
				<c:forEach items="${list}" var="total" varStatus="index">
				   <c:if test="${index.index  mod 20 ==0}">
				        <div class="sundyn_row" align="left">
					     	<s:text name='sundyn.total.info'> <s:param>${startDate}</s:param><s:param>${endDate}</s:param> </s:text>
				        </div>
						<table width="600" border="1" align="center" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.windowName"/></td>
							    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.star"/></td>
							    <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_2"><s:text name="sundyn.column.content"/></td>
							    <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_3"><s:text name="sundyn.column.nocontent"/></td>
							    <c:if test="${k7 == true}">
							    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesNum"/></td>
			 				    <td width="10%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.noappriesNum"/></td>
							    </c:if>
							    <td width="5%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.sum"/></td>
							    <c:if test="${k7 == true}">
							    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesRate"/></td>
							    </c:if>
							    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.contentRate"/></td>
							</tr>
							<tr>
								<c:forEach items="${mls}" var="key">
							    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
							    </c:forEach>
							    <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text name="sundyn.column.contentTotal"/></td>
							    <c:forEach items="${bmls}" var="key">
							    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
							    </c:forEach>   
							    <td width="12%" align="center" valign="middle" bgcolor="f5fff2" class="px13_1"><s:text name="sundyn.column.nocontentTotal"/></td>
							</tr>
						</c:if>
							<tr>
								<td>
									${total.windowname}
								</td>
								<td>
									${total.star}
								</td>
								   <c:forEach items="${total.km}" var="k">
										<td align="center" valign="middle">
											${k}
										</td>
									</c:forEach>
									<td align="center" valign="middle">
										${total.msum}
									</td>
 									<c:forEach items="${total.kbm}" var="k">
										<td align="center" valign="middle">
											${k}
										</td>
									</c:forEach>
 									<td align="center" valign="middle">
										${total.bmsum}
									</td>
									<c:if test="${k7 ==true}">
									<td align="center" valign="middle">
										${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
									</td>
									<td align="center" valign="middle">
										${total.key6}
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.sum}
									</td>
									<c:if test="${k7 == true}">
									<td align="center" valign="middle">
										${total.prate}%
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.mrate}%
									</td>
							</tr>
  							<c:if test="${(index.index+1) mod 20 ==0 || fn:length(list)==(index.index+1)}">	
					     </table>
					     <div style="width: 100%;height: 20px;"> &nbsp;</div>
					   </c:if>
				  </c:forEach>
				</div>
			</div>