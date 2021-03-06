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
    <input type="hidden" name="id" id="id" class="input_comm" value="${model.id}"/>
    <table width="100%" height="173" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="32%" align="right">
                <s:text name="auth.form.label.name" />
            </td>
            <td width="68%" align="left" style="border-color: #e9f5fd;">
                <input name="funcName" id="funcName" class="input_comm" value="${model.funcName}"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="auth.form.label.code" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="funcCode" id="funcCode" class="input_comm" value="${model.funcCode}"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="auth.form.label.parent" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="hidden" name="parentId" id="parentId" class="input_comm" value="${model!=null?model.parentId:parentModel.id}"/>
                ${model!=null?model.parentName:parentModel.funcName}
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="auth.form.label.order" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="orderId" id="orderId" class="input_comm" value="${model.orderId}"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="funcAdd()" class="layui-btn"/>
                <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    layui.use('form');

    function funcAdd(){
        var id = document.getElementById("id").value;
        var funcName = document.getElementById("funcName").value;
        if(funcName==""){
            alert("<s:text name="auth.entity.validation.name.notnull"/>");
            return false;
        }
        var funcCode = document.getElementById("funcCode").value;
        if(funcCode==""){
            //alert("权限码不能为空");
            //return false;
        }
        var orderId = document.getElementById("orderId").value;
        var parentId = document.getElementById("parentId").value;

        dojo.xhrPost({url:"authEditPost.action", content:{id:id, funcName:funcName, funcCode:funcCode, parentId:parentId, orderId:orderId}, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    succ('<s:text name="auth.save.succ" />', function(){
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