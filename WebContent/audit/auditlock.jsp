<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<jsp:useBean id="now" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>绩效数据配置</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
</head>
<body>
<script type="text/javascript">
    function query(){
        window.location.href = "jxDataQuery.action?key="+ $("#key").val() + "&servicedate=" + $("#servicedate").val();
    }

    function unlock(id){
        lconfirm("确认要解锁吗?", function(index){
            lsubmit("auditunlock.action?id=" + id, null,function(resp){
                if(resp.succ){
                    succ("解锁成功");
                    refreshTab();
                }
                else{
                    err(resp.msg);
                }
            });
        })
    }
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
    <%--<div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:100px;">姓名/工号：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="key" class="scinput" type="text" value="<%=request.getParameter("key")==null?"":request.getParameter("key")%>" style="width:150px;" />
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;">月份：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="servicedate" name="servicedate" value="<%=request.getParameter("servicedate")==null?"":request.getParameter("servicedate")%>" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="query('')"/>
        </div>
    </div>--%>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号 </th>
                <th>
                    锁定对象
                </th>
                <th>
                    所属部门
                </th>
                <th>
                    状态
                </th>
                <th>
                    锁定类型
                </th>
                <th>
                    锁定时间
                </th>
                <th>
                    解锁时间
                </th>
                <th>
                    解锁人
                </th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.realname} / ${data.name}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>
                            <c:if test="${data.status==0 || data.unlocktime < now}">未锁定</c:if>
                            <c:if test="${data.status==1 && data.unlocktime > now}"><button class="layui-btn layui-btn-danger layui-btn-xs">已锁定</button>
                            </c:if>
                        </td>
                        <td>${data.locktype}</td>
                        <td>
                                ${data.locktime}
                        </td>
                        <td>
                                ${data.unlocktime}
                        </td>
                        <td>
                            <c:if test="${data.status==0 || data.unlocktime < now}">
                                ${data.unlockuser!=null?data.unlockusername:"自动解锁"}
                            </c:if>

                        </td>
                        <td>
                            <c:if test="${data.status==1 && data.unlocktime > now}">
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();unlock('${data.id}');"><i class="layui-icon layui-icon-edit"></i>解锁</a>
                            </c:if>
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
