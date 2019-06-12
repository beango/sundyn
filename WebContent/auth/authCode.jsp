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
    <input type="hidden" name="parentId" id="parentId" class="input_comm" value="${parentModel.id}"/>
    <span style="font-size: 32px; font-weight: bold;">${parentModel.funcName}</span>
    <fieldset style="width: 500px;">
        <legend><s:text name="menu.form.label.auth" /></legend>
        <div align="left">
            <ul>
                <c:forEach items="${authCodeList}" var="authCode">
                    <li style="padding:3px;">
                        <%--<input type="checkbox" lay-skin="switch" lay-text="授权|${authCode.funcName}" lay-filter="employeeInfoSet" name="employeeInfoSet1" value="1" id="est1" <c:if test="${employeeInfoSet.employeeName== 'true'}">checked="checked"</c:if> />--%>
                        <input type="button" value="${authCode.funcName}" class="layui-btn" onclick="editAuthCode(${authCode.id})"/>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </fieldset>

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
            <td></td>
            <td>
                <input type="button" class="layui-btn" onclick="funcAdd()" value="<s:text name="main.save" />"/>
                <input type="button" id="btnDel" style="display: none;" class="layui-btn layui-btn-danger" onclick="funcDel()" value="<s:text name="main.delete" />"/>
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    //Demo
    layui.use(['form', 'element'], function(){
        var form = layui.form;
    });

    function funcAdd(){
        var id = document.getElementById("id").value;
        var funcName = document.getElementById("funcName").value;
        if(funcName==""){
            alert("<s:text name="auth.entity.validation.name.notnull" />");
            return false;
        }
        var funcCode = document.getElementById("funcCode").value;
        if(funcCode==""){
            alert("<s:text name="auth.entity.validation.code.notnull" />");
            return false;
        }
        var parentId = document.getElementById("parentId").value;

        dojo.xhrPost({url:"authEditPost.action", content:{id:id, funcName:funcName, funcCode:funcCode, parentId:parentId, orderId:0}, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    location.reload()
                }
                else{
                    layer.msg(resp, {
                        icon: 2,
                        time: 800
                    }, function(){
                    });
                }
            }});
    }

    function editAuthCode(id){
        dojo.xhrPost({url:"getAuthData.action", content:{id:id}, load:function (resp, ioArgs) {
            var json = JSON.parse(resp);
                if(json){
                    $("#funcName").val(json.name);
                    $("#funcCode").val(json.code);
                    $("#id").val(json.id);
                    $("#btnDel").show();
                }
            }});
    }

    function funcDel(){
        var id = document.getElementById("id").value;
        dojo.xhrPost({url:"authDelPost.action", content:{id: id}, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    succ('<s:text name="main.delete.succ" />', function(){
                        refreshTab();
                    });
                }
                else{
                    error(resp);
                }
            }});
    }
</script>
</html>