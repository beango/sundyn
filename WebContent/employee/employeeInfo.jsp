<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>

${m.picture}||
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td width="60" height="25"  align="right">单位：</td>
  <td>${m.showDeptName}</td>
</tr>
<tr>
  <td height="25" align="right" >科室：</td>
  <td>${m.showWindowName}</td>
</tr>
<tr>
  <td height="25" align="right">姓名：</td>
  <td>${m.Name}</td>
</tr>
<tr>
  <td height="25" align="right" >工号：</td>
  <td>${m.ext2}</td>
</tr>
</table>
||
工号：${m.ext2}
||
<table width="98%" border="0" cellspacing="0" cellpadding="0">
<tr>
  <td width="105" height="43" align="right"></td>
  <td width="19" align="right"></td>
  <td >&nbsp;</td>
</tr>
<tr>
  <td height="50" align="right">单位：</td>
  <td align="right">&nbsp;</td>
  <td>${m.showDeptName}</td>
</tr>
<tr>
  <td height="50" align="right">科室：</td>
  <td align="right">&nbsp;</td>
  <td>${m.showWindowName}</td>
</tr>									  
<tr>
  <td height="50" align="right">姓名：</td>
  <td align="right">&nbsp;</td>
  <td>${m.Name}</td>
</tr>
</table>
||
<table width="98%" border="0" cellspacing="0" cellpadding="0">
				  <tr>
					<td width="80" height="30" align="right">单位：</td>
					<td width="10" align="right">&nbsp;</td>
					<td>${m.danweiName}</td>
				  </tr>
				  <tr>
					<td height="30" align="right">机构：</td>
					<td align="right">&nbsp;</td>
					<td>${m.deptName}</td>
				  </tr>
				  <tr>
					<td height="30" align="right">姓名：</td>
					<td align="right">&nbsp;</td>
					<td>${m.Name}</td>
				  </tr>
				  <tr>
					<td height="30" align="right">工号：</td>
					<td>&nbsp;</td>
					<td>${m.CardNum}</td>
				  </tr>
				 <tr>
		<td>
			星级：
		</td>
		<td>
 		 <c:if test="${star == 'true'}">
 		    ${m.star}
		 </c:if>   
		 <c:if test="${star != 'true'}">       
		    ${m.Phone}   
		 </c:if>   
 		</td>
	</tr> 
				</table>

||
${m.remark}