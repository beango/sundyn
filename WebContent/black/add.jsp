<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function formPost(){
            $("#hallid").val($("#hallsele").val());
            $.ajax({
                url: "blackPost.action",
                data:$("form").serialize(),
                success: function(resp) {
                    if(resp.trim()==""){
                        succ($("#id").val() == '' ? '<s:text name="main.add.succ" />' : "<s:text name="main.save.succ" />", function(){
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
</head>

<body>
<form class="layui-form" action="blackPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${entity.id}" />
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="blacklist.field.name" /></label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" class="layui-input" value="${entity.name}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="blacklist.field.no" /></label>
        <div class="layui-input-inline">
            <input type="text" name="idcard" id="idcard" class="layui-input" value="${entity.idcard}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="blacklist.field.type" /></label>
        <div class="layui-inline">
            <input type="text" name="idtype" id="idtype" class="layui-input" value="${entity.idtype}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="blacklist.field.desc" /></label>
        <div class="layui-inline">
            <input type="text" name="note" id="note" class="layui-input" value="${entity.note}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="formPost()" />
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(['form', 'element']);
</script>
</body>
</html>
