<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/ddtree.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
		<div id="man_zone">
			<div class="sundyn_main">
				<div class="sundyn_left">
					<div class="dtree" id="tree">
						<script type="text/javascript">
							d = new dTree('d');
							<c:forEach items="${list}" var="dept">
								d.add(${dept.id},${dept.fatherId},'${dept.name}','javascript:regId(${dept.id})');
							</c:forEach>
							document.write(d);
						</script>
					</div>
				</div>
				<input type="hidden" id="deptId" />
				<div class="sundyn_right" id="deptView">
					<h3>
						机构管理sesdf
					</h3>
					<div class="sundyn_row">
						请先选择左边的机构，再进行相应的操作
					</div>
				</div>
			</div>
		</div>
		<div id="dialog" style="width: 600px; display: none;">
		</div>
	</body>
</html>
