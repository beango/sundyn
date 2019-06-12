<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
    <style type="text/css">
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 420px;
            height: 360px;
            overflow-y: scroll;
            overflow-x: auto;
        }
    </style>
    <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js?1"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        function formPost(){
            $("#hallid").val($("#hallsele").val());
            $.ajax({
                url: "jxDataPost.action",
                data : $("form").serialize(),
                success: function(resp) {
                    if(resp.trim()==""){
                        succ('<s:text name="main.delete.succ" />', function(){
                            parent.refreshTab();
                        });
                    }
                    else{
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

        function findEmployee(){
            var eno = $("#enostr").val();
            var key = eno.split('/')[0];
            initTree("?depttype=3&isOnlyLeaf=1&employeekey=" + key, null, true, null, function (e, treeId, treeNode) {
                var zTree = $.fn.zTree.getZTreeObj("treeDept"),
                    nodes = zTree.getCheckedNodes(true),
                    v = "";
                for (var i=0, l=nodes.length; i<l; i++) {
                    v += nodes[i].name + ",";
                }
                if (v.length > 0 ) v = v.substring(0, v.length-1);

                $("#enostr").val(v);
                $("#eno").val(nodes[0].name.split('/')[1]);
                $("#deptid").val(nodes[0].deptid);

                hideMenu();
            });
            showDeptTree('#enostr');
        }
    </script>
</head>

<body>
<form class="layui-form" action="jxDataPost.action" method="post" lay-filter="form">
    <input type="hidden" name="id" id="id" value="${entity.id}" />
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.query.search.name" /></label>
        <div class="layui-input-inline">
            <input type="text" name="enostr" id="enostr" class="layui-input" value="${entity.ename}${entity==null?"":"/"}${entity.eno}"/>
            <input type="hidden" name="eno" id="eno" value="${entity.eno}"/>
            <input type="hidden" name="deptid" id="deptid" value="${entity.deptid}"/>
        </div>
        <div class="layui-form-mid layui-word-aux"><input type="button" class="layui-btn layui-btn-xs layui-btn-normal" value="<s:text name="main.query" />" onclick="findEmployee()" /></div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.column.month"/></label>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="servicedate" name="servicedate" value="${entity.servicedate}" onClick="WdatePicker({dateFmt:'yyyy-MM',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.column.monthkq"/></label>
        <div class="layui-inline">
            <input type="text" name="ykq" id="ykq" class="layui-input" value="${entity.ykq}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.column.qzby"/></label>
        <div class="layui-inline">
            <input type="text" name="qzby" id="qzby" class="layui-input" value="${entity.qzby}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.column.rcxc"/></label>
        <div class="layui-inline">
            <input type="text" name="rcxc" id="rcxc" class="layui-input" value="${entity.rcxc}" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><s:text name="jx.column.ypfj"/></label>
        <div class="layui-input-inline" style="width:80%;">
            <input type="checkbox" lay-skin="switch" lay-text="<s:text name="main.radio.enable"/>|<s:text name="main.radio.disable"/>" lay-filter="ypfjset"
                   name="ypfjset" value="on" id="ypfjset" />
                <div style="display:none;" id="ypfjcontainer">
                    <c:forEach items="${ypfjGroup}" var="item" varStatus="index">
                        <input type="radio" name="ypfj" id="ypfj" value="${item.dictvalue}" title="${item.note}" lay-filter="ypfjother"></input><br>
                    </c:forEach>
                    <input type="text" id="fjdesc" name="fjdesc" value="${entity.fjdesc}" style="display: none;" class="layui-input" />
                </div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="<s:text name="main.save"/>" onclick="formPost()" />
        </div>
    </div>
    <div id="treeContent" class="menuContent" style="position: absolute; display:none;">
        <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
    </div>
</form>

<script type="text/javascript">
    layui.use(['form', 'element'], function() {
        var form = layui.form;
        var ypfj = ${entity.ypfj==null || entity.ypfj=="" ? false:true};

        form.on('switch(ypfjset)', function (data) {
            if (data.elem.checked) {
                $("#ypfjcontainer").show();
            }
            else {
                $("#ypfjcontainer").hide();
            }
        });
        form.on('radio(ypfjother)', function (data) {
            if (data.value=="999") {
                $("#fjdesc").show();
            }
            else
            {
                $("#fjdesc").hide();
            }
        });
        form.val("form", {
            "ypfjset": ypfj,
            "ypfj": "${entity.ypfj}"
        })
        form.render('checkbox');
        if (ypfj)
            $("#ypfjcontainer").show();
        if ("${entity.ypfj}"=="999")
            $("#fjdesc").show();
    });
</script>
</body>
</html>
