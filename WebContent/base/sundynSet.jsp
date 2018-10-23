<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?1"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script type="text/javascript" src="lib/Sortable.min.js"></script>
    <style type="text/css">
        #dom1 span {
            margin-top: 8px;
        }
    </style>
    <script language="javascript">
        function check() {
            var patrn = /^[0-9][0-9]:[0-9][0-9]$/;
            var sam = document.getElementById("sam").value;
            var eam = document.getElementById("eam").value;
            var spm = document.getElementById("spm").value;
            var epm = document.getElementById("epm").value;
            if (!patrn.test(sam)) {
                alert("<s:text name='sundyn.softSetup.timeerror1' />");
                return false;
            }
            if (!patrn.test(eam)) {
                alert("<s:text name='sundyn.softSetup.timeerror2' />");
                return false;
            }
            if (!patrn.test(spm)) {
                alert("<s:text name='sundyn.softSetup.timeerror3' />");
                return false;
            }
            if (!patrn.test(epm)) {
                alert("<s:text name='sundyn.softSetup.timeerror4' />");
                return false;
            }
            document.getElementById("f").submit();
        }

        function checkStar() {
            if (document.getElementById('star').checked) {
                document.getElementById('est66').style.display = '';
            }
            if (!document.getElementById('star').checked) {
                document.getElementById('est66').style.display = 'none';
                document.getElementById('est6').checked = '';
            }
        }
    </script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div class="layui-main layui-form" lay-filter="formTest">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li style="display:none;"><s:text name='sundyn.softSetup.systemSetup'/></li>
            <%--<li><s:text name='sundyn.softSetup.infoSetup' /></li>--%>
            <li style="display:none;"><s:text name='sundyn.softSetup.timeSetup'/></li>
            <li class="layui-this"><s:text name='sundyn.softSetup.starSetup'/></li>
            <li style="display:none;"><s:text name='sundyn.softSetup.employeeInfoSetup'/></li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item">
                <div style="display:none"><input type="checkbox" name="camera" id="camera" <c:if
                        test="${system.camera == 'true' }"> checked="checked"</c:if>      />&nbsp;&nbsp;<b><s:text
                        name='sundyn.softSetup.camera'/></b>&nbsp;&nbsp;&nbsp;&nbsp;
                    <c><s:text name='sundyn.softSetup.cameraInfo'/></c>
                </div>
                <div style="display:none"><input type="checkbox" name="k7" id="k7" <c:if
                        test="${system.k7 == 'true' }"> checked="checked"</c:if>  />&nbsp;&nbsp;<b><s:text
                        name='sundyn.softSetup.k7'/></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text
                        name='sundyn.softSetup.k7Info'/></c>
                </div>
                <div><input type="checkbox" name="star" onclick="checkStar()" id="star" <c:if
                        test="${system.star == 'true' }"> checked="checked"</c:if>
                            lay-skin="switch"/>&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.starGrade'/></b>&nbsp;&nbsp;&nbsp;&nbsp;<c>
                    <s:text name='sundyn.softSetup.starGradeInfo'/></c>
                </div>
                <div style="display:none"><input type="checkbox" name="bind" id="bind" <c:if
                        test="${system.bind == 'true' }"> checked="checked"</c:if>
                                                 lay-skin="switch"/>&nbsp;&nbsp;<b><s:text
                        name='sundyn.softSetup.bind'/></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text
                        name='sundyn.softSetup.bindInfo'/></c>
                </div>
                <div style="display:none"><input type="checkbox" name="guide" id="guide" <c:if
                        test="${system.guide == 'true' }"> checked="checked"</c:if>
                                                 lay-skin="switch"/>&nbsp;&nbsp;<b><s:text
                        name='sundyn.softSetup.guide'/></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text
                        name='sundyn.softSetup.guideInfo'/></c>
                </div>
                <input type="hidden" name="tipLanguage" id="tipLanguage" value="${system.tipLanguage}"/>
                <div style="display: none;">
                    <div><b><s:text name='sundyn.softSetup.danweiName'/></b><input type="text" name="title" id="title"
                                                                                   class="input_comm"
                                                                                   value="${content.title}"/></div>
                    <div style="display:none;"><b><s:text name='sundyn.softSetup.m7BottomInfo'/></b><input type="text"
                                                                                                           name="buttom"
                                                                                                           id="buttom"
                                                                                                           class="input_comm"
                                                                                                           value="${content.buttom}"/>
                    </div>
                    <div style="display: none;"><b><s:text name='sundyn.softSetup.tipServerUrl'/></b><input type="text"
                                                                                                            name="requestAddress"
                                                                                                            id="requestAddress"
                                                                                                            class="input_comm"
                                                                                                            value="${content.requestAddress}"/>
                    </div>
                    <div style="margin-top:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><s:text
                            name='sundyn.softSetup.logo'/></b><input type="text" name="logo" id="logo"
                                                                     class="input_comm" value="${content.logo}"/><input
                            value="<s:text name='sundyn.softSetup.upload'/>" type="button"
                            onclick="baseUploadPic()"/><input type="button" value="清空"
                                                              onclick="$('#logo').val('');$('#picLogo').attr('src','');$('#picLogo').parent().hide();"/>
                    </div>
                    <div class="left"><b><s:text name='sundyn.softSetup.require'/></b></div>
                    <div class="left" style="display:none;"><img src="${content.logo}" id="picLogo"
                                                                 style="width: 133px;height: 37px;"/></div>
                    <div style="display: none;"><b><s:text name='sundyn.softSetup.contentDegreeStandard'/></b><input
                            type="text" name="standard" id="standard" class="input_comm" value="100"/></div>
                </div>
            </div>
            <div class="layui-tab-item">
                <div><s:text name='sundyn.softSetup.moningUpTime'/><input type="text" value="${work2.sam}" size="50"
                                                                          name="sam" id="sam" class="input_comm"/></div>
                <div style="margin-top:5px;"><s:text name='sundyn.softSetup.moningDownTime'/><input type="text"
                                                                                                    value="${work2.eam}"
                                                                                                    size="50" name="eam"
                                                                                                    id="eam"
                                                                                                    class="input_comm"/>
                </div>
                <div style="margin-top:5px;"><s:text name='sundyn.softSetup.afternoonUpTime'/><input type="text"
                                                                                                     value="${work2.spm}"
                                                                                                     size="50"
                                                                                                     name="spm" id="spm"
                                                                                                     class="input_comm"/>
                </div>
                <div style="margin-top:5px;"><s:text name='sundyn.softSetup.afternoonDownTime'/><input type="text"
                                                                                                       value="${work2.epm}"
                                                                                                       size="50"
                                                                                                       name="epm"
                                                                                                       id="epm"
                                                                                                       class="input_comm"/>
                </div>
            </div>
            <div class="layui-tab-item layui-show">
                <ul id="stars">
                    <c:forEach var="star" items="${stars}">
                        <li style="margin-bottom:5px;">
                            <b><s:text name='sundyn.softSetup.contentRate'/></b><input type="text"
                                                                                       value="${star.star100}"
                                                                                       class="input_comm" name="prate"/><b><s:text
                                name='sundyn.softSetup.up'/></b><input style="display:none;" type="text"
                                                                       value="${star.star10}" class="input_comm"
                                                                       name="pgrade"/><b style="display:none;"><s:text
                                name='sundyn.softSetup.mark'/></b><input type="text" value="${star.star}"
                                                                         class="input_comm" name="pstar"/><b><s:text
                                name='sundyn.softSetup.star'/></b> <img src="images/tp_add.gif" onclick="starAdd()"/>
                            <img src="images/tp_del.gif" onclick="starDel(this)"/>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="layui-tab-item">
                <span style="color:red;">*&nbsp;&nbsp;<s:text name="sundyn.can.write"/></span>
                <div align="left">
                    <ul id="dom1">
                        <li id="m1">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet1" value="1" id="est1"
                                   <c:if test="${employeeInfoSet.employeeName== 'true'}">checked="checked"</c:if> /><span
                                style="margin-top:8px;">&nbsp;&nbsp;<s:text name='sundyn.column.employeeName'/></span>
                        </li>
                        <li id="m2">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet2" value="2" id="est2"
                                   <c:if test="${employeeInfoSet.job_desc== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.post'/></span><br/>
                        </li>
                        <li id="m3">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet3" value="3" id="est3"
                                   <c:if test="${employeeInfoSet.employeeJobNum== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.employeeJobNum'/></span><br/>
                        </li>
                        <%--<li id="m4">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet" name="employeeInfoSet4" value="4" id="est4" <c:if test="${employeeInfoSet.employeeCardNum== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text name='sundyn.column.employeeCardNum'/></span><br/>
                        </li>--%>
                        <li id="m5">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet5" value="5" id="est5"
                                   <c:if test="${employeeInfoSet.star== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.star'/></span><br/>
                        </li>
                        <li id="m6">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet6" value="6" id="est6"
                                   <c:if test="${employeeInfoSet.phone== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;联系方式</span><br/>
                        </li>
                        <li id="m7">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet7" value="7" id="est7"
                                   <c:if test="${employeeInfoSet.windowName== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.windowName'/></span><br/>
                        </li>
                        <li id="m8">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet8" value="8" id="est8"
                                   <c:if test="${employeeInfoSet.deptname== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.deptname'/></span><br/>
                        </li>
                        <li id="m9">
                            <input type="checkbox" lay-skin="switch" lay-text="启用|禁用" lay-filter="employeeInfoSet"
                                   name="employeeInfoSet9" value="9" id="est9"
                                   <c:if test="${employeeInfoSet.unitName== 'true'}">checked="checked"</c:if> /><span>&nbsp;&nbsp;<s:text
                                name='sundyn.column.unitName'/></span><br/>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div style="clear: both;">
        <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="sundynSetSave()"
               class="layui-btn"/>
        <input type="button" value="重置" class="layui-btn layui-btn-primary" onclick="refreshTab()"/>
    </div>
</div>
<div id="dialog" style="width: 700px; display: none;"></div>
</body>
</html>
<script>
    var employeeInfoSetArr = new Array();
    layui.use(['form', 'element'], function () {
        var form = layui.form;

        form.on('switch(employeeInfoSet)', function (data) {
            return;
            //console.log(data.elem.name); //得到checkbox原始DOM对象
            //console.log(data.elem.checked); //开关是否开启，true或者false
            //console.log(data.value); //开关value值，也可以通过data.elem.value得到

            if (data.elem.checked) {
                if (employeeInfoSetArr.length >= 5) {
                    alert("最多可以选择5项");
                }

                if (!employeeInfoSetArr.indexOf(data.value) > -1)
                    employeeInfoSetArr.push(data.value);

            }
            else {
                employeeInfoSetArr.remove(data.value);
            }
            console.log("总数:" + employeeInfoSetArr.length); //得到美化后的DOM对象
            console.log("是否存在:" + employeeInfoSetArr.indexOf(data.value)); //得到美化后的DOM对象
            var name = data.elem.name;
            form.val("formTest", {
                name: true
            })
        });

        var el = document.getElementById('dom1');
        var sortable = Sortable.create(el);
    });
</script>