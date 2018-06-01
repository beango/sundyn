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
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <table width="100%" height="173" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="32%" align="right">
                <s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" />
            </td>
            <td width=" 68%" align="left" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" name="id" value="${manager.id}">
                <input name="name" id="name" onchange="managerExist()" value="${manager.name}" class="input_comm" /><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.column.realName" /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="realname" id="realname" value="${manager.realname}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.role" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select id="userGroupId">
                    <c:forEach items="${list}" var="power" varStatus="index">
                        <option <c:if test="${power.id==manager.userGroupId}"> selected="selected"</c:if> value="${power.id}">${power.name}</option>
                    </c:forEach>
                </select>
                </div>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipType" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select name="remark" id="remark" onchange="tipChange(this.value)">
                    <option value="0" <c:if test="${manager.remark==0}"> selected="selected"</c:if>><s:text name="sundyn.user.select1" /></option>
                    <option value="1" <c:if test="${manager.remark==1}"> selected="selected"</c:if>><s:text name="sundyn.user.select2" /></option>
                    <option value="2" <c:if test="${manager.remark==2}"> selected="selected"</c:if>><s:text name="sundyn.user.select3" /></option>
                    <option value="3" <c:if test="${manager.remark==3}"> selected="selected"</c:if>><s:text name="sundyn.user.select4" /></option>
                </select>
                </div>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipMobile" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="ext1" id="ext1" value="${manager.ext1}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipPc" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="ext2" id="ext2" value="${manager.ext2}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />" onclick="managerEdit()" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"   onclick="closeDialog()" class="hand">
            </td>
        </tr>
    </table>
</div>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</body>
</html>
