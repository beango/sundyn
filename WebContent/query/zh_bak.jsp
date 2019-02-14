<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript" src="js/dtree.js"></script>
		<style>
a:link {
	color: #464646;
	text-decoration: none;
}

a:visited {
	color: #464646;
	text-decoration: none;
}

a:hover {
	color: #ed145b;
	text-decoration: underline;
}

a:active {
	color: #ed145b;
	text-decoration: underline;
}

td {
	font-size: 12px
}

/*想要改输入日历控件的样子就改下面的CSS样式就可以了*/ /*Date*/
.header {
	font: 12px Arial, Tahoma !important;
	font-weight: bold !important;
	font: 11px Arial, Tahoma;
	font-weight: bold;
	color: #154BA0;
	background: #C2DEED;
	height: 25px;
	padding-left: 10px;
}

.header td {
	padding-left: 10px;
}

.header a {
	color: #154BA0;
}

.header input {
	background: none;
	vertical-align: middle;
	height: 16px;
}

.category {
	font: 12px Arial, Tahoma !important;
	font: 11px Arial, Tahoma;
	color: #92A05A;
	height: 20px;
	background-color: #FFFFD9;
}

.category td {
	border-bottom: 1px solid #DEDEB8;
}

.expire,.expire a:link,.expire a:visited {
	color: #999999;
}

.default,.default a:link,.default a:visited {
	color: #000000;
}

.checked,.checked a:link,.checked a:visited {
	color: #FF0000;
}

.today,.today a:link,.today a:visited {
	color: #00BB00;
}

#calendar_year {
	display: none;
	line-height: 130%;
	background: #FFFFFF;
	position: absolute;
	z-index: 10;
}

#calendar_year .col {
	float: left;
	background: #FFFFFF;
	margin-left: 1px;
	border: 1px solid #86B9D6;
	padding: 4px;
}

#calendar_month {
	display: none;
	background: #FFFFFF;
	line-height: 130%;
	border: 1px solid #86B9D6;
	padding: 4px;
	position: absolute;
	z-index: 11;
}

.tableborder {
	background: white;
	border: 1px solid #86B9D6;
}

#year,#month {
	padding-right: 10px;
	background: url(onbottom.gif) no-repeat center right;
} /*图片路径可以改成自己的*/ /*Date*/
</style>
		<script>
			//这段脚本如果你的页面里有，就可以去掉它们了
			//欢迎访问我的网站queyang.com
			var ie =navigator.appName=="Microsoft Internet Explorer"?true:false;
			function $(objID){
				return document.getElementById(objID);
			}
		</script>
	</head>
	<body>
		<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
		<div id="man_zone">
			<div class="sundyn_main">
				<div class="sundyn_left">
					<h3>
						请选择机构
					</h3>
					<div class="dtree">
						<script type="text/javascript">
							d = new dTree('d');
							<c:forEach items="${deptList}" var="dept">
								d.add(${dept.id},${dept.fatherId},'${dept.name}');
							</c:forEach>
							document.write(d);
						</script>
					</div>
				</div>
				<div class="sundyn_right">
					<h3>
						请输入要查询的员工姓名
					</h3>
					<div class="sundyn_row">
						<div class="sundyn_text21">
							请输入员工:
						</div>
						<div class="sundyn_text22">
							<input type="hidden" id="id" name="id" value="0" />
							<input type="text" id="keyword" name="keyword" />
							<input type="button" onclick="queryEmployee()" value="查询" />
						</div>
					</div>
					<div class="sundyn_row">
						<div class="sundyn_text21">
							开始时间:
						</div>
						<div class="sundyn_text22">
							<input type="text" onclick="showcalendar(event, this);"
								id="startDate" readonly="readonly" />
						</div>
					</div>
					<div class="sundyn_row">
						<div class="sundyn_text21">
							结束时间:
						</div>
						<div class="sundyn_text22">
							<input type="text" onclick="showcalendar(event, this);"
								id="endDate" readonly="readonly" />
						</div>
					</div>
					<div class="sundyn_row">
						<h3>
							请选择评价结果
						</h3>
						<ul>
							<c:forEach items="${keyList}" var="key">
								<li>
									${key.name}:
									<input type="checkbox" value="${key.keyNo}"
										name="key${key.keyNo}" id="key${key.keyNo}" />
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="sundyn_row">
						<div class="sundyn_text21">
							是否保存为快速查询：
						</div>
						<div class="sundyn_text22">
							<input type="checkbox" name="quickquery" />
							<input type="button" onclick="queryZhDeal()" value="我要查询" />
						</div>
					</div>
				</div>
			</div>
		</div>
		<div
			style="width: 500px; height: 300px; display: none; background-color: rgb(213, 222, 219);"
			id="dialog">
		</div>
	</body>
</html>
