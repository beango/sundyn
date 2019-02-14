<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
<link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript" src="js/dialog.js"></script>
<script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
<script type="text/javascript" src="lib/layui/layui.js"></script>
<script type="text/javascript" src="js/myAjax.js"></script>
</head>

<body>
<div class="layer-form">
    <table width="99%" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
        <tr><td rowspan="11" width="20%;" style="text-align:center;">
            <img src="${m.picture}" id="img123" style="width: 250px;height: 280px;" border="0" />
            <div class="layui-upload">
                <input type="hidden" name="imgName" id="imgName" value="${m.picture }" />
                <button type="button" class="layui-btn" id="test1">选择图片</button>
                <%--<div class="layui-upload-list">
                    <img style="width: 140px;height: 147px;" class="layui-upload-img" name="img123" id="img123" value="${m.picture }">
                    <p id="demoText"></p>
                </div>--%>
            </div>
        </td></tr>
        <%--<tr>
            <td width="20%" align="right">
                <c:if test="${employeeJobNum == 'true'}">
                    <s:text name="sundyn.column.employeeJobNum" />
                </c:if>
                <c:if test="${employeeJobNum != 'true'}">
                    <s:text name="sundyn.column.userName" />
                </c:if><s:text name="sundyn.colon" />
            </td>
            <td align="left">
                <input type="text" name="ext2" id="ext2" class="input_comm" value="${m.ext2}"  onblur="employeeExsits()" /><span style="color: red; font-size: 12px;" id="tip"></span>
            </td>
        </tr>--%>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                工号<s:text name="sundyn.colon" />
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="CardNum" id="CardNum"  class="input_comm"  value="${m.CardNum}" /><font color="#FF0000">*</font>
                <div style="color:red; text-align:left;"><s:text name="sundyn.employee.alertCardNum"></s:text> </div>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" width="30%" align="right" class="ff">
                <s:text name="sundyn.column.employeeName" /><s:text name="sundyn.colon" />
            </td>
            <td style="border-color: #e9f5fd;" width="70%" align="left">
                <input type="hidden" name="employeeId" id="employeeId" value="${m.Id}" />
                <input type="text" name="Name" id="Name" class="input_comm" value="${m.Name}" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.post" /><s:text name="sundyn.colon" />
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name=job_desc id="job_desc" class="input_comm" value="${m.job_desc}" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.sex" /><s:text name="sundyn.colon" />
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="radio" name="Sex" id="Sex" value="1"
                       <c:if test="${m.Sex=='1'}">checked="checked" </c:if>>
                <s:text name="sundyn.male" />
                <input type="radio" name="Sex" id="Sex" value="0"
                       <c:if test="${m.Sex=='0'}">checked="checked" </c:if>>
                <s:text name="sundyn.female" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <c:if test="${star == 'true'}">
                    <s:text name="sundyn.employee.contact" />
                </c:if>
                <c:if test="${star != 'true'}">
                    <s:text name="sundyn.column.star" /><s:text name="sundyn.colon" />
                </c:if>
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="Phone" id="Phone"  class="input_comm" value="${m.Phone}" />
            </td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showWindow" id="showWindow"  class="input_comm" value="${m.showWindowName }" /></td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.deptname" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showDeptName" id="showDeptName"  class="input_comm" value="${m.showDeptName }"/></td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.unitName" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="ext1" id="ext1"  class="input_comm"  value="${m.companyName }"/></td>
        </tr>
        <tr>
            <td colspan="3" align="center">
                <div class="bottom">
                    <img src="<s:text name='sundyn.pic.ok' />"   onclick="employeeEdit()"  class="hand" />
                    <img src="<s:text name='sundyn.pic.close' />" onclick="parent.closeDialog()"	 class="hand" />
                </div>
            </td>
        </tr>
    </table>

    <script>
        layui.use(['form', 'layedit', 'laydate', 'upload'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate
                ,upload = layui.upload;

            //普通图片上传
            var uploadInst = upload.render({
                elem: '#test1'
                ,url: 'employeeUpload.action'
                ,before: function(obj){
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        //$('#imgName').attr('src', result); //图片链接（base64）
                    });
                }
                ,done: function(res){
                    if(res.rst == "success" && res.path && res.path.length>0){
                        $('#imgName').val(res.path[0]); //图片链接（base64）
                        $('#img123').attr('src', res.path[0]);
                        return layer.msg('上传成功！');
                    }
                    alert(res.msg);
                }
                ,error: function(){
                    layer.msg('上传失败！');
                }
            });
        });
    </script>
</div>
</body>
</html>