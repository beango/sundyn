<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <title><s:text name='sundyn.title'/></title>

    <script>
        //这段脚本如果你的页面里有，就可以去掉它们了
        //欢迎访问我的网站queyang.com
        var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;

        function $(objID) {
            return document.getElementById(objID);
        }
    </script>
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/line.js"></script>
    <script type="text/javascript" src="js/jscharts.js"></script>
    <script type="text/javascript" src="js/json.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
</head>
<body>
<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>

<form id="form1" name="form1" method="post" action="">
    <table width="100%" height="50" border="0" cellpadding="0" cellspacing="0"
           style="border-color:#FFFFFF;">
        <tr>
            <td style="border-color:#FFFFFF;" align="left">
                <s:text name='sundyn.total.startDate'/>
                <input type="text" id="startDate" class="input_comm" <s:text
                    name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                <s:text name='sundyn.total.endDate'/>
                <input type="text" id="endDate" class="input_comm" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                <s:text name='sundyn.analyse.by'/>
                <select name="type" id="type">
                    <option value="day" selected="selected">
                        <s:text name='sundyn.analyse.day'/>
                    </option>
                    <option value="month">
                        <s:text name='sundyn.analyse.month'/>
                    </option>
                    <option value="year">
                        <s:text name='sundyn.analyse.year'/>
                    </option>
                </select>
                <img src="<s:text name='sundyn.analyse.pic.dataAnalyse' />" width="94" height="25" onclick="analyseContentRateAjax()" style="cursor: pointer; vertical-align: middle;"/>
                <img src="<s:text name='sundyn.analyse.pic.week' />" onclick="analyseContentRateAjaxDay(7)" style="cursor: pointer; vertical-align: middle;"/>
                <img src="<s:text name='sundyn.analyse.pic.15' />" onclick="analyseContentRateAjaxDay(15)" style="cursor: pointer; vertical-align: middle;"/>
                <img src="<s:text name='sundyn.analyse.pic.month' />" onclick="analyseContentRateAjaxDay(30)" style="cursor: pointer; vertical-align: middle;"/>
            </td>
        </tr>
    </table>
</form>

<div style="height:100%;" id="chartcontainer">

</div>
</body>
</html>
<script language="javascript">
    analyseContentRateAjaxDay(7);
</script>