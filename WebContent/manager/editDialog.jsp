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
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
</head>
<body>
<div class="layui-form">
    <table width="100%" height="173" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="32%" align="right">
                <s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" />
            </td>
            <td width=" 68%" align="left" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" name="id" value="${manager.id}">
                <input name="name" id="name" onchange="managerExist()" value="${manager.name}" class="input_comm" /><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.column.realName" /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="realname" id="realname" value="${manager.realname}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                部门（机构）
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <ul id="zTreeMenuContent" class="ztree"></ul>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.role" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline" style="display: none;">
                <select id="userGroupId">
                    <c:forEach items="${list}" var="power" varStatus="index">
                        <option <c:if test="${power.id==manager.userGroupId}"> selected="selected"</c:if> value="${power.id}">${power.name}</option>
                    </c:forEach>
                </select>

                </div>
                <div class="layui-input-inline">
                    <c:forEach items="${list}" var="power" varStatus="index">
                        <input type="checkbox" lay-skin="switch" lay-text="${power.name}|${power.name}" lay-filter="employeeInfoSet" value="${power.id}" name="managerPowers" <c:if test="${power.checked}">checked="checked"</c:if> />&nbsp;&nbsp;<br/>
                    </c:forEach>
                </div>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipType" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select name="remark" id="remark" onchange="tipChange(this.value)">
                    <option value="0" <c:if test="${manager.remark==0}"> selected="selected"</c:if>><s:text name="sundyn.user.select1" /></option>
                    <option value="1" <c:if test="${manager.remark==1}"> selected="selected"</c:if>><s:text name="sundyn.user.select2" /></option>
                    <option value="2" <c:if test="${manager.remark==2}"> selected="selected"</c:if>><s:text name="sundyn.user.select3" /></option>
                    <option value="3" <c:if test="${manager.remark==3}"> selected="selected"</c:if>><s:text name="sundyn.user.select4" /></option>
                </select>
                </div>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipMobile" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="ext1" id="ext1" value="${manager.ext1}" class="input_comm" />
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipPc" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="ext2" id="ext2" value="${manager.ext2}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />" onclick="managerEdit()" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"   onclick="closeDialog()" class="hand">
            </td>
        </tr>
    </table>
</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
    });
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
            url: "${ctx}/authDeptTree.action",
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
                var funcid = "${manager.deptid}";
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

    //字体设置
    function getFont(treeId, node) {
        return node.font ? node.font : {};
    }
</script>
</body>
</html>
