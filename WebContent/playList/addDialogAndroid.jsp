<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px;"><s:text name='sudnyn.playList.playListName' /></label>
        <div class="layui-input-inline">
            <input name="playListName" id="playListName" class="input_comm"></input>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px;"><s:text name='sudnyn.playList.description' /></label>
        <div class="layui-input-inline">
            <input type="text" name="playListDescription" id="playListDescription" class="input_comm" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width: 100px;"><s:text name='sudnyn.playList.please' /></label>
        <div class="layui-input-inline" style="width: 100%;">
            <c:forEach items="${ls}" var="play">
                <input type="checkbox" value="${play.playId}" id="key${play.playId}" lay-skin="switch" />
                <label for="key${play.playId}">${play.playName}</label>
            </c:forEach>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.pic.ok' />"  onclick="playListAddAndroid()" class="hand" />
            <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()" class="hand">
        </div>
    </div>
</div>
</body>
<script>
    layui.use('form', function(){
    });
</script>
</html>
