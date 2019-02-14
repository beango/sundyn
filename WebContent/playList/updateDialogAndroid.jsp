<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <input type="hidden" name="Version" id="Version"   value="${config.Version }"/>
    <input type="hidden" name="Welcometime" id="Welcometime"  value="${config.Welcometime }" />
    <input type="hidden" name="Approvertime" id="Approvertime"  value="${config.Approvertime }"  />
    <input type="hidden" name="Shutdownhh" id="Shutdownhh"  value="${config.Shutdownhh }"  />
    <input type="hidden" name="Shutdownmm" id="Shutdownmm"  value="${config.Shutdownmm }"  />
    <input type="hidden" name="Boothh" id="Boothh"  value="${config.Boothh }"  />
    <input type="hidden" name="Bootmm" id="Bootmm"  value="${config.Bootmm }"  />
    <input type="hidden" name="ShowEmployeePage" id="ShowEmployeePage"  value="${config.ShowEmployeePage }"  />
    <input type="hidden" name="Type" id="Type"  value="${config.Type }" />
    <table width="90%" height="100" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" align="right"><s:text name='sundyn.playList.serverIp' />:</td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="text" name="IP" id="IP" onblur="isIP(this.value)" class="input_comm" value="${config.IP }"/>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right"><s:text name='sundyn.playList.serverPort' />:</td>
            <td align="left" style="border-color: #e9f5fd;" >
                <input type="text" name="Port" id="Port" onblur="isNumber(this.value)"  class="input_comm" value="${config.Port }" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right"><s:text name="sundyn.system.playlist.useWireless"></s:text>:</td>
            <td align="left" style="border-color: #e9f5fd;"><label>
                <input type="radio" name="Type" title="<s:text name="sundyn.conncet.yes"></s:text>"  value="1"   <c:if test="${config.Type == '1' }"> checked="checked" </c:if> />
                <input type="radio" name="Type"  title="<s:text name="sundyn.connect.no"></s:text>" value="0"   <c:if test="${config.Type == '0' }"> checked="checked" </c:if> />
            </label></td>
        </tr>
        <tr><td style="border-color: #e9f5fd;" align="center" colspan="2">
            <span style="font-size: 12px; color: red;" id="tip">(<s:text name="sundyn.alert.updata1" />)</span>
        </td></tr>
        <tr>
            <td style="border-color: #e9f5fd;" colspan="2" align="center">
                <span style="font-size: 12px; color: red;" id="tip">(<s:text name="sundyn.alert.updata2" />)</span>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <form id="temp" enctype="multipart/form-data"
                      action="playListUpdateDeal.action" method="post">
                    <input type="hidden" name="imgName" id="imgName"  />
                    <input type="hidden" name="playListId" id="playListId" value="${playListId}" />
                    <input type="file" name="img" id="img" onblur="getFileName()" style="width: 200px;"  />
                </form>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <a href="#"><img src="<s:text name='sundyn.pic.save' />"  onclick="playListConfigSaveAndroid2()" class="hand" /> </a>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#"><img src="<s:text name='sudnyn.playList.pic.update' />"
                                 onclick="playListUpdateDeal3()" class="hand" /> </a>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                <a href="#"><img src="<s:text name='sundyn.pic.close' />"
                                 onclick="closeDialog()" class="hand" /> </a>
            </td>
        </tr>
    </table>

</div>
</body>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
        var form = layui.form;

        form.on('switch(playIdSet)', function(data){
            setvalue();
        });
    });
</script>
</html>