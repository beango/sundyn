<%@ page import="org.json.JSONArray" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>排队历史数据查询</title>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
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
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body>
<script type="text/javascript">
    function counterQuery(){
        var hallid = $("#hallsele").val();
        var seriid = $("#seriid").val();
        if (!seriid) seriid = ''
        var status = $("#statussele").val();
        var queuetype = $("#queuetypesele").val();
        window.location.href = "queueDetailQuery.action?hallid="+hallid + "&seriid=" + seriid + "&status=" + status + "&queuetype=" + queuetype;
    }
</script>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form">
    <div class="layui-inline">
        <label class="layui-form-label" style="width:55px;">服务厅：</label>
        <div class="layui-input-inline">
            <select id="hallsele" lay-filter="hallsele">
                <option <c:if test="${''==hallid || hallid==null}"> selected="selected"</c:if> value="">全部</option>
                <c:forEach items="${hallList}" var="hall" varStatus="index">
                    <option <c:if test="${hall.id==hallid}"> selected="selected"</c:if> value="${hall.id}">${hall.hallname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label" style="width:50px;">业务：</label>
        <div class="layui-input-inline">
            <select id="seriid" lay-filter="seriid">
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label" style="width:65px;">取号类型：</label>
        <div class="layui-input-inline">
            <select id="queuetypesele" lay-filter="queuetypesele">
                <option <c:if test="${queuetype==null || ''.equals(queuetype)}"> selected="selected"</c:if> value="">全部</option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label" style="width:50px;">状态：</label>
        <div class="layui-input-inline">
            <select id="statussele" lay-filter="statussele">
                <option <c:if test="${status==null || ''.equals(status)}"> selected="selected"</c:if> value="">全部</option>
            </select>
        </div>
    </div>
    <%--<div class="layui-select-cus layui-inline">
        <label class="layui-form-label">业务：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="cname" class="scinput" type="text" value="<%=request.getParameter("countername")==null?"":request.getParameter("countername")%>" style="width:120px;" />
        </div>
    </div>--%>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" onclick="counterQuery('')" style="vertical-align: middle;cursor:pointer;"/>
        </div>
    </div>
    <div>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="text-align: center;">序号</th>
                <th style="text-align: center;">
                    服务厅
                </th>
                <th style="text-align: center;">
                    业务名称
                </th>
                <th style="text-align: center;">
                    排队号码
                </th>
                <th style="text-align: center;">办事人</th>
                <th style="text-align: center;"><s:text name='sundyn.column.employeeName'/></th>
                <th style="text-align: center;">取号／叫号时间</th>
                <th style="text-align: center;">办理／办结时间</th>
                <th style="text-align: center;">等候时长</th>
                <th style="text-align: center;">办理时长</th>
                <th style="text-align: center;">评价结果</th>
                <th style="text-align: center;">状态</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td style="text-align: center;">
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>
                                ${data.bizname}
                        </td>
                        <td>
                                ${data.queuenum}(${data.queuetypename})
                        </td>
                        <td>
                                ${data.cardname}<c:if test="${data.isagent==1}"> （代理人） </c:if>
                        </td>
                        <td>
                                ${data.staffname}<c:if test="${data.hjcountername!=null}"> （${data.hjcountername}） </c:if>
                        </td>
                        <td>
                                <label style="height:20px;"><fmt:formatDate value="${data.tickettime}" type="both" /></label><c:if test="${data.hjtime!=null}">　／　</c:if><label style="height:20px;"><fmt:formatDate value="${data.hjtime}" type="both" /></label>
                        </td>
                        <td>
                            <label style="height:20px;"><fmt:formatDate value="${data.starttime}" type="both" /></label><c:if test="${data.endtime!=null}">　／　</c:if><label style="height:20px;"><fmt:formatDate value="${data.endtime}" type="both" /></label>
                        </td>
                        <td>
                                ${data.waittimename.replace("0天00时00分","").replace("0天00时","").replace("0天","")}
                        </td>
                        <td>
                                ${data.servicetimename.replace("0天00时00分","").replace("0天00时","").replace("0天","")}
                        </td>
                        <td>
                                ${data.appriseresultname}
                        </td>
                        <td>
                                ${data.statusname}
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
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    $(function () {
        $('.tablelist tbody tr:odd').addClass('odd');

        var qs_hallid = '<%=request.getParameter("hallid")%>';

        layui.use(['form', 'element'], function() {
            var form = layui.form;
            form.on('select(hallsele)', function(data){
                console.log(data.elem); //得到select原始DOM对象
                console.log(data.value); //得到被选中的值
                console.log(data.othis); //得到美化后的DOM对象
                loadHallSerial(form, data.value);
            });

            if (qs_hallid != ''){
                loadHallSerial(form, qs_hallid);
            }

            var statusList = JSON.parse('${statusList}');
            var qs_status = '<%=request.getParameter("status")%>';
            for (var i=0; i<statusList.length; i++){
                var status = statusList[i];
                if (qs_status != null && qs_status!='' && qs_status == status.id)
                    $("#statussele").append("<option value='"+status.id+"' selected='selected'>"+status.name+"</option>");
                else
                    $("#statussele").append("<option value='"+status.id+"'>"+status.name+"</option>");
            }

            var queuetypeList = JSON.parse('${queueTypeList}');
            var qs_queuetype = '<%=request.getParameter("queuetype")%>';
            for (var i=0; i<queuetypeList.length; i++){
                var queuetype = queuetypeList[i];
                if (qs_queuetype!=null && qs_queuetype!='' && qs_queuetype == queuetype.id)
                    $("#queuetypesele").append("<option value='"+queuetype.id+"' selected='selected'>"+queuetype.name+"</option>");
                else
                    $("#queuetypesele").append("<option value='"+queuetype.id+"'>"+queuetype.name+"</option>");
            }
            form.render('select');
        });
    });

    function loadHallSerial(form, hallid) {
        var qs_seriid = '<%=request.getParameter("seriid")%>';
        $.post("getHallSerial.action?hallid=" + hallid, function(data){
            var serialList = JSON.parse(data.data);
            $("#seriid").empty();
            $("#seriid").append("<option value=''>全部</option>");
            for (var i=0; i< serialList.length; i++){
                var item = serialList[i];
                if (qs_seriid == item.id)
                    $("#seriid").append("<option value='"+item.id+"' selected='selected'>"+item.name+"</option>");
                else
                    $("#seriid").append("<option value='"+item.id+"'>"+item.name+"</option>");
            }
            form.render('select');
        });
    }
</script>
</html>
