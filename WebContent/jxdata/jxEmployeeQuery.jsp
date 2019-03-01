<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

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
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script language="javascript" type="text/javascript" src="lib/util/TableSorterV2.js?1"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
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
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.query.selectEmployee'/></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <input id="deptSel" class="scinput" type="text" readonly value="<%=request.getParameter("deptname")==null||request.getParameter("deptname").equals("")?"全部":request.getParameter("deptname")%>" style="width:180px;" onclick="showDeptTree(this,null);"/>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;">月份：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input type="text" class="layui-input" id="servicedate" name="servicedate" value="${servicedate}" onClick="WdatePicker({dateFmt:'yyyy-MM'})"/>
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;">状态：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline" style="width:120px">
            <select name="ypfj" id="ypfj">
                <option value="" <c:if test="${ypfj==null}">selected="selected"</c:if>>全部</option>
                <option value="1" <c:if test="${ypfj=='1'}">selected="selected"</c:if>>否决</option>
                <option value="0" <c:if test="${ypfj=='0'}">selected="selected"</c:if>>未否决</option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="queryDeal(false, '<%=request.getParameter("sort")%>')"/>
        </div>
        <div style="fmargin-left:10px;" class="layui-input-inline"><s:if test="list.size==0"><a href="javascript:noData('this');"></s:if><s:else>
            <a href="#" onclick="queryDeal(true, '<%=request.getParameter("sort")%>')"></s:else>
                <img src="<s:text name='sundyn.total.pic.excel'/>" /></a>
        </div>
    </div>

    <div>
        <table class="tablelist" lay-filter="tbl" id="table123">
            <thead>
            <tr>
                <th>序号</th>
                <th>
                    姓名
                </th>
                <th>
                    所属部门
                </th>
                <th>
                    月份
                </th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="servicetime">个人本月有效服务时间<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="servicetime">个人本月有效服务人次<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="servicetime">大厅本月平均服务时间<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="fwzl">服务质量<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="employeefwxn">个人服务效能<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="deptfwxn">相对本单位服务效能<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="fwpjl">评价率<img src="../images/nor.png" /></th>
                <th background="images/03_02_07.jpg" class="px13_1" sort="ykq">月度考勤<img src="../images/nor.png" />
                </th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="qzby">群众表扬<img src="../images/nor.png" />
                </th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="rcxc">日常巡查<img src="../images/nor.png" />
                </th>
                <th>
                    一票否决
                </th>
                <th background="images/03_02_07.jpg"
                    class="px13_1" sort="totalscore">总得分<img src="../images/nor.png" />
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
                                ${data.ename}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>
                                ${data.servicedate}
                        </td>
                        <td>
                            ${data.servicetimename}
                        </td>
                        <td>
                                ${data.servicecount}
                        </td>
                        <td>
                            ${data.deptservicetimeavgname}
                        </td>
                        <td>
                                <fmt:formatNumber type="number" value="${data.fwzl}" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.employeefwxn}" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.deptfwxn}" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.fwpjl * 100.0}" maxFractionDigits="2"/>%
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.ykq}" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.qzby}" maxFractionDigits="2"/>
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.rcxc}" maxFractionDigits="2"/>
                        </td>
                        <td>
                                ${data.fjdesc}
                        </td>
                        <td>
                            <fmt:formatNumber type="number" value="${data.totalscore}" maxFractionDigits="2"/>
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

    $(document).ready(function () {
        initTree("?depttype=3", '<%=request.getParameter("deptId")%>');
        new TableSorter("table123", tableSortCallback, '<%=request.getParameter("sort")%>');
    });

    function tableSortCallback(sort){
        queryDeal(false, sort);
    }

    // 窗口汇总
    function queryDeal(isExport, sort) {
        if(sort=='null')
            sort = null;
        var servicedate = document.getElementById("servicedate").value;
        var ypfj = document.getElementById("ypfj").value;
        var deptId = getCheck();
        var deptname = getCheckName();
        window.location.href = "?servicedate=" + servicedate + "&ypfj=" + ypfj + "&deptId=" + deptId
            + "&export=" + (isExport==undefined?false:isExport) + "&deptname=" + deptname + "&sort=" + (sort==undefined?"":sort);
    }

    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</html>
