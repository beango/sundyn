<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device"content="mobile">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta http-equiv="Cache-Control" content="no-transform" />
    <title>广州市众鑫电子设备授权管理系统</title>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<form class="layui-form" id="formex" action="#" method="post">
    <input type="hidden" name="id" id="id" value="${managerentity.id}" />
    <div class="layui-form-item">
        <label class="layui-form-label">密码：</label>
        <div class="layui-input-inline">
            <input type="password" class="layui-input" name="password" value="" placeholder="密码，如不修改请留空！" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">真实姓名：</label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" name="realname" value="${managerentity.realname}" placeholder="真实姓名" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">公司名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="orgname" class="weui-input" value="${managerextentity.orgname}" placeholder="公司名称" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系人：</label>
        <div class="layui-input-inline">
            <input type="text" name="contact" class="weui-input" value="${managerentity.contact}" placeholder="联系人" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机：</label>
        <div class="layui-input-inline">
            <input type="text" name="telphone" class="weui-input" value="${managerentity.telphone}" placeholder="手机" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱：</label>
        <div class="layui-input-inline">
            <input type="text" name="email" class="weui-input" value="${managerentity.email}" placeholder="邮箱" />
        </div>
    </div>
    <div class="layui-form-item">
    <label class="layui-form-label"></label>
    <div class="layui-input-inline">
        <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="saveManagerExt()" />
    </div>
</div>
</form>
<script type="text/javascript">
    layui.use("layer");
    function saveManagerExt() {
        $.ajax({
            url:"saveRemoteManagerExt.action",
            data: $("#formex").serialize(),
            success: function (resp) {
                if(resp.succ){
                    succ("<s:text name="main.save.succ" />", function(){
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                }
                else{
                    error(resp);
                }
            }
        });
    }
</script>
</body>
</html>