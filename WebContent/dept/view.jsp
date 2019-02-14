<%@ page import="java.util.Date" %>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/style.css" type="text/css" />
    <title><s:text name='sundyn.title'/></title>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/ddtree.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?<%=new Date().getTime()%>"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
</head>
<body>
<script type="text/javascript">
    function chooseMenus() {
        var menus = document.getElementById("CmenuNames");
        var retVal = new Array();

        for (var i = 0; i < menus.options.length; i++) {
            retVal[i] = menus.options[i].value;
        }
        return retVal;
    }

    function checkForm(ac) {
        var menus = chooseMenus();
        $.ajax({
            url: ac + "&menus=" + menus,
            type: 'GET',
            dataType: 'json',
            timeout: 20000,
            success: function (html) {
                dialogAjaxDone(html);
            }
        });
    }

    function moveMenus() {
        var e1 = document.getElementById("menuNames");
        var e2 = document.getElementById("CmenuNames");

        for (var i = 0; i < e1.options.length; i++) {

            if (e1.options[i].selected) {
                var e = e1.options[i];
                e2.options.add(new Option(e.text, e.value));
                e1.remove(i);
                i--;
            }
        }
    }

    function removeMenus() {
        var e1 = document.getElementById("menuNames");
        var e2 = document.getElementById("CmenuNames");
        for (var i = 0; i < e2.options.length; i++) {
            if (e2.options[i].selected) {
                var e = e2.options[i];
                e1.options.add(new Option(e.text, e.value));
                e2.remove(i);
                i--;
            }
        }
    }
</script>
<style type="text/css">
    div#rMenu{
        position: fixed;
        visibility:hidden;
        top:0;
        background-color: #fff;
        text-align: left;
        border: 1px solid #c9caca;
        box-shadow: 0 0 2px #c9caca;
        padding: 2px 0;
        z-index: 999;
    }

    div#rMenu ul li{
        margin: 1px 0;
        padding: 5px;
        cursor: pointer;
        list-style: none;
        height: 20px;
        background-color: #fff;
    }
    div#rMenu ul li:hover{
        background: #ddd;
    }
</style>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div id="man_zone">
    <div class="center_04">
        <div class="center_04_left">
            <div class="dtree kuang" id="tree" style="text-align: left;">
                <script type="text/javascript">
                    d = new dTree('d');
                    <c:forEach items="${list}" var="dept">
                    d.add(${dept.id}, ${dept.fatherId}, '${dept.name}', 'javascript:regId(${dept.id})');
                    </c:forEach>
                    document.write(d);
                </script>
            </div>
        </div>
        <input type="hidden" id="deptId"/>
        <div class="center_04_right" id="deptView">
            <div class="center_04_right_01 kuang">
                <s:text name="sundyn.dept.info"/>
            </div>
        </div>
    </div>
</div>
<div id="dialog" style="width: 600px; display: none;">
</div>
</body>
</html>
