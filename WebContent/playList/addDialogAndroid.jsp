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
            <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="playListAddAndroid()" class="layui-btn"/>
            <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
        </div>
    </div>
</div>
</body>
<script>
    layui.use('form');

    // 播放列表添加
    function playListAddAndroid() {
        var playListName = document.getElementById("playListName").value;
        var playListDescription = document.getElementById("playListDescription").value;
        var playIds = getAllKey();
        dojo.xhrPost({url:"playListAddAndroid.action", content:{playListName:playListName, playListDescription:playListDescription, playIds:playIds}, load:function (resp, ioArgs) {
                if (resp.trim() == "") {
                    succ('<s:text name="main.add.succ" />', function(){
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                }
                else{
                    error(resp);
                }
            }});
    }
</script>
</html>
