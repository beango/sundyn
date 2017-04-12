<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 	<head>
	 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 		<script type="text/javascript">
 		function changeStat(){
	 		var input = document.getElementsByTagName("input");
 		  	var result = input[0].checked;
	 		  	for (i = 1; i < input.length; i++) {
	 		  		input[i].checked=result;
				}
	 	}
 		</script>
 	</head>
	<body>
			<div id="man_zone">
			<div class="sundyn_main"><button onclick="delAppraise()" > 删除所选项</button>
			<div class="fengge"></div>
				<table width="98%" class="t" >
					<tr>	
					   <td class="tdtitle">全选
					   <input type="checkbox" name="ttt" onclick="changeStat()"></td>
						<td width="15%" class="tdtitle">
							员工姓名
						</td>
						<td width="15%" class="tdtitle">
							机构
						</td>
						<td width="15%" class="tdtitle">
							评价结果
						</td>
						<td width="30%" class="tdtitle">
							评价时间
						</td>
						
						
					</tr>
				 <form >
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
						    <td width="15%" class="td">
								<input name="ids" type="checkbox" value="${query.id }" />
							<br /></td>
							<td width="15%" class="td">
								${query.employeeName}
							<br /></td>
							<td width="15%" class="td">
								${query.deptName}
							<br /></td>
							<td width="15%" class="td">
								${query.keyName}
							<br /></td>
	                        <td width="30%" class="td">
								<fmt:formatDate value="${query.serviceDate}" type="both"/>
							</td>
  						</tr>
					</c:forEach>
					
				</form>
				</table>
				<div class="sundyn_rows">
					${pager.pageTextCn}
				</div>
  			</div>
		</div>
 	</body>
</html>