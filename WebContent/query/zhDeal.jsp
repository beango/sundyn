<%@ page import="java.text.*,java.io.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="css/dialog.css" type="text/css"/>
    <link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
    <title><s:text name='zx.title'/></title>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script type="text/javascript" src="js/dtree.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <link href="css/clearbox.css" rel="stylesheet" type="text/css"/>
    <script src="js/clearbox.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script>
        function toshow(videofile, path1, id) {
            var a = document.getElementById(id);
            var file1 = videofile;
            videofile = videofile.toLowerCase();
            videofile = videofile.substring(videofile.lastIndexOf('.') + 1);
            if (videofile == "jpg") {
                a.href = "showPic.jsp?pic=" + file1;
                a.target = "_bank";
            } else {
                a.href = "query/videoPlay2.jsp?videofile=" + file1;
                a.target = "_bank";
            }
        }
    </script>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<iframe src="piedata.xml" style="display: none;"></iframe>
<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}"/>
<input type="hidden" name="keys" id="keys" value="${keys}"/>
<div id="man_zone">
    <table width="100%" height="100%" border="0">
        <tr>
            <td width="30%" valign="top">
                <div class="center_04">
                    <div class="center_04_left">
                        <div><img src="<s:text name='sundyn.query.pic.selectDept'/>" /></div>
                        <div class="center_04_left_01 kuang" style="text-align: left;">
                            <div class="dtree">${deptList}
                                <script type="text/javascript">
                                    d = new dTree('d');
                                    <c:forEach items="${deptList2}" var="dept">
                                    d.add(${dept.id},${dept.fatherId},'${dept.name}');
                                    </c:forEach>
                                    document.write(d);
                                </script>
                            </div>
                        </div>
                    </div>
                    <div class="center_04_right">
                        <div class="title360A"><h3><s:text name='sundyn.query.please1'/></h3></div>
                        <div class="center_04_right_01 kuang">
                            <form id="form1" name="form1" method="post" action="">
                                <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
                                    <tr>
                                        <td width="100%" align="left" style="border-color:#FFFFFF;"><s:text name='sundyn.query.selectEmployee'/>
                                            <input type="hidden" id="id" name="id" value="0" />
                                            <input type="text" id="keyword" name="keyword" class="input_comm" />
                                            <img src="<s:text name='sundyn.pic.query'/>" onclick="queryEmployeeNoKey()" width="55" height="25" style="cursor: pointer; vertical-align: middle;" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left" style="border-color:#FFFFFF;"><s:text name='sundyn.total.startDate'/>
                                            <input type="text" <s:text name="sundyn.language.calendar.setDay"/>  id="startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left" style="border-color:#FFFFFF;"><s:text name='sundyn.total.endDate'/>
                                            <input type="text" <s:text name="sundyn.language.calendar.setDay"/>  id="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <div class="fengge">&nbsp;</div>
                        <div class="title360A"><h3><s:text name='sundyn.query.please2'/></h3></div>
                        <div class="center_04_right_02 kuang">
                            <ul class="list">
                                <c:forEach items="${keyList}" var="key">
                                    <li>${key.name}:<input type="checkbox" value="${key.keyNo}"
                                               name="key${key.keyNo}" id="key${key.keyNo}" />
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                        <div class="center_04_right_03">
                            <table width="60%" border="0" cellspacing="0" cellpadding="0" style="border-color:#d6ecf8">
                                <tr>
                                    <td width="40%" align="right" style="border-color:#d6ecf8" colspan="3">
                                        <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="queryZhDeal1()" value="<s:text name="main.query" />" />
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </td>
            <td valign="top" id="queryZhDealAjaxContent">

            </td>
        </tr>
    </table>
</div>
<div id="dialog" style="width: 400px; height: 328px; display: none;"></div>
</body>
</html>
