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
                <s:text name='sundyn.column.deptName'/>
            </td>
            <td align="left">
                <input id="deptSel" class="input_comm" type="text" readonly value="${manager2.deptname}" onclick="showDeptTree(this,null);"/>
            </td>
            <td align="right"><s:text name="manage.label.password" /></td>
            <td align="left">
                <input type="password" name="pwd" id="pwd" value="" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="manage.label.status" />
            </td>
            <td>
                <input type="radio" name="status" id="status" value="1" <c:if test="${manager2==null || manager2.status==1 || manager2.status==null}"> checked="checked"</c:if>><s:text name="main.radio.enable" />
                <input type="radio" name="status" id="status" value="0" <c:if test="${manager2!=null && manager2.status==0}"> checked="checked"</c:if>><s:text name="main.radio.disable" />
            </td>
            <td align="right">
                <s:text name="manage.label.localuser" />
            </td>
            <td>
                <div class="layui-input-inline">
                    <input type="radio" name="localuser" value="1" title="<s:text name="manage.localuser.local" />" <c:if test="${manager2==null || manager2.localuser}"> checked="checked"</c:if>>
                    <input type="radio" name="localuser" value="0" title="<s:text name="manage.localuser.notlocal" />" <c:if test="${manager2!=null && !manager2.localuser}"> checked="checked"</c:if>>
                </div>
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="manage.label.idcard" />
            </td>
            <td colspan="3">
                <input name="idcard" id="idcard" value="${manager2.idcard}" class="input_comm" style="width:80%" />
            </td>
        </tr>
        <tr>
            <td align="right">
                <s:text name="manage.label.jy" />
            </td>
            <td>
                <input type="checkbox" name="jyflag" id="jyflag" value="1" lay-skin="switch" lay-text="<s:text name="main.yes" />|<s:text name="main.no" />" <c:if test="${manager2.jyflag==1}"> checked="checked"</c:if>>
            </td>
            <td align="right"><s:text name="manage.label.jyno" /></td>
            <td align="left">
                <input name="jyno" id="jyno" value="${manager2.jyno}" class="input_comm" />
            </td>
        </tr>
        <tr>
            <td align="right"><s:text name="manage.label.accountexpired" /></td>
            <td>
                <input type="text" class="input_comm" id="uexpired" name="uexpired" value="${manager2.uexpired}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd', lang:'${locale}'})" />
            </td>
            <td align="right"><s:text name="manage.label.pwdexpired" /></td>
            <td>
                <input type="text" class="input_comm" id="pwdexpired" name="pwdexpired" value="${manager2.pwdexpired}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd', lang:'${locale}'})" />
            </td>
        </tr>
        <tr>
            <td align="right"><s:text name="manage.label.accesstime1" /></td>
            <td>
                <input type="text" class="input_comm" id="accesstime1" name="accesstime1" value="${manager2.accesstime1}" onClick="WdatePicker({dateFmt:'HH:mm', lang:'${locale}'})" />
            </td>
            <td align="right"><s:text name="manage.label.accesstime2" /></td>
            <td>
                <input type="text" class="input_comm" id="accesstime2" name="accesstime2" value="${manager2.accesstime2}" onClick="WdatePicker({dateFmt:'HH:mm', lang:'${locale}'})" />
            </td>
        </tr>
        <tr>
            <td align="right"><s:text name="manage.label.bindip" /></td>
            <td colspan="3">
                <div class="layui-form-item">
                    <div class="layui-input-inline">
                        <input type="text" class="input_comm" id="accessip" name="accessip" value="${manager2.accessip}" />
                    </div>
                    <div class="layui-form-mid layui-word-aux"><s:text name="manage.label.bindiptips" /></div>
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
                <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="managerEdit()" class="layui-btn"/>
                <input type="button" value="<s:text name='main.cancel'/>" class="layui-btn layui-btn-primary" onclick="parent.closeDialog()"/>
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


    // 验证用户不为空
    function managerCheck(){
        var name = document.getElementById("name").value;
        if(name==""){
            error("<s:text name="manage.entity.validation.name.notnull" />");
            return false;
        }
        var realname = document.getElementById("realname").value;
        if(realname==""){
            error("<s:text name="manage.entity.validation.realname.notnull" />");
            return false;
        }
        return true;
    }
    // 修改用户
    function managerEdit() {
        var check=managerCheck();
        if(!check){
            return false;
        }
        var id = document.getElementById("id").value;
        var name = document.getElementById("name").value;
        var realname = document.getElementById("realname").value;
        var remark = document.getElementById("remark").value;
        var ext1 = document.getElementById("ext1").value;
        var ext2 = document.getElementById("ext2").value;
        var userGroupId = document.getElementById("userGroupId").value;
        var powers = "";
        var managerPowers = $("input[name=managerPowers]");
        var localuser = $('input:radio[name="localuser"]:checked').val();
        var pwd = document.getElementById("pwd").value;
        var accessip = document.getElementById("accessip").value;
        var userid = document.getElementById("userid").value;
        if (id=="" && pwd == "") {
            error("<s:text name="manage.entity.validation.password.notnull" />");
            return false;
        }

        var powerLen = 0;
        for (var i=0; i<managerPowers.length; i++){
            if(managerPowers[i].checked){
                powers += (managerPowers[i].value) + ",";
                powerLen++;
            }
        }
        if(powerLen != 1){
            //error("只能选择一个角色！");
            //return false;
        }
        var deptId = getCheck();
        var deptname = getCheckName();
        var idcard = document.getElementById("idcard").value;
        var jyflag = document.getElementById("jyflag").checked?1:0;
        var status=$('input:radio[name="status"]:checked').val();
        var jyno = document.getElementById("jyno").value;
        var uexpried = document.getElementById("uexpired").value;
        var pwdexpired = document.getElementById("pwdexpired").value;
        var accesstime1 = document.getElementById("accesstime1").value;
        var accesstime2 = document.getElementById("accesstime2").value;

        dojo.xhrPost({url:"managerEdit.action", content:{id:id, name:name, realname:realname, pwd: pwd, remark:remark, ext1:ext1,
                ext2:ext2, userGroupId:userGroupId, powers:powers, dept:deptId, localuser: localuser,
                idcard: idcard, jyflag: jyflag, jyno: jyno, uexpired: uexpried, pwdexpired: pwdexpired, status: status,
                accesstime1: accesstime1, accesstime2:accesstime2, aname:(id==""||id==0)?"<s:text name="main.add" />" : "<s:text name="main.edit" />",
                accessip:accessip, userid:userid
            }, load:function (resp, ioArgs) {
                if(resp.trim()==""){
                    succ("<s:text name="main.save.succ" />", function(){
                        parent.closeDialog();
                        parent.lowerManagerQueryAjax();
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
