<%@ page import="com.xuan.xutils.utils.StringUtils" %>
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
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body>
<div class="layui-form">
    <table width="100%" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="20%" align="right">
                <s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" />
            </td>
            <td width="30%" align="left" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" name="id" value="${manager2.id}">
                <input type="hidden" id="userid" name="userid" value="${manager2.userid}">
                <input name="name" id="name" onchange="managerExist()" value="${manager2.name}" class="input_comm" /><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
            <td style="border-color: #e9f5fd;" width="20%" align="right">
                <s:text name="sundyn.column.realName" /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="realname" id="realname" value="${manager2.realname}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                部门（机构）<s:text name="sundyn.colon" />
            </td>
            <td align="left">
                <input id="deptSel" class="input_comm" type="text" readonly value="${manager2.deptname}" onclick="showDeptTree(this,null);"/>
            </td>

            <td align="right">密码<s:text name="sundyn.colon" /></td>
            <td align="left">
                <input type="password" name="pwd" id="pwd" value="" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td align="right">
                状态<s:text name="sundyn.colon" />
            </td>
            <td>
                <input type="radio" name="status" id="status" value="1" <c:if test="${manager2==null || manager2.status==1 || manager2.status==null}"> checked="checked"</c:if>>启用
                <input type="radio" name="status" id="status" value="0" <c:if test="${manager2!=null && manager2.status==0}"> checked="checked"</c:if>>禁用
            </td>
            <td align="right">
                本地用户<s:text name="sundyn.colon" />
            </td>
            <td>
                <div class="layui-input-inline">
                    <input type="radio" name="localuser" value="1" title="本地用户" <c:if test="${manager2==null || manager2.localuser}"> checked="checked"</c:if>>
                    <input type="radio" name="localuser" value="0" title="异地用户" <c:if test="${manager2!=null && !manager2.localuser}"> checked="checked"</c:if>>
                </div>
            </td>
        </tr>
        <tr>
            <td align="right">
                身份证号码<s:text name="sundyn.colon" />
            </td>
            <td colspan="3">
                <input name="idcard" id="idcard" value="${manager2.idcard}" class="input_comm" style="width:80%" />
            </td>
        </tr>
        <tr>
            <td align="right">
                警员<s:text name="sundyn.colon" />
            </td>
            <td>
                <input type="checkbox" name="jyflag" id="jyflag" value="1" lay-skin="switch" lay-text="是|否" <c:if test="${manager2.jyflag==1}"> checked="checked"</c:if>>
            </td>
            <td align="right">警员编号<s:text name="sundyn.colon" /></td>
            <td align="left">
                <input name="jyno" id="jyno" value="${manager2.jyno}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td align="right">用户有效期<s:text name="sundyn.colon" /></td>
            <td>
                <input type="text" class="input_comm" id="uexpired" name="uexpired" value="${manager2.uexpired}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="right">密码有效期<s:text name="sundyn.colon" /></td>
            <td>
                <input type="text" class="input_comm" id="pwdexpired" name="pwdexpired" value="${manager2.pwdexpired}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
            </td>
        </tr>
        <tr>
            <td align="right">访问时间段<s:text name="sundyn.colon" /></td>
            <td>
                <input type="text" class="input_comm" id="accesstime1" name="accesstime1" value="${manager2.accesstime1}" onClick="WdatePicker({dateFmt:'HH:mm'})" />
            </td>
            <td align="right">至<s:text name="sundyn.colon" /></td>
            <td>
                <input type="text" class="input_comm" id="accesstime2" name="accesstime2" value="${manager2.accesstime2}" onClick="WdatePicker({dateFmt:'HH:mm'})" />
            </td>
        </tr>
        <tr>
            <td align="right">绑定IP<s:text name="sundyn.colon" /></td>
            <td colspan="3">
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" class="input_comm" id="accessip" name="accessip" value="${manager2.accessip}" />
                    </div>
                    <div class="layui-form-mid layui-word-aux">多个IP使用逗号隔开,连接IP段可用~连接</div>
                </div>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.role" />
            </td>
            <td align="left" style="border-color: #e9f5fd;" colspan="3">
                <div class="layui-input-inline" style="display: none;">
                <select id="userGroupId">
                    <c:forEach items="${list}" var="power" varStatus="index">
                        <option <c:if test="${power.id==manager2.userGroupId}"> selected="selected"</c:if> value="${power.id}">${power.name}</option>
                    </c:forEach>
                </select>

                </div>
                <div class="layui-input-inline">
                    <c:forEach items="${list}" var="power" varStatus="index">
                            <input type="checkbox" style="padding-bottom:5px;" lay-skin="switch" lay-text="${power.name}|${power.name}" lay-filter="employeeInfoSet" value="${power.id}" name="managerPowers" <c:if test="${power.checked}">checked="checked"</c:if> />&nbsp;&nbsp;
                    </c:forEach>
                </div>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipType" />
            </td>
            <td align="left" style="border-color: #e9f5fd;" colspan="3">
                <div class="layui-input-inline">
                <select name="remark" id="remark" onchange="tipChange(this.value)">
                    <option value="0" <c:if test="${manager2.remark==0}"> selected="selected"</c:if>><s:text name="sundyn.user.select1" /></option>
                    <option value="1" <c:if test="${manager2.remark==1}"> selected="selected"</c:if>><s:text name="sundyn.user.select2" /></option>
                    <option value="2" <c:if test="${manager2.remark==2}"> selected="selected"</c:if>><s:text name="sundyn.user.select3" /></option>
                    <option value="3" <c:if test="${manager2.remark==3}"> selected="selected"</c:if>><s:text name="sundyn.user.select4" /></option>
                </select>
                </div>
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipMobile" />
            </td>
            <td align="left" style="border-color: #e9f5fd;" colspan="3">
                <input name="ext1" id="ext1" value="${manager2.ext1}" class="input_comm" />
            </td>
        </tr>
        <tr style="display: none;">
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name="sundyn.user.tipPc" />
            </td>
            <td align="left" style="border-color: #e9f5fd;" colspan="3">
                <input name="ext2" id="ext2" value="${manager2.ext2}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td colspan="3">
                <img src="<s:text name='sundyn.pic.ok' />" onclick="managerEdit()" class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"   onclick="closeDialog()" class="hand">
            </td>
        </tr>
    </table>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
    });

    $(document).ready(function () {
        initTree("?depttype=1", '${manager2.deptid}');
    });
</script>
</body>
</html>
