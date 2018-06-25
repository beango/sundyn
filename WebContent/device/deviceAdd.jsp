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
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<div class="layui-form">
    <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td width="32%" align="right" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" value="<%=request.getParameter("id")%>"/>
                MAC地址<s:text name='sundyn.colon' />
            </td>

            <td align="left" style="border-color: #e9f5fd;">
                <input name="mac" class="input_comm" id="mac"  value="${vo.mac}" />
            </td>
        </tr>
        <tr >
            <td align="right"  style="border-color: #e9f5fd;">批次<s:text name='sundyn.colon' /></td>
            <td align="left"  style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                    <select name="batchid" id="batchid">
                        <option value="" <c:if test="batch.id==vo.batchid">selected="selected"</c:if>>--请选择--</option>
                        <c:forEach items="${batchList}" var="batch" varStatus="index">
                            <option value="${batch.id}" <c:if test="${batch.id == vo.batchid}">selected="selected"</c:if>>${batch.batchname}</option>
                        </c:forEach>
                    </select>
                </div>

            </td>
        </tr>
        <tr >
            <td align="right"  style="border-color: #e9f5fd;">添加时间<s:text name='sundyn.colon' /></td>
            <td align="left"  style="border-color: #e9f5fd;">
                <input name="ctime" class="input_comm" id="ctime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${vo.ctime}" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name="sundyn.pic.ok" />" onclick="deviceAdd();"  style="cursor: pointer;"/>
                <img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
            </td>
        </tr>
    </table>
</body>
<script type="text/javascript">
    $(function(){
        layui.use(['form'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;
        });
    });
</script>
</html>