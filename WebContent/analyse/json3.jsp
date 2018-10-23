<%@page pageEncoding="UTF-8"%>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<table style="border:0px;margin: 0; padding:0;" border="0" width="100%">
    <tr>
        <td style="border:0px;margin: 0; padding:0;">
            <%
                String strXML1 = (String)request.getAttribute("strXML1");
                String swf = (String)request.getAttribute("strXMLType1");
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
                w = "100%";
                if (strXML1 != null && !"".equals(strXML1)) {
                    String chartHTML1 = createChartHTML("Charts/" + swf, "", strXML1, "", w, h, false);
            %>
            <%=chartHTML1%>
            <%
                }
            %>
        </td>
    </tr>
    <tr>
        <td>
            <%
                strXML1 = (String)request.getAttribute("strXML2");
                swf = (String)request.getAttribute("strXMLType2");
                w = "100%";
                if (strXML1 != null && !"".equals(strXML1)) {
                    String chartHTML2 = createChartHTML("Charts/" + swf, "", strXML1, "", w, h, false);
            %>
            <%=chartHTML2%>
            <%
                }
            %>
        </td>
    </tr>
    <tr>
        <td>
            <%
                strXML1 = (String)request.getAttribute("strXML3");
                swf = (String)request.getAttribute("strXMLType3");
                w = "100%";
                if (strXML1 != null && !"".equals(strXML1)) {
                    String chartHTML2 = createChartHTML("Charts/" + swf, "", strXML1, "", w, h, false);
            %>
            <%=chartHTML2%>
            <%
                }
            %>
        </td>
    </tr>
    <tr>
        <td>
            <%
                strXML1 = (String)request.getAttribute("strXML4");
                swf = (String)request.getAttribute("strXMLType4");
                w = "100%";
                if (strXML1 != null && !"".equals(strXML1)) {
                    String chartHTML2 = createChartHTML("Charts/" + swf, "", strXML1, "", w, h, false);
            %>
            <%=chartHTML2%>
            <%
                }
            %>
        </td>
    </tr>
</table>


