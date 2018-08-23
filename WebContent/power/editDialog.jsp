<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
</head>

<body>
<div class="layui-form">
    <table width="100%" height="173" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="12%" align="right">
                <s:text name='sundyn.role.roleName' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" name="id" value="${m.id}" />
                <input name="name" id="name"   value="${m.name}" onchange="powerExist()" class="input_comm" /><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.column.systemSetup' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="checkbox" name="baseSet" id="baseSet" <c:if test="${m.baseSet==1}">  checked="checked" </c:if> lay-skin="switch" />
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.column.baseSetup' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="checkbox" name="dataManage" id="dataManage" <c:if test="${m.dataManage==1}">  checked="checked"  </c:if> lay-skin="switch" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.role.atDept' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select name="deptId" id="deptId">
                    <c:forEach items="${list}" var="dept" varStatus="index">
                        <option value="${dept.id}" <c:if test="${dept.id==m.deptIdGroup}">selected="selected"</c:if>><c:forEach begin="0" end="${dept.lenvel}">&nbsp;</c:forEach>${dept.name}</option>
                    </c:forEach>
                </select>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <div class="kuang">
                    <ul id="zTreeMenuContent" class="ztree"></ul>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />"  onclick="powerEdit()"
                     class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"   onclick="closeDialog()"
                     class="hand">
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
    var curStatus = "init", curAsyncCount = 0, asyncForAll = false, goAsync = false;
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType:  { "Y" : "ps", "N" : "s" }
        },
        async: {
            enable: true,
            autoParam: ["id=ids"],//, "name=n", "level=lv"
            url: "${ctx}/authQueryJSON.action?isAll=1&isCheck=1",
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
                <c:forEach items="${powerFunc}" var="pf" varStatus="index">
                var node = zTree.getNodeByParam("id", ${pf.funcid});
                if(node){
                    node.checked = true;
                    zTree.updateNode(node);
                }
                </c:forEach>
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