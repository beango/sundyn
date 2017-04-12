<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib  prefix="s" uri="/struts-tags"%>
<%@ page  import="java.util.List,java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  <html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
	 	 <STYLE type="text/css">
			 v\:* { Behavior: url(#default#VML) }
	         o\:* { behavior: url(#default#VML) }
	         #PieDiv{
		         font-family:arial;
 		      	 line-height: normal;
  	         }
  	         #PieDiv div{
  	            font-size: 9px;
             }
             table {
				font-size: 12px;
				font-weight: 10px;
				text-align: center;
			 }
		 </STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		 <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge">&nbsp;</div>
			<div class="sundyn_row"><s:text name='sundyn.total.info'> <s:param>${startDate}</s:param><s:param>${endDate}</s:param> </s:text>   </div>
			<div><img src="<s:text name='sundyn.total.pic.title' />" /></div>
 			<div style="width:732px;margin:0 auto;">
				<table width="100%"  cellpadding="0" cellspacing="0"  >
				  <tr>
				    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.name"/></td>
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.star"/></td>
				    <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_2"><s:text name="sundyn.column.content"/></td>
				    <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_3"><s:text name="sundyn.column.nocontent"/></td>
				    <td width="10%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.noappries"/></td>
				    <c:if test="${k7 == true}">
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesNum"/></td>
				    </c:if>
				    <td width="5%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.sum"/></td>
				    <!--
				    <c:if test="${k7 == true}">
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesRate"/></td>
				    </c:if>
				    
				     <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.workEffective"/></td>
				    -->
				    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.contentRate"/></td>
				    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.contentDegree"/></td>
				  </tr>
				  <tr>
				   <c:forEach items="${mls}" var="key">
<%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
				    <s:if test='getText("sundyn.language") eq "en"'>
				     <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.ext2}</td>
					</s:if>
					<s:else>
 						<td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
					 </s:else>
				   </c:forEach>
				    <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text name="sundyn.column.contentTotal"/></td>
				  <c:forEach items="${bmls}" var="key">
<%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
				  <s:if test='getText("sundyn.language") eq "en"'>
				     <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.ext2}</td>
					</s:if>
					<s:else>
 						<td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
					 </s:else>
				  </c:forEach>   
				    <td width="7%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text name="sundyn.column.nocontentTotal"/></td>
			      </tr>
	 			  <c:forEach items="${list}" var="total">
								<tr>
									<td align="center" valign="middle">
										${total.employeeName}
									</td>
									<td align="center" valign="middle">
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
									<td align="center" valign="middle">
									    ${total.key6}
									</td>
									<c:if test="${k7 ==true}">
									<td align="center" valign="middle">
										${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
									</td>
									<!--
									<c:if test="${k7 == true}">
									<td align="center" valign="middle">
										${total.prate}%
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.erate}%
									</td>
									-->
									<td align="center" valign="middle">
										${total.mrate}%
									</td>
									<td align="center" valign="middle">
										${total.num}
									</td>
								</tr>
						</c:forEach>
				</table>
			</div>
			<div class="sundyn_row">
				    ${pager.pageTextCn }
			</div>
			<div class="fengge"></div>
            <div><s:if test="list.size==0"><a href="javascript:noData('this');"></s:if><s:else><a href="totalPersonExcel.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank"></s:else>
            <img src="<s:text name='sundyn.total.pic.excel'/>" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="totalPersonPrint.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank">
            <img src="<s:text name='sundyn.total.pic.print'/>" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
            <!-- 人员满意度对比 -->
             <a href="personDuiBiAction.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_self">
            <img src="<s:text name='sundyn.total.jigou.duibi'/>" /></a>
            </div>
<%--		    <div class="fengge" style="height:21px;"></div>--%>
		    		    <div class="fengge" style="height:21px;text-align:left;">		    
	        <%String str=""; %>
          <s:if test='getText("sundyn.language") eq "en"'>
           <%
           
          List mls = (List)request.getAttribute("mls");
       	int n =0;
		for(int i=0;i<mls.size();i++){
			Map m = (Map)mls.get(i);
			String s = m.get("ext2")+"="+m.get("name");
			if(n%3==0){
				str = str+s+" ";
			}else{
				if(n%3==2){
					str = str+" , "+s+"</br></br>";
				}else{
				str = str+","+s;
				}
			}
			n++;
		}
		List bmls = (List)request.getAttribute("bmls");
		for(int i=0;i<bmls.size();i++){
			Map m = (Map)bmls.get(i);
			String s = m.get("ext2")+"="+m.get("name");
			if(n%3==0){
				str = str+s+" ";
			}else{
				if(n%3==2){
					str = str+" , "+s+"</br></br>";
				}else{
				str = str+", "+s;
				}
			}
			n++;
		}
           %> 
           </s:if>
		    <s:if test='getText("sundyn.language") eq "en"'>
              <span style="color:red;"><% out.print(str); %></span>
            </s:if></div>
            </br></br>
		    <!-- 统计信息开始 -->
 		     <table id="table1" width="70%" height="172" border="0" cellpadding="0" cellspacing="0" class="px12" style="border-top:1px solid #bad6ec;border-right:1px solid #bad6ec;margin:0 auto;">
			  <tr>
			    <td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12"><s:text name="sundyn.total.toatlInfo"/></td>
			    </tr>
				<c:forEach items="${mls}" var="key" varStatus="index">
				  <tr>
					    <c:if test="${index.index == 0}">
					      <td width="15%" rowspan="${fn:length(mls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.content"/></td>
					    </c:if>
					     <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.msum}</td>
					    </c:if>
					    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.mrate}%</td>
					    </c:if>
				   </tr>
				</c:forEach>
				<c:forEach items="${bmls}" var="key" varStatus="index">
				  <tr>
					    <c:if test="${index.index == 0}">
					      <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.nocontent"/></td>
					    </c:if>
					     <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.bmsum}</td>
					    </c:if>
					    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					     <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.bmrate}%</td>
					    </c:if>
				   </tr>
				</c:forEach>
				<c:if test="${k7 == true}">
				  <tr>
 					    <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.noappries"/></td>
 					    <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${totalMap.k7Name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.kr6}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key6}</td>
					    <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.key6}</td>
					    <td width="6%" align="center" valign="middle">${totalMap.kr6}%</td>
					    <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.kr6}%</td>
				   </tr>
				</c:if>
 			</table>
		    <!-- 统计信息结束-->
		    <div class="fengge"></div>
		</div>
	</body>
</html>