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
    <table width="100%" height="50" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
        <tr>
            <td align="left" style="border-color:#FFFFFF;">
                <div id="dept" style="float:left;margin-top:5px;">
                    <select name="deptId">
                        <c:forEach items="${deptList}" var="dept" varStatus="index">
                            <option value="${dept.id}"
                                    <c:if test="${index.index ==0 }">
                                        selected="selected"
                                    </c:if>
                            >
                                    ${dept.name}
                            </option>
                        </c:forEach>
                    </select>
                    <img src="images/arrow.gif" style="cursor: pointer;" onclick="deptopen()"/>
                </div>
                <div style="float:left;">
                    <s:text name='sundyn.total.startDate'/>
                    <input type="text" id="startDate" class="input_comm" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    <s:text name='sundyn.total.endDate'/>
                    <input type="text" id="endDate" class="input_comm"<s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    <label><input type="radio" name="type" checked="checked" value="day"/><s:text name='sundyn.analyse.day'/></label>
                    <label><input type="radio" name="type" value="month"/><s:text name='sundyn.analyse.month'/></label>
                    <label><input type="radio" name="type" value="year"/><s:text name='sundyn.analyse.year'/></label>
                    <img src="<s:text name="sundyn.analyse.businessNumAnalyse" />" onclick="analyseDeptAjax()" style="cursor: pointer; vertical-align: middle;"/>
                    <img src="<s:text name="sundyn.analyse.contentNumAnalyse" />" onclick="analyseDeptContentAjax()" style="cursor: pointer; vertical-align: middle;"/>
                    <img src="<s:text name="sundyn.analyse.contentDegreeAnalyse" />" onclick="analyseDeptContentRateAjax()" style="cursor: pointer; vertical-align: middle;"/>
                </div>
            </td>
        </tr>
    </table>
</form>
<div style="height:100%;" id="chartcontainer">
</div>
<script type="text/javascript">
    $(function () {
        deptopen();
    })
</script>
</body>
</html>
