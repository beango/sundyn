<%@ page import="com.sundyn.vo.PowerTypeEnum" %>
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
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
</head>

<body>
<div class="layui-form">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="15%" align="right">
                <s:text name='sundyn.role.roleName' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" name="id" value="${m.id}" />
                <input name="name" id="name"   value="${m.name}" onchange="powerExist()" class="input_comm" /><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" width="12%" align="right">
                <s:text name="sundyn.column.roleStatus" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="radio" name="status" id="status" value="1" <c:if test="${m.status!=0}">checked="checked"</c:if> /><s:text name="main.radio.enable" />
                <input type="radio" name="status" id="status" value="0" <c:if test="${m.status==0}">checked="checked"</c:if> /><s:text name="main.radio.disable" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" width="12%" align="right">
                <s:text name="sundyn.column.roleType" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                    <c:set var="p1" value="<%=PowerTypeEnum.业务办理.getCode()%>" />
                    <c:set var="p2" value="<%=PowerTypeEnum.系统管理.getCode()%>" />
                    <c:set var="p3" value="<%=PowerTypeEnum.安全管理.getCode()%>" />
                    <c:set var="p4" value="<%=PowerTypeEnum.审计管理.getCode()%>" />
                    <select style="width:150px" id="powertype" name="powertype" lay-filter="powertype">
                        <option value="" <c:if test="${m.powertype==''}">selected="selected"</c:if>><s:text name="main.none" /></option>
                        <option value="<%=PowerTypeEnum.业务办理.getCode()%>" <c:if test="${m.powertype==p1}">selected="selected"</c:if>><%=PowerTypeEnum.业务办理%></option>
                        <option value="<%=PowerTypeEnum.系统管理.getCode()%>" <c:if test="${m.powertype==p2}">selected="selected"</c:if>><%=PowerTypeEnum.系统管理%></option>
                        <option value="<%=PowerTypeEnum.安全管理.getCode()%>" <c:if test="${m.powertype==p3}">selected="selected"</c:if>><%=PowerTypeEnum.安全管理%></option>
                        <option value="<%=PowerTypeEnum.审计管理.getCode()%>" <c:if test="${m.powertype==p4}">selected="selected"</c:if>><%=PowerTypeEnum.审计管理%></option>
                    </select>
                </div>
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
        <tr style="display: none;">
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
            <td style="border-color: #e9f5fd;" align="right"><s:text name="menu.form.label.auth" /></td>
            <td>
                <div style="overflow:auto;height:300px;">
                    <ul id="zTreeMenuContent" class="ztree"></ul>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="powerEdit()" class="layui-btn"/>
                <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    layui.use('form', function(){
        var form = layui.form;
        form.on('select(powertype)', function(data){
            setting.async.url = "authQueryJSON.action?isAll=1&isCheck=1&powertype=" + data.value;
            loadfunctree();
        });
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
            url: "authQueryJSON.action?isAll=1&isCheck=1&powertype=" + $("#powertype").val(),
            dataFilter: filter,
            type: "post"
        },
        view: {
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
        loadfunctree();
    });

    function loadfunctree(){
        zTree = jQuery.fn.zTree.init($("#zTreeMenuContent"), setting);
    }
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


    function powerEdit() {
        var id = document.getElementById("id").value;
        var name = document.getElementById("name").value;
        if(name==""){
            error("<s:text name="power.valid.name.notnull" />");
            return false;
        }
        if (document.getElementById("tip").innerHTML=="<s:text name="power.valid.name.exists" />") {
            error("<s:text name="power.valid.name.exists" />");
            return false;
        }
        var baseSet = document.getElementById("baseSet");
        if (baseSet.checked) {
            baseSet = 1;
        } else {
            baseSet = 0;
        }
        var dataManage = document.getElementById("dataManage");
        if (dataManage.checked) {
            dataManage = 1;
        } else {
            dataManage = 0;
        }
        var deptId = document.getElementById("deptId").value;
        var zTree = jQuery.fn.zTree.getZTreeObj("zTreeMenuContent");
        var nodes=zTree.getCheckedNodes(true),
            v="";
        for(var i=0;i<nodes.length;i++){
            v+=nodes[i].id + ",";
        }
        var powertype = $("#powertype").val();
        var status = $('input:radio[name="status"]:checked').val();

        dojo.xhrPost({url:"powerEdit.action", content:{id:id,funcid:v, name:name, baseSet:baseSet, dataManage:dataManage,
                deptId:deptId, powertype:powertype, status:status}, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    succ(id==''?"<s:text name="main.add.succ" />":"<s:text name="main.save.succ" />", function(){
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
</html>