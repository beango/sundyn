
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></META>
		<META HTTP-EQUIV="Expires" CONTENT="Mon， 04 Dec 1999 21：29：02 GMT"></META>
		<STYLE type="text/css">
v\:* {
	Behavior: url(#default#VML)
}

o\:* {
	behavior: url(#default#VML)
}

#PieDiv {
	font-family: arial;
	line-height: normal;
}

#PieDiv div {
	font-size: 9px;
}
</STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet"
			href="css/common_<s:text name='sundyn.language' />.css"
			type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript"
			src="js/my_<s:text name='sundyn.language' />.js"></script>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="/css/vip.css" type="text/css">
		<script language="JavaScript"
			src="<%=basePath%>/JSClass/FusionCharts.js"></script>
		<base href="<%=basePath%>">
		<title>报表显示</title>

	</head>



	<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">
		<%
			String strXML1 = (String) request.getAttribute("strXML1");
			if (strXML1 != null && !"".equals(strXML1)) {
				String chartHTML1 = createChartHTML(
						"/pingjia2/Charts/FCF_Column3D.swf", "", strXML1, "",
						600, 400, false);
		%>
		<span> <%=chartHTML1%> </span>
		<%
			}
		%>

		<br />
		<br />
		<br />
		<br />
		<%
			String strXML = (String) request.getAttribute("strXML");
			if (strXML != null && !"".equals(strXML)) {
				String chartHTML = createChartHTML(
						"/pingjia2/Charts/FCF_Column2D.swf", "", strXML, "",
						600, 400, false);
		%>
		<span> <%=chartHTML%> </span>
		<%
			}
		%>
		<div align="center">
			<a href="javascript:void(0)" onclick=
	javascript: window.print();;
></a>
		</div>
	</body>
</html>

