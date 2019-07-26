<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>

<body style="min-height:500px;min-width:600px;">
<form class="layui-form" action="managerratepost.action" method="post">
    <input type="hidden" name="managerid" value="${param.get("managerid")}"/>
    <table class="tablelist">
        <thead>
        <tr>
            <th>产品名</th>
            <th>现价</th>
            <th>当前折后价</th>
            <th>折扣</th>
        </tr>
        </thead>
        <c:forEach items="${list}" var="entity" varStatus="s">
            <tr>
                <td>
                        ${entity.productname}
                </td>
                <td>
                        ${entity.price}
                </td>
                <td>
                        ${entity.price * (entity.rate==null?1:entity.rate)}
                </td>
                <td>
                    <input type="hidden" name="productid" value="${entity.product_id}"/>
                    <input type="hidden" name="id" value="${entity.id}"/>
                    <input type="text" name="rate" class="layui-input" value="${entity.rate}" style="width:100px;"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div class="layui-form-mid layui-word-aux">
        * 折扣：填写小数，如0.95，不填为1.0即原价；<br>
        * 当前折后价 = 现价 * 折扣， 修改折扣后的折后价请重新打开该页面查看
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"></label>
        <div class="layui-input-inline">
            <input type="button" class="layui-btn" value="<s:text name="main.save" />" onclick="save()" style=""/>
        </div>

    </div>
</form>

<script type="text/javascript">
    layui.use("layer")
    function save(){
        $.ajax({
            url: "managerratepost.action",
            data: $("form").serialize(),
            success: function(res2){
                console.log(res2)
                if (res2.succ)
                    succ("成功", function () {
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                else
                    error("失败");
            }
        });
    }
</script>
</body>
</html>