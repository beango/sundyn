<%@page pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<%
    String strXML1 = (String)request.getAttribute("strXML1");
    String swf = (String)request.getAttribute("strXMLType");
    if(null == swf)
        swf = "FCF_Line.swf";
    if (strXML1 != null && !"".equals(strXML1)) {
        String chartHTML1 = createChartHTML("Charts/" + swf, "", strXML1, "", "600", 350, false);
%>
<%=chartHTML1%>
<%
    }
%>