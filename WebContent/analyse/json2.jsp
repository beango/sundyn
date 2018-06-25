<%@page pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<%
    String strXML1 = (String)request.getAttribute("strXML1");
    String swf = (String)request.getAttribute("strXMLType");
    String paraW = (String)request.getAttribute("w");
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
    if (strXML1 != null && !"".equals(strXML1)) {
        String chartHTML1 = createChartHTML("Charts/" + swf, "", strXML1, "", w, 500, false);
%>
<%=chartHTML1%>
<%
    }
%>