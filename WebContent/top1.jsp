<%@ page pageEncoding="UTF-8" %>
<%@page import="com.sundyn.util.SundynSet" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="powers" value="${pageContext.session.getAttribute('powers')}" />

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>top</title>
    <link rel="stylesheet" href="lib/layui/css/layui.2.4.5.css" media="all">
    <link rel="stylesheet" href="css/top.css" media="all">
</head>

<body class="site-home" id="LAY_home" style="background-color: #eee;" data-date="7-10">
<div class="layui-header header header-index" summer="">
    <div class="layui-main">
        <a class="logo" href="/">
            123
        </a>
        <ul class="layui-nav">
            <li class="layui-nav-item ">
                <a href="/doc/">文档<!-- <span class="layui-badge-dot"></span> --></a>
            </li>
            <li class="layui-nav-item ">
                <a href="/demo/">示例<!-- <span class="layui-badge-dot"></span> --></a>
            </li>




            <li class="layui-nav-item layui-hide-xs">
                <a href="//fly.layui.com/" target="_blank">社区</a>
            </li>

            <li class="layui-nav-item">
                <a href="javascript:;"><!--<span class="layui-badge-dot" style="margin: -5px 0 0 -15px;"></span>-->周边<span class="layui-nav-more"></span></a>
                <dl class="layui-nav-child layui-anim layui-anim-upbit">
                    <dd lay-unselect="">
                        <a href="//fly.layui.com/extend/" target="_blank">扩展组件</a>
                    </dd>
                    <dd lay-unselect="">
                        <a href="//fly.layui.com/store/" target="_blank">模板市场 <span class="layui-badge-dot"></span></a>
                        <hr>
                    </dd>

                    <dd class="layui-hide-sm layui-show-xs" lay-unselect="">
                        <a href="//fly.layui.com/" target="_blank">社区交流</a>
                        <hr>
                    </dd>
                    <dd lay-unselect=""><a href="/admin/" target="_blank">后台模板</a></dd>
                    <dd lay-unselect=""><a href="/layim/" target="_blank">即时聊天</a><hr></dd>

                    <dd lay-unselect=""><a href="/alone.html" target="_blank" lay-unselect="">独立组件</a></dd>
                    <dd lay-unselect=""><a href="https://fly.layui.com/jie/24209/" target="_blank">Axure 组件</a></dd>
                </dl>
            </li>

            <li class="layui-nav-item layui-hide-xs" lay-unselect="">
                <a href="/admin/">后台模板<span class="layui-badge-dot" style="margin-top: -5px;"></span></a>
            </li>
            <span class="layui-nav-bar" style="left: 344px; top: 55px; width: 0px; opacity: 0;"></span></ul>
    </div>
</div>
<script type="text/javascript" src="lib/layui/layui.js"></script>
<script>
    window.global = {
        pageType: 'index'
        ,preview: function(){
            var preview = document.getElementById('LAY_preview');
            return preview ? preview.innerHTML : '';
        }()
    };
    layui.config({
        base: 'lib/'
        ,version: '1560414887305'
    }).use('global');
</script>
</body>
</html>
