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
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function formPost(){
            $("#hallid").val($("#hallsele").val());
            $.ajax({
                url: "proxyPost.action",
                data: $("form").serialize(),
                success: function(resp) {
                    if(resp.trim()==""){
                        succ($("#id").val() == '' ? '增加成功' : "修改成功", function(){
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
<form class="layui-form" action="proxyPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${entity.id}" />
    <div class="layui-form-item">
        <label class="layui-form-label">代理人姓名：</label>
        <div class="layui-input-inline">
            <input type="text" name="name" id="name" class="layui-input" value="${entity.name}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">身份识别号码：</label>
        <div class="layui-input-inline">
            <input type="text" name="idcard" id="idcard" class="layui-input" value="${entity.idcard}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属机构编码：</label>
        <div class="layui-input-inline">
            <input type="text" name="orgcode" id="orgcode" class="layui-input" value="${entity.orgcode}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">号码类型：</label>
        <div class="layui-inline">
            <input type="text" name="idtype" id="idtype" class="layui-input" value="${entity.idtype}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注信息：</label>
        <div class="layui-inline">
            <input type="text" name="note" id="note" class="layui-input" value="${entity.note}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="提交" onclick="formPost()" />
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(['form', 'element', 'layer'], function() {
        var form = layui.form;
    });

</script>
</body>
</html>
