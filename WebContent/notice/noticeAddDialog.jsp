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
    <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td width="12%" align="right" style="border-color: #e9f5fd;">
                <s:text name="sundyn.notice.title"/><s:text name='sundyn.colon' />
            </td>

            <td align="left" style="border-color: #e9f5fd;">
                <input style="width:600px;" name="notice.title" class="input_comm" id="noticeTitle"  value="" />
            </td>
        </tr>
        <tr >
            <td align="right"  style="border-color: #e9f5fd;"><s:text name="sundyn.notice.content"/><s:text name='sundyn.colon' />
                <div style="color:red;">(<s:text name="notice.valid.content.length4000" />)</div>
            </td>
            <td align="left"  style="border-color: #e9f5fd;">
                <textarea name="notice.content"  id="noticeContent"  style="height:400px; width:100%;"></textarea>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="noticAdd()" class="layui-btn"/>
                <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    layui.use("layer")
    // 添加 通知公告
    function noticAdd(){
        var noticeTitle = document.getElementById("noticeTitle").value;
        var noticeContent = document.getElementById("noticeContent").value;
        if (noticeTitle.length==0){
            lalert("<s:text name="notice.valid.title.nonull" />");
            return;
        }
        if (noticeContent.length==0){
            lalert("<s:text name="notice.valid.content.nonull" />");
            return;
        }
        dojo.xhrPost({url:"noticeAdd.action", content:{title:noticeTitle,content:noticeContent}, load:function (resp, ioArgs) {
                if (resp.trim() == "") {
                    succ('<s:text name="main.add.succ" />', function(){
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
</body>
</html>