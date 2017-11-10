<%@page pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<%
    String strXML1 = (String) request.getAttribute("strXML1");
    if (strXML1 != null && !"".equals(strXML1)) {
        String chartHTML1 = createChartHTML("Charts/FCF_Line.swf", "", strXML1, "", 600, 350, false);
%>
<span style="z-index:0"> <%=chartHTML1%> </span>
<%
    }
%>