<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
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
</head>

<body style="min-height:500px;min-width:600px;">
<div class="layui-tab">
    <ul class="layui-tab-title">
        <li class="layui-this">附件列表</li>
        <li>上传新附件</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item layui-show">
            <table class="tablelist">
                <thead>
                <tr>
                    <th>文件名称</th>
                    <th>版本号</th>
                    <th>文件地址</th>
                    <th style="width:50%;">备注</th>
                    <th>删除</th>
                </tr>
                </thead>
                <c:forEach items="${list}" var="entity" varStatus="s">
                <tr>
                    <td>
                            ${entity.filename}
                    </td>
                    <td>
                            ${entity.apkver}
                    </td>
                    <td>
                            <a href="download.action?filepath=${entity.filepath}&filename=${entity.filename}" target="_blank">下载</a>
                    </td>
                    <td>
                        <div style="white-space:normal">${entity.comment}</div>
                    </td>
                    <td>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();del(${entity.id});"><i class="layui-icon layui-icon-delete"></i><s:text name="main.delete"/></a>
                    </td>
                </tr>
                </c:forEach>
            </table>
        </div>
        <div class="layui-tab-item">
            <form class="layui-form" action="vipPost.action" method="post">
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i>上传文件</button>
                        <a href="#" id="filepreview"></a>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注：</label>
                    <div class="layui-input-inline">
                        <textarea type="text" name="comment" id="comment" class="layui-textarea" style="width:500px;"></textarea>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline">
                        <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="save()" style=""/>
                    </div>
                </div>

                <input type="hidden" id="tmp_val_1" value="" />
            </form>
        </div>
    </div>
</div>

<script type="text/javascript">
    var _res = null;
    layui.use(['form', 'element', 'layer', 'upload'], function(){
        var $ = layui.jquery, upload = layui.upload;
        upload.render({
            elem: '#test3'
            ,url: 'productFileUpload.action'
            ,accept: 'file' //普通文件
            ,auto: false
            ,data: {filename: function(){
                    return $('#tmp_val_1').val();
                }}
            ,choose: function(obj){
                obj.preview(function(index, file, result){
                    $("#tmp_val_1").val(file.name);
                    obj.upload(index, file);
                });
            }
            ,done: function(res){
                _res = res;
                if (res.succ){
                    $("#filepreview").attr("href", res.filepath);
                    $("#filepreview").html(res.filename);
                }
                else
                    error("文件上传失败");
            }
        });
    });

    function save(){
        $.ajax({
            url: "productversionpost.action",
            type: "post",
            data: {productid: '${param.get("productid")}'
                ,filename: _res.filename
                ,filepath: _res.filepath
                ,comment: $("#comment").val()
            },
            success: function(res2){
                if (res2.succ)
                    succ("成功", function () {
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                else
                    error("失败");
            }
        });
    }

    function del(id){
        lconfirm("<s:text name="main.delete.confirm" />", function () {
            $.post("productversiondel.action?id=" + id, function(res2){
                if (res2.succ)
                    succ("<s:text name="main.delete.succ"/>", function () {
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                else
                    error("<s:text name="main.delete.fail"/>");
            });
        })
    }
</script>
</body>
</html>