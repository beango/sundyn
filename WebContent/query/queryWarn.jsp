<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>VIP客户信息管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <link type="text/css" rel="stylesheet" href="js/jquery.dropdown.min.css" />
    <script type="text/javascript" src="js/jquery.dropdown.min.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function proxyQuery(){
        var type = $("#type").val();
        var nofityname = $("#nofityname").val();
        window.location.href = "warnQuery.action?type="+type+"&nofityname="+nofityname;
    }

    function proxyAdd(id){
        var action = id==undefined?"增加":"编辑";
        new dialog().iframe("proxyAdd.action?id=" + id, {title: action+"代理人", resize:false, w:"460px", h:"340px"});
    }

    function proxyDel(id){
        layer.confirm('真的删除么', function(index){
            $.post("proxyDel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 800
                    }, function(){
                        refreshTab();
                    });
                }
                else{
                    layer.msg(resp, {
                        icon: 2,
                        time: 1200
                    }, function(){
                    });
                }
            });
            layer.close(index);
        });
    }
    $(function(){
        $('.dropdown-mul-1').dropdown({
            limitCount: 40,
            multipleMode: 'label',
            choice: function () {
                console.log(arguments,this);
            }
        });
    });
</script>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>

<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;">类型：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <select name="type" id="type" lay-verify="" lay-search>
                <option value=""<%=request.getParameter("type")==null?"":" selected"%>>全部</option>
              <option value="1"<%=request.getParameter("type")!=null&&request.getParameter("type").equals("1")?" selected":""%>>等待超时预警</option>
              <option value="3"<%=request.getParameter("type")!=null&&request.getParameter("type").equals("3")?" selected":""%>>差评预警</option>
                <option value="2"<%=request.getParameter("type")!=null&&request.getParameter("type").equals("2")?" selected":""%>>办理超时预警</option>
                <option value="4"<%=request.getParameter("type")!=null&&request.getParameter("type").equals("4")?" selected":""%>>违规代理人预警</option>
            </select>
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:80px;">相关人：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="nofityname" value="${nofityname}" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="proxyQuery('')"/>
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号</th>
                <th>
                    消息类型
                </th>
                <th>
                    业务流水号
                </th>
                <th>
                    时间
                </th>
                <th>
                    相关人
                </th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <fmt:setLocale value="zh_CN"/>
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.title}
                        </td>
                        <td>
                                ${data.describe}
                        </td>
                        <td>
                            <fmt:formatDate value="${data.ctime}" type="both"/><br>
                        </td>
                        <td>
                                ${data.nofityname}
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</html>
