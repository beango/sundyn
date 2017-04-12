<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>部门工作量统计图表</title>
		<meta http-equiv="content-type" content="text/html; charset=GB18030">
		<link rel="stylesheet" href="../Contents/Style.css" type="text/css" />
		<script language="JavaScript" src="../JSClass/FusionCharts.js"></script>
<script LANGUAGE="JavaScript">  
    var xmlHttpRequest;  
    function myJS(myVar) {  
        initRequest(myVar);  
    }  
      
    //初始化xmlHttpRequest对象  
    function initRequest(myVar) {  
        if(window.XMLHttpRequest) {  
            try {  
                xmlHttpRequest = new XMLHttpRequest();  
                if(xmlHttpRequest.overrideMimeType) {  
                    xmlHttpRequest.overrideMimeType("text/html;charset=UTF-8");  
                }  
            }catch (e) {}  
        }else if(window.ActiveXObject) {  
            try {  
                xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");  
            } catch (e) {  
                try {  
                    xmlHttpRequest = new ActiveXObject("Msxml2.XMLHttp");  
                } catch (e) {  
                    try {  
                        xmlHttpRequest = new ActiveXObject("Msxml3.XMLHttp");  
                    } catch (e) {}  
                }  
            }  
        }  
        verify(myVar);  
        
        //准备传输数据  
    function verify(myVar) {  
        var url = "deptAction.action";  
        xmlHttpRequest.onreadystatechange = callback;  
        xmlHttpRequest.open("POST", url, true);  
        xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");  
        xmlHttpRequest.send("jiDu=" + myVar);  
        xmlHttpRequest.send(null);  
    }  
      
    //定义回调函数  
    function callback() {  
        if(xmlHttpRequest.readyState == 4) {  
            if(xmlHttpRequest.status == 200) {  
                var responseText = xmlHttpRequest.responseText;  
                createHTML3D(responseText);  
            }  
        }  
    }  
    //创建子数据图  
    function createHTML3D(data) {  
        var myChart = new FusionCharts("../Charts/Pie3D.swf", "myChartId", "500", "300", "0", "0");  
        myChart.setDataXML(data);  
        myChart.render("chartdiv");  
    }  
    </script>  
  
	</head>

	<body bgcolor="#FFFFFF" text="#000000" marginwidth="0" marginheight="0">  
            <%                    
                String strXML1 = (String)request.getAttribute("strXML1");  
                if(strXML1 != null && !"".equals(strXML1)) {  
                    String chartHTML1 = createChartHTML("../charts/Column3D.swf",   
                        "", strXML1, "YearUnits", 500, 300, false);  
            %>  
                <span>  
                    <%=chartHTML1%>  
                </span>  
            <%   
                }  
            %>  
              
            <span id="chartdiv">  
            </span>  
    </body>  
</html>
