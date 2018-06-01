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
    <table width="100%" border="0" cellpadding="0"
           cellspacing="0" style="border-color:#FFFFFF;">
        <tr><td rowspan="11" width="30%;" align="right;">
            <img src="${m.picture}" id="img123"  style="width: 140px;height: 147px;" class="hand" onclick="closeDialog()"  border="0" />
            <form id="pic" enctype="multipart/form-data" name="pic" action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                <input type="hidden" name="imgName" id="imgName" value="${m.picture }" />
                <input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" />
                <img src="<s:text name='sundyn.pic.upload' />" width="60" height="25" onclick="employeeUpload()" class="hand" />
            </form>
        </td></tr>
        <tr>
            <td width="30%" align="right">
                <c:if test="${employeeJobNum == 'true'}">
                    <s:text name="sundyn.column.employeeJobNum" />
                </c:if>
                <c:if test="${employeeJobNum != 'true'}">
                    <s:text name="sundyn.column.userName" />
                </c:if><s:text name="sundyn.colon" />
            </td>
            <td width="70%" align="left">
                <input type="text" name="ext2" id="ext2" class="input_comm" value="${m.ext2}"  onblur="employeeExsits()" /><span style="color: red; font-size: 12px;" id="tip"></span>
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
                       <c:if test="${m.Sex=='1'}">checked="checked"  </c:if>>
                <s:text name="sundyn.male" />
                <input type="radio" name="Sex" id="Sex" value="0"
                       <c:if test="${m.Sex=='0'}">checked="checked"  </c:if>>
                <s:text name="sundyn.female" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.employeeCardNum" /><s:text name="sundyn.colon" />
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="CardNum" id="CardNum"  class="input_comm"  value="${m.CardNum}"  />
                <font color="#FF0000">*</font>						</td>
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
                <s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" />							</td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showWindow" id="showWindow"  class="input_comm" value="${m.showWindowName }" />						</td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.deptname" /><s:text name="sundyn.colon" />						</td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showDeptName" id="showDeptName"  class="input_comm" value="${m.showDeptName }"/>						</td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.unitName" /><s:text name="sundyn.colon" />							</td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="ext1" id="ext1"  class="input_comm"  value="${m.companyName }"/>						</td>
        </tr>


        <%--<tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                备注：
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                 <textarea rows="3" cols="22" id="remark">${m.remark}</textarea>
            </td>
        </tr>

    --%>
        <tr>
            <td colspan="3" align="center">
                <div class="bottom">
                    <img src="<s:text name='sundyn.pic.ok' />"   onclick="employeeEdit()"  class="hand" />
                    <img src="<s:text name='sundyn.pic.close' />" onclick="parent.closeDialog()"	 class="hand" />
                </div>
                <div style="color:red; text-align:left;"><s:text name="sundyn.employee.alertCardNum"></s:text> </div>
            </td>
        </tr>
    </table>

    <script>
        layui.use(['form', 'layedit', 'laydate'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;

        });
    </script>
</div>
</body>
</html>