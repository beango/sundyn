<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        .layui-form-label{width:120px;}
    </style>
    <script type="text/javascript">
        function hallPost() {
            var data = $("form").serialize();
            $.ajax({
                url: 'hallPost.action',
                type: 'POST',
                data: data,
                success:function (resp) {
                    if (resp.trim() == "") {
                        succ("<s:text name="main.save.scuc" />", function () {
                            parent.closeDialog();
                            parent.refreshTab();
                        });
                    } else {
                        error(resp);
                    }
                }
            });
        }

        function validate(formData, jqForm, options) {
            var usernameValue = $('#hallno').fieldValue();
            var addressValue = $('#hallname').fieldValue();

            if (!usernameValue[0] || !addressValue[0]) {
                //alert('用户名和地址不能为空，自我介绍可以为空！');
                //return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form class="layui-form" action="hallPost.action" method="post">
    <input type="hidden" name="id" id="id" value="${hall.id}" />
    <input type="hidden" name="deptid" id="deptid" value="<%=request.getParameter("deptid")%>" />

    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.hallcode"/></label>
            <div class="layui-input-inline">
                <input type="text" name="hallno" id="hallno" class="layui-input" value="${hall.hallno}"/>
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.hallname"/></label>
            <div class="layui-input-inline">
                <label class="layui-form-label" style="text-align: left;width: 100%;">${hall.hallname}</label>
                <input type="hidden" name="hallname" id="hallname" class="layui-input" value="${hall.hallname}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.cpyjz"/></label>
            <div class="layui-input-inline">
                <input type="text" name="eval6warnvalue" id="eval6warnvalue" class="layui-input" value="${hall.eval6warnvalue}" />
            </div>
            <div class="layui-form-mid layui-word-aux">%</div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.ztyjz"/></label>
            <div class="layui-input-inline" style="width:160px;">
                <input type="text" name="pausewarnvalue" id="pausewarnvalue" class="layui-input" value="${hall.pausewarnvalue}" />
            </div>
            <div class="layui-form-mid layui-word-aux"><s:text name="main.minu"/></div>
        </div>
    </div>

    <%--<div class="layui-form-item">
        <label class="layui-form-label">大厅地址：</label>
        <div class="layui-input-inline">
            <input type="text" name="address" id="address" class="layui-input" value="${hall.address}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">经度：</label>
            <div class="layui-input-inline">
                <input type="text" name="longitude" id="longitude" class="layui-input" value="${hall.longitude}" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">纬度：</label>
            <div class="layui-input-inline">
                <input type="text" name="dimension" id="dimension" class="layui-input" value="${hall.dimension}"/>
            </div>
        </div>
    </div>--%>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.lxr"/></label>
            <div class="layui-input-inline">
                <input type="text" name="head" id="head" class="layui-input" value="${hall.head}" />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label"><s:text name="hall.field.lxrtel"/></label>
            <div class="layui-input-inline">
                <input type="text" name="headtel" id="headtel" autocomplete="off" class="layui-input" value="${hall.headtel}" />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="hall.field.desc"/></label>
        <div class="layui-input-inline">
            <textarea type="text" name="note" id="note" class="layui-input" style="width:524px; height:100px;">${hall.note}</textarea>
        </div>
    </div>
    <%--
    <div class="layui-form-item">
        <label class="layui-form-label">办公时间说明：</label>
        <div class="layui-input-inline">
            <input type="text" name="busitmnote" id="busitmnote" class="layui-input" value="${hall.busitmnote}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">公交路线：</label>
        <div class="layui-input-inline">
            <input type="text" name="path" id="path" class="layui-input" value="${hall.path}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">远程取号：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="远程取号" name="onlinenetxcticket" id="onlinenetxcticket" value="1" <c:if test="${hall.onlinenetxcticket==1}">checked="checked"</c:if> />
            </div>
        </div>
        <div class="layui-inline">
            <label class="layui-form-label">预约：</label>
            <div class="layui-input-inline">
                <input type="checkbox" title="预约" name="onlineappoint" id="onlineappoint" value="1" <c:if test="${hall.onlineappoint==1}">checked="checked"</c:if> />
            </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">预约说明：</label>
        <div class="layui-input-inline">
            <input type="text" name="appointnote" id="appointnote" class="layui-input" value="${hall.appointnote}" style="width:524px;" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">预约菜单排序：</label>
        <div class="layui-input-inline">
            <input type="text" name="sort" id="sort" class="layui-input" value="${hall.sort}" />
        </div>
    </div>--%>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="hallPost()" />
        </div>
    </div>
</form>
<script type="text/javascript">
    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</body>
</html>