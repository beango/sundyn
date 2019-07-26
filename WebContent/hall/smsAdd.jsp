<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function post() {
            var data = $("form").serialize();
            $.ajax({
                url: 'hallSmsPost.action',
                type: 'POST',
                data: data,
                success:function (resp) {
                    if (resp.trim() == "") {
                        succ("<s:text name="main.save.succ" />",function () {
                            parent.closeDialog();
                            parent.refreshTab();
                        });
                    } else {
                        error(resp);
                    }
                }
            });
        }

        function validate(formData, jqForm, options) {
            var usernameValue = $('#hallno').fieldValue();
            var addressValue = $('#hallname').fieldValue();

            if (!usernameValue[0] || !addressValue[0]) {
                //alert('用户名和地址不能为空，自我介绍可以为空！');
                //return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form class="layui-form" action="hallPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${hall.id}" />
    <input type="hidden" name="deptid" id="deptid" value="<%=request.getParameter("deptid")%>" />

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.hallname" /></label>
            <div class="layui-input-inline">
                <label class="layui-form-label" style="text-align: left;width: 100%;">${hall.hallname}</label>
                <input type="hidden" name="hallname" id="hallname" class="layui-input" value="${hall.hallname}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.notifymobile" /></label>
            <div class="layui-input-inline" style="width:auto">
                <input type="text" name="notifymobile" id="notifymobile" class="layui-input" style="width:524px;" value="${hall.notifymobile}" />
                <div class="layui-form-mid layui-word-aux" style="width:500px;"><s:text name="hall.field.notifymobile.tips" /></div>
            </div>

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="hall.field.notifytemplate" /></label>
        <div class="layui-input-inline" style="width:auto">
            <textarea type="text" name="eval7template" id="eval7template" class="layui-input" style="width:524px; height:100px;">${hall.eval7template}</textarea>
            <div class="layui-form-mid layui-word-aux" style="width:500px;">${smsTemplateTip.note}</div>
        </div>

    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="post()" />
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(['form', 'element']);
</script>
</body>
</html>