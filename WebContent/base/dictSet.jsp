<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><s:text name='zx.title'/></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery.js"></script>
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
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div class="layui-main layui-form" lay-filter="formTest">
    <div class="layui-tab layui-tab-brief">
        <ul class="layui-tab-title">
            <li class="layui-this">参数配置</li>
            <li>接口地址</li>
            <li>短信模板</li>
            <li>安全参数</li>
            <li>审计参数</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <form class="layui-form" action="dictSave.action" method="post">
                        <c:forEach items="${dictinfos_config}" var="dictinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:120px;">${dictinfo.dictname}：</label>
                        <c:if test="${dictinfo.dictkey=='unkeydef'}">
                        <div class="layui-input-inline" style="width:400px;">
                            <c:forEach items="${KeyTypeList}" varStatus="status" var="data">
                            <input isdict="true" type="radio" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" name="${dictinfo.dictkey}" value="${data.keyNo}" dictgroup="dictinfos_config" title="${data.name}" <c:if test="${data.keyNo==dictinfo.dictvalue}"> checked="checked"</c:if>>
                            </c:forEach>
                        </div>
                        </c:if>
                        <c:if test="${dictinfo.dictkey!='unkeydef'}">
                            <div class="layui-input-inline">
                                <input isdict="true" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" type="input" name="${dictinfo.dictkey}" value="${dictinfo.dictvalue}" dictgroup="dictinfos_config" class="scinput">
                            </div>
                        </c:if>
                        <div class="layui-form-mid layui-word-aux">${dictinfo.note}</div>
                    </div>
                        </c:forEach>
                </form>
            </div>
            <div class="layui-tab-item">
                <c:forEach items="${dictinfos_inte}" var="dictinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:120px;">${dictinfo.dictname}：</label>
                        <div class="layui-input-inline">
                            <input isdict="true" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" type="input" name="${dictinfo.dictkey}" value="${dictinfo.dictvalue}" dictgroup="dictinfos_inte" class="scinput" style="width:300px;" />
                        </div>
                        <div class="layui-form-mid layui-word-aux">${dictinfo.note}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="layui-tab-item">
                <c:forEach items="${dictinfos_sms}" var="dictinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:120px;">${dictinfo.dictname}：</label>
                        <div class="layui-input-inline" style="width:600px; height:100px;">
                            <textarea isdict="true" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" type="input" name="${dictinfo.dictkey}" dictgroup="dictinfos_inte" class="scinput" style="width:600px; height:100px;">${dictinfo.dictvalue}</textarea>
                        </div>
                        <div class="layui-form-mid layui-word-aux" style="width:300px;">${dictinfo.note}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="layui-tab-item">
                <c:forEach items="${dictinfos_security}" var="dictinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:140px;">${dictinfo.dictname}：</label>
                        <div class="layui-input-inline">
                            <input isdict="true" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" type="input" name="${dictinfo.dictkey}" value="${dictinfo.dictvalue}" dictgroup="dictinfos_security" class="scinput" style="width:190px;">
                        </div>
                        <div class="layui-form-mid layui-word-aux">${dictinfo.note}</div>
                    </div>
                </c:forEach>
            </div>
            <div class="layui-tab-item">
                <c:forEach items="${dictinfos_audit}" var="dictinfo">
                    <div class="layui-form-item">
                        <label class="layui-form-label" style="width:140px;">${dictinfo.dictname}：</label>
                        <div class="layui-input-inline">
                            <input isdict="true" dictid="${dictinfo.id}" dictname="${dictinfo.dictname}" type="input" name="${dictinfo.dictkey}" value="${dictinfo.dictvalue}" dictgroup="dictinfos_audit" class="scinput" style="width:190px;">
                        </div>
                        <div class="layui-form-mid layui-word-aux">${dictinfo.note}</div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>

    <div style="clear: both;">
        <input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="save()"
               class="layui-btn"/>
        <input type="button" value="<s:text name='main.reset'/>" class="layui-btn layui-btn-primary" onclick="refreshTab()"/>
    </div>
</div>
<div id="dialog" style="width: 700px; display: none;"></div>
</body>
</html>
<script>
    var employeeInfoSetArr = new Array();
        layui.use(['form', 'element'], function () {
    });

    function save(){
        var inputs = $(".layui-form").find("input,textarea");
        var postdata = [];
        for (var i=0; i<inputs.length; i++){
            if ($(inputs[i]).attr("isdict")){
                var inputtype = $(inputs[i]).attr("type"),
                    inputname = $(inputs[i]).attr("dictid"),
                    dictname= $(inputs[i]).attr("dictname"),
                    dictkey = $(inputs[i]).attr("name"),
                    dictgroup= $(inputs[i]).attr("dictgroup"),
                    inputval = $(inputs[i]).val();
                if (inputtype == "radio") {
                    var radiochecked = $(inputs[i])[0].checked;
                    if (radiochecked)
                        postdata.push({dictkey: inputname, dictvalue: inputval, dictname:dictname, dictkeyname: dictkey, dictgroup:dictgroup});
                }
                else if (inputtype == "input"){
                    postdata.push({dictkey: inputname, dictvalue: inputval, dictname:dictname, dictkeyname: dictkey, dictgroup:dictgroup})
                }
            }
        }
        dojo.xhrPost({url:"dictSave.action",content:{postdata:JSON.stringify(postdata)},load:function (resp, ioArgs){
            var r = JSON.parse(resp);
            if (r.succ){
                succ("保存成功！");
            }
            else
                error(r.msg);

            }});
    }
</script>
