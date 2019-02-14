<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table cellspacing="0" cellpadding="0" border="0" class="tablelist" lay-skin="line">
    <tbody>
    <c:forEach items="${data}" var="d">
    <tr>
        <td>
            <div class="layui-table-cell laytable-cell-1-0-1">${d.appriesname}</div>
        </td>
        <td>
            <div class="layui-table-cell laytable-cell-1-0-2">${d.apprisetime}</div>
        </td>
        <td>
            <div class="layui-table-cell laytable-cell-1-0-3">${d.staffname}（${d.countername}）</div>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>