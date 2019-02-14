<%@page pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<%
    String strXML1 = (String)request.getAttribute("strXML1");
    String swf = (String)request.getAttribute("strXMLType");
    String paraW = (String)request.getAttribute("w");
    String paraH = (String)request.getAttribute("h");
    if(null == swf)
        swf = "Line.swf";
    String w = "100%";
    if(paraW!=null)
        w = paraW;
    else{
        if(swf == "FCF_Pie2D.swf"){
            w = "627";
        }
    }
    int h = 600;
    if (paraH!=null)
        h = Integer.parseInt(paraH);
    if (strXML1 != null && !"".equals(strXML1)) {
        String chartHTML1 = createChartHTML("Charts/" + swf, "", strXML1, "", w, h, false);
%>
<%=chartHTML1%>
<%
    }
%>