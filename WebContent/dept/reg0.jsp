<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="center_04_right_01 kuang" align="center" style="width:500px;padding-top:0px;">
    <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border:0;">
        <tr>
            <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.column.windowName"/><s:text
                    name="sundyn.colon"/></td>
            <td align="left">${dept.name}</td>
        </tr>
        <tr>
            <td align="right"><s:text name="sundyn.guide.deviceInfo"/></td>
            <td align="left">${dept.remark}</td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <div>
                    <div class="button left"
                         onclick="deptEditDialog('<s:text name="sundyn.dept.modifyWindow"/>','${dept.fatherId}')">
                        <s:text name="sundyn.dept.modifyWindow"/></div>
                    <div style="margin-left: 5px;" class="button left"
                         onclick="counterCfgDialog('窗口参数配置','${dept.name}','${dept.fatherId}','${dept.id}')">窗口参数配置
                    </div>
                    <%--<div class="button left" onclick="bindWeburlDialog('<s:text name="sundyn.weburl.bind"/>')"
                         style="margin-left: 4px;"><s:text name="sundyn.weburl.bind"/></div>--%>
                    <div class="button left" onclick="del()" style="margin-left: 4px;"><s:text name="sundyn.del"/></div>
                </div>
            </td>
        </tr>
    </table>
</div>