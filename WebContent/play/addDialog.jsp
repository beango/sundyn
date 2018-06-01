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
    <table width="100%" height="173" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="20%" align="right">
                <s:text name='sundyn.play.playName' />
            </td>
            <td width="68%" align="left" style="border-color: #e9f5fd;">
                <input name="playName" id="playName" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playType' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select id="playType" onchange="playTypeChange(this.value)">
                    <option value="img" selected="selected"><s:text name='sundyn.play.pic' /></option>
                    <option value="text"><s:text name='sundyn.play.txt' /></option>
                    <option value="video"><s:text name='sundyn.play.vio' /></option>
                    <option value="doc"><s:text name='sundyn.play.doc' /></option>
                </select>
                </div>
            </td>
        </tr>
        <tr id="other" >
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playSource' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="text" name="playSource" id="playSource" readonly="readonly" style="background-color: #c3c3c3;" class="input_comm"/>
                <form id="pic" enctype="multipart/form-data" name="pic" action="playUpload.action" method="post">
                    <input type="hidden" name="imgName" id="imgName" />
                    <input type="file" name="img" id="img" onchange="getFileName()" />
                    <input type="button" value=" <s:text name='sundyn.upload' />" onclick="playupload();" class="layui-btn"/>
                </form>
            </td>
        </tr>
        <tr id="txt"  style="display:none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playSource' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <s:text name='sundyn.play.biaoti' /><input type="text" id="playTitle" name="title"></input><br/>
                <textarea rows="10" cols="30" id="playContent" name="playContent"></textarea>
            </td>
        </tr>

        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.playTime' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="playTimes" id="playTimes" value="5000" class="input_comm"/><s:text name='sundyn.play.info' />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.play.index' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="playIndex" id="playIndex" value="0" class="input_comm"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />" onclick="playAdd()" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()" class="hand">
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</html>