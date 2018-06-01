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
<input type="hidden" id="deptId" value="<%=request.getParameter("deptId")%>" />

<div class="layer-form">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td rowspan="11" width="30%;" align="right;">
                <div class="center_10_left kuang">
                    <img src="images/employee_head_photo.gif" id="img123"  style="width: 140px;height: 147px;"  border="0" />
                    <form id="pic" enctype="multipart/form-data" name="pic" action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
                        <input type="hidden" name="imgName" id="imgName" />
                        <input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" />
                        <img src="<s:text name='sundyn.pic.upload' />" width="60" height="25" onclick="employeeUpload()" class="hand" />
                    </form>
                </div>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" width="30%" align="right" class="ff">
                <c:if test="${employeeJobNum == 'true'}">
                    <s:text name="sundyn.column.employeeJobNum" />
                </c:if>
                <c:if test="${employeeJobNum != 'true'}">
                    <s:text name="sundyn.column.userName" />
                </c:if><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" width="70%" align="left">
                <input type="text" name="ext2" id="ext2" class="input_comm" onblur="employeeExsits()" /><span style="color: red; font-size: 12px;" id="tip"></span>						</td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" width="30%" align="right" class="ff">
                <s:text name="sundyn.column.employeeName" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" width="70%" align="left">
                <input type="text" name="Name" id="Name" class="input_comm" /></td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.post" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name=job_desc id="job_desc" class="input_comm" /></td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.sex" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="radio" name="Sex" id="Sex" value="1"
                       checked="checked" />
                <s:text name="sundyn.male" />
                <input type="radio" name="Sex" id="Sex" value="0" />
                <s:text name="sundyn.female" /></td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.employeeCardNum" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="CardNum" id="CardNum"  onblur="employeeCardNumExsits()" class="input_comm"  /><font color="#FF0000">*</font><span style="color: red; font-size: 12px;" id="tipcardnum"></span></td>
        </tr>

        <tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <c:if test="${star == 'true'}">
                    <s:text name="sundyn.employee.contact" />
                </c:if>
                <c:if test="${star != 'true'}">
                    <s:text name="sundyn.column.star" /><s:text name="sundyn.colon" />
                </c:if>						</td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="Phone" id="Phone"  class="input_comm" /></td>
        </tr>

        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showWindow" id="showWindow"  class="input_comm"  /></td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.deptname" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="showDeptName" id="showDeptName"  class="input_comm" /></td>
        </tr>
        <tr >
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                <s:text name="sundyn.column.unitName" /><s:text name="sundyn.colon" /></td>
            <td style="border-color: #e9f5fd;" align="left">
                <input type="text" name="ext1" id="ext1"  class="input_comm"  /></td>
        </tr>
        <%--<tr>
            <td style="border-color: #e9f5fd;" align="right" class="ff">
                备注：
            </td>
            <td style="border-color: #e9f5fd;" align="left">
                 <textarea rows="2" cols="22" id="remark"></textarea>
            </td>
        </tr>
     --%>
        <tr>
            <td colspan="3" align="center">
                <div class="bottom">
                    <img src="<s:text name='sundyn.pic.ok' />" onclick="employeeAdd()" class="hand" />
                    <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()" class="hand" />
                </div>
                <div style="color:red; text-align:left;"><s:text name="sundyn.employee.alertCardNum"></s:text></div>
            </td>
        </tr>
    </table>


</div>
</body>
</html>