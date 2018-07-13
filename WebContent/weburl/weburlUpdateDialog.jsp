<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel=stylesheet href="js/ueditor/themes/default/css/ueditor.css"></link>

    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <!-- 配置文件 -->
    <script type="text/javascript" src="js/ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="js/ueditor/ueditor.all.min.js"></script>
</head>

<body>
<div class="layui-form">
    <input  type="hidden" name="weburl.id" id="uid" value="${weburl.id }"/>
    <table style="width:100%;height:100%;" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td align="right" style="width:120px;">
                <s:text name="sundyn.weburl.name"/><s:text name='sundyn.colon' />
            </td>
            <td>
                <input name="weburl.name" style="width:500px;" class="input_comm" id="webname"  value="${weburl.name }" />
            </td>
        </tr>
        <tr >
            <td align="right"><s:text name="sundyn.weburl.url"/><s:text name='sundyn.colon' /></td>
            <td align="left">
                <textarea name="weburl.url" id="weburl" style="width:94%;height:500px;">${weburl.url}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align:center;">
                <img src="<s:text name='sundyn.pic.ok' />" onclick="weburlUpate();" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog();" class="hand">
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    UE.getEditor('weburl');
</script>
</body>
</html>