<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <table class="layui-table">
        <tr>
            <td align="right" width="20%">
                <input type="hidden" name="advice.question.id" id="aid" value="${advice.question.id }"/>
                <s:text name='sundyn.advice.question' /><s:text name='sundyn.colon' />
            </td>
            <td align="left">
                <input name="notice.title" style="width:220px" class="input_comm" id="adviceQuestion"  value="${advice.question.question }" />
            </td>
        </tr>
        <tr>
            <td>
                <s:text name='sundyn.advice.choose' /><s:text name='sundyn.colon' />
            </td>
            <td>
                <ul id="addInput">
                    <s:iterator value="advice.answers" status="index" >
                        <li style="width:400px">
                            <input style="width:300px" type='text' class="input_comm" name='as' value='${answer}' /> <c:if test="${index.index ==0 }"><img src="images/tp_add.gif"  onclick="addInput()"/>   </c:if><c:if test="${index.index !=0 }"><img src="images/tp_del.gif" onclick="delInput(this)" /> </c:if>
                        </li>
                    </s:iterator>
                </ul>
            </td>
        </tr>
    </table>
    <div class="bottom" style="margin-bottom: 5px; text-align: center;width:100%;">
        <div class="close">
            <img src="<s:text name="sundyn.pic.ok" />" onclick="adviceUpate();"  style="cursor: pointer;"/>
            <img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
        </div>
    </div>
</div>
</body>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</html>
