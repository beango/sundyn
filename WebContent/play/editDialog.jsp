<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
                <s:text name='sundyn.play.playName' />
            </td>
            <td width="68%" align="left" style="border-color: #e9f5fd;">
                <input type="hidden" name="playId" id="playId" value="${p.playId}" />
                <input name="playName" id="playName" value="${p.playName}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playType' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select id="playType" lay-filter="playType">
                    <option value="img" <c:if test="${p.playType=='img'}">selected="selected"</c:if> ><s:text name='sundyn.play.pic' /></option>
                    <option value="text" <c:if test="${p.playType=='text'}">selected="selected"</c:if> ><s:text name='sundyn.play.txt' /></option>
                    <option value="html" <c:if test="${p.playType=='html'}">selected="selected"</c:if> ><s:text name='sundyn.play.html' /></option>
                    <option value="video" <c:if test="${p.playType=='video'}">selected="selected"</c:if> ><s:text name='sundyn.play.vio' /></option>
                    <option value="doc" <c:if test="${p.playType=='doc'}">selected="selected"</c:if> ><s:text name='sundyn.play.doc' /></option>
                </select>
                </div>
            </td>
        </tr>
        <tr id="other"  <c:if test="${p.playType == 'text'}">  style="display:none;" </c:if> >
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playSource' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="text" name="playSource" id="playSource" readonly="readonly" style="background-color: #c3c3c3;" value="${p.playSource}" class="input_comm" />
                <form id="pic" enctype="multipart/form-data" name="pic" action="employeeAdd.action" method="post">
                    <input type="hidden" name="imgName" id="imgName" />
                    <input type="file" name="img" id="img" onblur="getFileName()" style="width: 180px;" />
                    <input type="button" class="layui-btn layui-btn-sm" value="<s:text name='sundyn.upload' />" onclick="playupload();"/>
                    <c:if test="${p.playType=='doc'}">
                        <a href="playSource/${p.playSource}/index.html" target="_blank" class="layui-btn layui-btn-sm">下载doc</a>
                    </c:if>
                    <c:if test="${p.playType!='doc'}">
                        <a href="playSource/${p.playSource}" target="_blank" class="layui-btn layui-btn-sm">下载</a>
                    </c:if>
                </form>
            </td>
        </tr>
        <tr id="txt1" <c:if test="${p.playType != 'text'}">  style="display:none;" </c:if>>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.biaoti' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="text" id="playTitle" name="playTitle" value="${p.playTitle}" class="input_comm"></input>
            </td>
        </tr>
        <tr id="txt2" <c:if test="${p.playType != 'text'}">  style="display:none;" </c:if>>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playSource' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <textarea rows="10" cols="50" id="playContent" name="playContent">${p.playContent }</textarea>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playTime' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="playTimes" id="playTimes" value="${p.playTimes}" class="input_comm" /><s:text name='sundyn.play.info' />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.index' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="playIndex" id="playIndex" value="${p.playIndex}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />"  onclick="playEdit()" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()" class="hand">
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    layui.use('form', function(){
        var form = layui.form;
        form.on('select(playType)', function(data){
            playTypeChange(data.value);
        });
    });
</script>
</html>