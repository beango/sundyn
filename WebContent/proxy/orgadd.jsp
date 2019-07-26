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
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function formPost(){
            $.ajax({
                url : "proxyOrgPost.action",
                data: $("form").serialize(),
                success: function(resp) {
                    if(resp.trim()==""){
                        succ($("#id").val() == '' ? '<s:text name="main.add.succ"/>' : "<s:text name="main.save.succ"/>", function(){
                            parent.closeDialog();
                            parent.refreshTab();
                        });
                    }
                    else{
                        error(resp)
                    }
                }
            });
        }
    </script>
</head>

<body>
<form class="layui-form" action="proxyOrgPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${entity.id}" />
    <input type="hidden" name="level" id="level" value="${entity.level==null?0:entity.level}" />
    <input type="hidden" name="isenable" id="isenable" value="${entity.isenable==null?0:entity.isenable}" />
    <div class="layui-form-item">
        <label class="layui-form-label"> <s:text name="proxyorg.field.name" /></label>
        <div class="layui-input-inline">
            <input type="text" name="orgname" id="orgname" class="layui-input" value="${entity.orgname}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="proxyorg.field.no" /></label>
        <div class="layui-input-inline">
            <input type="text" name="orgcode" id="orgcode" class="layui-input" value="${entity.orgcode}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="proxyorg.field.contact" /></label>
        <div class="layui-inline">
            <input type="text" name="mainname" id="mainname" class="layui-input" value="${entity.mainname}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="proxyorg.field.contactphone" /></label>
        <div class="layui-inline">
            <input type="text" name="maintel" id="maintel" class="layui-input" value="${entity.maintel}" />
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
