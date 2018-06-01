<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><s:text name='sundyn.advice.question.list' /></title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
	</head>
	<body>
    <div id="layui-main">
        <div>
            <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
                <tr>
                    <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="55" height="25" onclick="adviceToAdd('<s:text name="sundyn.advice.question.add1" />');" class="hand"/></td>
                </tr>
            </table>
        </div>
        <table width="100%" class="layui-table">
            <tr>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.advice.question' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
            </tr>
            <s:iterator value="advices"  status="num">
                <tr>
                    <td style="text-align: center;">
                        <s:property value='#num.index+1'/>
                    </td>
                    <td style="text-align: center;">
                            ${question.question}
                    </td>
                    <td style="text-align: center;">
                        <a href="javascript:adviceToUpate('${question.id}','<s:text name='sundyn.advice.question.update1' />');"><s:text name='sundyn.modifyOrupdate' /></a>
                        <a href="javascript:adviceDelete('${question.id}');"><s:text name='sundyn.del' /></a>
                    </td>
                </tr>
            </s:iterator>
        </table>
        <div id="pp"></div>
    </div>
	</body>
    <script type="text/javascript">
        initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});
    </script>
</html>
