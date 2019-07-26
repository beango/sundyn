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
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function formPost(){
            $("#hallid").val($("#hallsele").val());
            $.ajax({
                url: "productformpost.action",
                data: $("form").serialize(),
                type: "post",
                success: function(resp) {
                    if(resp.succ){
                        succ($("#id").val() == '' ? '<s:text name="main.add.succ" />' : "<s:text name="main.save.succ" />", function(){
                            parent.closeDialog();
                            parent.refreshTab();
                        });
                    }
                    else{
                        error(resp.msg);
                    }
                }
            });
        }
    </script>
</head>

<body>
<form class="layui-form" action="productformpost.action" method="post">
    <input type="hidden" name="id" id="id" value="${model.id}" />
    <div class="layui-form-item">
        <label class="layui-form-label">产品名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="productname" id="productname" class="layui-input" value="${model.productname}"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">产品型号：</label>
        <div class="layui-input-inline">
            <input type="text" name="productcode" id="productcode" class="layui-input" value="${model.productcode}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">价格：</label>
        <div class="layui-input-inline">
            <input type="text" name="price" id="price" class="layui-input" value="${model.price}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">允许购买：</label>
        <div class="layui-input-inline">
            <input type="radio" name="canbuy" value="1" title="允许" <c:if test="${model.canbuy==1}"> checked="checked"</c:if>>
            <input type="radio" name="canbuy" value="0" title="不允许" <c:if test="${model.canbuy==0}"> checked="checked"</c:if>>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="vip.field.desc" /></label>
        <div class="layui-inline">
            <textarea type="text" name="comment" id="comment" class="layui-textarea" >${model.comment}</textarea>
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