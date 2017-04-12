<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
		<div class="fengge">&nbsp;</div>
			<div id="query" class="sundyn_main">
				<table border="0" width="100%">
					<tr>
						<td width="40%">
							查询名称
						</td>
						<td width="40%">
							创建日期
						</td>
						<td width="20%">
							操作
						</td>
					</tr>
					<c:forEach items="${quicklyList}" var="quickly">
						<tr>
							<td>
								${quickly.name}asdf
							</td>
							<td>
								${quickly.name}asdf
							</td>
							<td>
								<input type="button" value="查询"
									onclick="quicklyQuery(${quickly.id})" />
								<input type="button" value="删除"
									onclick="quicklyDel(${quickly.id})" />
							</td>
						</tr>
					</c:forEach>
				</table>
				<div>
					为了保证系统运行效率，每个人最多创建10个快速查询
				</div>
			</div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
	</body>
</html>
