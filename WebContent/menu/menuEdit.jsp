<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all" />
    <link rel="stylesheet" href="lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
</head>
<body>
<div class="layui-form" lay-filter="form1">
    <input type="hidden" name="id" id="id" class="input_comm" value="${model.id}"/>
    <table width="100%" height="173" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="32%" align="right">
                <s:text name="menu.form.label.name" />
            </td>
            <td width="68%" align="left" style="border-color: #e9f5fd;">
                <input name="menuName" id="menuName" class="input_comm" value="${model.menuName}"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="menu.form.label.nav" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="nav1" id="nav1" class="input_comm" style="width:280px;" value="${model.nav}"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="menu.form.label.parent" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="hidden" name="parentId" id="parentId" class="input_comm" value="${model!=null?model.parentId:parentModel.id}"/>
                ${model!=null?model.parentName:parentModel.menuName}
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="menu.form.label.isjy" />
            </td>
            <td>
                <input type="checkbox" name="isjy" id="isjy" lay-skin="switch" lay-text="<s:text name="main.yes" />|<s:text name="main.no" />" value="1" />
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="menu.form.label.isgeneral" />
            </td>
            <td>
                <input type="checkbox" name="isnotgeneral" id="isnotgeneral" lay-skin="switch" lay-text="<s:text name="main.yes" />|<s:text name="main.no" />" value="1" />
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="menu.form.label.iscore" />
            </td>
            <td>
                <input type="checkbox" name="iscore" id="iscore" lay-skin="switch" lay-text="<s:text name="main.yes" />|<s:text name="main.no" />" value="1" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="menu.form.label.order" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="menuorder" id="menuorder" class="input_comm" value="${model.menuorder}"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="menu.form.label.auth" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <ul id="zTreeMenuContent" class="ztree"></ul>
            </td>
        </tr>

        <tr>
            <td></td>
            <td>
    <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="post()" class="layui-btn"/>
    <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
            </td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript">
layui.use('form', function(){
    var form = layui.form;
    form.val("form1", {
        "isjy": ${model.isjy==1?1:0},
        "iscore": ${model.iscore==1?1:0},
        "isnotgeneral": ${model.isnotgeneral==1?1:0},
    })
});

    function post(){
        var id = document.getElementById("id").value;
        var menuName = document.getElementById("menuName").value;
        if(menuName==""){
            //alert("菜单名不能为空");
            //return false;
        }
        var nav = document.getElementById("nav1").value;

        var menuorder = document.getElementById("menuorder").value;
        var parentId = document.getElementById("parentId").value;
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        var nodes=zTree.getCheckedNodes(true),
        v="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].id + ",";
        }
    var isjy = $('input:checkbox[name="isjy"]:checked').val();
    var iscore = $('input:checkbox[name="iscore"]:checked').val();
    var isnotgeneral = $('input:checkbox[name="isnotgeneral"]:checked').val();

        dojo.xhrPost({url:"menuEditPost.action", content:{id:id, menuName:menuName, nav:nav, parentId:parentId,
            menuorder:menuorder, funccode: v, iscore:iscore, isjy:isjy, isnotgeneral:isnotgeneral}, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    succ('<s:text name="main.save.succ" />', function(){
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                }
                else{
                    error(resp);
                }
            }});
    }
    var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
    var setting = {
        check: {
            enable: true,
            chkStyle: "radio",
            radioType : "all"
        },
        async: {
            enable: true,
            autoParam: ["id=ids"],//, "name=n", "level=lv"
            url: "${ctx}/authQueryJSON.action?isCheck=1",
            dataFilter: filter,
            type: "post"
        },
        view: {
            fontCss: getFont,
            showLine: true,
            expandSpeed: "",
            selectedMulti: true
        },
        data: {
            keep: {
                parent: true
            },
            simpleData: {
                enable: false
            }
        },
        callback: {
            onAsyncSuccess: function(){
                var funcid = "${model.funcId}";
                if (funcid!=null && funcid != ""){
                    var node = zTree.getNodeByParam("id", funcid);
                    if(node){
                        node.checked = true;
                        zTree.updateNode(node);
                    }
                }
            }
        }
    };

    //初始化
    var rMenu,zTree;
    $(document).ready(function () {
        zTree = jQuery.fn.zTree.init($("#zTreeMenuContent"), setting);
    });

    //节点数据过滤
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) {
            return null;
        }
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    //字体设置
    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }
</script>
</html>